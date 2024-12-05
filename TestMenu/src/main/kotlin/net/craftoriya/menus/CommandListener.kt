package net.craftoriya.menus

import net.craftoriya.packetuxui.service.MenuService
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.plugin.java.JavaPlugin
import org.incendo.cloud.execution.ExecutionCoordinator
import org.incendo.cloud.paper.PaperCommandManager
import org.incendo.cloud.paper.util.sender.PaperSimpleSenderMapper
import org.incendo.cloud.paper.util.sender.PlayerSource
import org.incendo.cloud.paper.util.sender.Source
import org.joml.Vector3d
import org.joml.Vector3i


class CommandListener(
    private val plugin: JavaPlugin,
    private val service: MenuService,
) {
    private val static3x9 = Static3x9()
    private val dynamic4x9 = Dynamic4x9(service)
    private val buttonDetector = ButtonDetector()
    private val cooldownTest = CooldownTest()
    private val allInOne = AllInOne(service)


    init {
        val coordinator = ExecutionCoordinator.simpleCoordinator<Source>()
        val commandManager: PaperCommandManager<Source> = PaperCommandManager
            .builder(PaperSimpleSenderMapper.simpleSenderMapper())
            .executionCoordinator(coordinator)
            .buildOnEnable(plugin)

        val openMenuBuilder = commandManager.commandBuilder("open_menu")
            .senderType(PlayerSource::class.java)

        val subcommands = listOf(
            "static_3x9" to Pair(
                static3x9.menu, """
                
                Simple static menu 3x9.
                Entire menu under cooldown of 5 seconds
                And freeze time of 1 second.
                
            """.trimIndent()
            ),

            "dynamic_4x9" to Pair(
                dynamic4x9.menu, """
                
                Dynamic menu 4x9.
                buttons become a stone after a click
                Also some items are blinking
            """.trimIndent()
            ),

            "button_detector" to Pair(
                buttonDetector.menu, """
                
                Static crafting table menu
                This menu detects button click types.
                freeze time 300ms
                """.trimIndent()
            ),
            "cooldown_test" to Pair(
                cooldownTest.menu, """
                
                
                 Static anvil menu.
                 Demonstrates how cooldown works.
                
                """.trimIndent()
            ),
            "all_in_one" to Pair(
                allInOne.menu, """
                
                This is a comprehensive all-in-one menu.
                
                """.trimIndent()
            )
        )

        for ((subcommand, menuData) in subcommands) {
            val (menu, description) = menuData

            commandManager.command(
                openMenuBuilder.literal(subcommand)
                    .handler { context ->
                        service.openMenu(context.sender().source(), menu)
                    }
            )

            commandManager.command(
                openMenuBuilder.literal(subcommand)
                    .literal("desc")
                    .handler { context ->
                        val player = context.sender().source()
                        player.sendMessage(description)
                    }
            )
        }
    }
}

fun Location.Vector3d(): Vector3d {
    return Vector3d(this.x, this.y, this.z)
}

fun Location.Vector3i(): Vector3i = Vector3i(this.x.toInt(), this.y.toInt(), this.z.toInt())

fun Vector3i.toBlock(world: World): Block = world.getBlockAt(this.x, this.y, this.z)

fun Vector3i.toVector3d(): Vector3d {
    return Vector3d(this.x.toDouble(), this.y.toDouble(), this.z.toDouble())
}

fun Vector3d.plus(value: Double): Vector3d = Vector3d(this.x + value, this.y + value, this.z + value)
