plugins {
    id("java")
    id("maven-publish")
}

group = "com.JPolanco"
version = "1.0.0-SNAPSHOT"

java {
    toolchain{ languageVersion.set(JavaLanguageVersion.of(21)) }
    withSourcesJar()
    withJavadocJar()
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")

    group = "com.JPolanco"
    version = "1.0.0-SNAPSHOT"

    java {
        toolchain { languageVersion.set(JavaLanguageVersion.of(21)) }
        withSourcesJar()
        withJavadocJar()
    }

    publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                from(components["java"])
                groupId = group.toString()
                version = version.toString()
            }
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = group.toString()
            artifactId = "filterable-api"
            version = version.toString()
        }
    }
}

tasks.test {
    useJUnitPlatform()
}