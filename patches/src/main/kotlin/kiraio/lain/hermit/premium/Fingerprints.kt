package kiraio.lain.hermit.premium

import app.morphe.patcher.Fingerprint

object PremiumFingerprint : Fingerprint(
    strings = listOf("UNKNOWN", "ERROR", "NOT_PURCHASED")
)
