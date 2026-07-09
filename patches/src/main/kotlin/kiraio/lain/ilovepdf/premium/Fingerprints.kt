package kiraio.lain.ilovepdf.premium

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.fieldAccess
import com.android.tools.smali.dexlib2.Opcode

object SetPremiumBooleanFingerprint : Fingerprint(
    filters = listOf(
        fieldAccess(
            name = "premium",
            opcode = Opcode.IPUT_BOOLEAN,
            type = "Z"
        )
    )
)
