package ua.ddovgal.trackerKunBot.command

import ua.ddovgal.trackerKunBot.TrackerKunBot
import ua.ddovgal.trackerKunBot.service.worker.DatabaseConnector

abstract class BaseCommand : Command {
    lateinit var trackerKun: TrackerKunBot
    lateinit var dbConnector: DatabaseConnector
}