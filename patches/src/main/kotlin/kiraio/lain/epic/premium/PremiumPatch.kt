package kiraio.lain.epic.premium

import app.morphe.patcher.extensions.InstructionExtensions.addInstruction
import app.morphe.patcher.extensions.InstructionExtensions.instructions
import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility
import app.morphe.patcher.patch.bytecodePatch
import com.android.tools.smali.dexlib2.iface.instruction.TwoRegisterInstruction
import kiraio.lain.epic.shared.Constants

@Suppress("unused")
val premiumPatch = bytecodePatch(
    name = "Enable Epic Family",
    description = "Unlock access to all books, videos and audiobooks for lifetime.",
    default = true
) {
    compatibleWith(Constants.COMPATIBILITY)
    execute {
        RealSubscriptionStatusFingerprint.matchAll().forEach {
            val index = it.instructionMatches.first().index
            val indexReg = (it.method.instructions[index] as TwoRegisterInstruction).registerA
            it.method.addInstruction(
                index + 1,
                """
                    const/4 v$indexReg, 0x2
                """.trimIndent()
            )
        }
    }
}

@Suppress("unused")
val regionLockPatch = bytecodePatch(
    name = "Disable Region Lock",
    description = "Bypass restricted region contents; access it anywhere, anytime.",
    default = true
) {
    compatibleWith(Constants.COMPATIBILITY)
    execute {
        RegionRestrictedFingerprint.matchAll().forEach {
            val index = it.instructionMatches.first().index
            val indexReg = (it.method.instructions[index] as TwoRegisterInstruction).registerA
            it.method.addInstruction(
                index + 1,
                """
                    const/4 v$indexReg, 0x0
                """.trimIndent()
            )
        }
    }
}
