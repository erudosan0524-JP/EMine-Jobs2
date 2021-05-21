package click.erudosaba.mc.eminejobs2.skill.skills

import click.erudosaba.mc.eminejobs2.skill.Skill
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

object WoodCutterSkills {

    val jobID = "woodcutter"

    class WoodHaste1 : Skill("WoodHaste1", "破壊速度上昇1", jobID, arrayOf("10秒間採掘速度を上昇する"), Material.OAK_SAPLING, 10, 15, 60), Listener{

    }

    class WoodHaste2 : Skill("WoodHaste2", "破壊速度上昇2", jobID, arrayOf("30秒間採掘速度を上昇する"), Material.OAK_SAPLING, 30, 25, 80), Listener{

    }

    class WoodHaste3 : Skill("WoodHaste3", "破壊速度上昇3", jobID, arrayOf("50秒間採掘速度を上昇する"), Material.OAK_SAPLING, 50, 35, 80), Listener{

    }

    class CutAll : Skill("CutAll", "一括破壊", jobID, arrayOf("10秒間木を一括破壊できる"), Material.DIAMOND_AXE, 10, 40, 5), Listener{

    }
}