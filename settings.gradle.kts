pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // Unity exported libraries are resolved from the integrated unityLibrary/libs directory.
        flatDir {
            dirs("libs/unityLibrary/libs")
        }
    }
}
rootProject.name = "KAUSHAL_WORKER_APP"
include(":app")

// Unity AR runtime exported from D:\My project\Builds\UnityLibraryExport\unityLibrary
include(":unityLibrary")
project(":unityLibrary").projectDir = file("libs/unityLibrary")
include(":unityLibrary:xrmanifest.androidlib")
project(":unityLibrary:xrmanifest.androidlib").projectDir = file("libs/unityLibrary/xrmanifest.androidlib")