package kiraio.lain.fakegpslocation.pro

import app.morphe.patcher.extensions.InstructionExtensions.addInstruction
import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility
import app.morphe.patcher.patch.bytecodePatch

val premiumPatch = bytecodePatch(
    name = "Unlock Premium",
    description = "Unlock premium features.",
    default = true
) {
    compatibleWith(
        Compatibility(
            name = "Fake GPS Location",
            packageName = "com.hopefactory2021.fakegpslocation",
            appIconColor = 0x5f7c8a,
            targets = listOf(AppTarget(null), AppTarget("113.0"))
        )
    )
    execute {
        PurchasedSetterFingerprint.method.addInstruction(
            0,
            "const/4 p1, 0x1"
        )
    }
}
