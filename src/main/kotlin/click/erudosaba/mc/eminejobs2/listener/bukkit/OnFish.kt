package click.erudosaba.mc.eminejobs2.listener.bukkit

import click.erudosaba.mc.eminejobs2.Main
import click.erudosaba.mc.eminejobs2.event.FishEvent
import click.erudosaba.mc.eminejobs2.jobs.JobPlayer
import org.bukkit.entity.Item
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerFishEvent


class OnFish(val plugin : Main) : Listener {

    @EventHandler
    fun onFish(e: PlayerFishEvent) {
        val player = e.player
        val item = if(e.caught is Item) e.caught as Item else return

        val event = FishEvent(JobPlayer(player),item.itemStack)
        plugin.server.pluginManager.callEvent(event)
    }
}