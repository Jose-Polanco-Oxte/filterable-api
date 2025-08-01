package api.queries.contracts;

import api.operations.CollectionOp;
import api.operations.Op;
import filters.CollectionFilter;
import filters.Filter;
import jakarta.persistence.metamodel.SingularAttribute;

import java.util.Collection;

public interface MetamodelQuery<T, Y, E extends Enum<? extends Op>, EC extends Enum<? extends CollectionOp>> {
    MetamodelQuery<T, Y, E, EC> filter(Filter<Y, E> filter, SingularAttribute<T, Y> attribute);
    MetamodelQuery<T, Y, E, EC> filter(SingularAttribute<T, Y> attribute, Y value, E operation);
    MetamodelQuery<T, Y, E, EC> filterIn(SingularAttribute<T, Y> attribute, Collection<Y> values, EC operation);
    MetamodelQuery<T, Y, E, EC> filterIn(CollectionFilter<Y, EC> filter, SingularAttribute<T, Y> attribute);
}