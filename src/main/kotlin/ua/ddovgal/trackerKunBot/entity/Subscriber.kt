package ua.ddovgal.trackerKunBot.entity

import ua.ddovgal.trackerKunBot.command.SubscriberState

data class Subscriber(val chatId: Long = 0,
                      val fName: String = "",
                      val sName: String = "",
                      var subscriptionsActiveStatus: Boolean = true,
                      var state: SubscriberState = SubscriberState.WAITING_FOR_ANYTHING,
                      var subscriptionCount: Int = 0) {
}