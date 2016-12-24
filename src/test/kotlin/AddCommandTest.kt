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
import ua.ddovgal.trackerKunBot.command.impl.reserved.AddCommand
import ua.ddovgal.trackerKunBot.entity.Subscriber
import ua.ddovgal.trackerKunBot.service.worker.DatabaseConnector
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@RunWith(MockitoJUnitRunner::class)
class AddCommandTest {

    @Mock lateinit var dbConnector: DatabaseConnector
    @Mock lateinit var trackerKun: TrackerKunBot
    @Mock(answer = Answers.RETURNS_DEEP_STUBS) lateinit var update: Update

    lateinit var input: CommandInputData
    lateinit var command: AddCommand

    val user = Subscriber(chatId = 22)

    @Before
    fun config() {
        Mockito.`when`(update.message.chatId).thenReturn(22)
        Mockito.`when`(update.message.text).thenReturn("/add")
        Mockito.`when`(trackerKun.sendSimpleMessage(Mockito.anyString(), Mockito.eq(user.chatId)))
                .then { println("Sending message") }
        Mockito.`when`(dbConnector.getSubscriber(user.chatId)).thenReturn(user)
        Mockito.`when`(dbConnector.updateSubscribersState(user.chatId, SubscriberState.WAITING_FOR_ADD_STRING))
                .thenAnswer { user.state = SubscriberState.WAITING_FOR_ADD_STRING; null }

        input = CommandInputData(update)
        command = AddCommand(input)

        input.dbConn = dbConnector
        command.dbConnector = dbConnector
        command.trackerKun = trackerKun
    }

    @Test
    fun testSuitability() {
        val actual = AddCommand.empty.getIfSuitable(input)
        assertNotNull(actual)
    }

    @Test
    fun testExec() {
        assertEquals(user.state, SubscriberState.WAITING_FOR_ANYTHING)
        command.exec()
        assertEquals(user.state, SubscriberState.WAITING_FOR_ADD_STRING)
    }
}