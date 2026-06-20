package kiraio.lain.hermit.premium

import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.util.returnEarly

@Suppress("unused")
val premiumPatch = bytecodePatch(
    name = "Unlock Premium",
    description = "Enable premium features.",
    default = true
) {
    compatibleWith(
        Compatibility(
            name = "Hermit",
            packageName = "com.chimbori.hermitcrab",
            appIconColor = 0xe35252,
            targets = listOf(AppTarget(null), AppTarget("31.6.1"))
        )
    )
    execute {
        val premiumClass = PremiumFingerprint.classDef
        val premiumMethod = premiumClass.methods.first { it.returnType == "Z" && it.parameterTypes.isEmpty() }
        premiumMethod.returnEarly(true)
    }
}
