package ua.ddovgal.trackerKunBot.command

import org.telegram.telegrambots.api.objects.Update
import ua.ddovgal.trackerKunBot.command.impl.FindTitlesCommand
import ua.ddovgal.trackerKunBot.command.impl.reserved.AddCommand
import ua.ddovgal.trackerKunBot.command.impl.reserved.CancelCommand
import ua.ddovgal.trackerKunBot.command.impl.reserved.DeleteCommand
import ua.ddovgal.trackerKunBot.command.impl.reserved.SwitchCommand
import ua.ddovgal.trackerKunBot.command.impl.selecting.SelectOnAddCommand
import ua.ddovgal.trackerKunBot.command.impl.selecting.SelectOnDeleteCommand


object CommandFactory {

    fun getSuitableInstance(update: Update): Command? {
        val commandInputData = CommandInputData(update)
        val possibleCommands = mutableListOf<Command>()

        //just for CancelCommand's return@loop
        run loop@ {
            allCommandsTogether.forEach {
                val commandCandidate = it.getIfSuitable(commandInputData)
                commandCandidate?.let {
                    possibleCommands.add(it)
                    //CancelCommand have HIGH priority
                    //CancelCommand is first in allCommandsTogether
                    if (it is CancelCommand) return@loop
                }
            }
        }

        when (possibleCommands.size) {
            0 -> return null
            1 -> return possibleCommands[0]
            else -> {
                val message = "There are several variants of command possible. But must be only one"
                throw RuntimeException(message)
            }
        }
    }

    private val reservedCommands = listOf<ReservedCommand>(
            CancelCommand.empty,
            AddCommand.empty,
            DeleteCommand.empty,
            SwitchCommand.empty
    )

    // contains empty variants of commands
    private val selectingCommands = listOf<SelectingCommand>(
            SelectOnAddCommand.empty,
            SelectOnDeleteCommand.empty
    )

    // contains empty variants of commands
    private val undefinedCommands = listOf<Command>(
            FindTitlesCommand.empty
    )

    private val allCommandsTogether = reservedCommands + selectingCommands + undefinedCommands
}