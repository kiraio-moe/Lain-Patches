package kiraio.lain.pixellab.shared

import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility

object Constants {
    val COMPATIBILITY = Compatibility(
        name = "PixelLab",
        packageName = "com.imaginstudio.imagetools.pixellab",
        appIconColor = 0x3fa2e4,
        targets = listOf(AppTarget(null), AppTarget("2.1.9"))
    )
}
