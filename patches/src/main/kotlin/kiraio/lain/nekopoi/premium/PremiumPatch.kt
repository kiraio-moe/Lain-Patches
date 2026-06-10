package kiraio.lain.nekopoi.premium

import app.morphe.patcher.extensions.InstructionExtensions.addInstruction
import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
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
val disableAdsPatch = bytecodePatch(
    name = "Disable Ads",
    description = "Disable banner, splash and video player ads.",
    default = true,
) {
    compatibleWith(Constants.COMPATIBILITY)
    execute {
        AdsFingerprint.method.addInstruction(
            AdsFingerprint.instructionMatches.first().index,
            "return-void"
        )
//        VideoPlayerAdsFingerprint.matchAll().forEach { match ->
//            match.method.addInstructions(
//                0,
//                """
//                    invoke-interface {p2}, Landroid/webkit/WebResourceRequest;->getUrl()Landroid/net/Uri;
//                    move-result-object v0
//
//                    invoke-virtual {v0}, Landroid/net/Uri;->toString()Ljava/lang/String;
//                    move-result-object v0
//
//                    const-string v1, "MORPHE"
//                    invoke-static {v1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I
//                """.trimIndent()
//                """
//                invoke-interface {p2}, Landroid/webkit/WebResourceRequest;->getUrl()Landroid/net/Uri;
//                move-result-object v0
//
//                if-eqz v0, :continue
//
//                invoke-virtual {v0}, Landroid/net/Uri;->toString()Ljava/lang/String;
//                move-result-object v0
//
//                const-string v1, "adsco.re"
//                invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//                move-result v1
//                if-nez v1, :block
//
//                const-string v1, "yweakelandorde.org"
//                invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//                move-result v1
//                if-nez v1, :block
//
//                const-string v1, "dadgah.org"
//                invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//                move-result v1
//                if-nez v1, :block
//
//                const-string v1, "oulapoat.qpon"
//                invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//                move-result v1
//                if-nez v1, :block
//
//                const-string v1, "chiasmiraisers.com"
//                invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//                move-result v1
//                if-nez v1, :block
//
//                const-string v1, "cloudflareinsights.com"
//                invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//                move-result v1
//                if-nez v1, :block
//
//                const-string v1, "cloudfront.net"
//                invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//                move-result v1
//                if-nez v1, :block
//
//                const-string v1, "peoriacommunityagainstviolence.org"
//                invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//                move-result v1
//                if-nez v1, :block
//
//                const-string v1, "subducearabs.shop"
//                invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//                move-result v1
//                if-nez v1, :block
//
//                const-string v1, "tsyndicate.com"
//                invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//                move-result v1
//                if-eqz v1, :continue
//
//                :block
//                const/4 v0, 0x0
//                return-object v0
//
//                :continue
//            """.trimIndent()
//            )
//        }
//        VideoPlayerAds2Fingerprint.matchAll().forEach { match ->
//            match.method.addInstructions(
//                0,
//                """
//        if-eqz p2, :morphe_continue
//
//        invoke-interface {p2}, Landroid/webkit/WebResourceRequest;->getUrl()Landroid/net/Uri;
//        move-result-object v0
//
//        if-eqz v0, :morphe_continue
//
//        invoke-virtual {v0}, Landroid/net/Uri;->toString()Ljava/lang/String;
//        move-result-object v0
//
//        const-string v1, "adsco.re"
//        invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//        move-result v1
//        if-nez v1, :morphe_block
//
//        const-string v1, "yweakelandorde.org"
//        invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//        move-result v1
//        if-nez v1, :morphe_block
//
//        const-string v1, "dadgah.org"
//        invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//        move-result v1
//        if-nez v1, :morphe_block
//
//        const-string v1, "oulapoat.qpon"
//        invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//        move-result v1
//        if-nez v1, :morphe_block
//
//        const-string v1, "tsyndicate.com"
//        invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//        move-result v1
//        if-nez v1, :morphe_block
//
//        const-string v1, "badlandlispyippee.com"
//        invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//        move-result v1
//        if-nez v1, :morphe_block
//
//        const-string v1, "chiasmiraisers.com"
//        invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//        move-result v1
//        if-nez v1, :morphe_block
//
//        const-string v1, "cloudflareinsights.com"
//        invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//        move-result v1
//        if-nez v1, :morphe_block
//
//        const-string v1, "cloudfront.net"
//        invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//        move-result v1
//        if-nez v1, :morphe_block
//
//        const-string v1, "peoriacommunityagainstviolence.org"
//        invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//        move-result v1
//        if-nez v1, :morphe_block
//
//        const-string v1, "subducearabs.shop"
//        invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//        move-result v1
//        if-nez v1, :morphe_block
//
//        goto :morphe_continue
//
//        :morphe_block
//        const/4 v0, 0x1
//        return v0
//
//        :morphe_continue
//        """.trimIndent()
//            )
//        }
//        VideoPlayerAds3Fingerprint.matchAll().forEach { match ->
//            match.method.addInstructions(
//                0,
//                """
//        if-eqz p2, :morphe_continue
//
//        const-string v0, "adsco.re"
//        invoke-virtual {p2, v0}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//        move-result v0
//        if-nez v0, :morphe_block
//
//        const-string v0, "yweakelandorde.org"
//        invoke-virtual {p2, v0}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//        move-result v0
//        if-nez v0, :morphe_block
//
//        const-string v0, "dadgah.org"
//        invoke-virtual {p2, v0}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//        move-result v0
//        if-nez v0, :morphe_block
//
//        const-string v0, "oulapoat.qpon"
//        invoke-virtual {p2, v0}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//        move-result v0
//        if-nez v0, :morphe_block
//
//        const-string v0, "tsyndicate.com"
//        invoke-virtual {p2, v0}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//        move-result v0
//        if-nez v0, :morphe_block
//
//        const-string v0, "badlandlispyippee.com"
//        invoke-virtual {p2, v0}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//        move-result v0
//        if-nez v0, :morphe_block
//
//        const-string v0, "chiasmiraisers.com"
//        invoke-virtual {p2, v0}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//        move-result v0
//        if-nez v0, :morphe_block
//
//        const-string v0, "cloudflareinsights.com"
//        invoke-virtual {p2, v0}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//        move-result v0
//        if-nez v0, :morphe_block
//
//        const-string v0, "cloudfront.net"
//        invoke-virtual {p2, v0}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//        move-result v0
//        if-nez v0, :morphe_block
//
//        const-string v0, "peoriacommunityagainstviolence.org"
//        invoke-virtual {p2, v0}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//        move-result v0
//        if-nez v0, :morphe_block
//
//        const-string v0, "subducearabs.shop"
//        invoke-virtual {p2, v0}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
//        move-result v0
//        if-nez v0, :morphe_block
//
//        goto :morphe_continue
//
//        :morphe_block
//        const/4 v0, 0x1
//        return v0
//
//        :morphe_continue
//        """.trimIndent()
//            )
//        }
        BannerAdsFingerprint.matchAll().forEach { match -> match.method.returnEarly() }
        UnityAdsFingerprint.matchAll().forEach { match -> match.method.returnEarly() }
    }
}
