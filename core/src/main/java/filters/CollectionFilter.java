package filters;

import api.operations.CollectionOp;
import jakarta.validation.constraints.NotNull;

import java.util.Collection;

/**
 * A filter that operates on a collection of values.
 * <p>
 * {@link CollectionOp} defines operations that can be performed on collections.
 * <ul>
 *     <li>{@link filters.operations.InOperation}
 *     <li>{@link filters.operations.TextCollectionOperation}
 * </ul>
 *
 * @param values    the collection of values to filter on
 * @param operation the operation to perform on the collection
 * @param <T>       the type of the values in the collection
 * @param <O>       the type of the operation, must extend {@link CollectionOp}
 */
public record CollectionFilter<T, O extends Enum<? extends CollectionOp>>(@NotNull Collection<T> values,
                                                                          @NotNull O operation) {
}