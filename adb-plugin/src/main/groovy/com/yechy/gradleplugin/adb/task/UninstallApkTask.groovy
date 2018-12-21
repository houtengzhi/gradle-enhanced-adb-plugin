package com.yechy.gradleplugin.adb.task

import com.android.build.gradle.api.BaseVariant
import com.android.builder.testing.api.DeviceException
import com.yechy.gradleplugin.adb.Adb
import com.yechy.gradleplugin.adb.ConfigExt
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class UninstallApkTask extends DefaultTask {

    String applicationId
    ConfigExt configExt
    BaseVariant variant

    @TaskAction
    def doUninstall() throws DeviceException{
        configExt = project.adbPlugin
        uninstall()
    }

    def uninstall() {
        if (configExt.isSystemApp) {
            String apkInstallPath = "/system/app/${configExt.apkPrefix}*"
            Adb.deleteFile(apkInstallPath)

            String dataPath = "/data/data/${applicationId}"

        } else {
            Adb.uninstallApk(applicationId)
        }
    }
}