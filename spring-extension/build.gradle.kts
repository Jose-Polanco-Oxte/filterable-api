plugins {
    java
    `java-library`
    id("io.spring.dependency-management") version "1.1.7"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
    withJavadocJar()
    withSourcesJar()
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.5.4")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:6.0.0-RC1")

    implementation(project(":core"))
}

tasks.withType<Test> {
    useJUnitPlatform()
}
