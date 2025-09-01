package io.github.josepolanco.filterable.filters.operations;

import io.github.josepolanco.filterable.api.operations.Op;

/**
 * Enumeration of text operations.
 * <p>
 * {@link Op} for operations that involve text values.
 * 
 * <p>
 * Includes:
 * <ul>
 *     <li>{@link TextOperation#EQ}: Equal</li>
 *     <li>{@link TextOperation#NEQ}: Not Equal</li>
 *     <li>{@link TextOperation#CONTAINS}: Contains</li>
 *     <li>{@link TextOperation#NOT_CONTAINS}: Not Contains</li>
 *     <li>{@link TextOperation#STARTS_WITH}: Starts With</li>
 *     <li>{@link TextOperation#ENDS_WITH}: Ends With</li>
 * </ul>
 * 
 */
public enum TextOperation implements Op {
    EQ, NEQ, CONTAINS, NOT_CONTAINS, STARTS_WITH, ENDS_WITH
}
