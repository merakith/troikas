import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.ksp)
    kotlin("plugin.serialization") version libs.versions.kotlin.get()
}

kotlin {
    androidTarget {
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
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
val localProps = java.util.Properties()
val localPropsFile = rootProject.file("local.properties")

if (localPropsFile.exists()) {
    localPropsFile.inputStream().use { localProps.load(it) }
}

// Helper function to get the key from local.properties OR Environment Variables
fun getSecret(key: String): String {
    return (localProps[key] as? String) 
        ?: System.getenv(key) 
        ?: ""
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
        buildConfigField(
            "String", 
            "SUPABASE_URL", 
            "\"${getSecret("SUPABASE_URL")}\""
        )
        buildConfigField(
            "String", 
            "SUPABASE_ANON_KEY", 
            "\"${getSecret("SUPABASE_ANON_KEY")}\""
        )
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