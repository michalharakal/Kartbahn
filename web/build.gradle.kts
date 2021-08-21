plugins {
  kotlin("multiplatform")
  id("org.jetbrains.compose")
}

// Enable JS(IR) target and add dependencies
kotlin {
  js(IR) {
    browser {
      useCommonJs()
      binaries.executable()
    }
  }
  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation(project(":common"))
      }
    }
    val jsMain by getting {
      dependencies {
        implementation(compose.runtime)
        implementation(compose.web.widgets)
        implementation(compose.web.core)
        implementation(npm("copy-webpack-plugin", "9.0.0"))
      }
    }
  }
}
