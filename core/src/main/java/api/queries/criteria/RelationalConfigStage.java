package api.queries.criteria;

import api.configurations.FilterConfiguration;
import api.queries.utils.FilterSpecification;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Root;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Stage for configuring relational filter queries.
 *
 * @param <T> the root entity type
 * @param <R> the related entity type
 * @param <Y> the type of the attribute in the related entity
 * @param <C> the type of the filter configuration must extend {@link FilterConfiguration}
 * @param <Q> the type of the query manager must extend {@link SpecRelationQuery}
 * @apiNote this class is used internally to manage the configuration and building of relational filter specifications
 * @see FilterConfiguration
 * @see SpecRelationQuery
 */
public class RelationalConfigStage<T, R, Y, C extends FilterConfiguration<T, Y>, Q extends SpecRelationQuery<T, R, Y, ?, ?>> {

    private final C config;

    private final Q queryManager;

    private final Function<Root<T>, From<?, R>> joinPath;

    private final FilterSpecification<T> specification;

    public RelationalConfigStage(C config, Q queryManager, FilterSpecification<T> specification, Function<Root<T>, From<?, R>> joinPath) {
        this.config = config;
        this.queryManager = queryManager;
        this.specification = Objects.requireNonNullElseGet(
                specification, FilterSpecification::none);
        this.joinPath = joinPath;
    }

    /**
     * Applies the given consumer to configure the filter configuration.
     *
     * @param configConsumer the consumer to apply to the filter configuration
     * @return the query manager: {@link SpecRelationQuery} for further query building
     * @apiNote this method allows for fluent configuration of the filter settings
     */
    public Q configure(Consumer<C> configConsumer) {
        configConsumer.accept(config);
        queryManager.setRegistry(config.getOperationRegistry());
        queryManager.setSpecification(specification);
        queryManager.setJoinPath(joinPath);
        return queryManager;
    }

    /**
     * Finalizes the configuration and returns the query manager.
     *
     * @return the query manager for further query building
     * @apiNote this method allows to skip configuration and proceed directly to query building
     */
    public Q configure() {
        queryManager.setSpecification(specification);
        queryManager.setJoinPath(joinPath);
        return queryManager;
    }
}
