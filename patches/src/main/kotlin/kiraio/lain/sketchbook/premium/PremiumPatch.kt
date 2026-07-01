package kiraio.lain.sketchbook.premium

import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.util.returnEarly

@Suppress("unused")
val premiumPatch = bytecodePatch(
    name = "Enable Premium",
    description = "Unlock premium features such as Advanced Color Tools, Clipping Masks & Alpha Masks, Manage layers with Groups, Import & customize brushes, and more...",
    default = true
) {
    compatibleWith(
        Compatibility(
            name = "Sketchbook",
            packageName = "com.adsk.sketchbook",
            appIconColor = 0xe45a36,
            targets = listOf(AppTarget(null), AppTarget("6.2.6"))
        )
    )
    execute {
        PremiumFingerprint.method.returnEarly(true)
    }
}
