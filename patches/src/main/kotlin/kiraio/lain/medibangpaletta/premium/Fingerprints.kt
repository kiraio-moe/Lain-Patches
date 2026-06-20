package kiraio.lain.medibangpaletta.premium

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.methodCall
import app.morphe.patcher.string
import com.android.tools.smali.dexlib2.AccessFlags

object PremiumFingerprint : Fingerprint(
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    parameters = listOf("Ljava/lang/Object;"),
    name = "invokeSuspend",
    filters = listOf(
        methodCall(
            definingClass = "Lcom/medibang/android/medibangpro/domain/model/billing/BillingPurchase;",
            name = "isPurchased"
        ),
        string("Purchased user detected: stop trial timer and dismiss trial UI"),
    )
)
