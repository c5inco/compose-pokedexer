plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.ksp)
    alias(libs.plugins.apollo)
    alias(libs.plugins.room)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    androidLibrary {
        namespace = "des.c5inco.pokedexer.shared"
        compileSdk = 36
        minSdk = 28

        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }

    // Enable expect/actual classes for Room KMP
    targets.all {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    freeCompilerArgs.add("-Xexpect-actual-classes")
                }
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            // Room KMP
            implementation(libs.room.runtime)

            // Apollo GraphQL
            implementation(libs.apollo.runtime)

            // Coroutines
            implementation(libs.kotlinx.coroutines.core)

            // Serialization
            implementation(libs.kotlinx.serialization.json)
        }

        androidMain.dependencies {
            // Android-specific coroutines
            implementation(libs.kotlinx.coroutines.android)
        }

        iosMain.dependencies {
            // iOS uses bundled SQLite driver
            implementation(libs.sqlite.bundled)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

// Room KMP configuration
room {
    schemaDirectory("$projectDir/schemas")
}

// Apollo configuration
apollo {
    service("service") {
        packageName.set("des.c5inco.pokedexer.shared")
        srcDir("src/commonMain/graphql")
    }
}

// KSP for Room compiler - must be added for each target
dependencies {
    add("kspAndroid", libs.room.compiler)
    add("kspIosSimulatorArm64", libs.room.compiler)
    add("kspIosX64", libs.room.compiler)
    add("kspIosArm64", libs.room.compiler)
}

// TODO: Remove this workaround when Room KMP stabilizes expect/actual support
// Workaround for Room KMP expect/actual issue with Kotlin 2.0+
// The metadata compilation fails but platform-specific compilations succeed.
// See: https://issuetracker.google.com/issues/342905180
// Revisit when updating Room or Kotlin versions.
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask<*>>().configureEach {
    if (name == "compileCommonMainKotlinMetadata" || name == "compileIosMainKotlinMetadata") {
        enabled = false
    }
}
