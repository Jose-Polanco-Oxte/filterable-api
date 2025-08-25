plugins {
    `java-library`
    `maven-publish`
    signing
}

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")
    apply(plugin = "signing")

    group = "io.github.jose-polanco-oxte"
    version = "1.0.1"

    publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                from(components["java"])

                // Customize pom
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
                        developerConnection.set("scm:git:ssh://github.com:Jose-Polanco-Oxte/filterable-api.git")
                        url.set("https://github.com/Jose-Polanco-Oxte/filterable-api")
                    }
                }
            }
        }
        repositories {
            mavenLocal()
            maven {
                name = "OSSRH"
                url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                credentials {
                    username = findProperty("ossrhUsername") as String?
                    password = findProperty("ossrhPassword") as String?
                }
            }
        }
    }

    signing {
        useGpgCmd()
        sign(publishing.publications["mavenJava"])
    }
}

tasks.test {
    useJUnitPlatform()
}