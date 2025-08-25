package api.queries.criteria;

import api.configurations.OperationRegistry;
import api.exceptions.FilterDisabledException;
import api.operations.FilterOperation;
import api.queries.contracts.MetamodelQuery;
import api.queries.utils.FilterSpecification;
import api.relations.RelationalApi;
import filters.CollectionFilter;
import filters.Filter;
import filters.operations.TextCollectionOperation;
import filters.operations.TextOperation;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.SingularAttribute;

import java.util.Collection;
import java.util.function.Function;

/**
 * Manager for building filter specifications for text attributes in relational contexts.
 * <p> Their methods can throw {@link FilterDisabledException} if the operation is disabled in the registry. </p>
 *
 * @param <T> the type of the root entity to filter
 * @param <R> the type of the related entity
 * @see TextOperation
 * @see TextCollectionOperation
 * @see FilterSpecification
 * @see RelationalApi
 */
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

    /**
     * Adds a custom filter specification to the current specification.
     *
     * @param specification the custom filter specification to add
     * @return the current RQueryTextManager instance
     * @apiNote if the provided specification is null, it will be ignored
     */
    @Override
    public MetamodelQuery<R, String, TextOperation, TextCollectionOperation> custom(FilterSpecification<T> specification) {
        if (specification == null) {
            return this;
        }
        this.specification = this.specification.and(specification);
        return this;
    }

    /**
     * Applies a filter based on the provided filter object and attribute.
     *
     * @param filter    the filter object containing the value and operation
     * @param attribute the attribute to filter on
     * @return the current {@link RQueryTextManager} instance
     * @throws FilterDisabledException if the operation is disabled in the registry
     * @apiNote if the filter, attribute, operation, or value is null or empty, the method will have no effect
     * @see Filter
     * @see TextOperation
     */
    @Override
    public RQueryTextManager<T, R> filter(Filter<String, TextOperation> filter, SingularAttribute<R, String> attribute) {
        if (filter == null || attribute == null || filter.operation() == null || filter.value() == null || filter.value().isEmpty()) {
            return this;
        }
        return filter(attribute, filter.value(), filter.operation());
    }

    /**
     * Applies a filter based on the provided value, operation, and attribute.
     *
     * @param attribute the attribute to filter on
     * @param value     the value to filter by
     * @param operation the text operation to apply
     * @return the current {@link RQueryTextManager} instance
     * @throws FilterDisabledException if the operation is disabled in the registry
     * @apiNote if the attribute, value, or operation is null or empty, the method will have no effect
     * @see TextOperation
     */
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

    /**
     * Applies a collection-based filter based on the provided filter object and attribute.
     *
     * @param attribute the attribute to filter on
     * @param values    the collection of values to filter by
     * @param operation the text collection operation to apply
     * @return the current {@link RQueryTextManager} instance
     * @throws FilterDisabledException if the operation is disabled in the registry
     * @apiNote if the attribute, values, or operation is null, or if values is empty, the method will have no effect
     * @see TextCollectionOperation
     */
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

    /**
     * Applies a collection-based filter based on the provided filter object and attribute.
     *
     * @param filter    the collection filter object containing the values and operation
     * @param attribute the attribute to filter on
     * @return the current {@link RQueryTextManager} instance
     * @throws FilterDisabledException if the operation is disabled in the registry
     * @apiNote if the filter, attribute, operation, or values are null or empty, the method will have no effect
     * @see CollectionFilter
     * @see TextCollectionOperation
     */
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
