package click.erudosaba.mc.eminejobs2.event

import click.erudosaba.mc.eminejobs2.jobs.JobPlayer
import org.bukkit.block.Block
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class BuildEvent(val player : JobPlayer, val placedBlock : Block) : Event(), Cancellable {
    var cancel = false

    override fun getHandlers(): HandlerList {
        return HANDLERS
    }

    override fun isCancelled(): Boolean {
        return cancel
    }

    override fun setCancelled(p0: Boolean) {
        cancel = p0
    }

    companion object {
        private val HANDLERS = HandlerList()

        @JvmStatic
        fun getHandlerList() : HandlerList {
            return HANDLERS
        }
    }
}