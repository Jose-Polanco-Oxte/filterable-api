package api.configurations;

import api.operations.FilterOperation;

/**
 * Configuration for comparable types.
 *
 * @param <T> the entity type
 * @param <Y> the comparable type
 * @see FilterConfiguration
 * @see FilterOperation
 */
public class ComparableConfig<T, Y extends Comparable<? super Y>> extends FilterConfiguration<T, Y> {

    /**
     * Disable greater than operation.
     *
     * @return the current configuration
     * @see FilterOperation#GREATER_THAN
     */
    public ComparableConfig<T, Y> disableGt() {
        if (this.operationRegistry.isOperationDisabled(FilterOperation.GREATER_THAN)) {
            return this;
        }
        this.operationRegistry.disableOperation(FilterOperation.GREATER_THAN);
        return this;
    }

    /**
     * Disable greater than or equal operation.
     *
     * @return the current configuration
     * @see FilterOperation#GREATER_THAN_OR_EQUAL
     */
    public ComparableConfig<T, Y> disableGte() {
        if (this.operationRegistry.isOperationDisabled(FilterOperation.GREATER_THAN_OR_EQUAL)) {
            return this;
        }
        this.operationRegistry.disableOperation(FilterOperation.GREATER_THAN_OR_EQUAL);
        return this;
    }

    /**
     * Disable less than operation.
     *
     * @return the current configuration
     * @see FilterOperation#LESS_THAN
     */
    public ComparableConfig<T, Y> disableLt() {
        if (this.operationRegistry.isOperationDisabled(FilterOperation.LESS_THAN)) {
            return this;
        }
        this.operationRegistry.disableOperation(FilterOperation.LESS_THAN);
        return this;
    }

    /**
     * Disable less than or equal operation.
     *
     * @return the current configuration
     * @see FilterOperation#LESS_THAN_OR_EQUAL
     */
    public ComparableConfig<T, Y> disableLte() {
        if (this.operationRegistry.isOperationDisabled(FilterOperation.LESS_THAN_OR_EQUAL)) {
            return this;
        }
        this.operationRegistry.disableOperation(FilterOperation.LESS_THAN_OR_EQUAL);
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

    @Override
    public FilterConfiguration<T, Y> disableIn() {
        super.interDisableIn();
        return this;
    }

    @Override
    public FilterConfiguration<T, Y> disableNotIn() {
        super.interDisableNotIn();
        return this;
    }
}
