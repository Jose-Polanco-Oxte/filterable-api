package api.configurations;

import api.operations.FilterOperation;

/**
 * Configuration class for text-based filtering operations.
 *
 * @param <T> The type of the entity being filtered.
 * @see FilterConfiguration
 * @see FilterOperation
 */
public class TextConfig<T> extends FilterConfiguration<T, String> {

    /**
     * Disable the contains operation for filtering.
     *
     * @return The current TextConfig instance for method chaining
     * @see FilterOperation#CONTAINS
     */
    public TextConfig<T> disableContainsAny() {
        if (this.operationRegistry.isOperationDisabled(FilterOperation.CONTAINS)) {
            return this;
        }
        this.operationRegistry.disableOperation(FilterOperation.CONTAINS);
        return this;
    }

    /**
     * Disable the not contains operation for filtering.
     *
     * @return The current TextConfig instance for method chaining
     * @see FilterOperation#NOT_CONTAINS
     */
    public TextConfig<T> disableNotContains() {
        if (this.operationRegistry.isOperationDisabled(FilterOperation.NOT_CONTAINS)) {
            return this;
        }
        this.operationRegistry.disableOperation(FilterOperation.NOT_CONTAINS);
        return this;
    }

    /**
     * Disable the contains all operation for filtering.
     *
     * @return The current TextConfig instance for method chaining
     * @see FilterOperation#CONTAINS_ALL
     */
    public TextConfig<T> disableContainsAll() {
        if (this.operationRegistry.isOperationDisabled(FilterOperation.CONTAINS_ALL)) {
            return this;
        }
        this.operationRegistry.disableOperation(FilterOperation.CONTAINS_ALL);
        return this;
    }

    /**
     * Disable the starts with operation for filtering.
     *
     * @return The current TextConfig instance for method chaining
     * @see FilterOperation#STARTS_WITH
     */
    public TextConfig<T> disableStartsWith() {
        if (this.operationRegistry.isOperationDisabled(FilterOperation.STARTS_WITH)) {
            return this;
        }
        this.operationRegistry.disableOperation(FilterOperation.STARTS_WITH);
        return this;
    }

    /**
     * Disable the ends with operation for filtering.
     *
     * @return The current TextConfig instance for method chaining
     * @see FilterOperation#ENDS_WITH
     */
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
