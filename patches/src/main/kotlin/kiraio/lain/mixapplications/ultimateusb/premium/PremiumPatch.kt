package kiraio.lain.mixapplications.ultimateusb.premium

import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.util.returnEarly
import kiraio.lain.mixapplications.shared.PremiumFingerprint

@Suppress("unused")
val premiumPatch = bytecodePatch(
    name = "Enable Premium",
    description = "Unlock unlimited use of all tools, disable ads and disable coins system."
) {
    compatibleWith(
        Compatibility(
            name = "Ultimate USB",
            packageName = "com.mixapplications.ultimateusb",
            appIconColor = 0xf8f8f8,
            targets = listOf(AppTarget(null), AppTarget("10.2.4"))
        )
    )
    execute {
        PremiumFingerprint.method.returnEarly(true)
    }
}
