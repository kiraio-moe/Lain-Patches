package kiraio.lain.sketchbook.premium

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.methodCall

object PremiumFingerprint : Fingerprint(
    returnType = "Z",
    filters = listOf(
        methodCall(
            definingClass = "Ljava/util/stream/Stream;",
            name = "anyMatch"
        )
    )
)
