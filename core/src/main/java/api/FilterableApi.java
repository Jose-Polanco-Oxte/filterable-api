package api;

import api.configurations.ComparableConfig;
import api.configurations.TextConfig;
import api.queries.criteria.ConfigStage;
import api.queries.criteria.QueryComparableManager;
import api.queries.criteria.QueryTextManager;
import api.queries.utils.FilterSpecification;
import api.relations.JoinPathBuilder;

import java.util.Objects;

public class FilterableApi<T> {
    private final FilterSpecification<T> specification;

    public FilterSpecification<T> build() {
        return specification;
    }

    private FilterableApi(FilterSpecification<T> specification) {
        this.specification = Objects.requireNonNullElseGet(
                specification, FilterSpecification::none);
    }

    public static <T> FilterableApi<T> create() {
        return new FilterableApi<>(FilterSpecification.none());
    }

    public static <T> FilterableApi<T> initialSpec(FilterSpecification<T> specification) {
        return new FilterableApi<>(specification);
    }

    public <Y extends Comparable<? super Y>> ConfigStage<T, Y, ComparableConfig<T, Y>, QueryComparableManager<T, Y>> comparable() {
        return new ConfigStage<>(new ComparableConfig<>(), new QueryComparableManager<T, Y>(), this.specification);
    }

    public ConfigStage<T, String, TextConfig<T>, QueryTextManager<T>> text() {
        return new ConfigStage<>(new TextConfig<>(), new QueryTextManager<>(), this.specification);
    }

    public JoinPathBuilder<T, T> relational() {
        return JoinPathBuilder.root(this.specification);
    }
}