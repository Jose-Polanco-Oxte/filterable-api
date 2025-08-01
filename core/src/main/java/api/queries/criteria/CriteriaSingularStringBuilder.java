package api.queries.criteria;

import api.queries.utils.FilterSpecification;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.SingularAttribute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class CriteriaSingularStringBuilder<T> {

    public CriteriaSingularStringBuilder() {}

    public static <T> CriteriaSingularStringBuilder<T> create() {
        return new CriteriaSingularStringBuilder<>();
    }

    public FilterSpecification<T> containsOp(SingularAttribute<T, String> attribute, String value) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.like(root.get(attribute), "%" + value + "%");
    }

    public <R> FilterSpecification<T> containsOp(SingularAttribute<R, String> attribute, String value, Function<Root<T>, From<?, R>> joinPath) {
        return (root, query, criteriaBuilder) -> {
            From<?, R> from = joinPath.apply(root);
            return criteriaBuilder.like(from.get(attribute), "%" + value + "%");
        };
    }

    public FilterSpecification<T> notContainsOp(SingularAttribute<T, String> attribute, String value) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.notLike(root.get(attribute), "%" + value + "%");
    }

    public <R> FilterSpecification<T> notContainsOp(SingularAttribute<R, String> attribute, String value, Function<Root<T>, From<?, R>> joinPath) {
        return (root, query, criteriaBuilder) -> {
            From<?, R> from = joinPath.apply(root);
            return criteriaBuilder.notLike(from.get(attribute), "%" + value + "%");
        };
    }

    public FilterSpecification<T> startsWithOp(SingularAttribute<T, String> attribute, String value) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.like(root.get(attribute), value + "%");
    }

    public <R> FilterSpecification<T> startsWithOp(SingularAttribute<R, String> attribute, String value, Function<Root<T>, From<?, R>> joinPath) {
        return (root, query, criteriaBuilder) -> {
            From<?, R> from = joinPath.apply(root);
            return criteriaBuilder.like(from.get(attribute), value + "%");
        };
    }

    public FilterSpecification<T> endsWithOp(SingularAttribute<T, String> attribute, String value) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.like(root.get(attribute), "%" + value);
    }

    public <R> FilterSpecification<T> endsWithOp(SingularAttribute<R, String> attribute, String value, Function<Root<T>, From<?, R>> joinPath) {
        return (root, query, criteriaBuilder) -> {
            From<?, R> from = joinPath.apply(root);
            return criteriaBuilder.like(from.get(attribute), "%" + value);
        };
    }

    // Operations with string collections

    public FilterSpecification<T> containsOp(SingularAttribute<T, String> attribute, Collection<String> values) {
        if (values == null || values.isEmpty()) {
            return FilterSpecification.none();
        }
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String value : values) {
                predicates.add(criteriaBuilder.like(root.get(attribute), "%" + value + "%"));
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

    public <R> FilterSpecification<T> containsOp(SingularAttribute<R, String> attribute, Collection<String> values, Function<Root<T>, From<?, R>> joinPath) {
        if (values == null || values.isEmpty()) {
            return FilterSpecification.none();
        }
        return (root, query, criteriaBuilder) -> {
            From<?, R> from = joinPath.apply(root);
            List<Predicate> predicates = new ArrayList<>();
            for (String value : values) {
                predicates.add(criteriaBuilder.like(from.get(attribute), "%" + value + "%"));
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

    public FilterSpecification<T> notContainsOp(SingularAttribute<T, String> attribute, Collection<String> values) {
        if (values == null || values.isEmpty()) {
            return FilterSpecification.none();
        }
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String value : values) {
                predicates.add(criteriaBuilder.notLike(root.get(attribute), "%" + value + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public <R> FilterSpecification<T> notContainsOp(SingularAttribute<R, String> attribute, Collection<String> values, Function<Root<T>, From<?, R>> joinPath) {
        if (values == null || values.isEmpty()) {
            return FilterSpecification.none();
        }
        return (root, query, criteriaBuilder) -> {
            From<?, R> from = joinPath.apply(root);
            List<Predicate> predicates = new ArrayList<>();
            for (String value : values) {
                predicates.add(criteriaBuilder.notLike(from.get(attribute), "%" + value + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public FilterSpecification<T> containsAllOp(SingularAttribute<T, String> attribute, Collection<String> values) {
        if (values == null || values.isEmpty()) {
            return FilterSpecification.none();
        }
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String value : values) {
                predicates.add(criteriaBuilder.like(root.get(attribute), "%" + value + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public <R> FilterSpecification<T> containsAllOp(SingularAttribute<R, String> attribute, Collection<String> values, Function<Root<T>, From<?, R>> joinPath) {
        if (values == null || values.isEmpty()) {
            return FilterSpecification.none();
        }
        return (root, query, criteriaBuilder) -> {
            From<?, R> from = joinPath.apply(root);
            List<Predicate> predicates = new ArrayList<>();
            for (String value : values) {
                predicates.add(criteriaBuilder.like(from.get(attribute), "%" + value + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public FilterSpecification<T> startsWithOp(SingularAttribute<T, String> attribute, Collection<String> values) {
        if (values == null || values.isEmpty()) {
            return FilterSpecification.none();
        }
        return (root, query, criteriaBuilder) ->  {
            List<Predicate> predicates = new ArrayList<>();
            for (String value : values) {
                predicates.add(criteriaBuilder.like(root.get(attribute), value + "%"));
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

    public <R> FilterSpecification<T> startsWithOp(SingularAttribute<R, String> attribute, Collection<String> values, Function<Root<T>, From<?, R>> joinPath) {
        if (values == null || values.isEmpty()) {
            return FilterSpecification.none();
        }
        return (root, query, criteriaBuilder) -> {
            From<?, R> from = joinPath.apply(root);
            List<Predicate> predicates = new ArrayList<>();
            for (String value : values) {
                predicates.add(criteriaBuilder.like(from.get(attribute), value + "%"));
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

    public FilterSpecification<T> endsWithOp(SingularAttribute<T, String> attribute, Collection<String> values) {
        if (values == null || values.isEmpty()) {
            return FilterSpecification.none();
        }
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String value : values) {
                predicates.add(criteriaBuilder.like(root.get(attribute), "%" + value));
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

    public <R> FilterSpecification<T> endsWithOp(SingularAttribute<R, String> attribute, Collection<String> values, Function<Root<T>, From<?, R>> joinPath) {
        if (values == null || values.isEmpty()) {
            return FilterSpecification.none();
        }
        return (root, query, criteriaBuilder) -> {
            From<?, R> from = joinPath.apply(root);
            List<Predicate> predicates = new ArrayList<>();
            for (String value : values) {
                predicates.add(criteriaBuilder.like(from.get(attribute), "%" + value));
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }
}
