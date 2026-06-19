package kiraio.lain.medibang.premium

import app.morphe.patcher.extensions.InstructionExtensions.removeInstruction
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.util.indexOfFirstInstruction
import app.morphe.util.returnEarly
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.ReferenceInstruction
import com.android.tools.smali.dexlib2.iface.reference.MethodReference
import kiraio.lain.medibang.shared.Constants

val premiumPatch = bytecodePatch(
    name = "Unlock Premium",
    description = "Unlock premium features such as No Ads, Premium Brushes, Filters & Fonts; Simultaneous Layer Operation. Server-side features is NOT included!",
    default = true
) {
    compatibleWith(Constants.COMPATIBILITY)
    execute {
        PremiumFingerprint.method.returnEarly(true)
    }
}

val disableAnalyticsPatch = bytecodePatch(
    name = "Disable Analytics",
    description = "Disables app tracker, Google & Firebase Analytics",
    default = false
) {
    compatibleWith(Constants.COMPATIBILITY)
    execute {
        val method = OnCreateFingerprint.method

        val start1 = method.indexOfFirstInstruction(0, Opcode.INVOKE_STATIC)
        val end1 = method.indexOfFirstInstruction(start1) {
            val ref = (this as? ReferenceInstruction)?.reference as? MethodReference
            ref?.definingClass == "Lcom/google/android/gms/analytics/Tracker;" &&
                    ref.name == "enableAdvertisingIdCollection"
        }
        val start2 = method.indexOfFirstInstruction(end1) {
            val ref = (this as? ReferenceInstruction)?.reference as? MethodReference
            ref?.definingClass == "Lcom/facebook/ads/AudienceNetworkAds;" &&
                    ref.name == "isInitialized"
        }
        val end2 = method.indexOfFirstInstruction(start2) {
            val ref = (this as? ReferenceInstruction)?.reference as? MethodReference
            ref?.definingClass == "Lcom/google/firebase/analytics/FirebaseAnalytics;" &&
                    ref.name == "logEvent"
        }

        for (i in end2 downTo start2) {
            method.removeInstruction(i)
        }

        for (i in end1 downTo start1) {
            method.removeInstruction(i)
        }
    }
}
