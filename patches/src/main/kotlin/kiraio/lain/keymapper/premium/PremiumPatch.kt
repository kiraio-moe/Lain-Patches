package kiraio.lain.keymapper.premium

import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.util.returnEarly

@Suppress("unused")
val premiumPatch = bytecodePatch(
    name = "Unlock Premium",
    description = "Enable Floating Buttons and Assistant Trigger features.",
    default = true
) {
    compatibleWith(
        Compatibility(
            name = "Key Mapper",
            packageName = "io.github.sds100.keymapper",
            appIconColor = 0xffffff,
            targets = listOf(AppTarget(null), AppTarget("4.2.1"))
        )
    )
    execute {
        PremiumFingerprint.method.returnEarly(true)
    }
}
