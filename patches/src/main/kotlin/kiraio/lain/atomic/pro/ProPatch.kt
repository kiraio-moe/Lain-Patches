package kiraio.lain.atomic.pro

import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.util.returnEarly

@Suppress("unused")
val proPlusPatch = bytecodePatch(
    name = "Enable PRO+",
    description = "Unlock all PRO+ features.",
    default = true
) {
    compatibleWith(
        Compatibility(
            name = "Atomic",
            packageName = "com.jlindemann.science",
            appIconColor = 0x3561a8,
            targets = listOf(AppTarget(null), AppTarget("4.7.0m"))
        )
    )
    execute {
        ProPlusFingerprint.method.returnEarly(true)
    }
}
