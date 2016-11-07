package ua.ddovgal.trackerKunBot.entity

import ua.ddovgal.trackerKunBot.source.Language
import java.util.*

abstract class Source {

    val url: String
    val name: String
    val language: Language

    constructor() : this("", "", Language.ENGLISH)
    constructor(url: String, name: String, language: Language) {
        this.url = url
        this.name = name
        this.language = language
    }

    /**
     * @return Last chapter release url, or 'null' if it not exist
     * @throws [TryCaughtException] if there is no chapters
     */
    abstract fun checkLastChapterUrl(titleUrl: String): String?

    /**
     * @return Last chapter name, or 'null' if it not exist
     * @throws [NoSuchElementException] if there is no chapters
     */
    abstract fun checkLastChapterName(titleUrl: String): String?

    /**
     * @return Last chapter release date, or 'null' if it not exist
     * @throws [NoSuchElementException] if there is no chapters
     */
    abstract fun checkLastChapterReleaseDate(titleUrl: String): Date?

    abstract fun searchForTitle(name: String): List<Title>
}