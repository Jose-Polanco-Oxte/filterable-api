package api.queries.contracts;

import api.FilterableApi;

public interface FApi<T> {
    FilterableApi<T> let();
}