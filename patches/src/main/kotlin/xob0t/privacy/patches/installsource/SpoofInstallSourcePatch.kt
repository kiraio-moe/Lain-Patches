package xob0t.privacy.patches.installsource

import app.morphe.patcher.extensions.InstructionExtensions.instructionsOrNull
import app.morphe.patcher.extensions.InstructionExtensions.replaceInstruction
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.patch.stringOption
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.Method
import com.android.tools.smali.dexlib2.iface.instruction.Instruction
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction
import com.android.tools.smali.dexlib2.iface.instruction.ReferenceInstruction
import com.android.tools.smali.dexlib2.iface.reference.MethodReference

private const val PLAY_STORE_PACKAGE = "com.android.vending"
private const val PACKAGE_MANAGER = "Landroid/content/pm/PackageManager;"
private const val INSTALL_SOURCE_INFO = "Landroid/content/pm/InstallSourceInfo;"

private fun Instruction.methodReferenceOrNull(): MethodReference? =
    (this as? ReferenceInstruction)?.reference as? MethodReference

private fun Instruction.isInstallSourcePatchTarget(): Boolean {
    if (opcode !in setOf(Opcode.INVOKE_VIRTUAL, Opcode.INVOKE_VIRTUAL_RANGE)) return false

    val reference = methodReferenceOrNull() ?: return false
    return reference.isPackageManagerGetInstallerPackageName() ||
            reference.isInstallSourceInfoPackageGetter()
}

private fun MethodReference.isPackageManagerGetInstallerPackageName() =
    definingClass == PACKAGE_MANAGER &&
            name == "getInstallerPackageName" &&
            parameterTypes.size == 1 &&
            parameterTypes[0].toString() == "Ljava/lang/String;" &&
            returnType == "Ljava/lang/String;"

private fun MethodReference.isInstallSourceInfoPackageGetter() =
    definingClass == INSTALL_SOURCE_INFO &&
            name in setOf(
        "getInitiatingPackageName",
        "getInstallingPackageName",
        "getOriginatingPackageName",
        "getUpdateOwnerPackageName",
    ) &&
            parameterTypes.isEmpty() &&
            returnType == "Ljava/lang/String;"

private fun Method.hasInstallSourcePatchTarget(): Boolean =
    instructionsOrNull?.any { it.isInstallSourcePatchTarget() } == true

@Suppress("unused")
val spoofInstallSourcePatch = bytecodePatch(
    name = "Spoof Install Source",
    description = "Spoofs package installer checks to report the configured package name as the installation source. Default is Play Store.",
    default = false,
) {
    val installerPackageName by stringOption(
        "packageName",
        PLAY_STORE_PACKAGE,
        mapOf(PLAY_STORE_PACKAGE to PLAY_STORE_PACKAGE),
        title = "Installer Package Name",
        description = "The package name of the installation source."
    )

    execute {
        var patchedInstallerPackageNameReads = 0

        classDefForEach { classDef ->
            if (classDef.methods.none { it.hasInstallSourcePatchTarget() }) return@classDefForEach

            mutableClassDefBy(classDef).methods.forEach { method ->
                if (!method.hasInstallSourcePatchTarget()) return@forEach

                val instructions = method.instructionsOrNull ?: return@forEach
                val instructionList = instructions.toList()

                instructionList.forEachIndexed { index, instruction ->
                    if (!instruction.isInstallSourcePatchTarget()) return@forEachIndexed

                    val reference = instruction.methodReferenceOrNull() ?: return@forEachIndexed

                    when {
                        reference.isPackageManagerGetInstallerPackageName() ||
                                reference.isInstallSourceInfoPackageGetter() -> {
                            val moveResult = instructionList.getOrNull(index + 1) as? OneRegisterInstruction
                                ?: return@forEachIndexed
                            if (moveResult.opcode != Opcode.MOVE_RESULT_OBJECT) return@forEachIndexed

                            method.replaceInstruction(
                                index + 1,
                                "const-string v${moveResult.registerA}, \"$installerPackageName\"",
                            )
                            patchedInstallerPackageNameReads++
                        }
                    }
                }
            }
        }

        if (patchedInstallerPackageNameReads == 0) {
            println("Spoof install source: no install source call sites were found.")
            return@execute
        }

        println(
            "Spoof install source: patched $patchedInstallerPackageNameReads installer package reads.",
        )
    }
}
