plugins {
    id("io.spring.dependency-management") version "1.1.7"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.5.4")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:6.0.0-RC1")

    implementation(project(":core"))
}

tasks.withType<Test> {
    useJUnitPlatform()
}
