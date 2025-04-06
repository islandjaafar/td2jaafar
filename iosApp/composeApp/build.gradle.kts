import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("app.cash.sqldelight") version "2.0.2"


}

kotlin {
    androidTarget()

// Configuration correcte de la JVM cible
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "11"
        }
    }

    jvm("desktop")


    
//    sourceSets {
//        val desktopMain by getting
//
//        androidMain.dependencies {
//            implementation(compose.preview)
//            implementation(libs.androidx.activity.compose)
//            implementation("app.cash.sqldelight:android-driver:2.0.2")
//            implementation ("com.google.guava:listenablefuture:9999.0-empty-to-avoid-conflict-with-guava")
//
//        }
//        commonMain.dependencies {
//            implementation(compose.runtime)
//            implementation(compose.foundation)
//            implementation(compose.material)
//            implementation(compose.ui)
//            implementation(compose.components.resources)
//            implementation(compose.components.uiToolingPreview)
//            implementation(libs.androidx.lifecycle.viewmodel)
//            implementation(libs.androidx.lifecycle.runtime.compose)
//            implementation ("com.google.guava:listenablefuture:9999.0-empty-to-avoid-conflict-with-guava")
//
//            implementation ("org.xerial:sqlite-jdbc:3.41.2.1") // Vérifiez la version compatible
//
//
//
//        }
//        desktopMain.dependencies {
//
//
//            implementation(compose.desktop.currentOs)
//            implementation(libs.kotlinx.coroutines.swing)
//            implementation("com.google.guava:guava:23.0") {
//                exclude(group = "com.google.guava", module = "listenablefuture")
//            }
//            implementation("moe.tlaster:precompose:1.4.3")
//
//            implementation("app.cash.sqldelight:sqlite-driver:2.0.2")
//            implementation ("com.google.guava:listenablefuture:9999.0-empty-to-avoid-conflict-with-guava")
//        }
//        val commonMain by getting {
//            dependencies {
//
//
//            }
//        }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(libs.androidx.lifecycle.viewmodel)
                implementation(libs.androidx.lifecycle.runtime.compose)
                implementation("com.google.guava:listenablefuture:9999.0-empty-to-avoid-conflict-with-guava")
                implementation("org.xerial:sqlite-jdbc:3.41.2.1") // Vérifiez la version compatible
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(compose.preview)
                implementation(libs.androidx.activity.compose)
                implementation("app.cash.sqldelight:android-driver:2.0.2")
                implementation("com.google.guava:listenablefuture:9999.0-empty-to-avoid-conflict-with-guava")
            }
        }

        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(libs.kotlinx.coroutines.swing)
                implementation("moe.tlaster:precompose:1.4.3") // Pour la navigation sur Desktop
                implementation("app.cash.sqldelight:sqlite-driver:2.0.2")
                implementation("com.google.guava:guava:23.0") {
                    exclude(group = "com.google.guava", module = "listenablefuture")
                }
            }
        }
    }

}

android {
    namespace = "fr.unice.pizzaproject"
    //compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileSdk = 34

    defaultConfig {
        applicationId = "fr.unice.pizzaproject"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    dependencies {


    }



}

dependencies {
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.compiler)
    implementation(libs.androidx.benchmark.macro)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.core.splashscreen)
    debugImplementation(compose.uiTooling)


}

compose.desktop {
    application {
        mainClass = "fr.unice.pizzaproject.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "fr.unice.pizzaproject"
            packageVersion = "1.0.0"
        }
    }


}
sqldelight {

    databases {

        create("Database") {
            packageName.set("fr.unice.pizzaproject.database")
            verifyMigrations.set(false)

        }

    }
}