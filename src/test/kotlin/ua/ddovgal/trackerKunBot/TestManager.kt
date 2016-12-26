package ua.ddovgal.trackerKunBot

import com.j256.ormlite.jdbc.JdbcConnectionSource
import com.j256.ormlite.table.TableUtils
import junit.framework.TestCase
import org.junit.AfterClass
import org.junit.runner.RunWith
import org.junit.runners.Suite
import ua.ddovgal.trackerKunBot.entity.Subscriber
import ua.ddovgal.trackerKunBot.entity.Subscription
import ua.ddovgal.trackerKunBot.entity.Title
import ua.ddovgal.trackerKunBot.entity.Variant
import java.net.URI

@RunWith(Suite::class)
@Suite.SuiteClasses(*arrayOf(AddCommandTest::class, DeleteCommandTest::class, CancelCommandTest::class))
class TestManager : TestCase() {
    companion object {
        @JvmStatic
        @AfterClass
        fun onAnyResultCompleteDBCleanup() {
            val dbUri = URI(DATABASE_URL)

            val username = dbUri.userInfo.split(":")[0]
            val password = dbUri.userInfo.split(":")[1]
            val dbUrl = "jdbc:postgresql://" + dbUri.host + ':' + dbUri.port + dbUri.path + "?sslmode=require"

            Class.forName(DATABASE_DRIVER)

            val connection = JdbcConnectionSource(dbUrl, username, password)

            TableUtils.dropTable<Subscriber, Long>(connection, Subscriber::class.java, false)
            TableUtils.dropTable<Title, String>(connection, Title::class.java, false)
            TableUtils.dropTable<Subscription, Any?>(connection, Subscription::class.java, false)
            TableUtils.dropTable<Variant, Any?>(connection, Variant::class.java, false)
        }
    }
}