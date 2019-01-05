package com.yechy.gradleplugin.adb

import org.gradle.api.NamedDomainObjectContainer

class ConfigExt {
    boolean isSystemApp = true
    boolean reboot = false
    boolean debugged = false
    String apkPrefix

    NamedDomainObjectContainer<DataDirExt> dataDirs

    ConfigExt(NamedDomainObjectContainer<DataDirExt> dataDirs) {
        this.dataDirs = dataDirs
    }

    def dataDirs(Closure closure) {
        dataDirs.configure {closure}
    }

}