package filters.operations;

import api.operations.CollectionOp;

public enum TextCollectionOperation implements CollectionOp {
    IN,
    NOT_IN,
    CONTAINS_ANY,
    CONTAINS_ALL,
    NOT_CONTAINS,
    STARTS_WITH,
    ENDS_WITH,
}
