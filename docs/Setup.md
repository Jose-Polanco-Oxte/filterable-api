# Setup guide

This project is licensed under the MIT License. See the [LICENSE](../LICENCE) file for details.

## Prerequisites

- Java 17+
- Jakarta EE 10 or Spring Boot 3.0
- Maven 3.9.6 or Gradle 8.5
- Hibernate core for metamodel generation
- A project structure that supports module separation for metamodel generation
- Familiarity with JPA and Criteria API
- Basic knowledge of JPA and Criteria API
- Understanding of your project's build tool (Maven or Gradle)
- Familiarity with your chosen framework (Jakarta EE or Spring Boot)
- Basic understanding of metamodel generation and its benefits

## Implementation

Include the dependency in your project configuration file.

### Core

#### Maven

```
<dependency>
    <groupId>io.github.jose-polanco-oxte</groupId>
    <artifactId>filterable-api-core</artifactId>
    <version>1.0.1</version>
</dependency>
```

#### Gradle

##### Gradle Kotlin DSL

```
implementation("io.github.jose-polanco-oxte:filterable-api-core:1.0.1")
```

##### Gradle Groovy DSL

```
implementation 'io.github.jose-polanco-oxte:filterable-api-core:1.0.1'
```

### Spring extensions

#### Maven

```
<dependency>
    <groupId>io.github.jose-polanco-oxte</groupId>
    <artifactId>filterable-api-spring-extension</artifactId>
    <version>1.0.1</version>
</dependency>
```

#### Gradle

##### Gradle Kotlin DSL

```
implementation("io.github.jose-polanco-oxte:filterable-api-spring-extension:1.0.1")
```

##### Gradle Groovy DSL

```
implementation 'io.github.jose-polanco-oxte:filterable-api-spring-extension:1.0.1'
```
