package net.craftoriya.packetuxui.bukkit.commands

import dev.jorel.commandapi.kotlindsl.commandAPICommand
import net.craftoriya.packetuxui.bukkit.commands.subcommands.PacketUxUiDebugCommand

object PacketUxUiCommand {

    init {
        println("Should register")
        commandAPICommand("packetuxui") {
            println("Registering")
            withAliases("puxui")
//            withPermission("packetuxui.command.packetuxui")

            withSubcommands(PacketUxUiDebugCommand)
        }
    }

}