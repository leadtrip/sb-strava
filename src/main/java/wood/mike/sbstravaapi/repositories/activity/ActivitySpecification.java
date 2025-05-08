package wood.mike.sbstravaapi.repositories.activity;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import wood.mike.sbstravaapi.entities.activity.Activity;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ActivitySpecification {

    public static Specification<Activity> withFilters(
            LocalDateTime from, LocalDateTime to, String activityType) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (from != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("startDate"), from));
            }

            if (to != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("startDate"), to));
            }

            if (activityType != null && !activityType.isEmpty()) {
                predicates.add(cb.equal(root.get("sportType"), activityType));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

