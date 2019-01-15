package com.yechy.gradleplugin.adb.task

import com.android.builder.testing.api.DeviceException
import com.yechy.gradleplugin.adb.Adb
import com.yechy.gradleplugin.adb.BaseApkTask
import com.yechy.gradleplugin.adb.extension.DataDirExt
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.tasks.TaskAction

class UninstallApkTask extends BaseApkTask {

    @TaskAction
    def doUninstall() throws DeviceException{
        connectDevice()
        uninstall()
    }

    def uninstall() {
        if (configExt.isSystemApp) {
            String apkInstallPath = "/system/app/${configExt.apkPrefix}*"
            Adb.deleteFile(apkInstallPath)

            String dataPath = "/data/data/${variant.applicationId}"
            Adb.deleteFile(dataPath)

        } else {
            Adb.uninstallApk(variant.applicationId)
        }

        deleteOtherFile()
    }

    def deleteOtherFile() {
        NamedDomainObjectContainer<DataDirExt> dataDirExts = configExt.dataDirs
        if (dataDirExts != null) {
            for (DataDirExt dataDirExt : dataDirExts) {
                if (dataDirExt != null && dataDirExt.deleteWhenUninstall) {
                    Adb.deleteFile(dataDirExt.filePath)
                }
            }
        }
    }
}