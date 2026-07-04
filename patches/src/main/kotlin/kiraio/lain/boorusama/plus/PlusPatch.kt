package kiraio.lain.boorusama.plus

import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility
import app.morphe.patcher.patch.rawResourcePatch
import util.wildcardHexPatch

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
        wildcardHexPatch(block = {
            val libPath = "lib/arm64-v8a/libapp.so"

            // Jump straight to premium check
            "?? 09 ?? ?? 40 ?? ?? ?? ?? 9C" asPatternTo "24 00 ?? 14 40 ?? ?? ?? ?? ??" inFile libPath

            // Always return premium
            "1? C? ?? ?? 1? ?? ?0 ?? A? 00 ?? ?? ?? 8?" asPatternTo "?? ?? ?? ?? ?? 20 03 D5 1F 20 03 D5 ?? ??" inFile libPath
        })
    )
}
