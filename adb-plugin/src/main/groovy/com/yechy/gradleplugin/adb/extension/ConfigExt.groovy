package com.yechy.gradleplugin.adb.extension

import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project

class ConfigExt {
    boolean isSystemApp = true
    boolean rebootAfterInstall = false
    boolean debugged = false
    String apkPrefix
    boolean runAfterInstall = false
    String launchActivity
    String adbPath
    String deviceId
    JniLibExt jniLib

    NamedDomainObjectContainer<DataDirExt> dataDirs
    NamedDomainObjectContainer<CustomTaskExt> customTasks

    ConfigExt(Project project) {
        this.dataDirs = project.container(DataDirExt)
        this.customTasks = project.container(CustomTaskExt)
        this.jniLib = project.objects.newInstance(JniLibExt)
    }

    def dataDirs(Closure closure) {
        dataDirs.configure(closure)
    }

    def customTasks(Closure closure) {
        customTasks.configure(closure)
    }

    void jniLib(Action<? super JniLibExt> action) {
        action.execute(jniLib)
    }


    @Override
    public String toString() {
        return "ConfigExt{" +
                "isSystemApp=" + isSystemApp +
                ", rebootAfterInstall=" + rebootAfterInstall +
                ", debugged=" + debugged +
                ", apkPrefix='" + apkPrefix + '\'' +
                ", runAfterInstall=" + runAfterInstall +
                ", launchActivity='" + launchActivity + '\'' +
                ", adbPath='" + adbPath + '\'' +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }
}