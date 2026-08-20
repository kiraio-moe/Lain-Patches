package kiraio.lain.all.manifest

import app.morphe.patcher.patch.PatchException
import app.morphe.patcher.patch.resourcePatch
import app.morphe.patcher.patch.stringOption
import org.w3c.dom.Element
import java.lang.System.err

@Suppress("unused")
val googleMapsPatch = resourcePatch(
    name = "Change Google Maps API Key",
    description = "Fix Google Maps not rendered on the application because of different app signature.",
    default = false
) {
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
                val name = node.getAttribute("android:name")

                if (name == "com.google.android.maps.v2.API_KEY" || name == "com.google.android.geo.API_KEY") {
                    node.setAttribute("android:value", apiKey)
                    return@execute
                }
            }

            err.println("WARNING: No Google Maps attributes found at Manifest.")
        }
    }
}
