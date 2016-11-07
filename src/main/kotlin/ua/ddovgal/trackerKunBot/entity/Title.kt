package ua.ddovgal.trackerKunBot.entity

import ua.ddovgal.trackerKunBot.source.SourceManager
import java.util.*

data class Title(val name: String, val url: String) {

    var lastCheckedChapterName: String = ""
    var lastCheckedChapterUrl: String = ""
    var lastCheckedChapterReleaseDate: Date = Date()
    var subscribersCount: Long = 0
    var asVariantUsingCount: Long = 1

    val source: Source by lazy { SourceManager.getSourceByTitleUrl(url) }

    //no param constructor with just empty implementation of Source
    constructor() : this("", "")

    fun checkLastChapterName() = source.checkLastChapterName(url)
    fun checkLastChapterUrl() = source.checkLastChapterUrl(url)
    fun checkLastChapterReleaseDate() = source.checkLastChapterReleaseDate(url)
}