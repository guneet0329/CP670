plugins {
    alias(libs.plugins.android.application)
    id("jacoco")
}

android {
    namespace = "com.example.androidassignments"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.androidassignments"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            isTestCoverageEnabled = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
    buildFeatures {
        viewBinding = true
    }


}
dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.core)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.robolectric)
    testImplementation(libs.junit)
    testImplementation(libs.mockitoCore)
    testImplementation(libs.spock.core)
    testImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.mockitoAndroid)


}
jacoco {
    toolVersion = "0.8.7"
}

tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest")

    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.outputLocation.set(layout.buildDirectory.dir("reports/jacoco"))
    }

    val fileFilter = listOf("**/R.class", "**/R$*.class", "**/BuildConfig.*", "**/Manifest*.*", "**/*Test*.*")
    val debugTree = fileTree("${buildDir}/intermediates/javac/debug") {
        exclude(fileFilter)
    }
    val kotlinDebugTree = fileTree("${buildDir}/tmp/kotlin-classes/debug") {
        exclude(fileFilter)
    }
    val mainSrc = "src/main/java"

    sourceDirectories.setFrom(files(mainSrc))
    classDirectories.setFrom(files(debugTree, kotlinDebugTree))
    executionData.setFrom(fileTree(buildDir).include("jacoco/testDebugUnitTest.exec"))
}
