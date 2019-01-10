package com.yechy.gradleplugin.adb

import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project

class ConfigExt {
    boolean isSystemApp = true
    boolean reboot = false
    boolean debugged = false
    String apkPrefix
    boolean runAfterInstall = false
    String launchActivity

    NamedDomainObjectContainer<DataDirExt> dataDirs
    NamedDomainObjectContainer<CustomTaskExt> customTasks

    ConfigExt(Project project) {
        this.dataDirs = project.container(DataDirExt)
        this.customTasks = project.container(CustomTaskExt)
    }

    ConfigExt(NamedDomainObjectContainer<DataDirExt> dataDirs, NamedDomainObjectContainer<CustomTaskExt> customTaskExts) {
        this.dataDirs = dataDirs
        this.customTasks = customTaskExts
    }

    def dataDirs(Closure closure) {
        dataDirs.configure(closure)
    }

    def customTasks(Closure closure) {
        customTasks.configure(closure)
    }

}