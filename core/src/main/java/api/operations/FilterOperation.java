package api.operations;

import filters.operations.ComparableOperation;
import filters.operations.InOperation;
import filters.operations.TextCollectionOperation;
import filters.operations.TextOperation;

/**
 * Enum representing various filter operations for querying.
 */
public enum FilterOperation {
    EQUALS("equals"),
    NOT_EQUALS("notEquals"),
    GREATER_THAN("greaterThan"),
    GREATER_THAN_OR_EQUAL("greaterThanOrEqual"),
    LESS_THAN("lessThan"),
    LESS_THAN_OR_EQUAL("lessThanOrEqual"),
    IN("in"),
    NOT_IN("notIn"),

    // Operation string representation
    CONTAINS("contains"),
    NOT_CONTAINS("notContains"),
    CONTAINS_ALL("containsAll"),
    STARTS_WITH("startsWith"),
    ENDS_WITH("endsWith");

    private final String operation;

    FilterOperation(String operation) {
        this.operation = operation;
    }

    // Static factory methods to convert from other operation enums
    public static FilterOperation fromComparableOperation(ComparableOperation op) {
        return switch (op) {
            case EQ -> EQUALS;
            case NEQ -> NOT_EQUALS;
            case GT -> GREATER_THAN;
            case GTE -> GREATER_THAN_OR_EQUAL;
            case LT -> LESS_THAN;
            case LTE -> LESS_THAN_OR_EQUAL;
        };
    }

    // Map TextCollectionOperation to FilterOperation
    public static FilterOperation fromTextCollectionOperation(TextCollectionOperation op) {
        return switch (op) {
            case IN -> IN;
            case NOT_IN -> NOT_IN;
            case CONTAINS_ANY -> CONTAINS;
            case NOT_CONTAINS -> NOT_CONTAINS;
            case CONTAINS_ALL -> CONTAINS_ALL;
            case STARTS_WITH -> STARTS_WITH;
            case ENDS_WITH -> ENDS_WITH;
        };
    }

    // Map TextOperation to FilterOperation
    public static FilterOperation fromTextOperation(TextOperation op) {
        return switch (op) {
            case EQ -> EQUALS;
            case NEQ -> NOT_EQUALS;
            case CONTAINS -> CONTAINS;
            case NOT_CONTAINS -> NOT_CONTAINS;
            case STARTS_WITH -> STARTS_WITH;
            case ENDS_WITH -> ENDS_WITH;
        };
    }

    // Map InOperation to FilterOperation
    public static FilterOperation fromInOperation(InOperation op) {
        return switch (op) {
            case IN -> IN;
            case NOT_IN -> NOT_IN;
        };
    }

    public String getOperation() {
        return operation;
    }
}
