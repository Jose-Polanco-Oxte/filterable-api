package io.github.josepolanco.filterable.api;

import io.github.josepolanco.filterable.api.configurations.ComparableConfig;
import io.github.josepolanco.filterable.api.configurations.TextConfig;
import io.github.josepolanco.filterable.api.queries.criteria.ConfigStage;
import io.github.josepolanco.filterable.api.queries.criteria.QueryComparableManager;
import io.github.josepolanco.filterable.api.queries.criteria.QueryTextManager;
import io.github.josepolanco.filterable.api.queries.utils.FilterSpecification;
import io.github.josepolanco.filterable.api.relations.JoinPathBuilder;

import java.util.Objects;

/**
 * Entry point for building filter specifications.
 *
 * @param <T> the type of the entity to filter.
 */
public class FilterableApi<T> {

    private final FilterSpecification<T> specification;

    private FilterableApi(FilterSpecification<T> specification) {
        this.specification = Objects.requireNonNullElseGet(specification, FilterSpecification::none);
    }

    /**
     * Creates a new instance of FilterableApi with no initial filter specification,
     * this is equivalent to calling initialSpec with an empty specification (null-safe).
     *
     * @param <T> the type of the entity to filter
     * @return a new FilterableApi instance
     */
    public static <T> FilterableApi<T> create() {
        return new FilterableApi<>(FilterSpecification.none());
    }

    /**
     * Creates a new instance of FilterableApi with the provided initial filter specification,
     * if initial specification is null, it will be replaced with null-safe empty specification.
     *
     * @param specification the initial filter specification
     * @param <T>           the type of the entity to filter
     * @return a new FilterableApi instance
     */
    public static <T> FilterableApi<T> initialSpec(FilterSpecification<T> specification) {
        return new FilterableApi<>(specification);
    }

    /**
     * Builds and returns the filter specification.
     *
     * @return the constructed filter specification
     */
    public FilterSpecification<T> build() {
        return specification;
    }

    /**
     * Starts building a comparable filter configuration,
     * {@code Y} must implement {@link Comparable} interface to ensure proper comparison operations.
     *
     * @param <Y> the type of the {@link Comparable} attribute
     * @return a configuration stage for comparable types
     * @see ComparableConfig
     * @see QueryComparableManager
     */
    public <Y extends Comparable<? super Y>> ConfigStage<T, Y, ComparableConfig<T, Y>, QueryComparableManager<T, Y>> comparable() {
        return new ConfigStage<>(new ComparableConfig<>(), new QueryComparableManager<T, Y>(), this.specification);
    }

    /**
     * Starts building a text filter configuration.
     *
     * @return a configuration stage for {@link String} types
     * @see TextConfig
     * @see QueryTextManager
     */
    public ConfigStage<T, String, TextConfig<T>, QueryTextManager<T>> text() {
        return new ConfigStage<>(new TextConfig<>(), new QueryTextManager<>(), this.specification);
    }

    /**
     * Starts building a relational filter configuration.
     *
     * @return a join path builder for relational types,
     * this method initializes the join path with the root entity type {@code T}.
     * @see JoinPathBuilder
     */
    public JoinPathBuilder<T, T> relational() {
        return JoinPathBuilder.root(this.specification);
    }
}