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
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@RunWith(MockitoJUnitRunner::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class AddCommandTest {

    private val trackerKun = TrackerKunBot
    private val databaseConnector = DatabaseConnector
    @Mock(answer = Answers.RETURNS_DEEP_STUBS) val update = Update()

    private val RANDOM_NEGATIVE_INVALID_ID = -22L

    @Before
    fun config() {
        Mockito.`when`(update.message.chatId).thenReturn(RANDOM_NEGATIVE_INVALID_ID)
        Mockito.`when`(update.message.chat.firstName).thenReturn("John")
        Mockito.`when`(update.message.chat.lastName).thenReturn("Doe")
        Mockito.`when`(update.message.text).thenReturn("/add")
    }

    @Test
    fun test1FirstInteract() {
        assertNull(databaseConnector.getSubscriber(RANDOM_NEGATIVE_INVALID_ID))

        trackerKun.onUpdateReceived(update)
        val subscriber = databaseConnector.getSubscriber(RANDOM_NEGATIVE_INVALID_ID)

        assertNotNull(subscriber)
        assertEquals(SubscriberState.WAITING_FOR_ADD_STRING, subscriber!!.state)
    }

    @Test
    fun test2AddSearch() {
        Mockito.`when`(update.message.text).thenReturn("Berserk")

        assertEquals(0, databaseConnector.getSubscriptionsOfSubscriber(RANDOM_NEGATIVE_INVALID_ID).count())
        trackerKun.onUpdateReceived(update)

        val subscriber = databaseConnector.getSubscriber(RANDOM_NEGATIVE_INVALID_ID)
        assertEquals(SubscriberState.WAITING_FOR_ADD_SELECTION, subscriber!!.state)
    }

    @Test
    fun test3AddSelection() {
        Mockito.`when`(update.message.text).thenReturn("/1")

        trackerKun.onUpdateReceived(update)
        assertEquals(1, databaseConnector.getSubscriptionsOfSubscriber(RANDOM_NEGATIVE_INVALID_ID).count())

        val subscriber = databaseConnector.getSubscriber(RANDOM_NEGATIVE_INVALID_ID)
        assertEquals(SubscriberState.WAITING_FOR_ANYTHING, subscriber!!.state)
    }

    @Test
    fun test4AddSelectionNone() {
        assertEquals(1, databaseConnector.getSubscriptionsOfSubscriber(RANDOM_NEGATIVE_INVALID_ID).count())

        Mockito.`when`(update.message.text).thenReturn("/add")
        trackerKun.onUpdateReceived(update)

        Mockito.`when`(update.message.text).thenReturn("Berserk")
        trackerKun.onUpdateReceived(update)

        Mockito.`when`(update.message.text).thenReturn("/0")
        trackerKun.onUpdateReceived(update)

        assertEquals(1, databaseConnector.getSubscriptionsOfSubscriber(RANDOM_NEGATIVE_INVALID_ID).count())

        val subscriber = databaseConnector.getSubscriber(RANDOM_NEGATIVE_INVALID_ID)
        assertEquals(SubscriberState.WAITING_FOR_ANYTHING, subscriber!!.state)
    }
}