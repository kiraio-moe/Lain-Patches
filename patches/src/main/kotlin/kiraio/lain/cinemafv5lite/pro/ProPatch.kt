package kiraio.lain.cinemafv5lite.pro

import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.patch.resourcePatch
import app.morphe.util.insertLiteralOverride
import kiraio.lain.cinemafv5lite.shared.Constants
import util.ManifestUtils.addPackageQuery

@Suppress("unused")
val unlockHigherResolutionPatch = bytecodePatch(
    name = "Higher Video Resolution",
    description = "Unlock higher resolutions that limited by the app.",
    default = true,
) {
    compatibleWith(Constants.COMPATIBILITY)
    execute {
        val videoResolutionMatches =
            VideoResolutionFingerprint.matchAll()

        // Actual method contains limiting resolutions
        videoResolutionMatches[0].method.insertLiteralOverride(
            videoResolutionMatches[0].instructionMatches.first().index - 2,
            true
        )
        // Method that populate resolutions list
        videoResolutionMatches[1].method.insertLiteralOverride( // Hide '[PRO]' prefix on resolutions list
            videoResolutionMatches[1].instructionMatches.first().index - 2,
            false
        )

        val buyProMatch = BuyProFingerprint.match() // Hide 'Buy Pro version'
        buyProMatch.method.insertLiteralOverride(
            buyProMatch.instructionMatches.first().index - 2,
            false
        )
    }
}

@Suppress("unused")
val fixCameraModeIntent = resourcePatch(
    name = "Fix Camera Mode Intent",
    description = "Fix opening Camera FV-5 Lite when entering camera mode.",
    default = true,
) {
    compatibleWith(Constants.COMPATIBILITY)
    execute {
        document("AndroidManifest.xml").use {
            it.addPackageQuery("com.flavionet.android.camera.lite")
        }
    }
}
