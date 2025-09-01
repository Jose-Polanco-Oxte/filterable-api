# Reference

## Table of Contents

- [Filterable Api](#filterable-api)
    - [API Call](#API-call)
        - [Creation](#creation)
        - [Initial Specification](#initial-specification)
        - [Build Specification](#build-specification)
        - [Considerations](#considerations)
    - [Configuring Filterable](#configuring-filterable)
        - [Disable Filters](#disabling-filters)
        - [Disable by group](#disable-by-group)
        - [Exceptions](#exceptions)
            - [FilterDisabledException](#filterdisabledexception)
    - [Building Filters](#building-filters)
        - [Filter Records Types](#filter-records-types)
            - [Basic Filters](#basic-filters)
            - [Collection Filters](#collection-filters)
            - [Range Filters](#range-filters)
        - [Filter Operations](#filter-operations)
            - [Comparable Operators](#comparable-operators)
            - [Text Operators](#text-operators)
            - [In Operators](#in-operators)
            - [Text Collection Operators](#text-collection-operators)
        - [Comparable](#comparable)
        - [Text](#text)
        - [Relational](#relational)
- [Spring Data JPA Integration](#spring-data-jpa-integration)
    - [Convert to Specification](#convert-to-specification)
    - [Use with Repositories](#use-with-repositories)
    - [Example Repository](#example-repository)
- [License](#license)

## Filterable Api

The Filterable API provides a flexible way to build and apply filters to data sets. It allows for dynamic filtering
based on various criteria, making it easier to manage and retrieve relevant data.

### API Call
The Filterable API can be accessed through the `FilterableApi` class, which provides methods to create and configure
filterable instances.

#### Creation
To create a new instance of `FilterableApi`, use the static `create` method:
```java
FilterableApi<Entity> api = FilterableApi.create();
```
Where `Entity` is the type of the data you want to filter.