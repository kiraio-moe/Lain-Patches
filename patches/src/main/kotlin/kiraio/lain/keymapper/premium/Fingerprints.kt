package kiraio.lain.keymapper.premium

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.methodCall

object PremiumFingerprint : Fingerprint(
    returnType = "Z",
    filters = listOf(
        methodCall(
            definingClass = "Lcom/revenuecat/purchases/CustomerInfo;",
            name = "getEntitlements"
        ),
        methodCall(
            definingClass = "Lcom/revenuecat/purchases/EntitlementInfo;",
            name = "isActive"
        )
    )
)
