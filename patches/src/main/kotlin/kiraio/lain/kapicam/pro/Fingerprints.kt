package kiraio.lain.kapicam.pro

import app.morphe.patcher.Fingerprint

object VipFingerprint : Fingerprint(
    definingClass = "Lcom/sensemobile/network/TokenRequest;",
    strings = listOf("key_vip2"),
    returnType = "Z"
)

object ForeverVipFingerprint : Fingerprint(
    definingClass = "Lcom/sensemobile/network/TokenRequest;",
    strings = listOf("key_lab_simulate_forever_vip"),
    returnType = "Z"
)
