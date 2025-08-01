package filters.operations;

import api.operations.Op;

public enum TextOperation implements Op {
    EQ,
    NEQ,
    CONTAINS,
    NOT_CONTAINS,
    STARTS_WITH,
    ENDS_WITH
}
