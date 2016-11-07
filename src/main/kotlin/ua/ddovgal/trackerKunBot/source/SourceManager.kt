package ua.ddovgal.trackerKunBot.source

import ua.ddovgal.trackerKunBot.entity.Source
import ua.ddovgal.trackerKunBot.entity.Title

object SourceManager {

    private val sources = emptyList<Source>()

    fun searchForTitle(name: String): List<Title> = emptyList()

    fun getSourceByTitleUrl(url: String): Source = sources.first { url.contains(it.url) }
}