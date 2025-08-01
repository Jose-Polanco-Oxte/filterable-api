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

public class RelationalApi<T, R> {
    private final FilterSpecification<T> specification;
    private final Function<Root<T>, From<?, R>> joinPath;

    private RelationalApi(Function<Root<T>, From<?, R>> joinPath, FilterSpecification<T> specification) {
        this.joinPath = joinPath;
        this.specification = Objects.requireNonNullElseGet(
                specification, FilterSpecification::none);
    }

    public static <T, R> RelationalApi<T, R> of(Function<Root<T>, From<?, R>> joinPath, FilterSpecification<T> specification) {
        return new RelationalApi<>(joinPath, specification);
    }

    public <Y extends Comparable<? super Y>> RelationalConfigStage<T, R, Y, ComparableConfig<T, Y>, RQueryComparableManager<T, R, Y>> comparable() {
        return new RelationalConfigStage<>(new ComparableConfig<>(), new RQueryComparableManager<T, R, Y>(), this.specification, this.joinPath);
    }

    public RelationalConfigStage<T, R, String, TextConfig<T>, RQueryTextManager<T, R>> text() {
        return new RelationalConfigStage<>(new TextConfig<>(), new RQueryTextManager<>(), this.specification, this.joinPath);
    }

    public FilterableApi<T> backToFilterableApi() {
        return FilterableApi.initialSpec(specification);
    }
}