package com.yechy.gradleplugin.adb

import com.android.build.gradle.api.BaseVariant
import com.yechy.gradleplugin.adb.extension.ConfigExt
import org.gradle.api.DefaultTask

class BaseApkTask extends DefaultTask {
    ConfigExt configExt
    BaseVariant variant
    String deviceId


    protected connectDevice() {
        configExt = project.adbPlugin
        Adb.adb = configExt.adbPath
        Adb.deviceId = configExt.deviceId
        GLog.d("${configExt.toString()}")
        Adb.waitForDevice()
        Adb.root()
        Adb.remount()
    }

}