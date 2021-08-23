buildscript {
    val kotlin_version by extra("1.5.21")
    repositories {
        google()
        mavenCentral()
        //gradlePluginPortal()
        //maven(uri("https://plugins.gradle.org/m2/"))
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    dependencies {
        // __LATEST_COMPOSE_RELEASE_VERSION__
        classpath("com.android.tools.build:gradle:7.0.1")
        // __KOTLIN_COMPOSE_VERSION__
        classpath(kotlin("gradle-plugin", version = kotlin_version))
        classpath(kotlin("serialization", version = kotlin_version))
        classpath("org.jetbrains.compose:compose-gradle-plugin:1.0.0-alpha4-build315")
        //classpath("gradle.plugin.com.github.jengelman.gradle.plugins:shadow:7.0.0")
        //classpath("org.jmailen.gradle:kotlinter-gradle:3.4.5")

        classpath("dev.icerock.moko:network-generator:0.16.0")
    }
}

allprojects {
    //apply(plugin = "org.jmailen.kotlinter")

    allprojects {
        repositories {
            google()
            mavenCentral()
            maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
            maven(url = "https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers")
        }
    }
}
