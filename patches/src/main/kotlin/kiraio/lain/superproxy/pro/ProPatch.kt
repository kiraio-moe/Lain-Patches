package kiraio.lain.superproxy.pro

import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility
import app.morphe.patcher.patch.rawResourcePatch
import app.morphe.patches.all.misc.hex.hexPatch

@Suppress("unused")
val proPatch = rawResourcePatch(
    name = "Unlock Pro",
    description = "Unlock pro features such as Unlimited proxy usage, Allow/disallow specific apps, Block apps in firewall and many more...",
    default = true
) {
    compatibleWith(
        Compatibility(
            name = "Super Proxy",
            packageName = "com.scheler.superproxy",
            appIconColor = 0x0078b7,
            targets = listOf(AppTarget("3.2.2"))
        )
    )
    dependsOn(
        hexPatch(block = {
            val libPath = "lib/arm64-v8a/libapp.so"

            // Always set entitlement to true
            "09 0D 00 54 41 70 40 B8 21 80 1C 8B 21 06 20 37" asPatternTo "09 0D 00 54 41 70 40 B8 21 80 1C 8B 1F 20 03 D5" inFile libPath
        })
    )
}
