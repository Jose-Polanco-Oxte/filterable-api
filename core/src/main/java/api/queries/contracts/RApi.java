package api.queries.contracts;

import api.relations.RelationalApi;

public interface RApi<T, R> {
    RelationalApi<T, R> let();
}
