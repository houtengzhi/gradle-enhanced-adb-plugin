package com.yechy.gradleplugin.adb.task

import com.yechy.gradleplugin.adb.Adb
import com.yechy.gradleplugin.adb.BaseApkTask
import com.yechy.gradleplugin.adb.GLog
import org.gradle.api.file.FileTree
import org.gradle.api.tasks.TaskAction

class InstallApkTask extends BaseApkTask {

    @TaskAction
    void doInstall() {
        File apkFile = getApkFile()
        if (apkFile == null || !apkFile.exists()) {
            GLog.e(project.getLogger(), "Warn: ${apkFile.getName()} not exist.")
            return
        }
        connectDevice()
        installApkFile(apkFile)
    }

    File getApkFile() {
        File apk = null
        variant.outputs.all { output ->
            if (output.outputFile.getName().endsWith('.apk')) {
                apk = output.outputFile
            }
        }
        return apk;
    }

    void installApkFile(File apkFile) {
        GLog.i("Start install ${apkFile.getAbsolutePath()}")

        if (configExt.isSystemApp) {
            Adb.pushFile(apkFile.getAbsolutePath(), '/system/app')
            installLibFile(apkFile)

        } else {
            Adb.installApk(apkFile.getAbsolutePath())
            if (configExt.runAfterInstall) {
                run()
            }
        }

        if (configExt.rebootAfterInstall) {
            Adb.reboot()
        }
    }

    def installLibFile(File apkFile) {

        FileTree apkTree = project.zipTree(apkFile)

        GLog.d("Shared library:\n")
        apkTree.each { File file ->
            GLog.d("${file.getAbsolutePath()}")
            if (file.isFile() && file.name.endsWith('.so')) {
                Adb.pushFile(file.getAbsolutePath(), '/vender/lib')
            }
        }
    }

    def run() {

        if (configExt.launchActivity == null) {
            GLog.e(project.getLogger(), "Launch activity has not been configured")
            return
        }

        String component = "${variant.applicationId}/${configExt.launchActivity}"
        Adb.run(component)
    }

}
