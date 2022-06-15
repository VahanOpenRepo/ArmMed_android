// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        google()
        mavenCentral()
        jcenter()
    }
    dependencies {

        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Dependencies.Versions.NAV_VERSION}")
        classpath ("com.android.tools.build:gradle:4.2.1")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:${Dependencies.Versions.kotlin}")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        maven(url = "https://jitpack.io")
        jcenter() // Warning: this repository is going to shut down soon
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}