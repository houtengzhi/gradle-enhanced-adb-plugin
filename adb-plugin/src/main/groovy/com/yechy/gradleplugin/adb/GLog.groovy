package com.yechy.gradleplugin.adb

import org.gradle.api.logging.Logger

class GLog {

    static final String TAG = "[DebugPlugin]"
    static boolean debug = false

    static def d(String message) {
        if (debug) {
            println(TAG + " " + message)
        }
    }

    static def i(String message) {
        println(TAG + " " + message)
    }

    static def e(Logger logger, String message) {
        logger.error(TAG + " " + message)
    }
}