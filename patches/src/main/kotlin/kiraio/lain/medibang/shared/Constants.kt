package kiraio.lain.medibang.shared

import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility

object Constants {
    val COMPATIBILITY = Compatibility(
        name = "MediBang Paint",
        packageName = "com.medibang.android.paint.tablet",
        appIconColor = 0xffffff,
        targets = listOf(AppTarget(null), AppTarget("28.24"))
    )
}
