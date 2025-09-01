package io.github.josepolanco.filterable.api.configurations;

import io.github.josepolanco.filterable.api.operations.FilterOperation;

/**
 * Base configuration class for filtering operations.
 *
 * @param <T> The type of the entity being filtered
 * @param <Y> The type of the field being filtered
 * @see OperationRegistry
 */
public abstract class FilterConfiguration<T, Y> {
    protected final OperationRegistry operationRegistry = new OperationRegistry();

    protected void interDisableEquals() {
        if (operationRegistry.isOperationDisabled(FilterOperation.EQUALS)) {
            return;
        }
        operationRegistry.disableOperation(FilterOperation.EQUALS);
    }

    protected void interDisableNotEquals() {
        if (operationRegistry.isOperationDisabled(FilterOperation.NOT_EQUALS)) {
            return;
        }
        operationRegistry.disableOperation(FilterOperation.NOT_EQUALS);
    }

    protected void interDisableIn() {
        if (operationRegistry.isOperationDisabled(FilterOperation.IN)) {
            return;
        }
        operationRegistry.disableOperation(FilterOperation.IN);
    }

    protected void interDisableNotIn() {
        if (operationRegistry.isOperationDisabled(FilterOperation.NOT_IN)) {
            return;
        }
        operationRegistry.disableOperation(FilterOperation.NOT_IN);
    }

    /**
     * Disable the equals operation for filtering.
     *
     * @return The current FilterConfiguration instance for method chaining
     * @see OperationRegistry
     */
    public abstract FilterConfiguration<T, Y> disableEquals();

    /**
     * Disable the not equals operation for filtering.
     *
     * @return The current FilterConfiguration instance for method chaining
     * @see OperationRegistry
     */
    public abstract FilterConfiguration<T, Y> disableNotEquals();

    /**
     * Disable the "in" operation for filtering.
     *
     * @return The current FilterConfiguration instance for method chaining
     * @see OperationRegistry
     */
    public abstract FilterConfiguration<T, Y> disableIn();

    /**
     * Disable the "not in" operation for filtering.
     *
     * @return The current FilterConfiguration instance for method chaining
     * @see OperationRegistry
     */
    public abstract FilterConfiguration<T, Y> disableNotIn();

    /**
     * Disable all filtering operations.
     *
     * @return The OperationRegistry instance for method chaining.
     * @see OperationRegistry
     */
    public OperationRegistry disableAll() {
        operationRegistry.disableAllOperations();
        return operationRegistry;
    }

    /**
     * Disable comparison operations (greater than, less than, etc.) for filtering
     *
     * @return The OperationRegistry instance for method chaining.
     * @see OperationRegistry
     */
    public OperationRegistry disableEqualsOperations() {
        operationRegistry.disableEqualsOperations();
        return operationRegistry;
    }

    /**
     * Disable collection operations (in, not in) for filtering.
     *
     * @return The OperationRegistry instance for method chaining
     * @see OperationRegistry
     */
    public OperationRegistry disableCollectionOperations() {
        operationRegistry.disableCollectionOperations();
        return operationRegistry;
    }

    /**
     * Get the current OperationRegistry instance.
     *
     * @return The current OperationRegistry instance
     * @see OperationRegistry
     */
    public OperationRegistry getOperationRegistry() {
        return operationRegistry;
    }
}
