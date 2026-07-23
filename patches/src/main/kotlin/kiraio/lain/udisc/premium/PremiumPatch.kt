package kiraio.lain.udisc.premium

import app.morphe.patcher.extensions.InstructionExtensions.addInstruction
import app.morphe.patcher.extensions.InstructionExtensions.instructions
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.util.returnEarly
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.TwoRegisterInstruction
import kiraio.lain.udisc.shared.Constants

@Suppress("unused")
val premiumPatch = bytecodePatch(
    name = "Unlock Premium",
    description = "Enable UDisc premium features. NOTICE: Use alongside the universal 'Spoof Install Source' patch to fix the login issue and 'Change Google Maps API Key' patch to fix the maps not rendering.",
    default = true
) {
    compatibleWith(Constants.COMPATIBILITY)
    execute {
        val userAccountClass = UserAccountClassFingerprint.classDef

        // Find j() - has proExpiresAt comparison > 0 with current time
        // Look for: compareTo, new instance creation, > 0 comparison
        val isProMethod = userAccountClass.methods.first { method ->
            method.returnType == "Z" &&
                    method.parameterTypes.isEmpty() &&
                    method.implementation?.instructions?.any { instruction ->
                        instruction.opcode == Opcode.IF_LEZ ||
                                instruction.opcode == Opcode.IF_GTZ
                    } == true
        }

        // Find k() - compares subscription status to enum constant
        // Look for: static field access to Account$Subscription$Status
        val isTrialMethod = userAccountClass.methods.first { method ->
            method.returnType == "Z" &&
                    method.parameterTypes.isEmpty() &&
                    method.implementation?.instructions?.any { instruction ->
                        instruction.opcode == Opcode.SGET_OBJECT
                    } == true
        }

//        val ambassadorCheck = userAccountClass.methods.first { method ->
//            method.returnType == "Z" && method.parameterTypes == listOf("I")
//        }

        isProMethod.returnEarly(true)
        isTrialMethod.returnEarly(false)
//        ambassadorCheck.returnEarly(true)

        // Set WatchAccount to Pro
        val watchIsProIndex = WatchAccountProFingerprint.instructionMatches.first().index
        val watchIsProReg =
            (WatchAccountProFingerprint.method.instructions[watchIsProIndex] as TwoRegisterInstruction).registerA
        WatchAccountProFingerprint.method.addInstruction(
            watchIsProIndex,
            "const/4 v$watchIsProReg, 0x1"
        )
    }
}
