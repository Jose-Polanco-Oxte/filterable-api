package filters.operations;

import api.operations.CollectionOp;

/**
 * Enumeration of collection operations.
 * <p>
 * {@link CollectionOp} for operations that involve collections of values.
 * </p>
 * <p>
 * Includes:
 * <ul>
 *     <li>{@link InOperation#IN}: In</li>
 *     <li>{@link InOperation#NOT_IN}: Not In</li>
 * </ul>
 * </p>
 */
public enum InOperation implements CollectionOp {
    IN, NOT_IN
}
