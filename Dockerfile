# ==========================================
# Stage 1: Build the Application
# ==========================================
FROM gradle:9.2.1-jdk21 AS builder

ARG GITHUB_TOKEN
ARG GITHUB_ACTOR

WORKDIR /app

COPY build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle
COPY src ./src

RUN GITHUB_TOKEN=${GITHUB_TOKEN} GITHUB_ACTOR=${GITHUB_ACTOR} gradle bootJar

RUN mv build/libs/bazar-chat-*.jar build/libs/application.jar

WORKDIR /app/build/libs

RUN java -Djarmode=tools -jar application.jar extract \
    --layers \
    --destination extracted

# ==========================================
# Stage 2: Runtime
# ==========================================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /application

ENV JDK_JAVA_OPTIONS="-XX:MaxRAMPercentage=50.0 -Xss512k"

COPY --from=builder /app/build/libs/extracted/dependencies/ ./
COPY --from=builder /app/build/libs/extracted/spring-boot-loader/ ./
COPY --from=builder /app/build/libs/extracted/snapshot-dependencies/ ./
COPY --from=builder /app/build/libs/extracted/application/ ./

ENTRYPOINT ["java", "-jar", "application.jar"]