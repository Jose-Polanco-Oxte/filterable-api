package io.github.josepolanco.filterable.api.queries.contracts;

import io.github.josepolanco.filterable.api.operations.CollectionOp;
import io.github.josepolanco.filterable.api.operations.Op;
import io.github.josepolanco.filterable.api.queries.utils.FilterSpecification;

/**
 * Interface for defining custom queries with filtering capabilities.
 *
 * @param <T>  The type of the entity being queried.
 * @param <R>  The type of the result returned by the query.
 * @param <Y>  The type of the field used for filtering.
 * @param <E>  The enum type representing operations for filtering must extend {@link Op}.
 * @param <EC> The enum type representing collection operations for filtering must extend {@link CollectionOp}.
 * @see Op
 * @see CollectionOp
 */
public interface CustomQueries<T, R, Y, E extends Enum<? extends Op>, EC extends Enum<? extends CollectionOp>> {
    /**
     * Applies a custom filter specification to the query,
     * if the specification is null, the method will have no effect.
     *
     * @param specification The filter specification defining the filtering criteria
     * @return The updated query instance with the applied filter
     */
    MetamodelQuery<R, Y, E, EC> custom(FilterSpecification<T> specification);
}