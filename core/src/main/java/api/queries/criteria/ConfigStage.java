package api.queries.criteria;

import api.configurations.FilterConfiguration;
import api.queries.utils.FilterSpecification;

import java.util.Objects;
import java.util.function.Consumer;

public class ConfigStage<T, Y, C extends FilterConfiguration<T, Y>, Q extends SpecQuery<T, Y, ?, ?>> {
    private final C config;
    private final Q query;
    private final FilterSpecification<T> specification;

    public ConfigStage(C config, Q query, FilterSpecification<T> specification) {
        this.config = config;
        this.query = query;
        this.specification = Objects.requireNonNullElseGet(
                specification, FilterSpecification::none);
    }

    public Q configure(Consumer<C> consumer) {
        consumer.accept(config);
        query.setRegistry(config.getOperationRegistry());
        query.setSpecification(specification);
        return query;
    }

    public Q configure() {
        query.setRegistry(config.getOperationRegistry());
        query.setSpecification(specification);
        return query;
    }
}