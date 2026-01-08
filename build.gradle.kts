import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.2.21"
    kotlin("plugin.spring") version "2.2.21"
    id("org.springframework.boot") version "4.0.1"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("plugin.jpa") version "2.2.21"
    id("org.openapi.generator") version "7.2.0"
}

group = "org.bazar"
version = "1.0.0"
description = "bazar-chat"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    //Web
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("io.swagger.core.v3:swagger-annotations:2.2.38")
    implementation("tools.jackson.module:jackson-module-kotlin")
    implementation("io.swagger.core.v3:swagger-models:2.2.38")

    //Observability
    implementation("io.github.oshai:kotlin-logging-jvm:5.1.0")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    //Database
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-liquibase")
    runtimeOnly("org.postgresql:postgresql")

    //Security
    implementation("org.springframework.boot:spring-boot-starter-security-oauth2-resource-server")

    //Test
    testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")
    testImplementation("org.springframework.boot:spring-boot-starter-liquibase-test")
    testImplementation("org.springframework.boot:spring-boot-starter-security-oauth2-resource-server-test")
    testImplementation("org.springframework.boot:spring-boot-starter-validation-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
    testImplementation("org.springframework.boot:spring-boot-starter-websocket-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    //Other
    implementation("org.jetbrains.kotlin:kotlin-reflect")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

openApiGenerate {
    generatorName.set("kotlin-spring")
    inputSpec.set("$rootDir/src/main/resources/openapi/bazar-chat.yaml")
    outputDir.set("${layout.buildDirectory.locationOnly.get()}/generated/openapi")
    // Packages for generated code
    apiPackage.set("org.bazar.chat.api")
    modelPackage.set("org.bazar.chat.model")
    typeMappings.set(mapOf(
        "DateTime" to "java.time.Instant",
    ))
    importMappings.set(mapOf(
        "Pageable" to "org.springframework.data.domain.Pageable"
    ))
    schemaMappings.set(mapOf(
        "Pageable" to "org.springframework.data.domain.Pageable"
    ))
    configOptions.set(mapOf(
        // 1. Fixes javax -> jakarta (CRITICAL for Spring Boot 3)
        "useSpringBoot3" to "true",

        // 2. Generates 'interface UsersApi' instead of 'class UsersApiController'
        "interfaceOnly" to "true",

        // 3. Removes DefaultExceptionHandler and ApiException classes
        "exceptionHandler" to "false",

        // 4. Removes the "Default" impl methods in the interface
        "skipDefaultInterface" to "true",
        "dateLibrary" to "java8",
        // Standard settings
        "useTags" to "true",
        "useBeanValidation" to "true",
        "documentationProvider" to "springdoc",
        "gradleBuildFile" to "false"
    ))
}

// 4. TELL KOTLIN WHERE TO FIND THE GENERATED CODE
sourceSets {
    main {
        kotlin.srcDir("${layout.buildDirectory.locationOnly.get()}/generated/openapi/src/main/kotlin")
    }
}

// 5. ENSURE CODE IS GENERATED BEFORE COMPILING
tasks.withType<KotlinCompile> {
    dependsOn("openApiGenerate")
}
