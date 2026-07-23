package kiraio.lain.pixiv.premium

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.fieldAccess

object OAuthUserPremiumFingerprint : Fingerprint(
    definingClass = "Ljp/pxv/android/domain/auth/entity/OAuthUser;",
    returnType = "Z",
    parameters = listOf(),
    filters = listOf(
        fieldAccess(
            name = "isPremium"
        )
    )
)
