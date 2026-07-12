package kiraio.lain.mangaplus.shared

import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility

object Constants {
    val COMPATIBILITY = Compatibility(
        name = "Manga Plus",
        packageName = "jp.co.shueisha.mangaplus",
        appIconColor = 0xffffff,
        targets = listOf(AppTarget(null), AppTarget("2.4.1"))
    )
}
