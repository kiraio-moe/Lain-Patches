package util

import org.w3c.dom.Document
import org.w3c.dom.Element

object ManifestUtils {
    fun Document.ensureQueries(): Element {
        val manifest = documentElement

        val existing = manifest.getElementsByTagName("queries")
        if (existing.length > 0) {
            return existing.item(0) as Element
        }

        return createElement("queries").also {
            manifest.appendChild(it)
        }
    }

    fun Document.addPackageQuery(packageName: String) {
        val pkg = createElement("package").apply {
            setAttribute("android:name", packageName)
        }

        ensureQueries().appendChild(pkg)
    }
}
