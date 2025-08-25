plugins {
    java
    `java-library`
    `maven-publish`
    signing
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

allprojects {
    group = "io.github.jose-polanco-oxte"
    version = "1.0.0"
    
    apply(plugin = "java")
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")
    apply(plugin = "signing")
}

subprojects {
    repositories {
        mavenCentral()
    }
    
    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
        withJavadocJar()
        withSourcesJar()
    }
    
    publishing {
        publications {
            create<MavenPublication>("maven") {
                from(components["java"])
                
                pom {
                    name.set("Filterable API ${project.name}")
                    description.set("A lightweight and type-safe filtering library for Java, designed to build dynamic JPA Specifications using fluent builder patterns and SingularAttribute metamodels.")
                    url.set("https://github.com/Jose-Polanco-Oxte/filterable-api")
                    
                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("https://opensource.org/licenses/MIT")
                        }
                    }
                    
                    developers {
                        developer {
                            id.set("Jose-Polanco-Oxte")
                            name.set("Jose Polanco")
                            email.set("78385333+Jose-Polanco-Oxte@users.noreply.github.com")
                        }
                    }
                    
                    scm {
                        connection.set("scm:git:git://github.com/Jose-Polanco-Oxte/filterable-api.git")
                        developerConnection.set("scm:git:ssh://github.com/Jose-Polanco-Oxte/filterable-api.git")
                        url.set("https://github.com/Jose-Polanco-Oxte/filterable-api/tree/main")
                    }
                }
            }
        }
        
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/Jose-Polanco-Oxte/filterable-api")
                credentials {
                    username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                    password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
                }
            }
            maven {
                name = "OSSRH"
                url = if (version.toString().endsWith("SNAPSHOT")) {
                    uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
                } else {
                    uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                }
                credentials {
                    username = project.findProperty("ossrh.username") as String? ?: System.getenv("OSSRH_USERNAME")
                    password = project.findProperty("ossrh.password") as String? ?: System.getenv("OSSRH_PASSWORD")
                }
            }
        }
    }
    
    signing {
        if (project.hasProperty("signing.keyId")) {
            sign(publishing.publications["maven"])
        }
    }
}