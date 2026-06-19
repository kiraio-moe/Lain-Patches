package kiraio.lain.nekopoi.premium

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.methodCall
import app.morphe.patcher.string
import com.android.tools.smali.dexlib2.AccessFlags

object AntiTamperFingerprint : Fingerprint(
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    returnType = "V",
    filters = listOf(
        methodCall(
            definingClass = "Ljava/util/Timer;",
            name = "schedule"
        ),
        methodCall(
            definingClass = "Landroid/widget/Toast;",
            name = "show"
        )
    )
)

object AdsFingerprint : Fingerprint(
    definingClass = "AdController",
    accessFlags = listOf(AccessFlags.PUBLIC),
    returnType = "V",
    filters = listOf(
        string("AdController")
    )
)

object BannerAdsFingerprint : Fingerprint(
    accessFlags = listOf(AccessFlags.PUBLIC),
    returnType = "V",
    filters = listOf(
        methodCall(
            definingClass = "Landroid/webkit/WebSettings;",
            name = "setSupportMultipleWindows"
        )
    ),
    strings = listOf("browser")
)

object UnityAdsFingerprint : Fingerprint(
    custom = { method, _ ->
        method.definingClass == "Lcom/unity3d/ads/UnityAds;" &&
                method.name == "initialize"
    }
)

object VideoAdsFingerprint : Fingerprint(
    name = "shouldInterceptRequest",
    parameters = listOf("Landroid/webkit/WebView;", "Landroid/webkit/WebResourceRequest;"),
    returnType = "Landroid/webkit/WebResourceResponse;",
    filters = listOf(
        methodCall(
            definingClass = "Landroid/webkit/WebViewClient;",
            name = "shouldInterceptRequest"
        )
    )
)

object VideoAds2Fingerprint : Fingerprint(
    name = "shouldInterceptRequest",
    parameters = listOf("Landroid/webkit/WebView;", "Ljava/lang/String;"),
    returnType = "Landroid/webkit/WebResourceResponse;",
    filters = listOf(
        methodCall(
            definingClass = "Landroid/webkit/WebViewClient;",
            name = "shouldInterceptRequest"
        )
    )
)