plugins {
    id("java")
    id("io.spring.dependency-management") version "1.1.7"
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
        name.set("filterable-api-${project.name}")
        description.set("Module ${project.name} of Filterable API")
        inceptionYear.set("2025")
        url.set("https://github.com/Jose-Polanco-Oxte/filterable-api")
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
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
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.5.4")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:6.0.0-RC1")

    implementation(project(":core"))
}

tasks.withType<Test> {
    useJUnitPlatform()
}
