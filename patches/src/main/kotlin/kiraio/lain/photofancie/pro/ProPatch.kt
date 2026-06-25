package kiraio.lain.photofancie.pro

import app.morphe.patcher.extensions.InstructionExtensions.instructions
import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.util.addInstructionsAtControlFlowLabel
import app.morphe.util.indexOfFirstInstructionReversed
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.TwoRegisterInstruction

@Suppress("unused")
val proPatch = bytecodePatch(
    name = "Unlock Pro",
    description = "Enable pro features.",
    default = true
) {
    compatibleWith(
        Compatibility(
            name = "Collage Maker - PhotoFancie",
            packageName = "com.surmin.photofancie.lite",
            appIconColor = 0xffffff,
            targets = listOf(AppTarget(null), AppTarget("5.8.3"))
        )
    )
    execute {
        ProFingerprint.matchAll().forEach { match ->
            val index = match.method.indexOfFirstInstructionReversed(Opcode.IPUT_BOOLEAN)
            val indexReg = (match.method.instructions[index] as TwoRegisterInstruction).registerA
            match.method.addInstructionsAtControlFlowLabel(
                index,
                "const/4 v$indexReg, 0x1"
            )
        }
    }
}
