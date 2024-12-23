package net.craftoriya.packetuxui

import com.github.retrooper.packetevents.PacketEvents
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder
import net.craftoriya.packetuxui.bukkit.PacketUxUiBukkitApi
import net.craftoriya.packetuxui.bukkit.commands.PacketUxUiCommand
import net.craftoriya.packetuxui.bukkit.controller.BukkitListener
import net.craftoriya.packetuxui.bukkit.user.BukkitUser
import net.craftoriya.packetuxui.user.UserManager
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

internal val plugin get() = JavaPlugin.getPlugin(PacketUxUiPlugin::class.java)

class PacketUxUiPlugin : JavaPlugin() {

    override fun onLoad() {
        UserManager.userCreator = { uuid -> BukkitUser(uuid) }
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this))
    }

    override fun onEnable() {
        PacketUxUiBukkitApi.init()

        // Listeners
        Bukkit.getPluginManager().registerEvents(BukkitListener, plugin)

        // Commands
        PacketUxUiCommand
    }

    override fun onDisable() {
        PacketUxUiBukkitApi.terminate()
    }
}