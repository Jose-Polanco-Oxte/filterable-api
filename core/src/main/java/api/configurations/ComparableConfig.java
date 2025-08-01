package api.configurations;

import filters.operations.FilterOperation;

public class ComparableConfig<T, Y extends Comparable<? super Y>> extends FilterConfiguration<T, Y> {
    public ComparableConfig<T, Y> disableGt() {
        if (this.operationRegistry.isOperationDisabled(FilterOperation.GREATER_THAN)) {
            return this;
        }
        this.operationRegistry.disableOperation(FilterOperation.GREATER_THAN);
        return this;
    }

    public ComparableConfig<T, Y> disableGte() {
        if (this.operationRegistry.isOperationDisabled(FilterOperation.GREATER_THAN_OR_EQUAL)) {
            return this;
        }
        this.operationRegistry.disableOperation(FilterOperation.GREATER_THAN_OR_EQUAL);
        return this;
    }

    public ComparableConfig<T, Y> disableLt() {
        if (this.operationRegistry.isOperationDisabled(FilterOperation.LESS_THAN)) {
            return this;
        }
        this.operationRegistry.disableOperation(FilterOperation.LESS_THAN);
        return this;
    }

    public ComparableConfig<T, Y> disableLte() {
        if (this.operationRegistry.isOperationDisabled(FilterOperation.LESS_THAN_OR_EQUAL)) {
            return this;
        }
        this.operationRegistry.disableOperation(FilterOperation.LESS_THAN_OR_EQUAL);
        return this;
    }

    public ComparableConfig<T, Y> disableBetween() {
        if (this.operationRegistry.isOperationDisabled(FilterOperation.BETWEEN)) {
            return this;
        }
        this.operationRegistry.disableOperation(FilterOperation.BETWEEN);
        return this;
    }

    @Override
    public ComparableConfig<T, Y> disableEquals() {
        super.interDisableEquals();
        return this;
    }

    @Override
    public ComparableConfig<T, Y> disableNotEquals() {
        super.interDisableNotEquals();
        return this;
    }
}
