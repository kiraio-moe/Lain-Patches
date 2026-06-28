package kiraio.lain.audiorelay.premium

import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.util.returnEarly

@Suppress("unused")
val premiumPatch = bytecodePatch(
    name = "Unlock Premium",
    description = "Unlock premium features.",
    default = true
) {
    compatibleWith(
        Compatibility(
            name = "AudioRelay",
            packageName = "com.azefsw.audioconnect",
            appIconColor = 0x2196f2,
            targets = listOf(AppTarget(null), AppTarget("0.26.1"))
        )
    )
    execute {
        val premiumClass = PremiumFingerprint.classDef
        val premiumMethod = premiumClass.methods.first { it.returnType == "Z" && it.parameterTypes.isEmpty() }

        premiumMethod.returnEarly(true)
    }
}
