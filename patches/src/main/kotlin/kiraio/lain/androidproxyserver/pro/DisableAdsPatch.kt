package kiraio.lain.androidproxyserver.pro

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.util.indexOfFirstInstructionReversedOrThrow
import app.morphe.util.returnEarly
import com.android.tools.smali.dexlib2.Opcode

@Suppress("unused")
val disableAdsPatch = bytecodePatch(
    name = "Disable Ads",
    description = "Disable banner, splash, and rewarded ads.",
    default = true,
) {
    compatibleWith(
        Compatibility(
            name = "Android Proxy Server",
            packageName = "cn.adonet.proxyevery",
            appIconColor = 0X01aa68,
            targets = listOf(AppTarget("9.9"))
            // "version = null" means the patch works with the latest app target
            // and is expected to work with all future app targets
        )
    )
    execute {
        MainFragmentAdsFingerprint.method.returnEarly()
        // Remove buy pro version menu item
        MainActivityMenuFingerprint.method.addInstructions(
            MainActivityMenuFingerprint.method.indexOfFirstInstructionReversedOrThrow(Opcode.IPUT_OBJECT) + 1,
            """
                const v0, 0x7f090037
                invoke-interface {p1, v0}, Landroid/view/Menu;->removeItem(I)V
            """.trimIndent()
        )
    }
}
