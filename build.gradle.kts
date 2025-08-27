plugins {
    id("java")
    id("com.vanniktech.maven.publish") version "0.34.0" apply false
    id("maven-publish")
}

allprojects {
    group = "io.github.jose-polanco-oxte"
    version = "1.0.0"
}

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "com.vanniktech.maven.publish")
}