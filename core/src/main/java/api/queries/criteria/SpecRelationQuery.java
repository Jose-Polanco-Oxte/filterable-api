package api.queries.criteria;

import api.configurations.OperationRegistry;
import api.operations.CollectionOp;
import api.operations.Op;
import api.queries.contracts.CustomQueries;
import api.queries.contracts.MetamodelQuery;
import api.queries.contracts.RApi;
import api.queries.utils.FilterSpecification;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Root;

import java.util.function.Function;

public abstract class SpecRelationQuery<T, R, Y, E extends Enum<? extends Op>, EC extends Enum<? extends CollectionOp>> implements MetamodelQuery<R, Y, E, EC>, RApi<T, R>, CustomQueries<T, R, Y, E, EC> {
    protected abstract void setRegistry(OperationRegistry registry);

    protected abstract void setSpecification(FilterSpecification<T> specification);

    protected abstract void setJoinPath(Function<Root<T>, From<?, R>> joinPath);
}
