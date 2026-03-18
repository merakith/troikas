# Project Overview

This document provides a comprehensive overview of the files and directories in this Kotlin Multiplatform (KMP) project. It explains the purpose of each file/folder and indicates how often you will interact with it during the development process.

## Root Directory

| File/Path | Purpose | Touched During Development? |
| --- | --- | --- |
| `build.gradle.kts` | The root Gradle build script. Configures plugins and repositories for all subprojects. | **Rarely.** Only when adding new global build configurations or plugins. |
| `gradle.properties` | Configuration properties for the Gradle build environment (e.g., memory limits, enabling specific KMP flags). | **Rarely.** Only when tweaking build performance or enabling new compiler features. |
| `gradlew` / `gradlew.bat` | The Gradle Wrapper scripts for Unix/macOS and Windows, respectively. Ensures the project uses a specific Gradle version. | **Never.** Executed often, but never manually edited. |
| `local.properties` | Contains local environment properties (like the path to the Android SDK). Ignored by Git. | **Rarely.** Automatically generated or configured once per developer machine. |
| `README.md` | The main project documentation file. | **Occasionally.** Updated as the project evolves and setup steps change. |
| `settings.gradle.kts` | Defines the project modules (`composeApp`, `shared`, etc.) and configures plugin resolution. | **Rarely.** Only touched when adding new modules. |

## `gradle/` Directory

| File/Path | Purpose | Touched During Development? |
| --- | --- | --- |
| `libs.versions.toml` | The Gradle Version Catalog. Centralized place to declare and manage dependencies and their versions. | **Frequently.** You will edit this whenever adding a new library or updating versions. |
| `wrapper/gradle-wrapper.properties` | Specifies the exact version of the Gradle distribution to download and use. | **Rarely.** Only updated when upgrading the project's Gradle version. |

## `shared/` Directory (Logic & Common Code)
This module holds the core logic, expected to be shared across platforms (Android/iOS).

| File/Path | Purpose | Touched During Development? |
| --- | --- | --- |
| `build.gradle.kts` | Configuration for the shared module (dependencies, Kotlin targets). | **Occasionally.** Touched when adding dependencies specific to the shared module. |
| `src/commonMain/kotlin/.../Greeting.kt` | Example of shared Kotlin code accessible from both iOS and Android. | **Frequently.** You will write most of your domain models, repositories, and ViewModels here. |
| `src/commonMain/kotlin/.../Platform.kt` | Defines `expect` declarations for platform-specific APIs. | **Occasionally.** When you need access to an API that differs between iOS and Android. |
| `src/androidMain/kotlin/.../Platform.android.kt` | Android-specific `actual` implementations of shared interfaces/expect declarations. | **Occasionally.** |
| `src/iosMain/kotlin/.../Platform.ios.kt` | iOS-specific `actual` implementations of shared interfaces/expect declarations. | **Occasionally.** |
| `src/commonTest/kotlin/.../SharedCommonTest.kt` | Tests for the shared business logic. | **Frequently.** Where your unit tests for shared code live. |

## `composeApp/` Directory (Android Application & Compose Multiplatform UI)
This directory contains the Android application wrapper and potentially shared Compose Multiplatform UI code.

| File/Path | Purpose | Touched During Development? |
| --- | --- | --- |
| `build.gradle.kts` | Build properties for the Android/Compose application. | **Occasionally.** Touched when adding Android-specific dependencies or changing Android compile SDKs. |
| `src/androidMain/AndroidManifest.xml` | Essential Android manifest file declaring permissions and core app components. | **Occasionally.** When adding permissions (e.g., internet, location) or new activity configs. |
| `src/androidMain/kotlin/.../App.kt` | The main Compose UI starting point. | **Frequently.** The root of your Compose UI tree. |
| `src/androidMain/kotlin/.../MainActivity.kt` | The Android Activity that hosts the Compose UI. | **Rarely.** Generally set up once to bridge to Compose. |
| `src/androidMain/composeResources/` | Resources (images, fonts) handled by Compose Multiplatform. | **Frequently.** Used when adding shared assets. |
| `src/androidMain/res/` | Standard Android resources (launchers, icons, values). | **Rarely.** Mostly for the Android app icon and basic theme XMLs. |
| `src/androidUnitTest/kotlin/.../ComposeAppAndroidUnitTest.kt` | Android-specific UI or integration tests. | **Occasionally.** |

## `iosApp/` Directory
The native Xcode project that builds the iOS app, consuming the compiled `shared` framework.

| File/Path | Purpose | Touched During Development? |
| --- | --- | --- |
| `Configuration/Config.xcconfig` | Xcode configuration file. | **Rarely.** |
| `iosApp/iOSApp.swift` | The entry point for the SwiftUI App. | **Rarely.** |
| `iosApp/ContentView.swift` | The root SwiftUI view. When using Compose Multiplatform for iOS, this typically wraps a Compose UI controller. | **Rarely to Occasionally.** Depends on whether UI is built in Swift or Compose. |
| `iosApp/Info.plist` | Core configurations for iOS (similar to AndroidManifest.xml). | **Occasionally.** When adding iOS permissions (e.g., camera, location). |
| `iosApp/Assets.xcassets/` | iOS native image and color assets (like the app icon). | **Rarely.** Updated for app icons or splash screens. |
| `iosApp.xcodeproj/` & `.xcworkspace/` | Xcode project files tracking Xcode settings and configurations. | **Occasionally.** Automatically modified by Xcode when changing iOS project settings. |
