package api.exceptions;

import api.operations.FilterOperation;

public class FilterDisabledException extends RuntimeException {
    private final FilterOperation operation;

    public FilterDisabledException(FilterOperation operation) {
        super("The filter operation is disabled: " + operation);
        this.operation = operation;
    }

    public FilterOperation getOperation() {
        return operation;
    }

    @Override
    public String getMessage() {
        return super.getMessage() == null ? "Operation deactivated" : super.getMessage() + " (Operation: " + operation + ")";
    }
}
