import org.gradle.kotlin.dsl.annotationProcessor
import org.gradle.kotlin.dsl.withType

plugins {
    id("java")
    id("org.springframework.boot") version "4.0.1"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.openapi.generator") version "7.2.0"
    id("io.freefair.lombok") version "9.2.0"
    jacoco
}

group = "org.bazar"
version = "1.0.0"
description = "bazar-chat"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

jacoco {
    toolVersion = "0.8.10"
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        html.required.set(true)
        xml.required.set(true)
        csv.required.set(false)
    }
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = 0.8.toBigDecimal()
            }
        }
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
    implementation("io.swagger.core.v3:swagger-models:2.2.38")
    implementation("org.openapitools:jackson-databind-nullable:0.2.4")

    //Observability
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    //Database
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-liquibase")
    runtimeOnly("org.postgresql:postgresql")

    //Security
    implementation("org.springframework.boot:spring-boot-starter-security-oauth2-resource-server")

    //Kafka
    implementation("org.springframework.boot:spring-boot-starter-kafka")

    //Test
    testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")
    testImplementation("org.springframework.boot:spring-boot-starter-liquibase-test")
    testImplementation("org.springframework.boot:spring-boot-starter-security-oauth2-resource-server-test")
    testImplementation("org.springframework.boot:spring-boot-starter-validation-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
    testImplementation("org.springframework.boot:spring-boot-starter-websocket-test")
    testImplementation("org.testcontainers:postgresql:1.21.0")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:testcontainers-junit-jupiter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation("org.awaitility:awaitility:4.2.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    //Other
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation("org.mapstruct:mapstruct:1.6.3")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.0")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")
    implementation("net.javacrumbs.shedlock:shedlock-spring:5.13.0")
    implementation("net.javacrumbs.shedlock:shedlock-provider-jdbc-template:5.13.0")
}


tasks.withType<Test> {
    useJUnitPlatform()
}

openApiGenerate {
    generatorName.set("spring")
    inputSpec.set("$rootDir/src/main/resources/openapi/bazar-chat.yaml")
    outputDir.set("${layout.buildDirectory.locationOnly.get()}/generated/openapi")

    apiPackage.set("org.bazar.chat.api")
    modelPackage.set("org.bazar.chat.model")

    typeMappings.set(
        mapOf(
            "DateTime" to "java.time.Instant"
        )
    )

    importMappings.set(
        mapOf(
            "Pageable" to "org.springframework.data.domain.Pageable"
        )
    )

    schemaMappings.set(
        mapOf(
            "Pageable" to "org.springframework.data.domain.Pageable"
        )
    )

    configOptions.set(
        mapOf(
            // Jakarta namespace (Boot 4)
            "useSpringBoot3" to "true",

            // Только интерфейсы API
            "interfaceOnly" to "true",
            "useTags" to "true",
            "skipDefaultInterface" to "true",

            // Не генерировать exception handler
            "exceptionHandler" to "false",

            // java.time.*
            "dateLibrary" to "java8",

            // Validation + OpenAPI docs
            "useBeanValidation" to "true",
            "documentationProvider" to "springdoc",

            // Генерируем IMMUTABLE Java DTO
            "modelMutable" to "true",

            // Не генерировать gradle файлы
            "gradleBuildFile" to "false"
        )
    )
}


// 4. TELL Java WHERE TO FIND THE GENERATED CODE
sourceSets {
    main {
        java {
            srcDir("${buildDir}/generated/openapi/src/main/java")
        }
    }
}

// 5. ENSURE CODE IS GENERATED BEFORE COMPILING
tasks.compileJava {
    dependsOn("openApiGenerate")
}
