// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    id("com.google.dagger.hilt.android") version "2.50" apply false
    id("com.android.library") version "8.2.0" apply false

}
buildscript{

    dependencies{
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")
    }
}