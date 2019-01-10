package com.yechy.gradleplugin.adb

import com.android.build.gradle.api.BaseVariant
import com.yechy.gradleplugin.adb.task.InstallApkTask
import com.yechy.gradleplugin.adb.task.UninstallApkTask
import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class AdbPlugin implements Plugin<Project> {
    Project mProject = null

    @Override
    void apply(Project project) {
        GLog.d("apply()")
        this.mProject = project

        project.extensions.create('adbPlugin', ConfigExt, project)

        if (project.android.hasProperty("applicationVariants")) {
            project.android.applicationVariants.all { variant ->

                createInstallTask(variant)
                createUninstallTask(variant)
            }
        }

        project.afterEvaluate {
            GLog.d("project afterEvaluate")
            createCustomTask()
        }

        project.beforeEvaluate {
            GLog.d("project beforeEvaluate")
        }
    }

    DefaultTask createInstallTask(Object variant) {
        GLog.d("createInstallTask()")
        String variantName = variant.name.capitalize()
        DefaultTask task = mProject.task("enhancedInstall${variantName}", type: InstallApkTask)
        task.group = 'enhanced adb'
        task.description = 'Install apk'
        task.variant = variant
        return task
    }

    DefaultTask createUninstallTask(BaseVariant variant) {
        GLog.d("createUninstallTask()")
        String variantName = variant.name.capitalize()
        DefaultTask task = mProject.task("enhancedUninstall${variantName}", type: UninstallApkTask)
        task.group = 'enhanced adb'
        task.description = 'Uninstall apk'
        task.variant = variant
        return task
    }

    def createCustomTask() {
        def customTaskExts = mProject.extensions.adbPlugin.customTasks
        customTaskExts.all {
            GLog.d("println ${it.toString()}")
        }
        customTaskExts
        if (customTaskExts != null) {
            GLog.d("customTaskExts size: ${customTaskExts.size()}")
            for (CustomTaskExt customTaskExt : customTaskExts) {
                GLog.d("createCustomTask: ${customTaskExt.toString()}")

                String name = customTaskExt.name.uncapitalize()
                String group = customTaskExt.group == null ? 'enhanced adb' : customTaskExt.group
                mProject.task(name, type: DefaultTask, group: group, description: customTaskExt.description)
                        .doFirst {
                    if (customTaskExt.command != null) {
                        ShellUtil.execCommand(customTaskExt.command)
                    }

                    if (customTaskExt.scriptPath != null) {
                        String command = ''
                        if (customTaskExt.executeProcedure != null) {
                            command = customTaskExt.executeProcedure + ' '
                        }
                        command = command + customTaskExt.scriptPath
                        ShellUtil.execCommand(command)
                    }
                }
            }
        }
    }
}