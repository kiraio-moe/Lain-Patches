package kiraio.lain.mangaplus.max

import app.morphe.patcher.extensions.InstructionExtensions.removeInstruction
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.util.indexOfFirstInstruction
import app.morphe.util.indexOfFirstInstructionReversed
import com.android.tools.smali.dexlib2.Opcode
import kiraio.lain.mangaplus.shared.Constants

@Suppress("unused")
val freeReadPatch = bytecodePatch(
    name = "Bypass Reading Limitation",
    description = "Bypass 'First Time Free' read limitation for all languages. Only affect 'Standard/Deluxe' plan titles, exclusive 'Deluxe' plan titles are server-driven.",
    default = true
) {
    compatibleWith(Constants.COMPATIBILITY)
    execute {
        FreeReadFingerprint.matchAll().forEach {
            it.method.removeInstruction(it.method.indexOfFirstInstructionReversed(Opcode.IF_NEZ))
        }
    }
}

@Suppress("unused")
val deluxePatch = bytecodePatch(
    name = "Enable Deluxe Plan",
    description = "Enable Deluxe plan badge on settings. Bypass the 'First Time Free' read limitation only for English language on 'Standard/Deluxe' plan titles. Other languages aren't affected.",
    default = false
) {
    compatibleWith(Constants.COMPATIBILITY)
    execute {
        DeluxeFingerprint.method.removeInstruction(DeluxeFingerprint.method.indexOfFirstInstruction(Opcode.IF_EQZ))
    }
}
