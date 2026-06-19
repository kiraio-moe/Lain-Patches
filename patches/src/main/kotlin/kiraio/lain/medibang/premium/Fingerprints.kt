package kiraio.lain.medibang.premium

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.string
import com.android.tools.smali.dexlib2.AccessFlags

object PremiumFingerprint : Fingerprint(
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
    returnType = "Z",
    filters = listOf(
        string("pref_subsc_valid_plan"),
        string("pref_subsc_expired_at")
    )
)

object OnCreateFingerprint : Fingerprint(
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    definingClass = "MedibangPaintApp",
    name = "onCreate",
    returnType = "V"
)
