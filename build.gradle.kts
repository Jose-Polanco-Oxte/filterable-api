plugins {
    id("java")
    id("com.vanniktech.maven.publish") version "0.34.0" apply false
}

repositories {
    mavenCentral()
    mavenLocal()
}

allprojects {
    group = "io.github.jose-polanco-oxte"
    version = "1.0.1"
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-Xlint:deprecation")
    options.compilerArgs.add("-Xlint:unchecked")
}

dependencies {
    implementation("io.github.jose-polanco-oxte:filterable-api-core:1.0.1")
    implementation("io.github.jose-polanco-oxte:filterable-api-spring-extension:1.0.1")
}

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "com.vanniktech.maven.publish")

    plugins.withId("java") {
        the<JavaPluginExtension>().toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
        tasks.withType<JavaCompile>().configureEach {
            options.release.set(17)
        }
    }
}