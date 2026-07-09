package kiraio.lain.mangaplus.max

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.literal
import app.morphe.patcher.opcode
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode

object DeluxeFingerprint : Fingerprint(
    parameters = listOf("Ljava/lang/String;"),
    strings = listOf("deluxe", "standard")
)

object FreeReadFingerprint : Fingerprint(
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.SYNTHETIC, AccessFlags.CONSTRUCTOR),
    name = "<init>",
    returnType = "V",
    filters = listOf(
        literal(0x8000),
        opcode(Opcode.IF_NEZ),
        opcode(Opcode.RETURN_VOID),
        opcode(Opcode.GOTO)
    )
)
