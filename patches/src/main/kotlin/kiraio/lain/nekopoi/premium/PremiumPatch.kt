package kiraio.lain.nekopoi.premium

import app.morphe.patcher.extensions.InstructionExtensions.addInstruction
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.patch.resourcePatch
import app.morphe.patches.all.misc.network.overrideCertificatePinningPatch
import app.morphe.util.returnEarly
import kiraio.lain.nekopoi.shared.Constants

@Suppress("unused")
val disableTamperDetectionPatch = bytecodePatch(
    name = "Disable Anti-Tamper Detection",
    description = "Disable Anti-Tamper detection that force quit the app.",
    default = true,
) {
    compatibleWith(Constants.COMPATIBILITY)
    execute {
        AntiTamperFingerprint.method.returnEarly()
    }
}

@Suppress("unused")
val enableCertificatePinning = resourcePatch(
    name = "Enable Certificate Pinning",
    description = "Allow app network traffic to be inspected.",
    default = true,
) {
    compatibleWith(Constants.COMPATIBILITY)
    execute {
        overrideCertificatePinningPatch.execute(this)
    }
}

@Suppress("unused")
val disableAdsPatch = bytecodePatch(
    name = "Disable Ads",
    description = "Disable banner & splash ads.",
    default = true,
) {
    compatibleWith(Constants.COMPATIBILITY)
    execute {
        AdsFingerprint.method.addInstruction(
            AdsFingerprint.instructionMatches.first().index,
            "return-void"
        )
        BannerAdsFingerprint.matchAll().forEach { match -> match.method.returnEarly() }
        UnityAdsFingerprint.matchAll().forEach { match -> match.method.returnEarly() }

//        // For shouldInterceptRequest(WebView, WebResourceRequest)
//        val ins1 = """
//            invoke-interface {p2}, Landroid/webkit/WebResourceRequest;->getUrl()Landroid/net/Uri;
//            move-result-object v0
//            invoke-virtual {v0}, Landroid/net/Uri;->toString()Ljava/lang/String;
//            move-result-object v0
//            const-string v1, "sv.chiasmiraisers.com"
//            invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//            move-result v1
//            if-eqz v1, :cond_return_null
//            const-string v1, "peoriacommunityagainstviolence.org"
//            invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//            move-result v1
//            if-eqz v1, :cond_return_null
//            const-string v1, "df6pt2obl092n.cloudfront.net"
//            invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//            move-result v1
//            if-eqz v1, :cond_return_null
//            const-string v1, "cdn.tsyndicate.com"
//            invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//            move-result v1
//            if-eqz v1, :cond_return_null
//            const-string v1, "i.doodcdn.io/ads/"
//            invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//            move-result v1
//            if-eqz v1, :cond_return_null
//            const-string v1, "badlandlispyippee.com"
//            invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//            move-result v1
//            if-eqz v1, :cond_return_null
//            const-string v1, "ma.hillaryluvian.shop"
//            invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//            move-result v1
//            if-eqz v1, :cond_return_null
//            const-string v1, "blockadsnot.com"
//            invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//            move-result v0
//            if-eqz v0, :cond_return_null
//            goto :cond_continue
//            :cond_return_null
//            const/4 v0, 0x0
//            return-object v0
//            :cond_continue
//        """.trimIndent()
//
//        // For shouldInterceptRequest(WebView, String)
//        // Note: p1 is WebView, p2 is the URL String
//        val ins2 = """
//            move-object v0, p2
//            const-string v1, "sv.chiasmiraisers.com"
//            invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//            move-result v1
//            if-eqz v1, :cond_return_null
//            const-string v1, "peoriacommunityagainstviolence.org"
//            invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//            move-result v1
//            if-eqz v1, :cond_return_null
//            const-string v1, "df6pt2obl092n.cloudfront.net"
//            invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//            move-result v1
//            if-eqz v1, :cond_return_null
//            const-string v1, "cdn.tsyndicate.com"
//            invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//            move-result v1
//            if-eqz v1, :cond_return_null
//            const-string v1, "i.doodcdn.io/ads/"
//            invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//            move-result v1
//            if-eqz v1, :cond_return_null
//            const-string v1, "badlandlispyippee.com"
//            invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//            move-result v1
//            if-eqz v1, :cond_return_null
//            const-string v1, "ma.hillaryluvian.shop"
//            invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//            move-result v1
//            if-eqz v1, :cond_return_null
//            const-string v1, "blockadsnot.com"
//            invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//            move-result v0
//            if-eqz v0, :cond_return_null
//            goto :cond_continue
//            :cond_return_null
//            const/4 v0, 0x0
//            return-object v0
//            :cond_continue
//        """.trimIndent()
//
//        // Apply to both methods
//        VideoAdsFingerprint.match().method.addInstructions(0, ins1)
//        VideoAds2Fingerprint.match().method.addInstructions(0, ins2)
    }
}
