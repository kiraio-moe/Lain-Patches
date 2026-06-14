package kiraio.lain.timestampcamera.pro

import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.util.insertLiteralOverride
import app.morphe.util.returnEarly

@Suppress("unused")
val disableAdsPatch = bytecodePatch(
    name = "Disable Ads",
    description = "Disable banner ads.",
    default = true
) {
    compatibleWith(
        Compatibility(
            name = "Timestamp Camera Enterprise Free",
            packageName = "com.jeyluta.timestampcamerafreeent",
            appIconColor = 0xffffff,
            targets = listOf(AppTarget(null), AppTarget("1.237"))
        ), Compatibility(
            name = "Timestamp Camera Free",
            packageName = "com.jeyluta.timestampcamerafree",
            appIconColor = 0xffffff,
            targets = listOf(AppTarget(null), AppTarget("1.252"))
        )
    )
    execute {
        IsActiveFingerprint.method.returnEarly(true)
        CameraCaptureOnCreateFingerprint.method.insertLiteralOverride(
            CameraCaptureOnCreateFingerprint.instructionMatches.first().index,
            true
        )
    }
}
