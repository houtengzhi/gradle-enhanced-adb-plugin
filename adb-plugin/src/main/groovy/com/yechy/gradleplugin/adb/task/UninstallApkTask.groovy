package com.yechy.gradleplugin.adb.task

import com.android.builder.testing.api.DeviceException
import com.yechy.gradleplugin.adb.Adb
import com.yechy.gradleplugin.adb.BaseApkTask
import org.gradle.api.tasks.TaskAction

class UninstallApkTask extends BaseApkTask {

    @TaskAction
    def doUninstall() throws DeviceException{
        configExt = project.adbPlugin
        uninstall()
    }

    def uninstall() {
        if (configExt.isSystemApp) {
            String apkInstallPath = "/system/app/${configExt.apkPrefix}*"
            Adb.deleteFile(apkInstallPath)

            String dataPath = "/data/data/${variant.applicationId}"

        } else {
            Adb.uninstallApk(variant.applicationId)
        }
    }

    def deleteOtherFile() {

    }
}