import org.gradle.buildconfiguration.tasks.UpdateDaemonJvm

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
}

tasks.withType<UpdateDaemonJvm>().configureEach {
    vendor = JvmVendorSpec.ADOPTIUM
}