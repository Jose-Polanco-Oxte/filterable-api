package api.configurations;

import filters.operations.FilterOperation;

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

    public abstract FilterConfiguration<T, Y> disableEquals();
    public abstract FilterConfiguration<T, Y> disableNotEquals();
    public abstract FilterConfiguration<T, Y> disableIn();
    public abstract FilterConfiguration<T, Y> disableNotIn();

    public OperationRegistry disableAll() {
        operationRegistry.disableAllOperations();
        return operationRegistry;
    }

    public OperationRegistry disableEqualsOperations() {
        operationRegistry.disableEqualsOperations();
        return operationRegistry;
    }

    public OperationRegistry disableCollectionOperations() {
        operationRegistry.disableCollectionOperations();
        return operationRegistry;
    }

    public OperationRegistry getOperationRegistry() {
        return operationRegistry;
    }
}
