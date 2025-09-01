package io.github.josepolanco.filterable.filters.operations;

import io.github.josepolanco.filterable.api.operations.CollectionOp;

/**
 * Enumeration of collection operations.
 * <p>
 * {@link CollectionOp} for operations that involve collections of values.
 * <p>
 * Includes:
 * <ul>
 *     <li>{@link InOperation#IN}: In</li>
 *     <li>{@link InOperation#NOT_IN}: Not In</li>
 * </ul>
 */
public enum InOperation implements CollectionOp {
    IN, NOT_IN
}
