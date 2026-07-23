package kiraio.lain.pixiv.premium

import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.util.returnEarly

@Suppress("unused")
val premiumPatch = bytecodePatch(
    name = "Enable Premium",
    description = "Unlock pixiv premium features such as Search by Popularity, Hide Ads & Tags and see Browsing History.",
    default = true
) {
    compatibleWith(
        Compatibility(
            name = "pixiv",
            packageName = "jp.pxv.android",
            appIconColor = 0x0196fa,
            targets = listOf(AppTarget(null), AppTarget("6.189.0"))
        )
    )
    execute {
        OAuthUserPremiumFingerprint.method.returnEarly(true)
    }
}
