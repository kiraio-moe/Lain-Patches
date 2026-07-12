package kiraio.lain.pixelstudio.pro

import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility
import app.morphe.patcher.patch.rawResourcePatch
import util.wildcardHexPatch

@Suppress("unused")
val proPatch = rawResourcePatch(
    name = "Enable Pro",
    description = "Unlock all pro features.",
    default = true
) {
    compatibleWith(
        Compatibility(
            name = "Pixel Studio",
            packageName = "com.PixelStudio",
            appIconColor = 0xffffff,
            targets = listOf(AppTarget("5.51"))
        )
    )
    dependsOn(
        wildcardHexPatch(block = {
            val ilbil2cpp = "lib/arm64-v8a/libil2cpp.so"

            // Always return 'isPro' true
            "FE 0F 1E F8 F4 4F 01 A9 94 78 00 D0 33 63 00 F0 88 EE 55 39" asPatternTo "20 00 80 52 C0 03 5F D6 ?? ?? ?? ?? ?? ?? ?? ?? ?? ?? ?? ??" inFile ilbil2cpp
        })
    )
}
