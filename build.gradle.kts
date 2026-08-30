import org.gradle.kotlin.dsl.annotationProcessor
import org.gradle.kotlin.dsl.withType

plugins {
    id("java")
    id("io.spring.dependency-management") version "1.1.7"
    id("org.springframework.boot") version "4.0.3"
    id("io.freefair.lombok") version "9.2.0"
    jacoco
}

group = "org.bazar"
version = "1.0.7"
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
    mavenLocal()
    maven {
        url = uri("https://maven.pkg.github.com/Xatas-dev/bazar-authorization-sdk")
        credentials {
            password = System.getenv("GITHUB_TOKEN")
                ?: project.findProperty("gpr.token") as String?
            username = System.getenv("GITHUB_ACTOR")
                ?: project.findProperty("gpr.user") as String?
        }
    }
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:4.0.1")
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2025.1.1")
    }
}

dependencies {
    //Web
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("io.swagger.core.v3:swagger-annotations:2.2.38")
    implementation("io.swagger.core.v3:swagger-models:2.2.38")

    //Observability
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    //Database
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-liquibase")
    runtimeOnly("org.postgresql:postgresql")

    //Security
    implementation("org.springframework.boot:spring-boot-starter-security-oauth2-resource-server")
    implementation("org.bazar:bazar-authorization-sdk:1.0.1")

    //Kafka
    implementation("org.springframework.boot:spring-boot-starter-kafka")

    //Feign
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

    //Cache
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.8")

    //Web Push
    implementation("nl.martijndwars:web-push:5.1.1")
    implementation("org.bouncycastle:bcprov-jdk18on:1.82")
    implementation("org.bouncycastle:bcpkix-jdk18on:1.82")

    //AOP
    implementation("org.springframework.boot:spring-boot-starter-aop:3.5.15")

    //Test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-websocket-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.testcontainers:postgresql:1.21.0")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:testcontainers-junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation("org.awaitility:awaitility:4.2.0")
    testImplementation("org.wiremock:wiremock-standalone:3.10.0")
    testImplementation("com.tngtech.archunit:archunit-junit5:1.2.1")
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

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-Amapstruct.defaultComponentModel=spring")
}
