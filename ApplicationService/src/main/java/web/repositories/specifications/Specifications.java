package web.repositories.specifications;


import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import web.dto.request.filter.Item;
import web.exception.InvalidDataException;

import java.util.UUID;


public class Specifications {

    public static <T> Specification<T> hasField(SearchCriteria searchCriteria) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate[] predicates = searchCriteria.getParams().stream()
                    .map(item -> createPredicate(item, root, criteriaBuilder))
                    .toArray(Predicate[]::new);

            return criteriaBuilder.and(predicates);
        };
    }

    private static <T> Predicate createPredicate(Item item, Root<T> root, CriteriaBuilder criteriaBuilder) {
        Path<?> path;

        if (item.getField().contains(".")) {
            String[] fields = item.getField().split("\\.");
            Join<Object, Object> join = root.join(fields[0], JoinType.LEFT);
            path = join.get(fields[1]);
        } else {
            path = root.get(item.getField());
        }

        Object value = convertToCorrectType(item.getValue(), path.getJavaType());

        return switch (item.getCondition()) {
            case "=" -> criteriaBuilder.equal(path, value);
            case "like" -> criteriaBuilder.like(path.as(String.class), "%" + value + "%");
            case "notLike" -> criteriaBuilder.notLike(path.as(String.class), "%" + value + "%");
            case "!=" -> criteriaBuilder.notEqual(path, value);
            case ">" -> criteriaBuilder.greaterThan(path.as(String.class), value.toString());
            case ">=" -> criteriaBuilder.greaterThanOrEqualTo(path.as(String.class), value.toString());
            case "<" -> criteriaBuilder.lessThan(path.as(String.class), value.toString());
            case "<=" -> criteriaBuilder.lessThanOrEqualTo(path.as(String.class), value.toString());
            default -> throw new InvalidDataException("Неизвестное условие: " + item.getCondition());
        };

    }

    private static Object convertToCorrectType(Object value, Class<?> fieldType) {
        if (fieldType.equals(Boolean.class) || fieldType.equals(boolean.class)) {
            return Boolean.parseBoolean(value.toString());
        } else if (fieldType.equals(UUID.class)) {
            return UUID.fromString(value.toString());
        }
        return value;
    }
}