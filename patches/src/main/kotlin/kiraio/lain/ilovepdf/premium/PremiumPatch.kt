package kiraio.lain.ilovepdf.premium

import app.morphe.patcher.extensions.InstructionExtensions.addInstruction
import app.morphe.patcher.extensions.InstructionExtensions.instructions
import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility
import app.morphe.patcher.patch.bytecodePatch
import com.android.tools.smali.dexlib2.iface.instruction.TwoRegisterInstruction

@Suppress("unused")
val premiumPatch = bytecodePatch(
    name = "Enable Premium",
    description = "Unlock premium features such as Unlimited access to PDF tools, Enable Cloud Storage; Split, Merge, Compress documents, and more. Google login or Google Drive storage is broken, need MicroG integration patch.",
    default = true
) {
    compatibleWith(
        Compatibility(
            name = "iLovePDF",
            packageName = "com.ilovepdf.www",
            appIconColor = 0xffffff,
            targets = listOf(AppTarget(null), AppTarget("4.0.1"))
        )
    )
    execute {
        SetPremiumObjectFingerprint.matchAll().forEach {
            val index = it.instructionMatches.first().index
            val indexReg = (it.method.instructions[index] as TwoRegisterInstruction).registerA
            it.method.addInstruction(
                index,
                """
                    sget-object p$indexReg, Ljava/lang/Boolean;->TRUE:Ljava/lang/Boolean;
                """.trimIndent()
            )
        }

        SetPremiumBooleanFingerprint.matchAll().forEach {
            val index = it.instructionMatches.first().index
            val indexReg = (it.method.instructions[index] as TwoRegisterInstruction).registerA
            it.method.addInstruction(
                index,
                """
                    const/4 v$indexReg, 0x1
                """.trimIndent()
            )
        }

        SetIsPremiumFingerprint.matchAll().forEach {
            val index = it.instructionMatches.first().index
            val indexReg = (it.method.instructions[index] as TwoRegisterInstruction).registerA
            it.method.addInstruction(
                index,
                """
                    const/4 v$indexReg, 0x1
                """.trimIndent()
            )
        }

        SetIsPremiumAltFingerprint.matchAll().forEach {
            val index = it.instructionMatches.first().index
            val indexReg = (it.method.instructions[index] as TwoRegisterInstruction).registerA
            it.method.addInstruction(
                index,
                """
                    const/4 v$indexReg, 0x1
                """.trimIndent()
            )
        }
    }
}
