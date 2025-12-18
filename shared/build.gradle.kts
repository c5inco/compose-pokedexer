import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    alias(libs.plugins.apollo)
    alias(libs.plugins.kmpNativeCoroutines)
}

kotlin {
    // Enable opt-ins for KMP-NativeCoroutines generated code
    sourceSets.all {
        languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
    }

    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            // Coroutines
            implementation(libs.kotlinx.coroutines.core)

            // Apollo GraphQL
            implementation(libs.apollo.runtime)

            // Room (KMP)
            implementation(libs.room.runtime)
            implementation(libs.sqlite.bundled)
        }

        androidMain.dependencies {
            // Coroutines Android
            implementation(libs.kotlinx.coroutines.android)

            // DataStore for preferences
            implementation(libs.datastore)
        }

        iosMain.dependencies {
            // iOS-specific dependencies (if any)
        }
    }
}

android {
    namespace = "des.c5inco.pokedexer.shared"
    compileSdk = 36

    defaultConfig {
        minSdk = 28
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

apollo {
    service("pokeapi") {
        packageName.set("des.c5inco.pokedexer.shared")
        generateKotlinModels.set(true)
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    // Room KSP for each target
    add("kspAndroid", libs.room.compiler)
    add("kspIosArm64", libs.room.compiler)
    add("kspIosSimulatorArm64", libs.room.compiler)
}

