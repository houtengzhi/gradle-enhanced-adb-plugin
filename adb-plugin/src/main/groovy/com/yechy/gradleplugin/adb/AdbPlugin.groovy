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
            GLog.debug = mProject.extensions.adbPlugin.debugged
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
        if (customTaskExts != null) {
            for (CustomTaskExt customTaskExt : customTaskExts) {
                GLog.d("createCustomTask: ${customTaskExt.toString()}")

                String name = customTaskExt.name.uncapitalize()
                String group = customTaskExt.group == null ? 'enhanced adb' : customTaskExt.group
                mProject.task(name, type: DefaultTask, group: group, description: customTaskExt.description)
                        .doFirst {
                    if (customTaskExt.commands != null) {
                        customTaskExt.commands.each {
                            ShellUtil.execCommand(it)
                        }
                    }

                    if (customTaskExt.scriptPath != null) {
                        File scriptFile = mProject.file(customTaskExt.scriptPath)
                        String command = ''
                        if (customTaskExt.executeProcedure != null) {
                            command = customTaskExt.executeProcedure + ' '
                        }
                        if (scriptFile.exists()) {
                            command = command + scriptFile.absolutePath
                            ShellUtil.execCommand(command)
                        }
                    }
                }
            }
        }
    }
}