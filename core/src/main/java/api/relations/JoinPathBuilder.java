package api.relations;

import api.queries.utils.FilterSpecification;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SetAttribute;

import java.util.Objects;
import java.util.function.Function;

/**
 * Builder for creating join paths in JPA criteria queries.
 *
 * @param <T> the root entity type
 * @param <R> the current entity type in the join path
 * @see RelationalApi
 */
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

    /**
     * Creates a new JoinPathBuilder by joining the specified attribute.
     *
     * @param attribute the list attribute to join
     * @param <Y>       the type of the joined attribute
     * @return a new {@link JoinPathBuilder} for the joined attribute
     * @apiNote {@code R} is the current entity type, and {@code Y} is the type of the joined entity
     * @see ListAttribute
     */
    public <Y> JoinPathBuilder<T, Y> join(ListAttribute<R, Y> attribute) {
        return new JoinPathBuilder<>(root -> path.apply(root).join(attribute), specification);
    }

    /**
     * Creates a new JoinPathBuilder by joining the specified attribute.
     *
     * @param attribute the set attribute to join
     * @param <Y>       the type of the joined attribute
     * @return a new {@link JoinPathBuilder} for the joined attribute
     * @apiNote {@code R} is the current entity type, and {@code Y} is the type of the joined entity
     * @see SetAttribute
     */
    public <Y> JoinPathBuilder<T, Y> join(SetAttribute<R, Y> attribute) {
        return new JoinPathBuilder<>(root -> path.apply(root).join(attribute), specification);
    }

    /**
     * Builds the relational API with the constructed join path.
     *
     * @return a {@link RelationalApi} instance with the join path.
     * @apiNote {@code T} is the root entity type, and {@code R} is the current entity type in the join path
     */
    public RelationalApi<T, R> buildPath() {
        return RelationalApi.of(this.path, this.specification);
    }
}