package api.queries.criteria;

import api.configurations.FilterConfiguration;
import api.queries.utils.FilterSpecification;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Root;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

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

    public Q configure(Consumer<C> configConsumer) {
        configConsumer.accept(config);
        queryManager.setRegistry(config.getOperationRegistry());
        queryManager.setSpecification(specification);
        queryManager.setJoinPath(joinPath);
        return queryManager;
    }

    public Q configure() {
        queryManager.setSpecification(specification);
        queryManager.setJoinPath(joinPath);
        return queryManager;
    }
}
