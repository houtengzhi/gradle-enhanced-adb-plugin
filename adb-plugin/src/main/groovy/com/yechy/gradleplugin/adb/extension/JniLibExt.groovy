package com.yechy.gradleplugin.adb.extension

class JniLibExt {
    String abi
    String installPath


    @Override
    public String toString() {
        return "JniLibExt{" +
                "abi='" + abi + '\'' +
                ", installPath='" + installPath + '\'' +
                '}';
    }
}