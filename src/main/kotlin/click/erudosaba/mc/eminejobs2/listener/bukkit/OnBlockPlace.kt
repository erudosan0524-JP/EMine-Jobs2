package click.erudosaba.mc.eminejobs2.listener.bukkit

import click.erudosaba.mc.eminejobs2.Main
import click.erudosaba.mc.eminejobs2.event.BuildEvent
import click.erudosaba.mc.eminejobs2.jobs.JobPlayer
import click.erudosaba.mc.eminejobs2.util.Blocks
import click.erudosaba.mc.eminejobs2.util.SideBar
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerJoinEvent

class OnBlockPlace(val plugin : Main) : Listener {

    @EventHandler
    fun onBlockPlace(e: BlockPlaceEvent) {
        val player = e.player
        val block = e.block

        if(Blocks.BuildBlocks.contains(block.type)) {
            val event = BuildEvent(JobPlayer(player,plugin),block)
            plugin.server.pluginManager.callEvent(event)
        }
    }
}