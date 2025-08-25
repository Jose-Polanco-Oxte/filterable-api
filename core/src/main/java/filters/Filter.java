package filters;

import api.operations.Op;
import jakarta.validation.constraints.NotNull;

/**
 * A generic filter that holds a value and an operation to be performed on that value.
 * <p>
 * {@link Op} defines operations that can be performed on values.
 * <ul>
 *     <li>{@link filters.operations.ComparableOperation}
 *     <li>{@link filters.operations.TextOperation}
 * </ul>
 *
 * @param value     the value to filter on
 * @param operation the operation to perform on the value
 * @param <T>       the type of the value
 * @param <O>       the type of the operation, must extend {@link Op}
 */
public record Filter<T, O extends Enum<? extends Op>>(@NotNull T value, @NotNull O operation) {
}