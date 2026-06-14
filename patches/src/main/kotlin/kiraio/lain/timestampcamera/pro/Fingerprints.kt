package kiraio.lain.timestampcamera.pro

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.fieldAccess
import app.morphe.patcher.methodCall

object CameraCaptureOnCreateFingerprint : Fingerprint(
    definingClass = "Lcom/android/grafika/CameraCaptureActivity;",
    name = "onCreate",
    filters = listOf(
        methodCall(
            definingClass = "Lcom/android/grafika/CameraCaptureActivity;",
            name = "isActive"
        )
    )
)

object IsActiveFingerprint : Fingerprint(
    definingClass = "Lcom/android/grafika/CameraCaptureActivity;",
    name = "isActive"
)
