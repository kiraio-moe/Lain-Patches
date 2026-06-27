package kiraio.lain.boorusama.plus

import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility
import app.morphe.patcher.patch.rawResourcePatch
import app.morphe.patches.all.misc.hex.hexPatch

@Suppress("unused")
val plusPatch = rawResourcePatch(
    name = "Unlock Plus",
    description = "Unlock plus features such as Exclusive Themes, Custom Layout & Home Screen, Enhanced Bulk Downloader.",
    default = true
) {
    compatibleWith(
        Compatibility(
            name = "Boorusama (Play Store)",
            packageName = "com.degenk.boorusama",
            appIconColor = 0xe8bf55,
            targets = listOf(AppTarget("4.5.1"))
        )
    )
    dependsOn(
        hexPatch(block = {
            val libPath = "lib/arm64-v8a/libapp.so"

            // Jump straight to premium check
            "40 BE 31 94 E7 FF FF 17 FD 79 BF A9 FD 03 0F AA EF 61 00 D1 50 27 40 F9 FF 01 10 EB 09 09 00 54" asPatternTo "40 BE 31 94 E7 FF FF 17 FD 79 BF A9 FD 03 0F AA EF 61 00 D1 50 27 40 F9 FF 01 10 EB 24 00 00 14" inFile libPath

            // Always return premium
            "EF 03 1D AA FD 79 C1 A8 C0 03 5F D6 70 CF 40 91 10 CE 47 F9 1F 00 10 6B A1 00 00 54 C0 82 00 91" asPatternTo "EF 03 1D AA FD 79 C1 A8 C0 03 5F D6 70 CF 40 91 10 CE 47 F9 1F 20 03 D5 1F 20 03 D5 C0 82 00 91" inFile libPath
        })
    )
}
