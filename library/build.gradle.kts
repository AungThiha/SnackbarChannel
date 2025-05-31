import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("com.vanniktech.maven.publish") version "0.32.0"
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_23)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "SnackbarChannel"
            isStatic = true
        }
    }

    jvm("desktop")

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        outputModuleName = "snackbarChannel"
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "snackbarChannel.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }
            }
        }
        binaries.executable()
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.androidx.lifecycle.runtimeCompose)
                implementation(compose.runtime)
                implementation(compose.material3)
                implementation(compose.components.resources)
            }
        }
    }
}

android {
    namespace = "io.github.aungthiha.snackbar"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
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
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()

    coordinates(
        groupId = "io.github.aungthiha",
        artifactId = "snackbar-channel",
        version = "1.0.4"
    )

    pom {
        name.set("SnackbarChannel")
        description.set("A lightweight, lifecycle-safe snackbar event dispatcher for Compose Multiplatform.")
        url.set("https://github.com/AungThiha/SnackbarChannel")

        licenses {
            license {
                name.set("MIT License")
                url.set("https://github.com/AungThiha/SnackbarChannel/blob/main/LICENSE")
            }
        }

        developers {
            developer {
                id.set("AungThiha")
                name.set("Aung Thiha")
                email.set("mr.aungthiha@gmail.com")
            }
        }

        scm {
            connection.set("scm:git:github.com/AungThiha/SnackbarChannel.git")
            developerConnection.set("scm:git:ssh://github.com/AungThiha/SnackbarChannel.git")
            url.set("https://github.com/AungThiha/SnackbarChannel")
        }
    }
}
