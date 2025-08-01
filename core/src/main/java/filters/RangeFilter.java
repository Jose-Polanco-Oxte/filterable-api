package filters;

import jakarta.validation.constraints.NotNull;

public record RangeFilter<Y>(
        @NotNull Y start,
        @NotNull Y end
) {
}
