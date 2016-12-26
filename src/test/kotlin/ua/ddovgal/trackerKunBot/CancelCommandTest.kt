package ua.ddovgal.trackerKunBot

import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.mockito.Answers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.runners.MockitoJUnitRunner
import org.telegram.telegrambots.api.objects.Update
import ua.ddovgal.trackerKunBot.command.SubscriberState
import ua.ddovgal.trackerKunBot.service.worker.DatabaseConnector
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

@RunWith(MockitoJUnitRunner::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class CancelCommandTest {

    private val trackerKun = TrackerKunBot
    private val databaseConnector = DatabaseConnector
    @Mock(answer = Answers.RETURNS_DEEP_STUBS) val update = Update()

    private val RANDOM_NEGATIVE_INVALID_ID = -22L

    @Before
    fun config() {
        Mockito.`when`(update.message.chatId).thenReturn(RANDOM_NEGATIVE_INVALID_ID)

        Mockito.`when`(update.message.text).thenReturn("/add")
        trackerKun.onUpdateReceived(update)

        Mockito.`when`(update.message.text).thenReturn("Berserk")
        trackerKun.onUpdateReceived(update)

        Mockito.`when`(update.message.text).thenReturn("/cancel")
    }

    @Test
    fun test1CancelOnAddingState() {
        assertNotNull(databaseConnector.getSpecificVariantOfSubscriber(RANDOM_NEGATIVE_INVALID_ID, 1))

        var subscriber = databaseConnector.getSubscriber(RANDOM_NEGATIVE_INVALID_ID)
        assertEquals(SubscriberState.WAITING_FOR_ADD_SELECTION, subscriber!!.state)

        trackerKun.onUpdateReceived(update)

        assertFailsWith(NoSuchElementException::class) {
            databaseConnector.getSpecificVariantOfSubscriber(RANDOM_NEGATIVE_INVALID_ID, 1)
        }

        subscriber = databaseConnector.getSubscriber(RANDOM_NEGATIVE_INVALID_ID)
        assertEquals(SubscriberState.WAITING_FOR_ANYTHING, subscriber!!.state)
    }

    @Test
    fun testCancel() {
        trackerKun.onUpdateReceived(update)

        val subscriber = databaseConnector.getSubscriber(RANDOM_NEGATIVE_INVALID_ID)
        assertEquals(SubscriberState.WAITING_FOR_ANYTHING, subscriber!!.state)
    }
}