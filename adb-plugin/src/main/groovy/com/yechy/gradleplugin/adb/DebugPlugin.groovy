package com.yechy.gradleplugin.adb

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Copy

class DebugPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        project.extensions.create('adbPlugin', ConfigExt)




        project.task("unzip", type: Copy) {

        }

    }
}