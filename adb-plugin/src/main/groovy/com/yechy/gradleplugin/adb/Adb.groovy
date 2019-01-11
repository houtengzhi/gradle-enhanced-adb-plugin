package com.yechy.gradleplugin.adb

class Adb {

    static String adb
    static String deviceId

    static def waitForDevice() {
        ShellUtil.execCommand("${getAdb()} wait-for-device")
    }

    static def root() {
        ShellUtil.execCommand("${getAdb()} root")
    }

    static def remount() {
        ShellUtil.execCommand("${getAdb()} remount")
    }

    static def installApk(String apkPath) {
        String command = "${getAdb()} install ${apkPath}"
        ShellUtil.execCommand(command)
    }

    static def uninstallApk(String packageName) {
        String command = "${getAdb()} uninstall ${packageName}"
        ShellUtil.execCommand(command)
    }

    static def pushFile(String srcPath, String desPath) {
        String command = "${getAdb()} push ${srcPath} ${desPath}"
        ShellUtil.execCommand(command)
    }

    static def deleteFile(String... filePaths) {
        String command
        for (String path : filePaths) {
            command = "${getAdb()} shell rm -rf ${path}"
            ShellUtil.execCommand(command)
        }
    }

    static def run(String componentName) {
        String command = "${getAdb()} shell am start -n ${componentName}"
        ShellUtil.execCommand(command)
    }

    static def reboot() {
        ShellUtil.execCommand("${getAdb()} reboot")
    }

    static def getAdb() {
        adb = adb ?: "adb"
        adb = adb + (deviceId ? " -s ${deviceId}" : "")
        return adb
    }
}