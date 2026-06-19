package kiraio.lain.hiddensettings.premium

import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility
import app.morphe.patcher.patch.rawResourcePatch
import app.morphe.patches.all.misc.hex.hexPatch

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
        hexPatch(block = {
            val libPath = "lib/arm64-v8a/libapp.so"

            // Set default 'is_premium' field to true at sets/screens/main_screen/main_screen.dart:_MainScreenState -> 0x72a5a4
            "EF 61 00 D1 C7 C2 00 91 66 6F 40 91 C6 18 44 F9 C5 82 00 91 64 6F 40 91 84 1C 44 F9 63 6F 40 91" asPatternTo "EF 61 00 D1 C7 82 00 91 66 6F 40 91 C6 18 44 F9 C5 82 00 91 64 6F 40 91 84 1C 44 F9 63 6F 40 91" inFile libPath

            // Set 'is_premium' true at sets/purchases/purchase_manager.dart:PurchaseManager._internal -> 0x4fc044
            "10 4E 44 F9 F0 01 00 F9 64 23 40 F9 A7 2F FF 97 1F 00 16 6B 61 00 00 54 C2 C2 00 91 02 00 00 14" asPatternTo "10 4E 44 F9 F0 01 00 F9 64 23 40 F9 A7 2F FF 97 1F 00 16 6B 1F 20 03 D5 C2 C6 00 91 02 00 00 14" inFile libPath

            // Bypass login check to access AI feature at sets/screens/ai_screen/ai_screen.dart:didChangeDependencies -> 0x533d90
            "02 02 91 9A A1 83 5F F8 22 30 01 B8 E0 03 16 AA EF 03 1D AA FD 79 C1 A8 C0 03 5F D6 97 9C 0C 94" asPatternTo "02 12 91 9A A1 83 5F F8 22 30 01 B8 E0 03 16 AA EF 03 1D AA FD 79 C1 A8 C0 03 5F D6 97 9C 0C 94" inFile libPath
        })
    )
}
