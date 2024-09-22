

import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {

    id("org.springframework.boot").version("3.3.0").apply(false)
    id("io.spring.dependency-management").version("1.1.5")

    kotlin("jvm")
    kotlin("plugin.serialization") version "2.0.20"
}

group = "com.sushobh"
version = "0.0.1-SNAPSHOT"
val ktor_version = "2.3.12"

repositories {
    mavenCentral()
}




dependencies {
    testImplementation(kotlin("test"))
    implementation("io.ktor:ktor-client-core:${ktor_version}")
    implementation("io.ktor:ktor-client-cio:${ktor_version}")
    implementation("io.ktor:ktor-client-content-negotiation:${ktor_version}")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")



    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation(project(":apiclasses"))

    implementation(project(":auth"))
    implementation(project(":common"))
    implementation(project(":posts"))
    implementation(project(":friends"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}



configure<DependencyManagementExtension> {
    imports {
        mavenBom(SpringBootPlugin.BOM_COORDINATES)
    }
}
