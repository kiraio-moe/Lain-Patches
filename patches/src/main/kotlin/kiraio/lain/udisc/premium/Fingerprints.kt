package kiraio.lain.udisc.premium

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.fieldAccess
import com.android.tools.smali.dexlib2.AccessFlags

object UserAccountClassFingerprint : Fingerprint(
    strings = listOf("Trial", "Pro", "Basic")
)

object WatchAccountProFingerprint : Fingerprint(
    definingClass = "WatchAccountInfo",
    name = "<init>",
    filters = listOf(
        fieldAccess(
            name = "isPro"
        )
    )
)

object AccountSubscriptionFingerprint : Fingerprint(
    accessFlags = listOf(
        AccessFlags.PUBLIC,
        AccessFlags.SYNTHETIC,
        AccessFlags.CONSTRUCTOR
    ),
    name = "<init>",
    parameters = listOf(
        "I",
        $$"Lcom/udisc/kmp/account/Account$Subscription$Platform;",
        $$"Lcom/udisc/kmp/account/Account$Subscription$Status;",
        "Ljava/lang/String;"
    )
)
