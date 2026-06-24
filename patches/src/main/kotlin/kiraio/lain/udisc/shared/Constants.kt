package kiraio.lain.udisc.shared

import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility

object Constants {
    val COMPATIBILITY = Compatibility(
        name = "UDisc",
        packageName = "com.regasoftware.udisc",
        appIconColor = 0xffffff,
        targets = listOf(AppTarget("24.1.2"))
    )
}
