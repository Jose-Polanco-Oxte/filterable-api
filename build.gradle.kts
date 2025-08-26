plugins {
    id("java")
    id("org.jreleaser") version "1.19.0"
    id("maven-publish")
    id("signing")
}

allprojects {
    group = "io.github.jose-polanco-oxte"
    version = "1.0.0"
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")
    apply(plugin = "signing")

    java {
        withSourcesJar()
        withJavadocJar()
    }

    publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                from(components["java"])

                pom {
                    name.set(project.name)
                    description.set("Módulo ${project.name} de mi librería")
                    url.set("https://github.com/Jose-Polanco-Oxte/filterable-api")
                    licenses {
                        license {
                            name.set("Apache-2.0")
                            url.set("https://www.apache.org/licenses/LICENSE-2.0")
                        }
                    }
                    developers {
                        developer {
                            id.set("jose-polanco-oxte")
                            name.set("José Antonio Polanco Oxté")
                        }
                    }
                    scm {
                        url.set("https://github.com/Jose-Polanco-Oxte/filterable-api")
                        connection.set("scm:git:git://github.com/Jose-Polanco-Oxte/filterable-api.git")
                        developerConnection.set("scm:git:ssh://github.com/Jose-Polanco-Oxte/filterable-api.git")
                    }
                }
            }
        }
        signing {
            sign(publishing.publications["mavenJava"])
        }
    }
}

allprojects {
    group = "io.github.jose-polanco-oxte"
    version = "1.0.0"
}