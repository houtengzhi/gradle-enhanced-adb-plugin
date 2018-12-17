package com.yechy.gradleplugin.adb

class Adb {

    static def waitForDevice() {
        ShellUtil.execCommand("adb wait-for-device")
    }

    static def root() {
        ShellUtil.execCommand("adb root")
    }

    static def remount() {
        ShellUtil.execCommand("adb remount")
    }

    static def installApk(String apkPath) {
        String command = "adb install ${apkPath}"
        ShellUtil.execCommand(command)
    }

    static def uninstallApk(String packageName) {
        String command = "adb uninstall ${packageName}"
        ShellUtil.execCommand(command)
    }

    static def pushFile(String srcPath, String desPath) {
        String command = "adb push ${srcPath} ${desPath}"
        ShellUtil.execCommand(command)
    }

    static def deleteFile(File... files) {
        String command
        for (File file : files) {
            command = "adb shell rm -rf ${file}"
            ShellUtil.execCommand(command)
        }
    }

    static def reboot() {
        ShellUtil.execCommand("adb reboot")
    }
}