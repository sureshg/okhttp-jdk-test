import org.jetbrains.kotlin.gradle.tasks.*

plugins {
    kotlin("jvm") version "1.5.10"
    application
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("com.github.ben-manes.versions") version "0.39.0"
    id("com.diffplug.spotless") version "5.13.0"
}

group = "dev.suresh"
version = "0.1.0"

application {
    mainClass.set("MainKt")
}

kotlin {
    sourceSets.all {
        languageSettings.apply {
            progressiveMode = true
        }
    }
}

tasks {
    withType<KotlinCompile>().configureEach {
        kotlinOptions {
            verbose = true
            jvmTarget = "16"
            javaParameters = true
            incremental = true
        }
    }

    // JUnit5
    test { useJUnitPlatform() }

    wrapper {
        gradleVersion = "7.1"
        distributionType = Wrapper.DistributionType.ALL
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.9.0"))
    implementation("com.squareup.okhttp3:okhttp:2.7.5")
    //implementation("com.squareup.okhttp3:logging-interceptor")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}