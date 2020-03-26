package com.example.coroutines.plugin

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal class ConfigurationPlugin : Plugin<Project> {

    override fun apply(project: Project) = with(project) {
        configureKapt()
        configureRepositories()

        tasks.withType(KotlinCompile::class.java).all {
            it.kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
            it.kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
        }
    }
}

internal fun Project.configureKapt() {
    pluginManager.apply("kotlin-kapt")
    extensions.configure(org.jetbrains.kotlin.gradle.plugin.KaptExtension::class.java) {
        it.correctErrorTypes = true
        it.useBuildCache = true
    }
}

internal fun Project.configureRepositories() = with(repositories) {
    google()
    jcenter()
}
