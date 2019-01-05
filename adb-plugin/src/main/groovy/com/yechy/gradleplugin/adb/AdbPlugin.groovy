package com.yechy.gradleplugin.adb

import com.android.build.gradle.api.BaseVariant
import com.yechy.gradleplugin.adb.task.InstallApkTask
import com.yechy.gradleplugin.adb.task.UninstallApkTask
import org.gradle.api.DefaultTask
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Plugin
import org.gradle.api.Project

class AdbPlugin implements Plugin<Project> {
    Project mProject = null

    @Override
    void apply(Project project) {
        this.mProject = project

        NamedDomainObjectContainer<DataDirExt> dataDirs = project.container(DataDirExt)

        ConfigExt adbPlugin = new ConfigExt(dataDirs)

        project.extensions.add('adbPlugin', adbPlugin)

        if (project.android.hasProperty("applicationVariants")) {
            project.android.applicationVariants.all { variant ->

                createInstallTask(variant)
                createUninstallTask(variant)
            }
        }

    }

    DefaultTask createInstallTask(Object variant) {
        String variantName = variant.name.capitalize()
        DefaultTask task = mProject.task("enhancedInstall${variantName}", type: InstallApkTask)
        task.group = 'enhanced adb'
        task.description = 'Install apk'
        task.variant = variant
        return task
    }

    DefaultTask createUninstallTask(BaseVariant variant) {
        String variantName = variant.name.capitalize()
        DefaultTask task = mProject.task("enhancedUninstall${variantName}", type: UninstallApkTask)
        task.group = 'enhanced adb'
        task.description = 'Uninstall apk'
        task.variant = variant
        return task
    }
}