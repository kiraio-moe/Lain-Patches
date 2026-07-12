package kiraio.lain.keymate.premium

import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility
import app.morphe.patcher.patch.rawResourcePatch
import hoodles.morphe.patches.all.pairip.license.disableLicenseCheckPatch
import util.wildcardHexPatch

@Suppress("unused")
val premiumPatch = rawResourcePatch(
    name = "Enable Premium",
    description = "Unlock all features in Pro.",
    default = true
) {
    compatibleWith(
        Compatibility(
            name = "Keymate",
            packageName = "net.nemostudio.keymate",
            appIconColor = 0x5281b3,
            targets = listOf(AppTarget("1.2.0"))
        )
    )
    dependsOn(
        wildcardHexPatch(block = {
            val bundlePath = "assets/index.android.bundle"

            // Always return true at syncCustomerInfo()
            "26 00 AE 37 DC 74 9D 1A" asPatternTo "17 ?? ?? ?? ?? ?? ?? ??" inFile bundlePath
            "2E 1C 01 00 00 00 01 00 00 00 03 00 00 00 01 4D 00" asPatternTo "00 ?? ?? ?? ?? ?? ?? ?? ?? ?? ?? ?? ?? ?? ?? ?? ??" inFile bundlePath
            "29 00 01 2E 04 00 06 76 00 6C 03 01 53 03" asPatternTo "78 03 40 01 03 69 7F 53 01 02 00 01 5C 00" inFile bundlePath
        })
    )
    dependsOn(disableLicenseCheckPatch)
}
