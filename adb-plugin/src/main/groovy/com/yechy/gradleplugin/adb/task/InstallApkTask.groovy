package com.yechy.gradleplugin.adb.task

import com.yechy.gradleplugin.adb.Adb
import com.yechy.gradleplugin.adb.BaseApkTask
import com.yechy.gradleplugin.adb.GLog
import org.gradle.api.file.FileTree
import org.gradle.api.tasks.TaskAction

class InstallApkTask extends BaseApkTask {

    @TaskAction
    void doInstall() {
        configExt = project.adbPlugin
        File apkFile = getApkFile()
        installApkFile(apkFile)
    }

    File getApkFile() {
        File apk = null
        variant.outputs.all { output ->
                if (output.name.endsWith('.apk')) {
                    apk = output.outputFile
                }
        }
        return apk;
    }

    void installApkFile(File apkFile) {
        if (apkFile == null || !apkFile.exists()) {
            GLog.e(project.getLogger(), "Apk not exist")
            return
        }

        GLog.i("Start install ${apkFile.getAbsolutePath()}")

        if (configExt.isSystemApp) {
            Adb.pushFile(apkFile.getAbsolutePath(), '/system/app')
            installLibFile(apkFile)

        } else {
            Adb.installApk(apkFile.getAbsolutePath())
        }

        if (configExt.reboot) {
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

}
