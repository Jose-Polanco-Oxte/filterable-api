package jpolanco.springextension;

import api.queries.utils.FilterSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

public class SpringSpecificationWrapper {
    public static <T> Specification<T> from(@NonNull FilterSpecification<T> filterSpecification) {
        return filterSpecification::toPredicate;
    }

    public static <T> FilterSpecification<T> to(@NonNull Specification<T> specification) {
        return specification::toPredicate;
    }
}