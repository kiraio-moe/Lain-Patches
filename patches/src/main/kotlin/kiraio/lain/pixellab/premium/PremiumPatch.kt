package kiraio.lain.pixellab.premium

import app.morphe.patcher.extensions.InstructionExtensions.removeInstruction
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.util.addInstructionsToEnd
import kiraio.lain.pixellab.shared.Constants

@Suppress("unused")
val premiumPatch = bytecodePatch(
    name = "Unlock Premium",
    description = "Unlock 'Remove Ads' premium feature.",
    default = true
) {
    compatibleWith(Constants.COMPATIBILITY)
    execute {
        MainActivityOnCreateFingerprint.method.addInstructionsToEnd(
            """
            const/4 v0, 0x1
            invoke-virtual {p0, v0}, Lcom/imaginstudio/imagetools/pixellab/MainActivity;->makePremium(Z)V
            """.trimIndent()
        )
        // Remove 'remove ads' button
        MainActivityCreateMainMenuFingerprint.method.removeInstruction(MainActivityCreateMainMenuFingerprint.instructionMatches.first().index + 1)
    }
}
