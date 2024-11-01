import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.10"
    kotlin("plugin.spring") version "1.9.10"

    id("org.springframework.boot") version "3.1.1"
    id("io.spring.dependency-management") version "1.1.0"

    id("org.jetbrains.dokka") version "1.8.20"

    idea
}

group = "com.jinni01.tool"
version = "1.0.0"
tasks.withType(Jar::class.java) {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

tasks.bootJar { enabled = false }
tasks.jar { enabled = true }

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

dependencies {
    //spring
    implementation("org.springframework.boot:spring-boot-starter-web")

    // lucy xss filter
    implementation("com.navercorp.lucy:lucy-xss-servlet:2.0.1")

    // jackson
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // apache commons
    implementation("org.apache.commons:commons-lang3:3.12.0")

    // logging
    implementation("io.github.microutils:kotlin-logging:3.0.5")
}

repositories {
    mavenCentral()
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
