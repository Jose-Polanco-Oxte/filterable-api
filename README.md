# 🧩 Filterable API

A lightweight and type-safe filtering library for Java, designed to build dynamic JPA `Specification`s using fluent builder patterns and `SingularAttribute` metamodels.

Perfect for projects that require dynamic querying without sacrificing compile-time safety or maintainability.

---

### ✨ Features

* ✅ Fluent, builder-style DSL (`filter(...)`, `build()`, etc.)
* ✅ Based on `javax.persistence.criteria.*` and `SingularAttribute<T, ?>`
* ✅ Supports operations:

  * `EQ`, `NEQ` (equals / not equals)
  * `GT`, `GTE`, `LT`, `LTE` (for `Comparable` types)
  * `IN`, `NOT_IN`, `CONTAINS`, `CONTAINS_ALL`, `STARTS_WITH`, `ENDS_WITH` (for strings & collections)
* ✅ Builds composable `Specification<T>` objects
* 
* ✅ Test-friendly with metamodel mocking
* ✅ Clear separation between core, test utilities, and optional Spring integration

---

### 🔧 Installation

#### Maven

Add the following dependencies to your `pom.xml`:

```xml
<dependencies>
    <!-- Core filtering library -->
    <dependency>
        <groupId>io.github.jose-polanco-oxte</groupId>
        <artifactId>core</artifactId>
        <version>1.0.0</version>
    </dependency>
    
    <!-- Optional Spring Data JPA integration -->
    <dependency>
        <groupId>io.github.jose-polanco-oxte</groupId>
        <artifactId>spring-extension</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

#### Gradle

Add the following dependencies to your `build.gradle` or `build.gradle.kts`:

```groovy
dependencies {
    // Core filtering library
    implementation 'io.github.jose-polanco-oxte:core:1.0.0'
    
    // Optional Spring Data JPA integration
    implementation 'io.github.jose-polanco-oxte:spring-extension:1.0.0'
}
```

For Kotlin DSL (`build.gradle.kts`):

```kotlin
dependencies {
    // Core filtering library
    implementation("io.github.jose-polanco-oxte:core:1.0.0")
    
    // Optional Spring Data JPA integration
    implementation("io.github.jose-polanco-oxte:spring-extension:1.0.0")
}
```

#### Development Setup (for contributors)

For a local Gradle multi-module setup:

```groovy
dependencies {
    implementation project(":filterable-api:core")
    implementation project(":filterable-api:spring-extension")
}
```

---

### 📦 Project Structure

```bash
filterable-api/
├── core/               # Main filtering logic & fluent builders
│   ├── api/
|     ├── configurations/
|     ├── ComparableConfig.java
|     ├── TextConfig.java
|     └── ...
|     ├── exceptions/
|     └── FilterDisabledException.java
|     ├── operations/
|     ├── CollectionOp.java
|     └── Op.java
|     ├── queries/
|       ├── contracts/
|         ├── CustomQueries.java
|         ├── MetamodelQuery.java
|         └── ...
|       ├── criteria/
|         ├── ConfigStage.java
|         ├── CriteriaSingularBuilder.java
|         └── ...
|       ├── utils/
|       └── FilterSpecification.java
|     ├── relations/
|       ├── JoinPathBuilder.java
|       ├── RelationalApi.java
│   ├── FilterableApi.java
├── filters/         # Metamodel mocks and testing utilities
│   ├── operations/
|     ├── ComparableOperation.java
|     ├── FilterOperation.java
|     └── ...
│   ├── CollectionFilter.java
│   ├── Filter.java
│   ├── RangeFilter.java

└── spring-extension/   # Optional Spring Data JPA integration layer
```

---

### 🛠️ Basic Usage

Suppose you have a `User` entity with a generated metamodel class `User_`:

```java
var specification = FilterableApi.<User>builder()
    .filter(User_.age, 25, ComparableOperation.GTE)
    .filter(User_.name, "Tony", TextOperation.EQ)
    .build();
```

Then pass the `specification` to a Spring Data JPA repository:

```java
userRepository.findAll(specification);
```
---

### 🧱 Internal Design

* `FilterSpecification<T>` is a support functional interface for predicate management
* `FilterableApi<T>` provides a composable builder-style API
* Builders like `CriteriaSingularBuilder` and `CriteriaSingularComparableBuilder` abstract the JPA Criteria logic
* Extensible design: can support collection filters, text filters, and more
---

### 📌 Requirements

* Java 21+
* JPA 3.2+ (Hibernate, EclipseLink, etc.)
* Optional: Spring Data JPA

---

### 📂 Roadmap

* [ ] Nested filtering (`user.address.city`)
* [ ] Auto-generating filters from annotated DTOs
* [ ] Exporting filters to JSON or query strings
* [ ] Support for grouped OR logic

---

### 🚀 Publishing & Development

#### Building the Project

```bash
./gradlew clean build
```

#### Publishing to Local Maven Repository

```bash
./gradlew publishToMavenLocal
```

#### Publishing to Maven Central

The project is configured to publish to Maven Central. Set the following environment variables or add them to your `~/.gradle/gradle.properties`:

```properties
# Maven Central / OSSRH credentials
ossrh.username=your-sonatype-username
ossrh.password=your-sonatype-password

# Signing configuration (required for Maven Central)
signing.keyId=your-key-id
signing.password=your-key-password
signing.secretKeyRingFile=/path/to/your/secring.gpg
```

Then run:

```bash
./gradlew publish
```

#### Publishing to GitHub Packages

Set the following environment variables or add them to your `~/.gradle/gradle.properties`:

```properties
# GitHub Packages credentials
gpr.user=your-github-username
gpr.key=your-github-personal-access-token
```

Then run:

```bash
./gradlew publishMavenPublicationToGitHubPackagesRepository
```
