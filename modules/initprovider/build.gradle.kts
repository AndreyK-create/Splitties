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
        androidMain.dependencies {
            implementation(splitties("exceptions"))
            api(Kotlin.stdlib.jdk7)
        }
    }
}

afterEvaluate {
    publishing {
        setupAllPublications(project)
    }
}
