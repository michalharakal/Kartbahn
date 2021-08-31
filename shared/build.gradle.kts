import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("dev.icerock.mobile.multiplatform-network-generator")
}

mokoNetwork {
    spec("kartbahn") {
        inputSpec = file("${rootDir}/specs/api.yaml")
        packageName = "org.kartbahn.api"
        isInternal = false
        isOpen = true
    }
}

version = "0.0.1"

kotlin {
    //iosSimulatorArm64()

    val sdkName: String? = System.getenv("SDK_NAME")

    val isiOSDevice = sdkName.orEmpty().startsWith("iphoneos")
    if (isiOSDevice) {
        iosArm64("iOS")
    } else {
        iosX64("iOS")
    }

    cocoapods {
        // Configure fields required by CocoaPods.
        summary = "Kartbahn"
        homepage = "https://github.com/michalharakal/Kartbahn"
    }

    android {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
    }
    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }
    sourceSets {
        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlin.RequiresOptIn")
                useExperimentalAnnotation("kotlinx.coroutines.ExperimentalCoroutinesApi")
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0-native-mt")
                implementation("io.ktor:ktor-utils:1.6.3")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.2.1")

                implementation("io.ktor:ktor-client-core:1.6.3")
                implementation("io.ktor:ktor-client-json:1.6.3")
                implementation("io.ktor:ktor-client-logging:1.6.3")
                implementation("io.ktor:ktor-client-serialization:1.6.3")

                implementation("io.insert-koin:koin-core:3.1.2")

                implementation("co.touchlab:stately-common:1.1.10")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                api("androidx.appcompat:appcompat:1.3.1")
                api("androidx.core:core-ktx:1.6.3")
                implementation("io.ktor:ktor-client-okhttp:1.6.3")

                val lifecycleVersion = "2.2.0"
                implementation("androidx.lifecycle:lifecycle-extensions:$lifecycleVersion")
                implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")

                implementation("org.slf4j:slf4j-api:1.7.32")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation("junit:junit:4.13.2")
            }
        }

        val desktopMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-apache:1.6.3")
                implementation("org.slf4j:slf4j-api:1.7.32")
            }
        }
        val desktopTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.0")
                implementation("junit:junit:4.13.2")
                implementation("io.mockk:mockk:1.11.0")
            }
        }
        /*
        val jsMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-js:1.6.1")
            }
        }

         */

        val iOSMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-ios:1.6.3")
            }
        }
        /*

        val iosSimulatorArm64Main by getting{
            dependsOn(iOSMain)
        }

         */
    }
}

android {
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 24
        targetSdk = 31
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    }
}