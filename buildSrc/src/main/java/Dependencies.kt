object Dependencies {


    object Versions {

        //plugins
        const val kotlin = "1.5.10"
        const val gradle = "4.2.1"
//    const val kotlin_gradle_plugin_version = "1.5.10"


        // AndroidX Dependencies
        const val ANDROIDX_APPCOMPAT_VERSION = "1.3.0"
        const val ANDROIDX_PAGING_VERSION = "3.0.1"
        const val ANDROIDX_KTX_CORE_VERSION = "1.3.2"
        const val ANDROIDX_CONSTRAINT_LAYOUT_VERSION = "2.1.1"
        const val ANDROIDX_PREFERENCES_VERSION = "1.1.1"
        const val DESUGAR_JDK_LIB_VERSION = "1.1.5"
        const val androidx_lifecycle_version = "2.2.0"
        const val androidx_multidex_vesion = "2.0.1"
        const val androidx_media_version = "1.0.0"
        const val androidx_security_crypto_version = "1.0.0"
        const val androidx_local_broadcast_manager_version = "1.0.0"
        const val androidx_architecture_components_version = "2.1.0"
        const val androidx_swipe_refresh_layout_version = "1.1.0"
        const val androidx_fragment_version = "1.3.0"
        const val androidx_activity_version = "1.2.3"
        const val room_version = "2.3.0"
        const val paging_version = "2.1.2"
        const val sqlite_version = "2.0.1"
        const val sql_cipher_version = "4.4.2"
        const val recycler_view_version = "1.2.1"

        //Navigation
        const val NAV_VERSION = "2.3.5"

        //coroutines
        const val LIB_COROUTINES_VERSION = "1.4.2"

        //material
        const val LIB_MATERIAL_VERSION = "1.4.0"

        // Tests
        const val junit_version = "5.7.0"
        const val junit_extension_version = "1.1.2"
        const val espresso_extension_version = "3.4.0"

        //DI
        const val KOIN_VERSION = "3.1.2"

        //Network
        const val RETROFIT_VERSION = "2.9.0"
        const val retrofit_scalars_factory_version = "2.9.0"
        const val RETROFIT_LOGGING_INTERCEPTOR_VERSION = "4.9.0"

        //Glide
        const val lib_glide_version = "4.12.0"
        const val lib_glide_transformations_version = "4.1.0"

        //Others
        const val CODE_SCANNER_VERSION = "2.1.0"


    }

    object ThirdPartyLibs {
        const val CODE_SCANNER = "com.budiyev.android:code-scanner:${Versions.CODE_SCANNER_VERSION}"
        const val glide = "com.github.bumptech.glide:glide:${Versions.lib_glide_version}"
        const val glide_compiler =
            "com.github.bumptech.glide:compiler:${Versions.lib_glide_version}"
        const val glide_okhttp_integration =
            "com.github.bumptech.glide:okhttp3-integration:${Versions.lib_glide_version}"
        const val glide_transformations =
            "jp.wasabeef:glide-transformations:${Versions.lib_glide_transformations_version}"
    }

    object Kotlin {
        const val KOTLIN_STD = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
        const val COROUTINES =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.LIB_COROUTINES_VERSION}"
        const val COROUTINES_CORE =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.LIB_COROUTINES_VERSION}"
    }

    object Tests {
        const val JUNIT = "org.junit.jupiter:junit-jupiter-api:${Versions.junit_version}"
        const val JUNIT_ANDROID_EXTENSION =
            "androidx.test.ext:junit:${Versions.junit_extension_version}"
        const val ESPRESSO_EXTENSION =
            "androidx.test.espresso:espresso-core:${Versions.espresso_extension_version}"
        const val INSTRUMENTATION_RUNNER = "androidx.test.runner.AndroidJUnitRunner"
    }

    object Material {
        const val MATERIAL = "com.google.android.material:material:${Versions.LIB_MATERIAL_VERSION}"
    }

    object DI {
        // Koin main features for Android (Scope,ViewModel ...)
        const val KOIN_MAIN_ANDROID = "io.insert-koin:koin-android:${Versions.KOIN_VERSION}"
    }

    object Network {
        const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT_VERSION}"
        const val RETROFIT_CONVERTER_GSON =
            "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT_VERSION}"
        const val RETROFIT_LOGGING_INTERCEPTOR =
            "com.squareup.okhttp3:logging-interceptor:${Versions.RETROFIT_LOGGING_INTERCEPTOR_VERSION}"
    }


    object AndroidXLibraries {
        const val APP_COMPAT = "androidx.appcompat:appcompat:${Versions.ANDROIDX_APPCOMPAT_VERSION}"
        const val ANDROIDX_CORE_KTX = "androidx.core:core-ktx:${Versions.ANDROIDX_KTX_CORE_VERSION}"
        const val CONSTRAINT_LAYOUT =
            "androidx.constraintlayout:constraintlayout:${Versions.ANDROIDX_CONSTRAINT_LAYOUT_VERSION}"
        const val ANDROIDX_PREFERENCES =
            "androidx.preference:preference:${Versions.ANDROIDX_PREFERENCES_VERSION}"
        const val ANDROIDX_PREFERENCES_KTX =
            "androidx.preference:preference-ktx:${Versions.ANDROIDX_PREFERENCES_VERSION}"
        const val NAVIGATION_FRAGMENT_KTX =
            "androidx.navigation:navigation-fragment-ktx:${Versions.NAV_VERSION}"
        const val NAVIGATION_UI_KTX =
            "androidx.navigation:navigation-ui-ktx:${Versions.NAV_VERSION}"
        const val ANDROIDX_PAGING =
            "androidx.paging:paging-runtime:${Versions.ANDROIDX_PAGING_VERSION}"
        const val DESUGAR_JDK_LIB =
            "com.android.tools:desugar_jdk_libs:${Versions.DESUGAR_JDK_LIB_VERSION}"
        const val LIFECYCLE_EXTENSIONS =
            "androidx.lifecycle:lifecycle-extensions:${Versions.androidx_lifecycle_version}"
        const val LIFECYCLE_LIVEDATA =
            "androidx.lifecycle:lifecycle-livedata:${Versions.androidx_lifecycle_version}"
        const val LIFECYCLE_LIVEDATA_KTX =
            "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.androidx_lifecycle_version}"
        const val LIFECYCLE_RUNTIME_KTS =
            "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.androidx_lifecycle_version}"
        const val local_broadcast_manager =
            "androidx.localbroadcastmanager:localbroadcastmanager:${Versions.androidx_local_broadcast_manager_version}"
        const val androidx_fragment =
            "androidx.fragment:fragment:${Versions.androidx_fragment_version}"
        const val androidx_fragment_ktx =
            "androidx.fragment:fragment-ktx:${Versions.androidx_fragment_version}"
        const val androidx_activity =
            "androidx.activity:activity:${Versions.androidx_activity_version}"
        const val androidx_activity_ktx =
            "androidx.activity:activity-ktx:${Versions.androidx_activity_version}"
        const val RETROFIT_CONVERTER_SCALARS =
            "com.squareup.retrofit2:converter-scalars:${Versions.retrofit_scalars_factory_version}"
        const val androidx_security_crypto_version =
            "androidx.security:security-crypto:${Versions.androidx_security_crypto_version}"
        const val media = "androidx.media:media:${Versions.androidx_media_version}"
        const val multidex = "androidx.multidex:multidex:${Versions.androidx_multidex_vesion}"
        const val swipe_refresh_layout =
            "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.androidx_swipe_refresh_layout_version}"
        const val room_runtime = "androidx.room:room-runtime:${Versions.room_version}"
        const val room_compiler = "androidx.room:room-compiler:${Versions.room_version}"
        const val room_ktx = "androidx.room:room-ktx:${Versions.room_version}"
        const val pagination_ktx = "androidx.paging:paging-runtime-ktx:${Versions.paging_version}"
        const val room_coroutines = "androidx.room:room-coroutines:${Versions.room_version}"
        const val sqlite = "androidx.sqlite:sqlite:${Versions.sqlite_version}"
        const val sql_cipher =
            "net.zetetic:android-database-sqlcipher:${Versions.sql_cipher_version}"
        const val recycler_view =
            "androidx.recyclerview:recyclerview:${Versions.recycler_view_version}"
    }

}