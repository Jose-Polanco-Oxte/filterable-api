package io.github.josepolanco.filterable.api.queries.criteria;

import io.github.josepolanco.filterable.api.FilterableApi;
import io.github.josepolanco.filterable.api.configurations.OperationRegistry;
import io.github.josepolanco.filterable.api.operations.CollectionOp;
import io.github.josepolanco.filterable.api.operations.Op;
import io.github.josepolanco.filterable.api.queries.contracts.CustomQueries;
import io.github.josepolanco.filterable.api.queries.contracts.FApi;
import io.github.josepolanco.filterable.api.queries.contracts.MetamodelQuery;
import io.github.josepolanco.filterable.api.queries.utils.FilterSpecification;

/**
 * Base class for building specifications based on entity attributes and operations.
 * <p>The interface {@link MetamodelQuery} provides methods to filter based on entity attributes
 * <p>The interface {@link FApi} provides the {@link #let()} method to finalize the specification building process and return to {@link FilterableApi}
 * <p>The interface {@link CustomQueries} provides a method to add custom specifications.
 *
 * @param <T>  the entity type
 * @param <Y>  the attribute type
 * @param <E>  the operation enum type must extend {@link Op}
 * @param <EC> the collection operation enum type must extend {@link CollectionOp}
 */
public abstract class SpecQuery<T, Y, E extends Enum<? extends Op>, EC extends Enum<? extends CollectionOp>> implements MetamodelQuery<T, Y, E, EC>, FApi<T>, CustomQueries<T, T, Y, E, EC> {
    /**
     * Configures a custom operation registry to manage available operations (internal use).
     *
     * @param registry the operation registry
     */
    protected abstract void setRegistry(OperationRegistry registry);

    /**
     * Sets the current filter specification (internal use).
     *
     * @param specification the filter specification
     */
    protected abstract void setSpecification(FilterSpecification<T> specification);
}