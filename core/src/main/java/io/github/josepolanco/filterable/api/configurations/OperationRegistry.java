package io.github.josepolanco.filterable.api.configurations;


import io.github.josepolanco.filterable.api.operations.FilterOperation;

import java.util.EnumSet;
import java.util.Set;

/**
 * Registry to manage the enabled/disabled state of filtering operations.
 */
public class OperationRegistry {
    private final Set<FilterOperation> disabled = EnumSet.noneOf(FilterOperation.class); // Initially, no operations are disabled

    public OperationRegistry() {
    }

    /**
     * Disable a specific filtering operation.
     *
     * @param operation the operation to disable
     * @throws IllegalArgumentException if the operation is null
     */
    public void disableOperation(FilterOperation operation) {
        if (operation == null) {
            throw new IllegalArgumentException("Operation cannot be null");
        }
        disabled.add(operation);
    }

    /**
     * Disable all filtering operations.
     */
    public void disableAllOperations() {
        for (FilterOperation operation : FilterOperation.values()) {
            disableOperation(operation);
        }
    }

    /**
     * Disable collection-based operations (IN and NOT_IN).
     */
    public void disableCollectionOperations() {
        disableOperation(FilterOperation.IN);
        disableOperation(FilterOperation.NOT_IN);
    }

    /**
     * Disable equality-based operations (EQUALS and NOT_EQUALS).
     */
    public void disableEqualsOperations() {
        disableOperation(FilterOperation.EQUALS);
        disableOperation(FilterOperation.NOT_EQUALS);
    }

    /**
     * Check if a specific filtering operation is disabled.
     *
     * @param operation the operation to check
     * @return true if the operation is disabled, false otherwise
     */
    public boolean isOperationDisabled(FilterOperation operation) {
        return disabled.contains(operation);
    }
}
