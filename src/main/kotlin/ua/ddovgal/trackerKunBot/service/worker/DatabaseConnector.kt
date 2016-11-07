package ua.ddovgal.trackerKunBot.service.worker

import ua.ddovgal.trackerKunBot.command.SubscriberState
import ua.ddovgal.trackerKunBot.entity.Subscriber
import ua.ddovgal.trackerKunBot.entity.Title

interface DatabaseConnector {

    fun getSubscriber(chatId: Long): Subscriber?

    fun saveSubscriber(subscriber: Subscriber)

    fun updateSubscribersState(chatId: Long, newState: SubscriberState)

    fun getSubscribersOfTitle(title: Title): List<Subscriber>

    fun updateTitle(title: Title)

    fun getAllPermanentTitles(): List<Title>

    fun subscribe(title: Title, chatId: Long)

    fun unsubscribe(title: Title, chatId: Long)

    fun getSpecificSubscriptionOfSubscriber(chatId: Long, position: Long): Title

    fun getSubscriptionsOfSubscriber(chatId: Long): List<Title>

    fun getSpecificVariantOfSubscriber(chatId: Long, position: Long): Title

    fun putVariantsForSubscriber(chatId: Long, variants: List<Title>)

    fun removeVariantsOfSubscriber(chatId: Long)

    fun changeSubscriptionStateOfSubscriber(chatId: Long): Boolean
}