package click.erudosaba.mc.eminejobs2.skill

import click.erudosaba.mc.eminejobs2.Main
import click.erudosaba.mc.eminejobs2.event.SkillUseEvent
import click.erudosaba.mc.eminejobs2.jobs.JobPlayer
import click.erudosaba.mc.eminejobs2.jobs.Jobs
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import java.util.*

enum class Skill(val job : Jobs, var defaultNeedLevel : Int, var defaultActiveTime : Int, var defaultInterval : Int, var defaultDescription : Array<String>, var defaultIcon : String) {
    CUTALL(Jobs.WOODCUTTER,40,30,5, arrayOf("30秒間木を一括破壊できる"),"DIAMOND_AXE"),
    WOODHASTE1(Jobs.WOODCUTTER,15,10,10,arrayOf("10秒間採掘速度を上昇する"),"OAK_SAPLING"),
    WOODHASTE2(Jobs.WOODCUTTER,25,30,80, arrayOf("30秒間採掘速度を上昇する"),"OAK_SAPLING"),
    WOODHASTE3(Jobs.WOODCUTTER,35,50,100, arrayOf("50秒間採掘速度を上昇する"),"OAK_SAPLING"),
    MINEALL(Jobs.MINER,40,30,5, arrayOf("30秒間鉱石ブロックを一括破壊できる","（自分の立ってる高さ以下は破壊しない）"),"DIAMOND_PICKAXE"),
    STONEHASTE1(Jobs.MINER,15,20,70, arrayOf("20秒間採掘速度を上昇する"),"COAL_ORE"),
    STONEHASTE2(Jobs.MINER,25,40,90, arrayOf("40秒間採掘速度を上昇する"),"IRON_ORE"),
    STONEHASTE3(Jobs.MINER,35,60,110, arrayOf("60秒間採掘速度を上昇する"),"GOLD_ORE"),
    DIGALL(Jobs.DIGGER,40,30,5, arrayOf("30秒間土ブロックを一括破壊できる","（自分の立ってる高さ以下は破壊しない）"),"DIAMOND_SHOVEL"),
    DIGHASTE1(Jobs.DIGGER,15,20,70, arrayOf("20秒間採掘速度を上昇する"),"PODZOL"),
    DIGHASTE2(Jobs.DIGGER,25,40,90, arrayOf("40秒間採掘速度を上昇する"),"PODZOL"),
    DIGHASTE3(Jobs.DIGGER,35,60,110, arrayOf("60秒間採掘速度を上昇する"),"PODZOL"),
    SLASH1(Jobs.SWORDMAN,20,30,20, arrayOf("30秒間スキル発動時に斬撃を飛ばして","あたったプレイヤーに持ってる剣のダメージ*1.3"),"IRON_SWORD"),
    SLASH2(Jobs.SWORDMAN,30,30,20, arrayOf("30秒間スキル発動時に斬撃を飛ばして","あたったプレイヤーに持ってる剣のダメージ*1.4 + 弱体化10秒"),"GOLDEN_SWORD"),
    SLASH3(Jobs.SWORDMAN,40,30,20, arrayOf("30秒間スキル発動時に斬撃を飛ばして","あたったプレイヤーに持ってる剣のダメージ*1.5 + 弱体化15秒"),"DIAMOND_SWORD"),
    SPEEDARROW(Jobs.ARCHER,25,10,90, arrayOf("10秒間自分が撃った矢に当たると空を飛べる","15秒間は落下ダメージが0となる"),"TIPPED_ARROW"),
    POWERARROW(Jobs.ARCHER,30,10,60, arrayOf("10秒間撃った矢が３方向に同時に飛ぶようになる"),"TIPPED_ARROW"),
    HOMINGARROW(Jobs.ARCHER,35,10,60, arrayOf("10秒間撃った矢がホーミングする"),"ARROW"),
    GROWING1(Jobs.FARMER,15,10,60, arrayOf("10秒間半径3ブロックの植物の成長速度を早める"),"WHEAT_SEEDS"),
    GROWING2(Jobs.FARMER,25,10,60, arrayOf("10秒間半径5ブロックの植物の成長速度を早める"),"WHEAT_SEEDS"),
    GROWING3(Jobs.FARMER,35,10,60, arrayOf("10秒間半径8ブロックの植物の成長速度を早める"),"WHEAT_SEEDS"),
    AUTOHARVEST(Jobs.FARMER,40,20,90, arrayOf("20秒間自分が歩いた地点から","半径3ブロックの範囲の作物を自動で破壊する"),"LETHER_BOOTS"),
    WALLCLIMB(Jobs.EXPLORER,25,180,300, arrayOf("180秒間壁が上れる＋移動速度上昇2"),"DIAMOND_BOOTS"),
    FROSTWALK(Jobs.EXPLORER,30,180,300, arrayOf("180秒間水の上を歩ける＋移動速度上昇2"),"DIAMOND_BOOTS"),
    SMELT1(Jobs.SMELTER,15,180,300, arrayOf("180秒間精練速度が1.3倍"),"FURNACE"),
    SMELT2(Jobs.SMELTER,25,180,300, arrayOf("180秒間精練速度が1.6倍"),"FURNACE"),
    SMELT3(Jobs.SMELTER,35,180,300, arrayOf("180秒間精練速度が2倍"),"FURNACE"),
    CATCHFISH(Jobs.FISHERMAN,25,5,60, arrayOf("5秒間素手で水を右クリックすると魚が取れる","（この時経験値は増えない）"),"SALMON"),
    RANDOMEFFECT1(Jobs.CRAFTER,15,30,90, arrayOf("30秒間の間にクラフトしたら","10秒間ランダムなエフェクト(lv.1)がつく"),"CRAFTING_TABLE"),
    RANDOMEFFECT2(Jobs.CRAFTER,25,30,90, arrayOf("30秒間の間にクラフトしたら","10秒間ランダムなエフェクト(lv.2)がつく"),"CRAFTING_TABLE"),
    RANDOMEFFECT3(Jobs.CRAFTER,35,30,90, arrayOf("30秒間の間にクラフトしたら","10秒間ランダムなエフェクト(lv.3)がつく"),"CRAFTING_TABLE"),
    RANDOMUPGRADE(Jobs.CRAFTER,40,10,130, arrayOf("10秒間の間にクラフトしたら，2%が上位のツールになる","（木→石→鉄→金→ダイヤ→ネザライト）"),"CRAFTING_TABLE"),
    TRANSENCHANT(Jobs.ENCHANTER,25,30,90, arrayOf("30秒間の間にエンチャントしたら","そのエンチャントの本が１つ手に入る"),"BOOK"),
    LOWCOSTREPAIR1(Jobs.WEAPONSMITH,20,10,130, arrayOf("10秒間の間に金床を使用すると","修理コストが（普通のコスト - 4)される"),"ANVIL"),
    LOWCOSTREPAIR2(Jobs.WEAPONSMITH,30,10,130, arrayOf("10秒間の間に金床を使用すると","修理コストが（普通のコスト - 7)される"),"ANVIL"),
    LOWCOSTREPAIR3(Jobs.WEAPONSMITH,40,10,130, arrayOf("10秒間の間に金床を使用すると","修理コストが（普通のコスト - 10)される"),"ANVIL"),
    FASTBREWING1(Jobs.BREWER,20,10,130, arrayOf("10秒間醸造の時間が0.8倍"),"BREWING_STAND"),
    FASTBREWING2(Jobs.BREWER,30,10,130, arrayOf("10秒間醸造の時間が0.6倍"),"BREWING_STAND"),
    FASTBREWING3(Jobs.BREWER,40,10,130, arrayOf("10秒間醸造の時間が0.4倍"),"BREWING_STAND"),
    PROTEAN(Jobs.BUILDER,30,30,90, arrayOf("30秒間ブロックを殴ると","左手に持っているブロックに殴ったブロックが変化する","(鉱石ブロックは変化しない)"),"BRICKS"),
    LEVITATION(Jobs.BUILDER,45,60,360, arrayOf("60秒間浮遊が可能"),"FETHER"),
    DOUBLEJUMP(Jobs.GUNNER,20,60,180, arrayOf("60秒間スペースキーを２回押すと","２段ジャンプが可能"),"FETHER"),
    NOSLOW1(Jobs.HUNGER,25,20,60, arrayOf("20秒間食事中の移動速度が遅くならない"),"STEAK"),
    NOSLOW2(Jobs.HUNGER,35,40,60, arrayOf("40秒間食事中の移動速度が遅くならない"),"STEAK"),
    ALWAYSFULL(Jobs.HUNGER,45,120,150, arrayOf("120秒間お腹が減らない"),"CAKE")




}