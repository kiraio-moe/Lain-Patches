package kiraio.lain.cinemafv5lite.shared

import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility

object Constants {
    val COMPATIBILITY = Compatibility(
        name = "Cinema FV-5 Lite",
        packageName = "com.flavionet.android.cinema.lite",
        appIconColor = 0xf01010,
        targets = listOf(AppTarget("2.2.1"))
    )
}
