package com.example.coroutines.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

internal class KotlinLibraryPlugin : Plugin<Project> {

    override fun apply(project: Project) = with(project) {
        pluginManager.apply("kotlin")
        pluginManager.apply(ConfigurationPlugin::class.java)
    }
}
