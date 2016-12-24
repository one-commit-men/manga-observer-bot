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
import ua.ddovgal.trackerKunBot.command.impl.reserved.CancelCommand
import ua.ddovgal.trackerKunBot.entity.Subscriber
import ua.ddovgal.trackerKunBot.service.worker.DatabaseConnector
import kotlin.test.*

@RunWith(MockitoJUnitRunner::class)
class CancelCommandTest {

    @Mock lateinit var dbConnector: DatabaseConnector
    @Mock lateinit var trackerKun: TrackerKunBot
    @Mock(answer = Answers.RETURNS_DEEP_STUBS) lateinit var update: Update

    lateinit var input: CommandInputData
    lateinit var command: CancelCommand

    var userHaveVariants = true
    val user = Subscriber(chatId = 11, state = SubscriberState.WAITING_FOR_ADD_SELECTION)

    @Before
    fun config() {
        Mockito.`when`(update.message.chatId).thenReturn(11)
        Mockito.`when`(update.message.text).thenReturn("/cancel")
        Mockito.`when`(trackerKun.sendSimpleMessage(Mockito.anyString(), Mockito.eq(user.chatId)))
                .then { println("Sending message") }
        Mockito.`when`(dbConnector.getSubscriber(user.chatId)).thenReturn(user)
        Mockito.`when`(dbConnector.updateSubscribersState(user.chatId, SubscriberState.WAITING_FOR_ANYTHING))
                .thenAnswer { user.state = SubscriberState.WAITING_FOR_ANYTHING; null }
        Mockito.`when`(dbConnector.removeVariantsOfSubscriber(user.chatId))
                .thenAnswer { userHaveVariants = false; null }

        input = CommandInputData(update)
        command = CancelCommand(input)

        input.dbConn = dbConnector
        command.dbConnector = dbConnector
        command.trackerKun = trackerKun
    }

    @Test
    fun testSuitability() {
        val actual = CancelCommand.empty.getIfSuitable(input)
        assertNotNull(actual)
        assertTrue(actual is CancelCommand)
    }

    @Test
    fun testExecOnAddingState() {
        assertTrue(userHaveVariants)
        assertEquals(user.state, SubscriberState.WAITING_FOR_ADD_SELECTION)
        command.exec()
        assertEquals(user.state, SubscriberState.WAITING_FOR_ANYTHING)
        assertFalse(userHaveVariants)
    }

    @Test
    fun testExec() {
        assertNotEquals(user.state, SubscriberState.WAITING_FOR_ANYTHING)
        command.exec()
        assertEquals(user.state, SubscriberState.WAITING_FOR_ANYTHING)
    }
}