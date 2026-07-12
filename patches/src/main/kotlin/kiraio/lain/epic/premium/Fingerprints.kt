package kiraio.lain.epic.premium

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.fieldAccess
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode

object RealSubscriptionStatusFingerprint : Fingerprint(
    filters = listOf(
        fieldAccess(
            name = "realSubscriptionStatus",
            opcode = Opcode.IGET
        ),
    )
)

object RegionRestrictedFingerprint : Fingerprint(
    filters = listOf(
        fieldAccess(
            name = "regionRestricted",
            opcode = Opcode.IGET
        )
    )
)
