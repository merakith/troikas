import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlinSerialization)
}

val secretsProperties = org.jetbrains.kotlin.konan.properties.Properties()
val secretsFile: File = rootProject.file("local.properties")
if (secretsFile.exists()) {
    secretsFile.inputStream().use { secretsProperties.load(it) }
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.gson)
            implementation(libs.compose.material.icons.extended)
            implementation(libs.retrofit)
            implementation(libs.converter.gson)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.room.runtime)
            implementation(libs.room.ktx)
            implementation(libs.work.runtime.ktx)
        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(projects.shared)
            implementation(libs.barcode.scanning)
            implementation(libs.androidx.camera.camera2)
            implementation(libs.androidx.camera.lifecycle)
            implementation(libs.androidx.camera.view)
            implementation(libs.androidx.concurrent.futures.ktx)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "org.troikas.main"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {

        applicationId = "org.troikas.main"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
        buildConfigField(
            "String", "GEMINI_API_KEY", "\"${project.findProperty("GEMINI_API_KEY") ?: ""}\""
        )

        buildConfigField("String", "SUPABASE_URL",
            "\"${secretsProperties.getProperty("SUPABASE_URL") ?: ""}\"")
        buildConfigField("String", "SUPABASE_ANON_KEY",
            "\"${secretsProperties.getProperty("SUPABASE_ANON_KEY") ?: ""}\"")

    }
    buildFeatures {
        buildConfig = true
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    debugImplementation(libs.compose.uiTooling)
    ksp(libs.room.compiler)
    implementation(platform("io.github.jan-tennert.supabase:bom:${libs.versions.supabase.get()}"))
    implementation(libs.postgrest.kt)
    implementation(libs.supabase.kt)
    implementation("io.ktor:ktor-client-android:${libs.versions.ktor.get()}")
    implementation("io.ktor:ktor-client-core:${libs.versions.ktor.get()}")

}