package com.yechy.gradleplugin.adb

import org.gradle.api.NamedDomainObjectContainer

class ConfigExt {
    boolean isSystemApp = true
    boolean reboot = false
    boolean debugged = false
    String apkPrefix
    boolean runAfterInstall = false
    String launchActivity

    NamedDomainObjectContainer<DataDirExt> dataDirs
    NamedDomainObjectContainer<CustomTaskExt> customTasks

    ConfigExt(NamedDomainObjectContainer<DataDirExt> dataDirs, NamedDomainObjectContainer<CustomTaskExt> customTaskExts) {
        this.dataDirs = dataDirs
        this.customTasks = customTaskExts
    }

    def dataDirs(Closure closure) {
        dataDirs.configure {closure}
    }

    def customTasks(Closure closure) {
        customTasks.configure {closure}
    }

}