package kiraio.lain.chatboost.premium

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility
import app.morphe.patcher.patch.bytecodePatch

@Suppress("unused")
val premiumPatch = bytecodePatch(
    name = "Enable Premium",
    description = "Unlock all premium benefits.",
    default = true
) {
    compatibleWith(
        Compatibility(
            name = "ChatBoost",
            packageName = "studio.muggle.chatboost",
            appIconColor = 0xffffff,
            targets = listOf(AppTarget(null), AppTarget("3.24.1")),
        )
    )
    execute {
        val clazz = PremiumClassFingerprint.classDef
        val premiumMethod = clazz.methods.first { it.parameterTypes == listOf("Z", "Z", "Z") }
        premiumMethod.addInstructions(
            0,
            """
                const/4 p1, 0x1
                const/4 p2, 0x1
                const/4 p3, 0x1
            """.trimIndent()
        )
    }
}
