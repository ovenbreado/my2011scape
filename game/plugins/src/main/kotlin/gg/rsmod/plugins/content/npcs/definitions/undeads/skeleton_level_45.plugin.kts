package gg.rsmod.plugins.content.npcs.definitions.undeads

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Gems
import gg.rsmod.plugins.content.drops.global.Herbs

val id = Npcs.SKELETON_93

val table = DropTableFactory
val skeleton =
    table.build {

        guaranteed {
            obj(Items.BONES)
        }
        main {
            total(total = 128)

            obj(Items.IRON_MED_HELM, quantity = 1, slots = 6)
            obj(Items.IRON_SWORD, quantity = 1, slots = 4)
            obj(Items.IRON_HATCHET, quantity = 1, slots = 2)
            obj(Items.IRON_SCIMITAR, quantity = 1, slots = 1)

            obj(Items.AIR_RUNE, quantity = 15, slots = 3)
            obj(Items.WATER_RUNE, quantity = 9, slots = 3)
            obj(Items.CHAOS_RUNE, quantity = 5, slots = 3)
            obj(Items.IRON_ARROW, quantity = 12, slots = 2)
            obj(Items.LAW_RUNE, quantity = 2, slots = 2)
            obj(Items.COSMIC_RUNE, quantity = 2, slots = 1)

            table(Herbs.minorHerbTable, slots = 20)

            obj(Items.COINS_995, quantity = 10, slots = 24)
            obj(Items.COINS_995, quantity = 5, slots = 25)
            obj(Items.COINS_995, quantity = 25, slots = 8)
            obj(Items.COINS_995, quantity = 45, slots = 4)
            obj(Items.COINS_995, quantity = 65, slots = 3)
            obj(Items.COINS_995, quantity = 1, slots = 2)

            nothing(slots = 8)
            obj(Items.BRONZE_BAR, slots = 5)

            table(Gems.gemTable, slots = 2)
        }
        table("Charms") {
            total(1000)
            obj(Items.GOLD_CHARM, quantity = 1, slots = 50)
            obj(Items.GREEN_CHARM, quantity = 1, slots = 12)
            obj(Items.CRIMSON_CHARM, quantity = 1, slots = 30)
            obj(Items.BLUE_CHARM, quantity = 1, slots = 14)
            nothing(slots = 894)
        }
    }

table.register(skeleton, id)

on_npc_pre_death(id) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.SKELETON_DEATH)
}

on_npc_death(id) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

set_combat_def(id) {
    configs {
        attackSpeed = 4
        respawnDelay = 30
    }
    stats {
        hitpoints = 590
        attack = 32
        strength = 35
        defence = 36
    }
    bonuses {
        attackStab = 15
        attackCrush = 14
        defenceStab = 9
        defenceSlash = 11
        defenceCrush = -2
        defenceMagic = 1
        defenceRanged = 4
    }
    anims {
        attack = Anims.SKELETON_ATTACK
        block = Anims.SKELETON_BLOCK
        death = Anims.SKELETON_DEATH
    }
    slayer {
        assignment = SlayerAssignment.SKELETON
        level = 1
        experience = 59.0
    }
}
