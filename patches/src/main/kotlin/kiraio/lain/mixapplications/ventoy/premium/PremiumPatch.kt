package kiraio.lain.mixapplications.ventoy.premium

import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.util.returnEarly
import kiraio.lain.mixapplications.shared.PremiumFingerprint

@Suppress("unused")
val premiumPatch = bytecodePatch(
    name = "Enable Premium",
    description = "Unlock unlimited use of Bootable USB drive creator, disable ads and disable coins system."
) {
    compatibleWith(
        Compatibility(
            name = "Ventoy",
            packageName = "com.mixapplications.ventoy_app",
            appIconColor = 0xf8f8f8,
            targets = listOf(AppTarget(null), AppTarget("10.2.3 (1.1.16)"))
        )
    )
    execute {
        PremiumFingerprint.method.returnEarly(true)
    }
}
