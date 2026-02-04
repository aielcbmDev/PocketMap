import dev.mokkery.gradle.ApplicationRule
import dev.mokkery.gradle.mokkery
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.mokkeryPlugin)
    alias(libs.plugins.kotlinAllOpen)
    alias(libs.plugins.secrets.gradle.plugin)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    compilerOptions {
        // Removes the following warning when executing unit tests:
        //
        // 'expect'/'actual' classes (including interfaces, objects, annotations, enums,
        // and 'actual' typealiases) are in Beta. Consider using the '-Xexpect-actual-classes'
        // flag to suppress this warning.
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
    sourceSets {
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.google.maps.compose)

            implementation(libs.koin.android)
            implementation(libs.koin.core)
        }
        commonMain.dependencies {
            implementation(project(":maps-feature:core"))
            implementation(project(":maps-feature:domain"))
            implementation(project(":startup"))
            implementation(project(":di-qualifiers"))
            implementation(libs.jetbrains.material.icons.extended)
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.jetbrains.lifecycle.viewmodel)
            implementation(libs.jetbrains.lifecycle.runtime.compose)
            implementation(libs.jetbrains.kotlin.serialization.json)

            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            implementation(libs.jetbrains.compose.navigation)

            implementation(libs.jetbrains.backhandler)
            implementation(libs.jetbrains.material3.adaptive)
            implementation(libs.jetbrains.material3.adaptive.navigation.suite)
            implementation(libs.jetbrains.material3.adaptive.layout)
            implementation(libs.jetbrains.material3.adaptive.navigation)
        }
        commonTest.dependencies {
            implementation(mokkery("coroutines"))
            implementation(libs.jetbrains.kotlinx.test)
            implementation(libs.jetbrains.kotlinx.coroutines.test)
        }
    }
}

android {
    val secretProperties = Properties()
    secretProperties.load(FileInputStream(file("../secrets.properties")))

    namespace = "charly.baquero.pocketmap"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "charly.baquero.pocketmap"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

secrets {
    // To add your Maps API key to this project:
    // 1. If the secrets.properties file does not exist, create it in the same folder as the local.properties file.
    // 2. Add this line, where YOUR_API_KEY is your API key:
    //        MAPS_API_KEY=YOUR_API_KEY
    propertiesFileName = "secrets.properties"

    // A properties file containing default secret values. This file can be
    // checked in version control.
    defaultPropertiesFileName = "local.defaults.properties"
}

// this check might require adjustment depending on your project type and the tasks that you use
// `name.endsWith("Test") || name.endsWith("check")` works with "*Test" and "check" tasks from
// Multiplatform projects
fun isTestingTask(name: String) = name.endsWith("Test") || name.endsWith("check")

val isTesting = gradle
    .startParameter
    .taskNames
    .any(::isTestingTask)

if (isTesting) {
    allOpen {
        annotation("charly.baquero.pocketmap.OpenClassForMocking")
    }
}

mokkery {
    rule.set(ApplicationRule.All)
}
