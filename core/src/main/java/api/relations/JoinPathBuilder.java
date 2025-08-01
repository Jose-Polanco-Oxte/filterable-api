package api.relations;

import api.queries.utils.FilterSpecification;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SetAttribute;

import java.util.Objects;
import java.util.function.Function;

public class JoinPathBuilder<T, R> {

    private final Function<Root<T>, From<?, R>> path;
    private final FilterSpecification<T> specification;

    private JoinPathBuilder(Function<Root<T>, From<?, R>> path, FilterSpecification<T> specification) {
        this.path = path;
        this.specification = Objects.requireNonNullElseGet(
                specification, FilterSpecification::none);
    }

    public static <T> JoinPathBuilder<T, T> root(FilterSpecification<T> specification) {
        return new JoinPathBuilder<>(root -> root, specification);
    }

    public <Y> JoinPathBuilder<T, Y> join(ListAttribute<R, Y> attribute) {
        return new JoinPathBuilder<>(root -> path.apply(root).join(attribute), specification);
    }

    public <Y> JoinPathBuilder<T, Y> join(SetAttribute<R, Y> attribute) {
        return new JoinPathBuilder<>(root -> path.apply(root).join(attribute), specification);
    }

    public RelationalApi<T, R> buildPath() {
        return RelationalApi.of(this.path, this.specification);
    }
}