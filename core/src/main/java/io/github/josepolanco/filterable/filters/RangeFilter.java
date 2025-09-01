package io.github.josepolanco.filterable.filters;

import jakarta.validation.constraints.NotNull;

/**
 * A filter that defines a range with a start and end value.
 *
 * @param start the start of the range
 * @param end   the end of the range
 * @param <Y>   the type of the start and end values
 */
public record RangeFilter<Y>(
        @NotNull Y start,
        @NotNull Y end
) {
}
