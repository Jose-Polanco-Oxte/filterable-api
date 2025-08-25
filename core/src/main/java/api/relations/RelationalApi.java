package api.relations;

import api.FilterableApi;
import api.configurations.ComparableConfig;
import api.configurations.TextConfig;
import api.queries.criteria.RQueryComparableManager;
import api.queries.criteria.RQueryTextManager;
import api.queries.criteria.RelationalConfigStage;
import api.queries.utils.FilterSpecification;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Root;

import java.util.Objects;
import java.util.function.Function;

/**
 * API for building filter specifications on related entities.
 *
 * @param <T> the root entity type
 * @param <R> the related entity type
 * @see RelationalConfigStage
 * @see RQueryComparableManager
 * @see RQueryTextManager
 * @see ComparableConfig
 * @see TextConfig
 */
public class RelationalApi<T, R> {

    private final FilterSpecification<T> specification;

    private final Function<Root<T>, From<?, R>> joinPath;

    private RelationalApi(Function<Root<T>, From<?, R>> joinPath, FilterSpecification<T> specification) {
        this.joinPath = joinPath;
        this.specification = Objects.requireNonNullElseGet(specification, FilterSpecification::none);
    }

    /**
     * Creates a new instance of RelationalApi with the provided join path and filter specification.
     *
     * @param joinPath      the function defining the join path from the root entity to the related entity
     * @param specification the initial filter specification
     * @param <T>           the root entity type
     * @param <R>           the related entity type
     * @return a new RelationalApi instance
     * @apiNote if initial specification is null, it will be replaced with null-safe empty specification
     */
    public static <T, R> RelationalApi<T, R> of(Function<Root<T>, From<?, R>> joinPath, FilterSpecification<T> specification) {
        return new RelationalApi<>(joinPath, specification);
    }

    /**
     * Starts building a comparable filter configuration for the related entity.
     *
     * @param <Y> the type of the {@link Comparable} attribute in the related entity
     * @return a new {@link RelationalConfigStage} for building comparable filters
     * @see ComparableConfig
     * @see RQueryComparableManager
     */
    public <Y extends Comparable<? super Y>> RelationalConfigStage<T, R, Y, ComparableConfig<T, Y>, RQueryComparableManager<T, R, Y>> comparable() {
        return new RelationalConfigStage<>(new ComparableConfig<>(), new RQueryComparableManager<T, R, Y>(), this.specification, this.joinPath);
    }

    /**
     * Starts building a text filter configuration for the related entity.
     *
     * @return a new {@link RelationalConfigStage} for building text filters
     * @see TextConfig
     * @see RQueryTextManager
     */
    public RelationalConfigStage<T, R, String, TextConfig<T>, RQueryTextManager<T, R>> text() {
        return new RelationalConfigStage<>(new TextConfig<>(), new RQueryTextManager<>(), this.specification, this.joinPath);
    }

    /**
     * Finalizes the relational filter building process and returns to the main FilterableApi.
     *
     * @return a {@link FilterableApi} instance with the constructed filter specification
     * @see FilterableApi
     */
    public FilterableApi<T> backToFilterableApi() {
        return FilterableApi.initialSpec(specification);
    }
}