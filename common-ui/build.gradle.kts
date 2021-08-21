import org.jetbrains.compose.compose

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("maven-publish")
}

kotlin {
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
        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)

                implementation(project(":common"))

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1-native-mt") {
                    version {
                        strictly("1.5.0")
                    }
                }
                implementation("io.ktor:ktor-utils:1.6.0")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.1")

                implementation("io.ktor:ktor-client-core:1.6.0")
                implementation("io.ktor:ktor-client-json:1.6.0")
                implementation("io.ktor:ktor-client-logging:1.6.0")
                implementation("io.ktor:ktor-client-serialization:1.6.0")

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
                api("androidx.appcompat:appcompat:1.4.0-alpha03")
                api("androidx.core:core-ktx:1.6.0")
                implementation("io.ktor:ktor-client-okhttp:1.6.0")

                val lifecycleVersion = "2.2.0"
                implementation("androidx.lifecycle:lifecycle-extensions:$lifecycleVersion")
                implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation("junit:junit:4.13.2")
            }
        }

        val desktopMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-apache:1.6.0")
            }
        }
        val desktopTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.3")
                implementation("junit:junit:4.13.2")
                implementation("io.mockk:mockk:1.11.0")
            }
        }
    }
}

android {
    compileSdk = 30
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 24
        targetSdk = 30
    }
}
