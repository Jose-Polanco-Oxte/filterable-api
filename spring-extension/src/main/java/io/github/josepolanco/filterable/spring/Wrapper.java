package io.github.josepolanco.filterable.spring;

import io.github.josepolanco.filterable.api.queries.utils.FilterSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

/**
 * Extension class to convert between FilterSpecification and Spring's Specification.
 */
public class Wrapper {

    /**
     * Converts a FilterSpecification to a Spring Specification.
     *
     * @param filterSpecification the FilterSpecification to convert
     * @param <T>                 the entity type
     * @return the corresponding Spring Specification
     */
    public static <T> Specification<T> from(@NonNull FilterSpecification<T> filterSpecification) {
        return filterSpecification::toPredicate;
    }

    /**
     * Converts a Spring Specification to a FilterSpecification.
     *
     * @param specification the Spring Specification to convert
     * @param <T>           the entity type
     * @return the corresponding FilterSpecification
     */
    public static <T> FilterSpecification<T> to(@NonNull Specification<T> specification) {
        return specification::toPredicate;
    }
}