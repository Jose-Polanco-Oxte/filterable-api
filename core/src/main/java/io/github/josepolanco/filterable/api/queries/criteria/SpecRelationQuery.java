package io.github.josepolanco.filterable.api.queries.criteria;

import io.github.josepolanco.filterable.api.FilterableApi;
import io.github.josepolanco.filterable.api.configurations.OperationRegistry;
import io.github.josepolanco.filterable.api.operations.CollectionOp;
import io.github.josepolanco.filterable.api.operations.Op;
import io.github.josepolanco.filterable.api.queries.contracts.CustomQueries;
import io.github.josepolanco.filterable.api.queries.contracts.MetamodelQuery;
import io.github.josepolanco.filterable.api.queries.contracts.RApi;
import io.github.josepolanco.filterable.api.queries.utils.FilterSpecification;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Root;

import java.util.function.Function;

/**
 * Base class for building specifications based on entity relationships and operations.
 * <p>The interface {@link MetamodelQuery} provides methods to filter based on entity attributes
 * <p>The interface {@link RApi} provides the {@link #let()} method to finalize the specification building process and return to {@link FilterableApi}
 * <p>The interface {@link CustomQueries} provides a method to add custom specifications.
 *
 * @param <T>  the entity type
 * @param <R>  the related entity type
 * @param <Y>  the attribute type of the related entity
 * @param <E>  the operation enum type must extend {@link Op}
 * @param <EC> the collection operation enum type must extend {@link CollectionOp}
 */
public abstract class SpecRelationQuery<T, R, Y, E extends Enum<? extends Op>, EC extends Enum<? extends CollectionOp>> implements MetamodelQuery<R, Y, E, EC>, RApi<T, R>, CustomQueries<T, R, Y, E, EC> {
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

    /**
     * Sets the join path function to navigate from the root entity to the related entity (internal use).
     *
     * @param joinPath a function that takes a {@link Root} of type T and returns a {@link From} of type R
     */
    protected abstract void setJoinPath(Function<Root<T>, From<?, R>> joinPath);
}
