package kiraio.lain.cinemafv5lite.pro

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.literal
import app.morphe.patcher.string
import com.android.tools.smali.dexlib2.AccessFlags

object VideoResolutionFingerprint : Fingerprint(
    accessFlags = listOf(AccessFlags.PUBLIC),
    filters = listOf(
//        string("show_extended_video_resolutions"),
        literal(921600)
    )
)

object BuyProFingerprint : Fingerprint(
    accessFlags = listOf(AccessFlags.PUBLIC),
    filters = listOf(
        string("buy_pro")
    )
)
