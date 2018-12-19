package com.yechy.gradleplugin.adb.task

import com.yechy.gradleplugin.adb.Adb
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class InitAdbTask extends DefaultTask {

    @TaskAction
    def init() {
        Adb.waitForDevice()
        Adb.root()
        Adb.remount()
    }
}