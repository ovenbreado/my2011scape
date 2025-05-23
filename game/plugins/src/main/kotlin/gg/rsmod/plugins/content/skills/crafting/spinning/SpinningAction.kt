package gg.rsmod.plugins.content.skills.crafting.spinning

import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Anims
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.api.ext.player
import kotlin.math.min

object SpinningAction {
    suspend fun spin(
        task: QueueTask,
        data: SpinningData,
        amount: Int,
    ) {
        val player = task.player
        val inventory = player.inventory

        val raw = getRawItem(player, data)

        if (raw == -1) {
            player.message("You don't have any ${data.rawName.lowercase()}.")
            return
        }

        val maxCount = min(amount, inventory.getItemCount(raw))

        repeat(maxCount) {
            if (!canSpin(task, data)) {
                player.animate(Anims.RESET)
                return
            }
            task.wait(1)
            player.animate(Anims.SPIN_SPINNING_WHEEL)
            if (!inventory.remove(raw, assureFullRemoval = true).hasSucceeded()) {
                return
            }
            inventory.add(data.product, assureFullInsertion = true)
            player.addXp(Skills.CRAFTING, data.experience)
            task.wait(3)
        }
    }

    private fun getRawItem(
        player: Player,
        data: SpinningData,
    ): Int {
        val inventoryItems =
            player.inventory.rawItems
                .filterNotNull()
                .map { it.id }
                .toIntArray()

        // TODO: Remove improper implementation after refactoring spinning wheel.
        //  Prioritizes CBOW_STRING enum instead of CBOW_STRING1 when Sinew is in players inventory.
        if (data == SpinningData.CBOW_STRING1 && inventoryItems.contains(Items.SINEW)) {
            return Items.SINEW
        }

        val rawItem = inventoryItems.find { data.raw.contains(it) }
        return rawItem ?: -1
    }

    private fun canSpin(
        task: QueueTask,
        data: SpinningData,
    ): Boolean {
        val player = task.player
        val inventory = player.inventory

        val rawItem = getRawItem(player, data)

        if (rawItem == -1) {
            player.message("You don't have any ${data.rawName.lowercase()}.")
            return false
        }

        if (!inventory.contains(rawItem)) {
            player.message("You don't have any ${data.rawName.lowercase()}.")
            return false
        }

        if (player.skills.getCurrentLevel(Skills.CRAFTING) < data.levelRequirement) {
            player.message("You need a crafting level of ${data.levelRequirement} to make this.")
            return false
        }

        return true
    }
}
