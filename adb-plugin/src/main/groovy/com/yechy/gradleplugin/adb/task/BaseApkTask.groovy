package com.yechy.gradleplugin.adb

import com.android.build.gradle.api.BaseVariant
import org.gradle.api.DefaultTask

class BaseApkTask extends DefaultTask {
    ConfigExt configExt
    BaseVariant variant
    String deviceId

    protected connectDevice() {
        Adb.waitForDevice()
        Adb.root()
        Adb.remount()
    }

}