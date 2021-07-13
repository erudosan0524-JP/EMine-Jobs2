package click.erudosaba.mc.eminejobs2.skill.skills

import click.erudosaba.mc.eminejobs2.Main
import click.erudosaba.mc.eminejobs2.event.SkillUseEvent
import click.erudosaba.mc.eminejobs2.jobs.JobPlayer
import click.erudosaba.mc.eminejobs2.jobs.Jobs
import click.erudosaba.mc.eminejobs2.skill.Skill
import click.erudosaba.mc.eminejobs2.skill.SkillProvider
import click.erudosaba.mc.eminejobs2.skill.SkillStatus
import click.erudosaba.mc.eminejobs2.util.Blocks
import click.erudosaba.mc.eminejobs2.util.Items
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.Potion
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class DiggerSkills(val plugin: Main) : Listener, SkillProvider() {

    @EventHandler
    fun onMove(e: PlayerMoveEvent) {
        val player = e.player
        val jp = JobPlayer(player, plugin)

        if (block(jp)) return

        when (jp.selectedSkill) {
            Skill.DIGHASTE1 -> { //ここからDIGHASTE1
                if (Items.shovels.contains(player.inventory.itemInMainHand.type)) {
                    val targetBlock = player.getTargetBlock(null, 5).type
                    if (Blocks.dirts.contains(targetBlock)) {
                        player.addPotionEffect(PotionEffect(PotionEffectType.FAST_DIGGING, 20 * 2, 1, true))
                    }
                }
            }
            Skill.DIGHASTE2 -> { //ここからDIGHASTE2
                if (Items.shovels.contains(player.inventory.itemInMainHand.type)) {
                    val targetBlock = player.getTargetBlock(null, 5).type
                    if (Blocks.dirts.contains(targetBlock)) {
                        player.addPotionEffect(PotionEffect(PotionEffectType.FAST_DIGGING, 20 * 2, 2, true))
                    }

                }
            }
            Skill.DIGHASTE3 -> { //ここからDIGHASTE3
                if (Items.shovels.contains(player.inventory.itemInMainHand.type)) {
                    val targetBlock = player.getTargetBlock(null, 5).type
                    if (Blocks.dirts.contains(targetBlock)) {
                        player.addPotionEffect(PotionEffect(PotionEffectType.FAST_DIGGING, 20 * 2, 2, true))
                    }
                }
            }
        }

    }

    @EventHandler
    fun onBlockBroken(e: BlockBreakEvent) {
        val player = e.player
        val jp = JobPlayer(player, plugin)
        val block = e.block
        val tool = player.inventory.itemInMainHand

        if (block(jp)) {
            return
        }

        if(jp.selectedSkill != Skill.DIGALL) return

        //スニーク時無効
        if (player.isSneaking) return

        //持っているアイテムがショベルかどうか
        if (!Items.shovels.contains(tool.type)) return

        //壊すブロックが土系かどうか
        if (!Blocks.dirts.contains(block.type)) return


        //掘り開始
        val count = mineRecursively(block, tool)

        tool.durability = (tool.durability + count).toShort()

        if (tool.type.maxDurability < tool.durability) {
            player.inventory.remove(tool)
        }
    }

    private fun mineRecursively(block: Block, tool: ItemStack, cnt: Int = 20): Int {
        if (cnt < 0) return 0
        val type = block.type.toString()
        var count = 0
        block.breakNaturally(tool)

        for (x in -1..1) {
            for (y in -1..1) {
                for (z in -1..1) {
                    if (x == 0 && y == 0 && z == 0) break

                    block.getRelative(x, y, z).let {
                        if (type == it.type.toString() && !(block.x == it.x && block.y == it.y && block.z == it.z)) {
                            count += mineRecursively(it, tool, cnt - 1)
                        }
                    }
                }
            }
        }
        return count + 1
    }
}