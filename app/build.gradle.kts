plugins {
    id("com.android.application")
    id("kotlin-android")
    id("org.jetbrains.kotlin.android.extensions")
    id("androidx.navigation.safeargs")

}

android {

    androidExtensions{
        isExperimental=true
    }

    compileSdkVersion(Config.COMPILE_SDK)
    buildToolsVersion = Config.BUILD_TOOL_VERSION

    defaultConfig {
        multiDexEnabled=true
        applicationId = Config.APPLICATION_ID
        minSdkVersion(Config.MIN_SDK)
        targetSdkVersion(Config.TARGET_SDK)
        versionCode = Config.VERSION_CODE
        versionName = Config.VERSION_NAME
        testInstrumentationRunner = Dependencies.Tests.INSTRUMENTATION_RUNNER
    }

    signingConfigs {
        getByName("debug") {
//            storeFile = file("../buildsystem/debug.keystore")
//            storePassword = "android"
//            keyAlias = "androiddebugkey"
//            keyPassword = "android"
        }

        create("release") {
//            storeFile = file("../buildsystem/release.keystore")
//            storePassword = "com1armed1am"
//            keyAlias = "armed"
//            keyPassword = "com1armed1am"
        }
    }

    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs.getByName(name)
            isMinifyEnabled = false
            isDebuggable = true
            buildConfigField("String", "BASE_URL", Config.ARMED_BASE_URL)
        }

        getByName("release") {
            signingConfig = signingConfigs.getByName(name)
            isMinifyEnabled = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", Config.ARMED_BASE_URL)
        }
        create("stage") {
            initWith(getByName("debug"))
            applicationIdSuffix = ".stage"
            versionNameSuffix = "-STAGE"
            setMatchingFallbacks("debug")
            buildConfigField("String", "BASE_URL", Config.ARMED_BASE_URL)
        }


    }

    buildFeatures {
        viewBinding = true
    }

    //?
    packagingOptions {
        exclude("META-INF/atomicfu.kotlin_module")
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = Config.JAVA_VERSION
        targetCompatibility = Config.JAVA_VERSION
    }

    kotlinOptions {
        jvmTarget = Config.JAVA_VERSION.toString()
    }
}

dependencies {
    implementation(Dependencies.Kotlin.KOTLIN_STD)
    implementation(Dependencies.AndroidXLibraries.ANDROIDX_CORE_KTX)
    implementation(Dependencies.AndroidXLibraries.APP_COMPAT)
    implementation(Dependencies.Material.MATERIAL)
    implementation(Dependencies.AndroidXLibraries.CONSTRAINT_LAYOUT)
    implementation(Dependencies.DI.KOIN_MAIN_ANDROID)
    implementation(Dependencies.AndroidXLibraries.NAVIGATION_FRAGMENT_KTX)
    implementation(Dependencies.AndroidXLibraries.NAVIGATION_UI_KTX)
    implementation(Dependencies.AndroidXLibraries.ANDROIDX_PREFERENCES)
    implementation(Dependencies.AndroidXLibraries.ANDROIDX_PREFERENCES_KTX)
    implementation(Dependencies.AndroidXLibraries.ANDROIDX_PAGING)
    implementation(Dependencies.AndroidXLibraries.LIFECYCLE_EXTENSIONS)
    implementation(Dependencies.AndroidXLibraries.LIFECYCLE_LIVEDATA)
    implementation(Dependencies.AndroidXLibraries.LIFECYCLE_LIVEDATA_KTX)
    implementation(Dependencies.AndroidXLibraries.LIFECYCLE_RUNTIME_KTS)
    implementation(Dependencies.Network.RETROFIT)
    implementation(Dependencies.AndroidXLibraries.RETROFIT_CONVERTER_SCALARS)
    implementation(Dependencies.Network.RETROFIT_CONVERTER_GSON)
    implementation(Dependencies.Network.RETROFIT_LOGGING_INTERCEPTOR)
    implementation(Dependencies.ThirdPartyLibs.CODE_SCANNER)
    implementation(Dependencies.ThirdPartyLibs.glide)
    implementation(Dependencies.ThirdPartyLibs.glide_compiler)
    implementation(Dependencies.ThirdPartyLibs.glide_okhttp_integration)
    implementation(Dependencies.ThirdPartyLibs.glide_transformations)
    implementation("androidx.lifecycle:lifecycle-common-java8:2.3.1")
    coreLibraryDesugaring(Dependencies.AndroidXLibraries.DESUGAR_JDK_LIB)
    testImplementation(Dependencies.Tests.JUNIT)
    androidTestImplementation(Dependencies.Tests.JUNIT_ANDROID_EXTENSION)
    androidTestImplementation(Dependencies.Tests.ESPRESSO_EXTENSION)
}