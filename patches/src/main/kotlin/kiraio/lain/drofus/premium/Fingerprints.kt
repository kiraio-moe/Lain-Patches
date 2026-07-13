package kiraio.lain.drofus.premium

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.methodCall
import com.android.tools.smali.dexlib2.AccessFlags

object PremiumFingerprint : Fingerprint(
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
    returnType = "Z",
    filters = listOf(
        methodCall(
            definingClass = "Landroidx/lifecycle/LiveData;",
            name = "getValue"
        )
    )
)
