package filters.operations;

import api.operations.CollectionOp;

public enum TextCollectionOperation implements CollectionOp {
    IN,
    NOT_IN,
    CONTAINS,
    NOT_CONTAINS,
    CONTAINS_ALL,
    STARTS_WITH,
    ENDS_WITH,
}
