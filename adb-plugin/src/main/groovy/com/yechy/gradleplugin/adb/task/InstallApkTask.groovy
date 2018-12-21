package com.yechy.gradleplugin.adb.task

import com.yechy.gradleplugin.adb.Adb
import com.yechy.gradleplugin.adb.ConfigExt
import com.yechy.gradleplugin.adb.GLog
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.file.FileTree
import org.gradle.api.tasks.TaskAction

class InstallApkTask extends DefaultTask {

    String assembleTaskName;

    ConfigExt configExt;

    @TaskAction
    void doInstall() {
        configExt = project.adbPlugin
        File apkFile = getApkFile(project)
        installApkFile(apkFile)
    }

    File getApkFile(Project project) {
        File apk = null
        project.android.applicationVariants.all {
            variant ->
                variant.outputs.all {
                    output ->
                        if (output.assemble.name == assembleTaskName) {
                            apk = output.outputFile
                        }
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
