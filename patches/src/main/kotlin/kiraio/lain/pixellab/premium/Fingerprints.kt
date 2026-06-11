package kiraio.lain.pixellab.premium

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.literal

object MainActivityOnCreateFingerprint : Fingerprint(
    custom = { method, _ ->
        method.name == "onCreate" &&
                method.definingClass ==
                "Lcom/imaginstudio/imagetools/pixellab/MainActivity;"
    }
)

object MainActivityCreateMainMenuFingerprint : Fingerprint(
    custom = { method, _ ->
        method.name == "createMainMenu" &&
                method.definingClass ==
                "Lcom/imaginstudio/imagetools/pixellab/MainActivity;"
    },
    filters = listOf(
        literal(0x7f11002f)
    )
)
