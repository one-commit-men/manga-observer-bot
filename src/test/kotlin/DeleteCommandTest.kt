import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Answers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.runners.MockitoJUnitRunner
import org.telegram.telegrambots.api.objects.Update
import ua.ddovgal.trackerKunBot.TrackerKunBot
import ua.ddovgal.trackerKunBot.command.CommandInputData
import ua.ddovgal.trackerKunBot.command.SubscriberState
import ua.ddovgal.trackerKunBot.command.impl.reserved.DeleteCommand
import ua.ddovgal.trackerKunBot.command.impl.reserved.SwitchCommand
import ua.ddovgal.trackerKunBot.entity.Source
import ua.ddovgal.trackerKunBot.entity.Subscriber
import ua.ddovgal.trackerKunBot.entity.Title
import ua.ddovgal.trackerKunBot.service.worker.DatabaseConnector
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@RunWith(MockitoJUnitRunner::class)
class DeleteCommandTest {

    @Mock lateinit var dbConnector: DatabaseConnector
    @Mock lateinit var trackerKun: TrackerKunBot
    @Mock lateinit var source: Source
    @Mock(answer = Answers.RETURNS_DEEP_STUBS) lateinit var title: Title
    @Mock(answer = Answers.RETURNS_DEEP_STUBS) lateinit var update: Update

    lateinit var input: CommandInputData
    lateinit var command: DeleteCommand

    val user = Subscriber(chatId = 74)

    @Before
    fun config() {
        Mockito.`when`(update.message.chatId).thenReturn(74)
        Mockito.`when`(update.message.text).thenReturn("/delete")
        Mockito.`when`(trackerKun.sendSimpleMessage(Mockito.anyString(), Mockito.eq(user.chatId)))
                .then { println("Sending message") }
        Mockito.`when`(dbConnector.getSubscriber(user.chatId)).thenReturn(user)
        Mockito.`when`(dbConnector.getSubscriptionsOfSubscriber(user.chatId)).thenReturn(listOf(title))
        Mockito.`when`(dbConnector.updateSubscribersState(user.chatId, SubscriberState.WAITING_FOR_REMOVE_SELECTION))
                .thenAnswer { user.state = SubscriberState.WAITING_FOR_REMOVE_SELECTION; null }

        input = CommandInputData(update)
        command = DeleteCommand(input)

        title.source = source
        input.dbConn = dbConnector
        command.dbConnector = dbConnector
        command.trackerKun = trackerKun
    }

    @Test
    fun testSuitability() {
        val actual = DeleteCommand.empty.getIfSuitable(input)
        assertNotNull(actual)
        assertTrue(actual is DeleteCommand)
    }

    @Test
    fun testExec() {
        assertEquals(user.state, SubscriberState.WAITING_FOR_ANYTHING)
        command.exec()
        assertEquals(user.state, SubscriberState.WAITING_FOR_REMOVE_SELECTION)
    }

    @Test
    fun testExecIfZeroSubscriptions() {
        Mockito.`when`(dbConnector.getSubscriptionsOfSubscriber(user.chatId)).thenReturn(emptyList())
        assertEquals(user.state, SubscriberState.WAITING_FOR_ANYTHING)
        command.exec()
        assertEquals(user.state, SubscriberState.WAITING_FOR_ANYTHING)
    }

    @Test
    fun testAnotherCommand() {
        Mockito.`when`(update.message.text).thenReturn("/<another>")
        val actual = SwitchCommand.empty.getIfSuitable(input)
        assertNull(actual)
    }
}