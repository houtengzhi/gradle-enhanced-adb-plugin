package com.yechy.gradleplugin.adb.extension

class DataDirExt {
    String name
    String filePath
    boolean deleteWhenUninstall = false

    DataDirExt() {
    }

    DataDirExt(String name) {
        this.name = name
    }
}