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
import ua.ddovgal.trackerKunBot.command.impl.reserved.SwitchCommand
import ua.ddovgal.trackerKunBot.entity.Subscriber
import ua.ddovgal.trackerKunBot.service.worker.DatabaseConnector
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@RunWith(MockitoJUnitRunner::class)
class SwitchCommandTest {

    @Mock lateinit var dbConnector: DatabaseConnector
    @Mock lateinit var trackerKun: TrackerKunBot
    @Mock(answer = Answers.RETURNS_DEEP_STUBS) lateinit var update: Update

    lateinit var input: CommandInputData
    lateinit var command: SwitchCommand

    val user = Subscriber(chatId = 44)

    @Before
    fun config() {
        Mockito.`when`(update.message.chatId).thenReturn(44)
        Mockito.`when`(update.message.text).thenReturn("/switch")
        Mockito.`when`(trackerKun.sendSimpleMessage(Mockito.anyString(), Mockito.eq(user.chatId)))
                .then { println("Sending message") }
        Mockito.`when`(dbConnector.getSubscriber(user.chatId)).thenReturn(user)
        Mockito.`when`(dbConnector.changeSubscriptionStateOfSubscriber(user.chatId))
                .thenAnswer {
                    user.subscriptionsActiveStatus = !user.subscriptionsActiveStatus
                    user.subscriptionsActiveStatus
                }

        input = CommandInputData(update)
        command = SwitchCommand(input)

        input.dbConn = dbConnector
        command.dbConnector = dbConnector
        command.trackerKun = trackerKun
    }

    @Test
    fun testSuitability() {
        val actual = SwitchCommand.empty.getIfSuitable(input)
        assertNotNull(actual)
        assertTrue(actual is SwitchCommand)
    }

    @Test
    fun testExec() {
        println("--------------------------So here you can see 'git describe' value: ${System.getProperty("describe")}")
        assertTrue(user.subscriptionsActiveStatus)
        command.exec()
        assertFalse(user.subscriptionsActiveStatus)
    }

    @Test
    fun testAnotherCommand() {
        Mockito.`when`(update.message.text).thenReturn("/<another>")
        val actual = SwitchCommand.empty.getIfSuitable(input)
        assertNull(actual)
    }
}