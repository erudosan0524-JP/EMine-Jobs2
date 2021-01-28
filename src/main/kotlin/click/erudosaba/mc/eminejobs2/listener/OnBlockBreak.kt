package click.erudosaba.mc.eminejobs2.listener

import click.erudosaba.mc.eminejobs2.Main
import click.erudosaba.mc.eminejobs2.event.DirtBreakEvent
import click.erudosaba.mc.eminejobs2.event.StoneBreakEvent
import click.erudosaba.mc.eminejobs2.event.WoodBreakEvent
import click.erudosaba.mc.eminejobs2.jobs.JobPlayer
import click.erudosaba.mc.eminejobs2.util.Blocks
import click.erudosaba.mc.eminejobs2.util.Items
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class OnBlockBreak(val plugin : Main) : Listener {

    @EventHandler
    fun onBlockBreak(e: BlockBreakEvent) {
        val player = e.player
        val brokenBlock = e.block

        val itemMainhand = player.inventory.itemInMainHand.type
        val itemOffhand = player.inventory.itemInOffHand.type

        if (Items.pickaxes.contains(itemMainhand) || Items.pickaxes.contains(itemOffhand)) {
            if (Blocks.stones.contains(brokenBlock.type)) {
                val event = StoneBreakEvent(player,brokenBlock)
                plugin.server.pluginManager.callEvent(event)
            }
        }

        if (Items.axes.contains(itemMainhand) || Items.axes.contains(itemOffhand)) {
            if (Blocks.woods.contains(brokenBlock.type)) {
                val event = WoodBreakEvent(player,brokenBlock)
                plugin.server.pluginManager.callEvent(event)
            }
        }

        if (Items.shovels.contains(itemMainhand) || Items.shovels.contains(itemOffhand)) {
            if (Blocks.dirts.contains(brokenBlock.type)) {
                val event = DirtBreakEvent(player,brokenBlock)
                plugin.server.pluginManager.callEvent(event)
            }
        }

    }

}