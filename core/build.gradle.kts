plugins {
    id("java")
}

repositories {
    mavenCentral()
}

tasks.withType<PublishToMavenLocal> {
    dependsOn(tasks.withType<Sign>())
}

mavenPublishing {
    coordinates(group.toString(), "filterable-api-${project.name}", version.toString())

    pom {
        name.set(project.name)
        description.set("Module ${project.name} of Filterable API")
        inceptionYear.set("2025")
        url.set("https://github.com/Jose-Polanco-Oxte/filterable-api")
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set("Jose-Polanco-Oxte")
                name.set("José Antonio Polanco Oxte")
                email.set("josepolanco4569@gmail.com")
                url.set("https://github.com/Jose-Polanco-Oxte")
            }
        }
        scm {
            url.set("https://github.com/Jose-Polanco-Oxte/filterable-api")
            connection.set("scm:git:git://github.com/Jose-Polanco-Oxte/filterable-api.git")
            developerConnection.set("scm:git:ssh://git@github.com/Jose-Polanco-Oxte/filterable-api.git")
        }
    }
}

dependencies {
    implementation("jakarta.persistence:jakarta.persistence-api:3.2.0")
    implementation("jakarta.validation:jakarta.validation-api:3.1.1")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.mockito:mockito-junit-jupiter:5.18.0")
    testImplementation("org.mockito:mockito-core:5.18.0")
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}