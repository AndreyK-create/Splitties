/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    `maven-publish`
}

android {
    setDefaults()
}

kotlin {
    android()
    configure(targets) { configureMavenPublication() }
    sourceSets {
        commonMain.dependencies {
            api(splitties("experimental"))
        }
        androidMain.dependencies {
            api(Kotlin.stdlib.jdk7)
            api(AndroidX.core.ktx)
            api(KotlinX.coroutines.android)
        }
        getByName("androidTest").dependencies {
            implementation(Kotlin.test.junit)
            implementation(AndroidX.test.coreKtx)
            implementation(AndroidX.test.runner)
            implementation(AndroidX.test.ext.junit)
            implementation(AndroidX.test.espresso.core)
            implementation(KotlinX.coroutines.android)
        }
        matching { it.name.startsWith("android") }.all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlin.RequiresOptIn")
            }
        }
    }
}

afterEvaluate {
    publishing {
        setupAllPublications(project)
    }
}
