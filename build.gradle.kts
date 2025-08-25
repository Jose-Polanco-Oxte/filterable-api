import com.vanniktech.maven.publish.SonatypeHost

plugins {
    `java-library`
    `maven-publish`
    signing
    id("com.vanniktech.maven.publish") version "0.30.0"
}

allprojects {
    group = "io.github.jose-polanco-oxte"
    version = "1.0.0"
}

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "com.vanniktech.maven.publish")

    java {
        withSourcesJar()
        withJavadocJar()
    }

    afterEvaluate {
        mavenPublishing {
            publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
            signAllPublications()

            coordinates("io.github.jose-polanco-oxte", project.name, version.toString())

            pom {
                name.set(project.name)
                description.set("Filtering and searching utilities for Metamodels")
                url.set("https://github.com/Jose-Polanco-Oxte/filterable-api")

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                developers {
                    developer {
                        id.set("jose-polanco-oxte")
                        name.set("José Antonio")
                        email.set("josepolanco4569@gmail.com")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/Jose-Polanco-Oxte/filterable-api.git")
                    developerConnection.set("scm:git:ssh://github.com/Jose-Polanco-Oxte/filterable-api.git")
                    url.set("https://github.com/Jose-Polanco-Oxte/filterable-api")
                }
            }
        }
    }
}