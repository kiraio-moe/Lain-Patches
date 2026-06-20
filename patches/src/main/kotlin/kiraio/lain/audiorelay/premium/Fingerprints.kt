package kiraio.lain.audiorelay.premium

import app.morphe.patcher.Fingerprint
import com.android.tools.smali.dexlib2.AccessFlags

object PremiumFingerprint : Fingerprint(
        strings = listOf("PremiumResponse(value=")
//    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL)
)