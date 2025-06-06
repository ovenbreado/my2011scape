package gg.rsmod.plugins.content.inter.itembox

val QUANTITY_SELECTION_INTERFACE = 916

val buttonIds = arrayOf(5, 6, 7, 8, 19, 20)

buttonIds.forEach {
    on_button(interfaceId = QUANTITY_SELECTION_INTERFACE, component = it) {
        when (it) {
            5 -> player.setVarbit(Varbits.MAKE_QUANTITY, 1)
            6 -> player.setVarbit(Varbits.MAKE_QUANTITY, 5)
            7 -> player.setVarbit(Varbits.MAKE_QUANTITY, 10)
            8 -> player.setVarbit(Varbits.MAKE_QUANTITY, player.getMaximumMakeQuantity())
            19 -> {
                var current = player.getMakeQuantity()
                val maximum = player.getMaximumMakeQuantity()
                current++
                if (current > maximum) {
                    current = maximum
                }
                player.setVarbit(Varbits.MAKE_QUANTITY, current)
            }
            20 -> {
                var current = player.getMakeQuantity()
                current--
                if (current < 0) {
                    current = 0
                }
                player.setVarbit(Varbits.MAKE_QUANTITY, current)
            }
        }
    }
}
