package com.yechy.gradleplugin.adb

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