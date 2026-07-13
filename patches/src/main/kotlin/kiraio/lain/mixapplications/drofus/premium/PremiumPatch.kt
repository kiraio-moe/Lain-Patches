package kiraio.lain.mixapplications.drofus.premium

import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.util.returnEarly
import kiraio.lain.mixapplications.shared.PremiumFingerprint

@Suppress("unused")
val premiumPatch = bytecodePatch(
    name = "Enable Premium",
    description = "Unlock unlimited use of ISO Burner, disable ads and disable coins system."
) {
    compatibleWith(
        Compatibility(
            name = "DROFUS",
            packageName = "com.mixapplications.rufus",
            appIconColor = 0xf8f8f8,
            targets = listOf(AppTarget(null), AppTarget("10.2.3"))
        )
    )
    execute {
        PremiumFingerprint.method.returnEarly(true)
    }
}
