package filters;

import api.operations.Op;
import jakarta.validation.constraints.NotNull;

public record Filter<T, O extends Enum<? extends Op>> (
        @NotNull T value,
        @NotNull O operation
) {
}