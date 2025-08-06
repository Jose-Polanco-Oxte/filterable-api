package api.queries.criteria;

import api.configurations.OperationRegistry;
import api.exceptions.FilterDisabledException;
import api.queries.contracts.MetamodelQuery;
import api.queries.utils.FilterSpecification;
import api.relations.RelationalApi;
import filters.CollectionFilter;
import filters.Filter;
import filters.operations.FilterOperation;
import filters.operations.TextCollectionOperation;
import filters.operations.TextOperation;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.SingularAttribute;

import java.util.Collection;
import java.util.function.Function;

public class RQueryTextManager<T, R> extends SpecRelationQuery<T, R, String, TextOperation, TextCollectionOperation> {
    private OperationRegistry operationRegistry;
    private FilterSpecification<T> specification;
    private Function<Root<T>, From<?, R>> joinPath;

    public RQueryTextManager() {
        this.operationRegistry = new OperationRegistry();
        this.specification = FilterSpecification.none();
        this.joinPath = null; // Default to the root
    }

    @Override
    protected void setRegistry(OperationRegistry registry) {
        if (registry != null) {
            this.operationRegistry = registry;
        }
    }

    @Override
    protected void setSpecification(FilterSpecification<T> specification) {
        if (specification != null) {
            this.specification = specification;
        }
    }

    @Override
    protected void setJoinPath(Function<Root<T>, From<?, R>> joinPath) {
        if (joinPath != null) {
            this.joinPath = joinPath;
        }
    }

    @Override
    public MetamodelQuery<R, String, TextOperation, TextCollectionOperation> custom(FilterSpecification<T> specification) {
        if (specification == null) {
            return this;
        }
        this.specification = this.specification.and(specification);
        return this;
    }

    @Override
    public RQueryTextManager<T, R> filter(Filter<String, TextOperation> filter, SingularAttribute<R, String> attribute) {
        if (filter == null || attribute == null || filter.operation() == null || filter.value() == null || filter.value().isEmpty()) {
            return this;
        }
        return filter(attribute, filter.value(), filter.operation());
    }

    @Override
    public RQueryTextManager<T, R> filter(SingularAttribute<R, String> attribute, String value, TextOperation operation) {
        if (attribute == null || value == null || value.isEmpty()) {
            return this;
        }
        checkAvailability(operation);
        CriteriaSingularBuilder<T, String> builder = CriteriaSingularBuilder.builder();
        CriteriaSingularStringBuilder<T> textBuilder = new CriteriaSingularStringBuilder<>();
        FilterSpecification<T> spec = switch (operation) {
            case EQ -> builder.equalsOp(attribute, value, joinPath);
            case NEQ -> builder.notEqualsOp(attribute, value, joinPath);
            case CONTAINS -> textBuilder.containsOp(attribute, value, joinPath);
            case NOT_CONTAINS -> textBuilder.notContainsOp(attribute, value, joinPath);
            case STARTS_WITH -> textBuilder.startsWithOp(attribute, value, joinPath);
            case ENDS_WITH -> textBuilder.endsWithOp(attribute, value, joinPath);
        };
        this.specification = this.specification.and(spec);
        return this;
    }

    @Override
    public RQueryTextManager<T, R> filterIn(SingularAttribute<R, String> attribute, Collection<String> values, TextCollectionOperation operation) {
        if (attribute == null || values == null || values.isEmpty()) {
            return this;
        }
        checkAvailability(operation);
        CriteriaSingularBuilder<T, String> builder = CriteriaSingularBuilder.builder();
        CriteriaSingularStringBuilder<T> textBuilder = new CriteriaSingularStringBuilder<>();
        FilterSpecification<T> spec = switch (operation) {
            case IN -> builder.inOp(attribute, values, joinPath);
            case NOT_IN -> builder.notInOp(attribute, values, joinPath);
            case CONTAINS_ANY -> textBuilder.containsOp(attribute, values, joinPath);
            case NOT_CONTAINS -> textBuilder.notContainsOp(attribute, values, joinPath);
            case CONTAINS_ALL -> textBuilder.containsAllOp(attribute, values, joinPath);
            case STARTS_WITH -> textBuilder.startsWithOp(attribute, values, joinPath);
            case ENDS_WITH -> textBuilder.endsWithOp(attribute, values, joinPath);
        };
        this.specification = this.specification.and(spec);
        return this;
    }

    @Override
    public RQueryTextManager<T, R> filterIn(CollectionFilter<String, TextCollectionOperation> filter, SingularAttribute<R, String> attribute) {
        if (filter == null || attribute == null || filter.operation() == null || filter.values() == null || filter.values().isEmpty()) {
            return this;
        }
        return filterIn(attribute, filter.values(), filter.operation());
    }

    @Override
    public RelationalApi<T, R> let() {
        return RelationalApi.of(this.joinPath, this.specification);
    }

    private void checkAvailability(TextOperation operation) {
        checkAvailability(FilterOperation.fromTextOperation(operation));
    }

    private void checkAvailability(TextCollectionOperation operation) {
        checkAvailability(FilterOperation.fromTextCollectionOperation(operation));
    }

    private void checkAvailability(FilterOperation operation) {
        if (operationRegistry.isOperationDisabled(operation)) {
            throw new FilterDisabledException(operation);
        }
    }
}
