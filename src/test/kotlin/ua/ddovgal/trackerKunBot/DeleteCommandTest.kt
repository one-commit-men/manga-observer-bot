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

@RunWith(MockitoJUnitRunner::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class DeleteCommandTest {

    private val trackerKun = TrackerKunBot
    private val databaseConnector = DatabaseConnector
    @Mock(answer = Answers.RETURNS_DEEP_STUBS) val update = Update()

    private val RANDOM_NEGATIVE_INVALID_ID = -22L

    @Before
    fun config() {
        Mockito.`when`(update.message.chatId).thenReturn(RANDOM_NEGATIVE_INVALID_ID)
        Mockito.`when`(update.message.text).thenReturn("/delete")
    }

    @Test
    fun test1DeleteIntent() {
        var subscriber = databaseConnector.getSubscriber(RANDOM_NEGATIVE_INVALID_ID)
        assertEquals(SubscriberState.WAITING_FOR_ANYTHING, subscriber!!.state)

        assertEquals(1, databaseConnector.getSubscriptionsOfSubscriber(RANDOM_NEGATIVE_INVALID_ID).count())
        trackerKun.onUpdateReceived(update)

        subscriber = databaseConnector.getSubscriber(RANDOM_NEGATIVE_INVALID_ID)
        assertEquals(SubscriberState.WAITING_FOR_REMOVE_SELECTION, subscriber!!.state)
    }

    @Test
    fun test2DeleteSelectionNone() {
        Mockito.`when`(update.message.text).thenReturn("/0")

        trackerKun.onUpdateReceived(update)

        assertEquals(1, databaseConnector.getSubscriptionsOfSubscriber(RANDOM_NEGATIVE_INVALID_ID).count())

        val subscriber = databaseConnector.getSubscriber(RANDOM_NEGATIVE_INVALID_ID)
        assertEquals(SubscriberState.WAITING_FOR_ANYTHING, subscriber!!.state)
    }

    @Test
    fun test3DeleteSelection() {
        Mockito.`when`(update.message.text).thenReturn("/delete")
        trackerKun.onUpdateReceived(update)

        Mockito.`when`(update.message.text).thenReturn("/1")
        trackerKun.onUpdateReceived(update)

        assertEquals(0, databaseConnector.getSubscriptionsOfSubscriber(RANDOM_NEGATIVE_INVALID_ID).count())

        val subscriber = databaseConnector.getSubscriber(RANDOM_NEGATIVE_INVALID_ID)
        assertEquals(SubscriberState.WAITING_FOR_ANYTHING, subscriber!!.state)
    }

    @Test
    fun test4DeleteIntentWhenNoSubscriptions() {
        Mockito.`when`(update.message.text).thenReturn("/delete")

        trackerKun.onUpdateReceived(update)

        val subscriber = databaseConnector.getSubscriber(RANDOM_NEGATIVE_INVALID_ID)
        assertEquals(SubscriberState.WAITING_FOR_ANYTHING, subscriber!!.state)
    }
}