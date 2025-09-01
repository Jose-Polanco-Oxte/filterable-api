# How to use Filterable API

1. [Introduction](#introduction)
2. [Features](#features)
3. [Singular Attribute Filtering](#singular-attribute-filtering)
4. [Collection Filtering](#collection-filtering)
5. [Records and DTOs](#records-and-dtos)
6. [Relationship Filtering](#relationship-filtering)
7. [Custom Filters](#custom-filters)
8. [Combining Filters](#combining-filters)
9. [Exception Handling](#exception-handling-and-filter-configuration)
10. [Integration with Spring Boot](#integration-with-spring-boot)

## Introduction

Filterable API is a powerful library that allows developers to implement dynamic filtering in their applications using
metamodel attributes. It provides a type-safe way to create filters, ensuring that the filters are consistent with the
entity model.

## Features

- Dynamic filtering using metamodel attributes.
- Type safety with metamodel attributes.
- Simple and intuitive DSL for filter creation.
- Support for singular and collection attributes.
- Records and DTOs support classes.
- Relationship filtering.
- Custom filter operations.
- Combining multiple filters with logical operations.
- Exception handling for invalid filters.
- Easy integration with Spring Boot.

## Singular Attribute Filtering

To filter entities based on singular attributes, you can use the `Metamodel Attribute` class. Here's an example of how
to create a filter for a singular attribute:

```java
import io.github.josepolanco.filterable.api.FilterableApi;
import io.github.josepolanco.filterable.api.queries.utils.FilterSpecification;
import io.github.josepolanco.filterable.filters.operations.ComparableOperation;

import java.time.Instant;

public class Test {

    void test() {
        FilterSpecification<Employee> filter = FilterableApi.<Employee>create()
                .<Instant>comparable().configure(cfg -> cfg
                        .disableEquals()
                        .disableNotEquals()
                        .disableGt()
                ).filter(Employee_.entryDate, 2, ComparableOperation.LT)
                .let().build();
    }
}

```

In this example, we create a filter for the `entryDate` attribute of the `Employee` entity, allowing only the "less
than" operation.

## Collection Filtering

To filter entities with operations as `IN` and `NOT IN` or text (`String`) lists, you can use the filterIn method.
Here's an example:

### Filtering collections for comparable attributes

```java
import io.github.josepolanco.filterable.api.FilterableApi;
import io.github.josepolanco.filterable.filters.operations.InOperation;
import io.github.josepolanco.filterable.api.queries.utils.FilterSpecification;

import java.time.Instant;
import java.util.List;

public class Test {
    void test() {
        FilterSpecification<Employee> filter = FilterableApi.<Employee>create()
                .<Instant>comparable().configure(cfg -> cfg
                        .disableIn()
                        .disableNotIn()
                ).filterIn(Employee_.entryDate, List.of(
                        Instant.parse("2023-01-01T00:00:00Z"),
                        Instant.parse("2023-06-01T00:00:00Z")
                ), InOperation.IN)
                .let().build();
    }
}
```

### Filtering collections for text attributes

```java
import io.github.josepolanco.filterable.api.FilterableApi;
import io.github.josepolanco.filterable.api.queries.utils.FilterSpecification;
import io.github.josepolanco.filterable.filters.operations.TextOperation;

import java.util.List;

public class EmployeeFilter {
    void test() {
        FilterSpecification<Employee> filter = FilterableApi.<Employee>create()
                .text().configure(cfg -> cfg
                        .disableContainsAny()
                        .disableStartsWith()
                ).filterIn(Employee_.firstName, List.of("John", "Jane"), TextOperation.ENDS_WITH)
                .let().build();
    }
}
```

### Records and DTOs

Filterable API supports filtering using records and DTOs. You can create a filter object from a record and use it in
your DTOs. Here's an example:

```java
import io.github.josepolanco.filterable.api.FilterableApi;
import io.github.josepolanco.filterable.api.queries.utils.FilterSpecification;
import io.github.josepolanco.filterable.filters.Filter;
import io.github.josepolanco.filterable.filters.operations.ComparableOperation;
import io.github.josepolanco.filterable.filters.operations.TextOperation;

public class Test {

    void test() {
        // Record class
        Filter<Long, ComparableOperation> filterId = new Filter<>(1L, ComparableOperation.EQ);
        Filter<String, TextOperation> filterName = new Filter<>("John", TextOperation.STARTS_WITH);

        FilterSpecification<Employee> filter = FilterableApi.<Employee>create()
                .<Long>comparable().configure()
                .filter(filterId, Employee_.id)
                .let().text().configure()
                .filter(filterName, Employee_.name)
                .let().build();
    }
}
```

> and use it in a DTO:

```java
import io.github.josepolanco.filterable.filters.Filter;
import io.github.josepolanco.filterable.filters.operations.ComparableOperation;
import io.github.josepolanco.filterable.filters.operations.TextOperation;

public class EmployeeDto {
    private Filter<Long, ComparableOperation> Id;
    private Filter<String, TextOperation> name;
// Getters and Setters
}
```

## Relationship Filtering

Filterable API allows filtering based on relationships between entities. You can create filters for related entities
using
the relational method. Here's an example:

```java
import io.github.josepolanco.filterable.api.FilterableApi;
import io.github.josepolanco.filterable.api.queries.utils.FilterSpecification;
import io.github.josepolanco.filterable.filters.Filter;
import io.github.josepolanco.filterable.filters.operations.ComparableOperation;
import io.github.josepolanco.filterable.filters.operations.TextOperation;

import java.time.Instant;

public class Test {

    void test() {
        // Record class
        Filter<Instant, ComparableOperation> purchaseDate = new Filter<>(Instant.now(), ComparableOperation.GT);
        Filter<String, TextOperation> companyName = new Filter<>("John", TextOperation.STARTS_WITH);

        FilterSpecification<Employee> filter = FilterableApi.<Employee>create()
                .relational().join(Department_.id).join(Department_.company).buildPath()
                .text().configure()
                .filter(companyName, Department_.name)
                .let().backToFilterableApi()
                .relational().join(Company_.name).buildPath()
                .<Instant>comparable().configure()
                .filter(purchaseDate, Department_.hireDate)
                .let().build();
    }
}
```

In this example, we create filters for the `companyName` and `purchaseDate` attributes of related entities.

## Custom Filters

Filterable API allows you to create custom filters by `FilterSpecification` functional interface. Here's an example:

```java
import io.github.josepolanco.filterable.api.FilterableApi;
import io.github.josepolanco.filterable.api.queries.utils.FilterSpecification;

public class Test {

    void test() {

        // Record class
        String country = "brazil";

        FilterSpecification<Employee> filter = FilterableApi.<Employee>create()
                .text().configure()
                .custom((root, query, criteriaBuilder) -> criteriaBuilder.like(Employee_.city, "%america." + country + "%"))
                .let().build();
    }
}
```

## Combining Filters

You can combine multiple filters using the api's methods. Here's an example of combining filters with secuencial calls:

```java
import io.github.josepolanco.filterable.api.FilterableApi;
import io.github.josepolanco.filterable.api.queries.utils.FilterSpecification;
import io.github.josepolanco.filterable.filters.operations.ComparableOperation;
import io.github.josepolanco.filterable.filters.operations.TextOperation;

import java.time.Instant;

public class Test {

    void test() {
        FilterSpecification<Employee> filter = FilterableApi.<Employee>create()
                .<Instant>comparable().configure()
                .filter(Employee_.entryDate, Instant.parse("2023-01-01T00:00:00Z"), ComparableOperation.GT)
                .filter(Employee_.salary, 50000, ComparableOperation.GT)
                .let().text().configure()
                .filter(Employee_.firstName, "John", TextOperation.STARTS_WITH)
                .let().build();
    }
}
```

In this example, we create a filter that combines conditions on the `entryDate`, `salary`, and `firstName` attributes.

## Exception Handling and Filter Configuration

Filterable API provides exception handling for invalid filters. You can configure exceptions for specific attributes
using the `configure` method. Here's an example:

```java
import io.github.josepolanco.filterable.api.FilterableApi;
import io.github.josepolanco.filterable.api.queries.utils.FilterSpecification;
import io.github.josepolanco.filterable.api.exceptions.FilterDisabledException;
import io.github.josepolanco.filterable.filters.operations.ComparableOperation;
import io.github.josepolanco.filterable.filters.operations.TextOperation;

import java.time.Instant;

public class Test {

    void test() throws FilterDisabledException {
        FilterSpecification<Employee> filter = FilterableApi.<Employee>create()
                .<Instant>comparable().configure(cfg -> cfg
                        .disableEquals()
                        .disableNotEquals()
                        .disableGt()
                        .disableGte()
                ).filter(Employee_.entryDate, Instant.parse("2023-01-01T00:00:00Z"), ComparableOperation.LT)
                .let().text().configure(cfg -> cfg
                        .disableContainsAll()
                        .disableStartsWith()
                ).filter(Employee_.firstName, "John", TextOperation.ENDS_WITH)
                .let().build();
    }
}
```

When attempting to use a disabled operation, a `FilterDisabledException` will be thrown.

## Integration with Spring Boot

Filterable API can be easily integrated with Spring Boot applications. You can use the filters in your service layer
to apply dynamic filtering to your repositories. Here's an example:

```java
import io.github.josepolanco.filterable.api.FilterableApi;
import io.github.josepolanco.filterable.api.queries.utils.FilterSpecification;
import io.github.josepolanco.filterable.filters.operations.ComparableOperation;
import io.github.josepolanco.filterable.filters.operations.TextOperation;
import io.github.josepolanco.filterable.spring.Wrapper;

import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;

public class EmployeeService {

    public Specification<Employee> getEmployeeSpecification() {
        FilterSpecification<Employee> filterSpec = FilterableApi.<Employee>create()
                .<Instant>comparable().configure()
                .filter(Employee_.entryDate, Instant.now(), ComparableOperation.LT)
                .let().text().configure()
                .filter(Employee_.firstName, "alex", TextOperation.STARTS_WITH)
                .let().build();

        return Wrapper.from(filterSpec);
    }
}
```

In this example, we create a `Specification` for the `Employee` entity using Filterable API and wrap it for use with
Spring Data JPA.

## Conclusion

Filterable API is a versatile and powerful library that simplifies the implementation of dynamic filtering in Java
applications. Its type-safe approach using metamodel attributes ensures consistency and reliability in filter creation.
With its intuitive DSL, support for various attribute types, and easy integration with Spring Boot, it is an
excellent choice for developers looking to enhance their application's filtering capabilities.

For more information, visit [Filterable API reference](Reference.md)
