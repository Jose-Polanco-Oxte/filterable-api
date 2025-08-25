package filters.operations;

import api.operations.Op;

/**
 * Enumeration of comparison operations.
 * <p>
 * {@link Op} for operations that compare two values.
 *
 * <p>
 * Includes:
 * <ul>
 *     <li>{@link ComparableOperation#EQ}: Equal</li>
 *     <li>{@link ComparableOperation#NEQ}: Not Equal</li>
 *     <li>{@link ComparableOperation#GT}: Greater Than</li>
 *     <li>{@link ComparableOperation#LT}: Less Than</li>
 *     <li>{@link ComparableOperation#GTE}: Greater Than or Equal</li>
 *     <li>{@link ComparableOperation#LTE}: Less Than or Equal</li>
 * </ul>
 *
 */
public enum ComparableOperation implements Op {
    EQ, NEQ, GT, LT, GTE, LTE
}