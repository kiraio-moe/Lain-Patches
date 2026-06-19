package kiraio.lain.nekopoi.shared

import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility

object Constants {
    val COMPATIBILITY = Compatibility(
        name = "Nekopoi",
        packageName = "com.kcstream.cing",
        appIconColor = 0xfbc5d1,
        targets = listOf(AppTarget(null), AppTarget("2.5.3-build01"))
    )
}
