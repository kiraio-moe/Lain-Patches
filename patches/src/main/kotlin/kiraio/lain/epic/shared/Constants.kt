package kiraio.lain.epic.shared

import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility

object Constants {
    val COMPATIBILITY = Compatibility(
        name = "Epic!",
        packageName = "com.getepic.Epic",
        appIconColor = 0x0b96e7,
        targets = listOf(AppTarget(null), AppTarget("3.141.43"))
    )
}
