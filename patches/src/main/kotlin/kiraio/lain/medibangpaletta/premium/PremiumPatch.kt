package kiraio.lain.medibangpaletta.premium

import app.morphe.patcher.extensions.InstructionExtensions.addInstruction
import app.morphe.patcher.extensions.InstructionExtensions.instructions
import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.util.addInstructionsAtControlFlowLabel
import app.morphe.util.indexOfFirstInstruction
import app.morphe.util.indexOfFirstInstructionReversed
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction
import com.android.tools.smali.dexlib2.iface.instruction.TwoRegisterInstruction
import com.android.tools.smali.dexlib2.iface.instruction.formats.Instruction21t

@Suppress("unused")
val premiumPatch = bytecodePatch(
    name = "Enable Premium",
    description = "Disable trial time counter.",
    default = true
) {
    compatibleWith(
        Compatibility(
            name = "Paletta",
            packageName = "com.medibang.android.medibangpro",
            appIconColor = 0xffffff,
            targets = listOf(AppTarget(null), AppTarget("4.11"))
        )
    )
    execute {
        val firstMatch = PremiumFingerprint.instructionMatches.first().index

        val gotoIndex = PremiumFingerprint.method.indexOfFirstInstruction(firstMatch, Opcode.GOTO)
        val moveIndex = PremiumFingerprint.method.indexOfFirstInstruction(gotoIndex, Opcode.MOVE)
        val moveReg = (PremiumFingerprint.method.instructions[moveIndex] as TwoRegisterInstruction).registerB
        PremiumFingerprint.method.addInstructionsAtControlFlowLabel(
            moveIndex,
            "const v$moveReg, 0x1"
        )

        val ifEqzIndex = PremiumFingerprint.method.indexOfFirstInstructionReversed(firstMatch, Opcode.IF_EQZ)
        val ifEqzReg = (PremiumFingerprint.method.instructions[ifEqzIndex] as OneRegisterInstruction).registerA
        PremiumFingerprint.method.addInstruction(
            ifEqzIndex,
            "const v$ifEqzReg, 0x0"
        )

        val ifNezIndex = PremiumFingerprint.method.indexOfFirstInstructionReversed(ifEqzIndex, Opcode.IF_NEZ)
        val ifNezReg = (PremiumFingerprint.method.instructions[ifNezIndex] as Instruction21t).registerA
        PremiumFingerprint.method.addInstruction(
            ifNezIndex,
            "const v$ifNezReg, 0x0"
        )
    }
}
