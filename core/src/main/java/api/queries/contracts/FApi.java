package api.queries.contracts;

import api.FilterableApi;

/**
 * Interface for finalizing filter query building and returning to the main {@link FilterableApi}.
 *
 * @param <T> the type of the entity being queried
 * @see FilterableApi
 */
public interface FApi<T> {
    /**
     * Finalizes the specification building process and returns to the main {@link FilterableApi} interface.
     *
     * @return the main {@link FilterableApi} instance
     */
    FilterableApi<T> let();
}