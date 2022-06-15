import org.gradle.api.JavaVersion

object Config {
    const val COMPILE_SDK = 30
    const val MIN_SDK = 24
    const val TARGET_SDK = 30
    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0.0"
    const val BUILD_TOOL_VERSION = "30.0.2"
    const val APPLICATION_ID = "com.armed.am"
    val JAVA_VERSION = JavaVersion.VERSION_1_8

    //Base URL
    const val ARMED_BASE_URL= "\"https://preprod.armed.am/\""



}