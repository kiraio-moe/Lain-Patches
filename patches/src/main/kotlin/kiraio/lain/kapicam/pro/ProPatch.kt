package kiraio.lain.kapicam.pro

import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.util.returnEarly

@Suppress("unused")
val proPatch = bytecodePatch(
    name = "Enable Kapi Pro",
    description = "Unlock all features of Kapi Pro lifetime membership.",
    default = true
) {
    compatibleWith(
        Compatibility(
            name = "Kapi Cam",
            packageName = "com.sensemobile.action",
            appIconColor = 0xc9b44d,
            targets = listOf(AppTarget(null), AppTarget("4.32.1"))
        )
    )
    execute {
        VipFingerprint.method.returnEarly(true)
        ForeverVipFingerprint.method.returnEarly(true)
    }
}
