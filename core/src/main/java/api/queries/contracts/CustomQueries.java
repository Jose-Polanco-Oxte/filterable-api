package api.queries.contracts;

import api.operations.CollectionOp;
import api.operations.Op;
import api.queries.utils.FilterSpecification;

public interface CustomQueries<T, R, Y, E extends Enum<? extends Op>, EC extends Enum<? extends CollectionOp>> {
    MetamodelQuery<R, Y, E, EC> custom(FilterSpecification<T> specification);
}