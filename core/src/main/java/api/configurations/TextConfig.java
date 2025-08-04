package api.configurations;

import filters.operations.FilterOperation;

public class TextConfig<T> extends FilterConfiguration<T, String> {
    public TextConfig<T> disableContains() {
        if (this.operationRegistry.isOperationDisabled(FilterOperation.CONTAINS)) {
            return this;
        }
        this.operationRegistry.disableOperation(FilterOperation.CONTAINS);
        return this;
    }

    public TextConfig<T> disableNotContains() {
        if (this.operationRegistry.isOperationDisabled(FilterOperation.NOT_CONTAINS)) {
            return this;
        }
        this.operationRegistry.disableOperation(FilterOperation.NOT_CONTAINS);
        return this;
    }

    public TextConfig<T> disableContainsAll() {
        if (this.operationRegistry.isOperationDisabled(FilterOperation.CONTAINS_ALL)) {
            return this;
        }
        this.operationRegistry.disableOperation(FilterOperation.CONTAINS_ALL);
        return this;
    }

    public TextConfig<T> disableStartsWith() {
        if (this.operationRegistry.isOperationDisabled(FilterOperation.STARTS_WITH)) {
            return this;
        }
        this.operationRegistry.disableOperation(FilterOperation.STARTS_WITH);
        return this;
    }

    public TextConfig<T> disableEndsWith() {
        if (this.operationRegistry.isOperationDisabled(FilterOperation.ENDS_WITH)) {
            return this;
        }
        this.operationRegistry.disableOperation(FilterOperation.ENDS_WITH);
        return this;
    }

    @Override
    public TextConfig<T> disableEquals() {
        super.interDisableEquals();
        return this;
    }

    @Override
    public TextConfig<T> disableNotEquals() {
        super.interDisableNotEquals();
        return this;
    }

    @Override
    public FilterConfiguration<T, String> disableIn() {
        super.interDisableIn();
        return this;
    }

    @Override
    public FilterConfiguration<T, String> disableNotIn() {
        super.interDisableNotIn();
        return this;
    }
}
