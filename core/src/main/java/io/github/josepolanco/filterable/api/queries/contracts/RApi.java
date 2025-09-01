package io.github.josepolanco.filterable.api.queries.contracts;

import io.github.josepolanco.filterable.api.relations.RelationalApi;

/**
 * Interface for finalizing relational query building and returning to the main {@link RelationalApi}.
 *
 * @param <T> the type of the entity being queried
 * @param <R> the type of the related entity
 * @see RelationalApi
 */
public interface RApi<T, R> {
    /**
     * Finalizes the relational query building process and returns to the main {@link RelationalApi} interface.
     *
     * @return the main {@link RelationalApi} instance
     */
    RelationalApi<T, R> let();
}
