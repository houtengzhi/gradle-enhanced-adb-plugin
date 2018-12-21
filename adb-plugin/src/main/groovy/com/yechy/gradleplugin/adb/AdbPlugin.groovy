package com.yechy.gradleplugin.adb

import com.android.build.gradle.api.BaseVariant
import com.yechy.gradleplugin.adb.task.InitAdbTask
import com.yechy.gradleplugin.adb.task.InstallApkTask
import com.yechy.gradleplugin.adb.task.UninstallApkTask
import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class AdbPlugin implements Plugin<Project> {
    Project mProject = null

    @Override
    void apply(Project project) {
        this.mProject = project

        project.extensions.create('adbPlugin', ConfigExt)

        DefaultTask initAdbTask = project.task('initAdbTask', type: InitAdbTask)


        if (project.android.hasProperty("applicationVariants")) {
            project.android.applicationVariants.all { variant ->

                DefaultTask installTask = createInstallTask(variant)

                variant.outputs.all { output ->
                    installTask.assembleTaskName = output.assemble.name
                }

                installTask.dependsOn initAdbTask
            }
        }

        createUninstallTask()

    }

    DefaultTask createInstallTask(Object variant) {
        String variantName = variant.name.capitalize()
        DefaultTask task = mProject.task("enhancedInstall${variantName}", type: InstallApkTask)
        task.group = 'enhanced adb'
        task.description = 'Install apk'
        return task
    }

    DefaultTask createUninstallTask(BaseVariant variant) {
        String variantName = variant.name.capitalize()
        DefaultTask task = mProject.task("enhancedUninstall${variantName}", type: UninstallApkTask)
        task.group = 'enhanced adb'
        task.description = 'Uninstall apk'
        return task
    }
}