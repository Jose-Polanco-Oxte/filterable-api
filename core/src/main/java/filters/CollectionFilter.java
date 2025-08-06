package filters;

import api.operations.CollectionOp;
import jakarta.validation.constraints.NotNull;

import java.util.Collection;

public record CollectionFilter<T, O extends Enum<? extends CollectionOp>>(
        @NotNull Collection<T> values,
        @NotNull O operation
) {
}