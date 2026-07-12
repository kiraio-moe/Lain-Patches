package kiraio.lain.hiddensettings.premium

import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility
import app.morphe.patcher.patch.rawResourcePatch
import util.wildcardHexPatch

val premiumPatch = rawResourcePatch(
    name = "Unlock Premium",
    description = "Unlock premium features such as Disable Ads and access AI Assistant.",
    default = true
) {
    compatibleWith(
        Compatibility(
            name = "Hidden Settings",
            packageName = "com.ceyhan.sets",
            appIconColor = 0x2c303b,
            targets = listOf(AppTarget("7.34"))
        )
    )
    dependsOn(
        wildcardHexPatch(block = {
            val libPath = "lib/arm64-v8a/libapp.so"

            // Set default 'is_premium' field to true at sets/screens/main_screen/main_screen.dart:_MainScreenState -> 0x72a5a4
            "C7 C? ?? ?? ?6 6?" asPatternTo "?? 82 ?? ?? ?? ??" inFile libPath

            // Set 'is_premium' true at sets/purchases/purchase_manager.dart:PurchaseManager._internal -> 0x4fc044
            "?7 ?? ?? ?? ?? ?? ?? ?? ?? ?? ?? 54 C2 C?" asPatternTo "?? ?? ?? ?? ?? ?? ?? ?? 1F 20 03 D5 C2 C6" inFile libPath

            // Bypass login check to access AI feature at sets/screens/ai_screen/ai_screen.dart:didChangeDependencies -> 0x533d90
            "02 02 9? ?? A? ?? ?? ?? 2?" asPatternTo "?? 12 ?? ?? ?? ?? ?? ?? ??" inFile libPath
        })
    )
}
