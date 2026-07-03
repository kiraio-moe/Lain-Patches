package kiraio.lain.ilovepdf.premium

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.fieldAccess
import com.android.tools.smali.dexlib2.Opcode

object SetPremiumObjectFingerprint : Fingerprint(
    filters = listOf(
        fieldAccess(
            name = "premium",
            opcode = Opcode.IPUT_OBJECT,
            type = "Ljava/lang/Boolean;"
        )
    )
)

object SetPremiumBooleanFingerprint : Fingerprint(
    filters = listOf(
        fieldAccess(
            name = "premium",
            opcode = Opcode.IPUT_BOOLEAN,
            type = "Z"
        )
    )
)

object SetIsPremiumFingerprint : Fingerprint(
    filters = listOf(
        fieldAccess(
            name = "isPremium",
            opcode = Opcode.IPUT_BOOLEAN,
            type = "Z"
        )
    )
)

object SetIsPremiumAltFingerprint : Fingerprint(
    filters = listOf(
        fieldAccess(
            name = $$"$isPremium",
            opcode = Opcode.IPUT_BOOLEAN,
            type = "Z"
        )
    )
)
