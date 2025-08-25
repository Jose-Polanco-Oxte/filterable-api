package api.queries.contracts;

import api.exceptions.FilterDisabledException;
import api.operations.CollectionOp;
import api.operations.Op;
import filters.CollectionFilter;
import filters.Filter;
import jakarta.persistence.metamodel.SingularAttribute;

import java.util.Collection;

/**
 * Interface for building metamodel-based filter queries.
 *
 * @param <T>  the type of the entity to filter
 * @param <Y>  the type of the attribute to filter on
 * @param <E>  the enum type representing filter operations must extend {@link Op}
 * @param <EC> the enum type representing collection filter operations must extend {@link CollectionOp}
 * @see Op
 * @see CollectionOp
 * @see SingularAttribute
 * @see Filter
 * @see CollectionFilter
 */
public interface MetamodelQuery<T, Y, E extends Enum<? extends Op>, EC extends Enum<? extends CollectionOp>> {
    /**
     * Applies a filter based on the provided filter object and attribute,
     * if the attribute, operation, or value is null, the method will have no effect.
     *
     * @param filter    the filter object containing the value and operation
     * @param attribute the attribute to filter on
     * @throws FilterDisabledException if the operation is disabled in the registry
     * @see Filter
     */
    MetamodelQuery<T, Y, E, EC> filter(Filter<Y, E> filter, SingularAttribute<T, Y> attribute);

    /**
     * Applies a filter based on the provided value, attribute, and operation,
     * if the attribute, operation, or value is null, the method will have no effect.
     *
     * @param attribute the attribute to filter on
     * @param value     the value to filter by
     * @param operation the operation to apply
     * @throws FilterDisabledException if the operation is disabled in the registry
     */
    MetamodelQuery<T, Y, E, EC> filter(SingularAttribute<T, Y> attribute, Y value, E operation);

    /**
     * Applies a filter based on the provided collection of values, attribute, and operation,
     * if the attribute, operation, or value is null, the method will have no effect.
     *
     * @param attribute the attribute to filter on
     * @param values    the collection of values to filter by
     * @param operation the collection operation to apply
     * @throws FilterDisabledException if the operation is disabled in the registry
     */
    MetamodelQuery<T, Y, E, EC> filterIn(SingularAttribute<T, Y> attribute, Collection<Y> values, EC operation);

    /**
     * Applies a filter based on the provided collection filter object and attribute,
     * if the attribute, operation, or values are null, the method will have no effect.
     *
     * @param filter    the collection filter object containing the values and operation
     * @param attribute the attribute to filter on
     * @throws FilterDisabledException if the operation is disabled in the registry
     * @see CollectionFilter
     */
    MetamodelQuery<T, Y, E, EC> filterIn(CollectionFilter<Y, EC> filter, SingularAttribute<T, Y> attribute);
}