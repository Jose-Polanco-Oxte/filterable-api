package api.configurations;


import filters.operations.FilterOperation;

import java.util.EnumSet;
import java.util.Set;

public class OperationRegistry {
    private final Set<FilterOperation> disabled = EnumSet.noneOf(FilterOperation.class);

    public OperationRegistry() {
    }

    public void disableOperation(FilterOperation operation) {
        if (operation == null) {
            throw new IllegalArgumentException("Operation cannot be null");
        }
        disabled.add(operation);
    }

    public void disableAllOperations() {
        for (FilterOperation operation : FilterOperation.values()) {
            disableOperation(operation);
        }
    }

    public void disableCollectionOperations() {
        disableOperation(FilterOperation.IN);
        disableOperation(FilterOperation.NOT_IN);
    }

    public void disableEqualsOperations() {
        disableOperation(FilterOperation.EQUALS);
        disableOperation(FilterOperation.NOT_EQUALS);
    }

    public boolean isOperationDisabled(FilterOperation operation) {
        return disabled.contains(operation);
    }
}
