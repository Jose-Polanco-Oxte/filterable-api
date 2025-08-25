package api.queries.criteria;

import api.configurations.FilterConfiguration;
import api.queries.utils.FilterSpecification;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Configuration stage for building filter specifications.
 *
 * @param <T> the type of the entity to filter
 * @param <Y> the type of the attribute to filter
 * @param <C> the type of the filter configuration must extend {@link FilterConfiguration}
 * @param <Q> the type of the query manager must extend {@link SpecQuery}
 * @see FilterConfiguration
 * @see SpecQuery
 */
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

    /**
     * Applies the given consumer to configure the filter configuration,
     * this method allows for fluent configuration of the filter settings
     *
     * @param consumer the consumer to apply to the filter configuration
     * @return the query manager: {@link SpecQuery} for filter specification buildin
     */
    public Q configure(Consumer<C> consumer) {
        consumer.accept(config);
        query.setRegistry(config.getOperationRegistry());
        query.setSpecification(specification);
        return query;
    }

    /**
     * Finalizes the configuration and returns the query manager,
     * this method allows to skip configuration and proceed directly to query building
     *
     * @return the query manager: {@link SpecQuery} for filter specification building
     */
    public Q configure() {
        query.setRegistry(config.getOperationRegistry());
        query.setSpecification(specification);
        return query;
    }
}