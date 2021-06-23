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
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class WoodCutterSkills(plugin: Main) {

    init {
        plugin.server.pluginManager.registerEvents(WoodHaste1(plugin), plugin)
        plugin.server.pluginManager.registerEvents(WoodHaste2(plugin), plugin)
        plugin.server.pluginManager.registerEvents(WoodHaste3(plugin), plugin)
        plugin.server.pluginManager.registerEvents(CutAll(plugin), plugin)
    }

    class WoodHaste1(val plg: Main) : SkillProvider(plg, Jobs.WOODCUTTER), Listener {

        @EventHandler
        fun onInteract(e: PlayerInteractEvent) {
            if (e.action == Action.RIGHT_CLICK_BLOCK || e.action == Action.RIGHT_CLICK_AIR) {
                val jp = JobPlayer(plugin = plg, player = e.player)
                if (activateBlock(jp, plg.skillManager, Skill.WOODHASTE1)) return

                val option = plg.skillManager.getSkillOption(Skill.WOODHASTE1)
                println(option)
                val event = SkillUseEvent(jp, option)
                Bukkit.getServer().pluginManager.callEvent(event)
            }
        }

        @EventHandler
        fun onMove(e: PlayerMoveEvent) {
            val player = e.player
            val jp = JobPlayer(player, plg)

            if (block(jp, Skill.WOODHASTE1)) return

            if (jp.skillStatus == SkillStatus.ENABLED) {
                if (Items.axes.contains(player.inventory.itemInMainHand.type)) {
                    val targetBlock = player.getTargetBlock(null, 5).type
                    if (Blocks.woods.contains(targetBlock)) {
                        player.addPotionEffect(PotionEffect(PotionEffectType.FAST_DIGGING, 20 * 2, 2, true))
                    }
                }
            }


        }
    }

    class WoodHaste2(val plg: Main) : SkillProvider(plg, Jobs.WOODCUTTER), Listener {

        @EventHandler
        fun onInteract(e: PlayerInteractEvent) {
            if (e.action == Action.RIGHT_CLICK_BLOCK || e.action == Action.RIGHT_CLICK_AIR) {
                val jp = JobPlayer(plugin = plg, player = e.player)
                if (activateBlock(jp, plg.skillManager, Skill.WOODHASTE2)) return
                val option = plg.skillManager.getSkillOption(Skill.WOODHASTE2)
                val event = SkillUseEvent(jp, option)
                Bukkit.getServer().pluginManager.callEvent(event)
            }
        }

        @EventHandler
        fun onMove(e: PlayerMoveEvent) {
            val player = e.player
            val jp = JobPlayer(player, plg)

            if (block(jp, Skill.WOODHASTE2))

                if (jp.skillStatus == SkillStatus.ENABLED) {
                    if (Items.axes.contains(player.inventory.itemInMainHand.type)) {
                        val targetBlock = player.getTargetBlock(null, 5).type
                        if (Blocks.woods.contains(targetBlock)) {
                            player.addPotionEffect(PotionEffect(PotionEffectType.FAST_DIGGING, 20 * 2, 2, true))
                        }
                    }
                }


        }
    }

    class WoodHaste3(val plg: Main) : SkillProvider(plg, Jobs.WOODCUTTER), Listener {

        @EventHandler
        fun onInteract(e: PlayerInteractEvent) {
            if (e.action == Action.RIGHT_CLICK_BLOCK || e.action == Action.RIGHT_CLICK_AIR) {
                val jp = JobPlayer(plugin = plg, player = e.player)
                if (activateBlock(jp, plg.skillManager, Skill.WOODHASTE3)) return
                val option = plg.skillManager.getSkillOption(Skill.WOODHASTE3)
                val event = SkillUseEvent(jp, option)
                Bukkit.getServer().pluginManager.callEvent(event)
            }
        }

        @EventHandler
        fun onMove(e: PlayerMoveEvent) {
            val player = e.player
            val jp = JobPlayer(player, plg)

            if(block(jp,Skill.WOODHASTE3))

            if (jp.skillStatus == SkillStatus.ENABLED) {
                if (Items.axes.contains(player.inventory.itemInMainHand.type)) {
                    val targetBlock = player.getTargetBlock(null, 5).type
                    if (Blocks.woods.contains(targetBlock)) {
                        player.addPotionEffect(PotionEffect(PotionEffectType.FAST_DIGGING, 20 * 2, 2, true))
                    }
                }
            }


        }
    }

    class CutAll(val plg: Main) : SkillProvider(plg, Jobs.WOODCUTTER), Listener {
        @EventHandler
        fun onInteract(e: PlayerInteractEvent) {
            if (e.action == Action.RIGHT_CLICK_BLOCK || e.action == Action.RIGHT_CLICK_AIR) {
                val jp = JobPlayer(plugin = plg, player = e.player)
                if (activateBlock(jp, plg.skillManager, Skill.MINEALL)) return

                val option = plg.skillManager.getSkillOption(Skill.MINEALL)
                val event = SkillUseEvent(jp, option)
                Bukkit.getServer().pluginManager.callEvent(event)
            }
        }

        @EventHandler
        fun onBlockBroken(e: BlockBreakEvent) {
            val player = e.player
            val jp = JobPlayer(player, plugin)
            val block = e.block
            val tool = player.inventory.itemInMainHand

            if (block(jp, Skill.CUTALL)) {
                return
            }

            //スニーク時無効
            if (player.isSneaking) return

            //持っているアイテムが斧かどうか
            if (!Items.axes.contains(tool.type)) return

            //壊すブロックが木かどうか
            if (!Blocks.woods.contains(block.type)) return


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
}