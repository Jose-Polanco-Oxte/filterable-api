package io.github.josepolanco.filterable.filters.operations;

import io.github.josepolanco.filterable.api.operations.CollectionOp;

/**
 * Enumeration of text collection operations.
 * <p>
 * {@link CollectionOp} for operations that involve collections of text values.
 * 
 * <p>
 * Includes:
 * <ul>
 *     <li>{@link TextCollectionOperation#IN}: In</li>
 *     <li>{@link TextCollectionOperation#NOT_IN}: Not In</li>
 *     <li>{@link TextCollectionOperation#CONTAINS_ANY}: Contains Any</li>
 *     <li>{@link TextCollectionOperation#CONTAINS_ALL}: Contains All</li>
 *     <li>{@link TextCollectionOperation#NOT_CONTAINS}: Not Contains</li>
 *     <li>{@link TextCollectionOperation#STARTS_WITH}: Starts With</li>
 *     <li>{@link TextCollectionOperation#ENDS_WITH}: Ends With</li>
 * </ul>
 * 
 */
public enum TextCollectionOperation implements CollectionOp {
    IN, NOT_IN, CONTAINS_ANY, CONTAINS_ALL, NOT_CONTAINS, STARTS_WITH, ENDS_WITH,
}
