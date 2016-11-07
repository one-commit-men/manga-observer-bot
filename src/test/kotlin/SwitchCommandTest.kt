import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import ua.ddovgal.trackerKunBot.TrackerKunBot
import ua.ddovgal.trackerKunBot.command.CommandInputData
import ua.ddovgal.trackerKunBot.command.impl.reserved.SwitchCommand
import ua.ddovgal.trackerKunBot.entity.Subscriber
import ua.ddovgal.trackerKunBot.service.worker.DatabaseConnector
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SwitchCommandTest {

    @Rule @JvmField val mockitoRule: MockitoRule? = MockitoJUnit.rule()
    @Mock lateinit var dbConnector: DatabaseConnector
    @Mock lateinit var trackerKun: TrackerKunBot
    @Mock lateinit var input: CommandInputData
    val user = Subscriber(chatId = 22)

    @Test
    fun testIt() {
        Mockito.`when`(input.chatIdFromMessage).thenReturn(22)
        Mockito.`when`(dbConnector.changeSubscriptionStateOfSubscriber(user.chatId)).thenAnswer { user.subscriptionsActiveStatus = !user.subscriptionsActiveStatus; user.subscriptionsActiveStatus }
        val switch = SwitchCommand(input)
        switch.dbConnector = dbConnector
        switch.trackerKun = trackerKun
        assertTrue(user.subscriptionsActiveStatus)
        switch.exec()
        assertFalse(user.subscriptionsActiveStatus)
    }
}