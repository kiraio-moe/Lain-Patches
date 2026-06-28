package kiraio.lain.udisc.premium

import app.morphe.patcher.extensions.InstructionExtensions.addInstruction
import app.morphe.patcher.extensions.InstructionExtensions.instructions
import app.morphe.patcher.patch.PatchException
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.patch.resourcePatch
import app.morphe.patcher.patch.stringOption
import app.morphe.util.returnEarly
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.TwoRegisterInstruction
import kiraio.lain.udisc.shared.Constants
import org.w3c.dom.Element
import xob0t.privacy.patches.installsource.spoofInstallSourcePatch

@Suppress("unused")
val loginPatch = bytecodePatch(
    name = "Login Fix",
    description = "Fix login issue by spoofing the installation source to Play Store.",
    default = true
) {
    compatibleWith(Constants.COMPATIBILITY)
    execute {
        spoofInstallSourcePatch.execute(this)
    }
}

@Suppress("unused")
val premiumPatch = bytecodePatch(
    name = "Unlock Premium",
    description = "Enable premium features.",
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

//        AccountSubscriptionFingerprint.method.addInstructions(
//            0,
//            $$"""
//                const-string p2, "GOOGLE_PLAY_STORE"
//
//                invoke-static {p2}, Lcom/udisc/kmp/account/Account$Subscription$Platform;->valueOf(Ljava/lang/String;)Lcom/udisc/kmp/account/Account$Subscription$Platform;
//
//                move-result-object p2
//
//                const-string p3, "SUBSCRIBED"
//
//                invoke-static {p3}, Lcom/udisc/kmp/account/Account$Subscription$Status;->valueOf(Ljava/lang/String;)Lcom/udisc/kmp/account/Account$Subscription$Status;
//
//                move-result-object p3
//            """.trimIndent()
//        )
    }
}

@Suppress("unused")
val mapsPatch = resourcePatch(
    name = "Course Maps Fix",
    description = "Fix course maps not rendered by using your own Google Maps API key.",
    default = true
) {
    compatibleWith(Constants.COMPATIBILITY)
    val apiKeyOption by stringOption(
        "apiKey",
        null,
        title = "Google Maps API Key",
        description = "The API key you created in Google Cloud Console. " +
                "The Free key should be enough for normal daily use. DON\'T SHARE YOUR OWN KEY!\n" +
                "In the Google Cloud Console: Create a project -> Enable Maps SDK for Android " +
                "-> A billing account is required, you will be prompted to create one if none exists (You will not get charged as long the monthly $200 limit not passed, use wisely) " +
                "-> Under Credentials, create an API key. The easiest way is to leave it unrestricted. If you do, fill in the patched app package name and SHA-1 signature. " +
                "-> If the maps still blank, either the app signature is invalid; the project missing Maps SDK for Android or billing account.",
        required = true
    )
    execute {
        if (apiKeyOption.isNullOrBlank())
            throw PatchException("No API key is provided! Please create one in https://console.cloud.google.com/google/maps-apis/.")

        val apiKey = apiKeyOption?.trim()
        val manifest = document("AndroidManifest.xml").use {
            val application = it.getElementsByTagName("application").item(0) as Element
            val metaData = application.getElementsByTagName("meta-data")

            for (i in 0 until metaData.length) {
                val node = metaData.item(i) as Element

                if (node.getAttribute("android:name") == "com.google.android.maps.v2.API_KEY") {
                    node.setAttribute("android:value", apiKey)
                    break
                }
            }
        }
    }
}
