package api.queries.criteria;

import api.FilterableApi;
import api.configurations.OperationRegistry;
import api.exceptions.FilterDisabledException;
import api.queries.utils.FilterSpecification;
import filters.CollectionFilter;
import filters.Filter;
import filters.operations.FilterOperation;
import filters.operations.InOperation;
import filters.operations.TextCollectionOperation;
import filters.operations.TextOperation;
import jakarta.persistence.metamodel.SingularAttribute;

import java.util.Collection;

public class QueryTextManager<T> extends SpecQuery<T, String, TextOperation, TextCollectionOperation> {
    private OperationRegistry operationRegistry;
    private FilterSpecification<T> specification;

    public QueryTextManager() {
        this.operationRegistry = new OperationRegistry();
        this.specification = FilterSpecification.none();
    }

    @Override
    public QueryTextManager<T> where(FilterSpecification<T> specification) {
        if (specification == null) {
            return this;
        }
        this.specification = this.specification.and(specification);
        return this;
    }

    @Override
    public QueryTextManager<T> filter(Filter<String, TextOperation> filter, SingularAttribute<T, String> attribute) {
        if (filter == null || attribute == null || filter.value() == null || filter.operation() == null) {
            return this;
        }
        checkAvailability(filter.operation());
        return filter(attribute, filter.value(), filter.operation());
    }

    @Override
    public QueryTextManager<T> filter(SingularAttribute<T, String> attribute, String value, TextOperation operation) {
        if (attribute == null || value == null || operation == null) {
            return this;
        }
        checkAvailability(operation);
        CriteriaSingularBuilder<T, String> builder = CriteriaSingularBuilder.builder();
        CriteriaSingularStringBuilder<T> stringBuilder = CriteriaSingularStringBuilder.create();
        FilterSpecification<T> spec = (root, query, criteriaBuilder) -> switch (operation) {
            case EQ -> builder.equalsOp(attribute, value).toPredicate(root, query, criteriaBuilder);
            case NEQ -> builder.notEqualsOp(attribute, value).toPredicate(root, query, criteriaBuilder);
            case CONTAINS -> stringBuilder.containsOp(attribute, value).toPredicate(root, query, criteriaBuilder);
            case NOT_CONTAINS -> stringBuilder.notContainsOp(attribute, value).toPredicate(root, query, criteriaBuilder);
            case STARTS_WITH -> stringBuilder.startsWithOp(attribute, value).toPredicate(root, query, criteriaBuilder);
            case ENDS_WITH -> stringBuilder.endsWithOp(attribute, value).toPredicate(root, query, criteriaBuilder);
        };
        this.specification = this.specification.and(spec);
        return this;
    }

    @Override
    public QueryTextManager<T> filterIn(CollectionFilter<String, TextCollectionOperation> filter, SingularAttribute<T, String> attribute) {
        if (filter == null || attribute == null || filter.values() == null || filter.operation() == null || filter.values().isEmpty()) {
            return this;
        }
        checkAvailability(filter.operation());
        return filterIn(attribute, filter.values(), filter.operation());
    }

    @Override
    public QueryTextManager<T> filterIn(SingularAttribute<T, String> attribute, Collection<String> values, TextCollectionOperation operation) {
        if (attribute == null || values == null || values.isEmpty() || operation == null) {
            return this;
        }
        checkAvailability(operation);
        CriteriaSingularStringBuilder<T> builder = CriteriaSingularStringBuilder.create();
        CriteriaSingularBuilder<T, String> singular = CriteriaSingularBuilder.builder();
        FilterSpecification<T> spec = (root, query, criteriaBuilder) -> switch (operation) {
            case IN -> singular.inOp(attribute, values).toPredicate(root, query, criteriaBuilder);
            case NOT_IN -> singular.notInOp(attribute, values).toPredicate(root, query, criteriaBuilder);
            case CONTAINS -> builder.containsOp(attribute, values).toPredicate(root, query, criteriaBuilder);
            case NOT_CONTAINS -> builder.notContainsOp(attribute, values).toPredicate(root, query, criteriaBuilder);
            case CONTAINS_ALL -> builder.containsAllOp(attribute, values).toPredicate(root, query, criteriaBuilder);
            case STARTS_WITH -> builder.startsWithOp(attribute, values).toPredicate(root, query, criteriaBuilder);
            case ENDS_WITH -> builder.endsWithOp(attribute, values).toPredicate(root, query, criteriaBuilder);
        };
        this.specification = this.specification.and(spec);
        return this;
    }

    @Override
    public FilterableApi<T> let() {
        return FilterableApi.initialSpec(specification);
    }

    private void checkAvailability(FilterOperation operation) {
        if (operationRegistry.isOperationDisabled(operation)) {
            throw new FilterDisabledException(operation);
        }
    }

    public void checkAvailability(InOperation operation) {
        checkAvailability(FilterOperation.fromInOperation(operation));
    }

    private void checkAvailability(TextOperation operation) {
        checkAvailability(FilterOperation.fromTextOperation(operation));
    }

    private void checkAvailability(TextCollectionOperation operation) {
        checkAvailability(FilterOperation.fromTextCollectionOperation(operation));
    }

    @Override
    protected void setRegistry(OperationRegistry registry) {
        if (this.operationRegistry == null && registry != null) {
            this.operationRegistry = registry;
        }
    }

    @Override
    protected void setSpecification(FilterSpecification<T> specification) {
        if (this.specification == null && specification != null) {
            this.specification = specification;
        }
    }
}
