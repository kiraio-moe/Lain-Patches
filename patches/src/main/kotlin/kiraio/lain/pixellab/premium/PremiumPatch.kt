package kiraio.lain.pixellab.premium

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import kiraio.lain.pixellab.shared.Constants

@Suppress("unused")
val premiumPatch = bytecodePatch(
    name = "Unlock Premium",
    description = "Unlock 'Remove Ads' premium feature.",
    default = true
) {
    compatibleWith(Constants.COMPATIBILITY)
    execute {
        MainActivityHandlePurchasesFingerprint.method.addInstructions(
            0,
            """
            const/4 v0, 0x1
            
            invoke-virtual {p0, v0}, Lcom/imaginstudio/imagetools/pixellab/MainActivity;->makePremium(Z)V
            
            sget-object v1, Lcom/imaginstudio/imagetools/pixellab/MainActivity;->USER_PREMIUM:Ljava/lang/String;
            
            iget-object p1, p0, Lcom/imaginstudio/imagetools/pixellab/MainActivity;->tinydb:Lcom/imaginstudio/imagetools/pixellab/TinyDB;
            
            invoke-virtual {p1, v1, v0}, Lcom/imaginstudio/imagetools/pixellab/TinyDB;->putBoolean(Ljava/lang/String;Z)V
            
            return-void
            """.trimIndent()
        )
    }
}
