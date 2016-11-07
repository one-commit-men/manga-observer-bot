package ua.ddovgal.trackerKunBot

interface TrackerKunBot {
    fun sendSimpleMessage(text: String, chatId: Long)
}