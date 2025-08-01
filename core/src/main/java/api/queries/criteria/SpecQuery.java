package api.queries.criteria;
import api.configurations.OperationRegistry;
import api.operations.CollectionOp;
import api.operations.Op;
import api.queries.contracts.CustomQueries;
import api.queries.contracts.FApi;
import api.queries.contracts.MetamodelQuery;
import api.queries.utils.FilterSpecification;

public abstract class SpecQuery<T, Y, E extends Enum<? extends Op>, EC extends Enum<? extends CollectionOp>> implements MetamodelQuery<T, Y, E, EC>, FApi<T>, CustomQueries<T, T, Y, E, EC> {
    protected abstract void setRegistry(OperationRegistry registry);
    protected abstract void setSpecification(FilterSpecification<T> specification);
}