package wood.mike.sbstravaapi.repositories.activity;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import wood.mike.sbstravaapi.filters.activity.ActivityFilter;
import wood.mike.sbstravaapi.entities.activity.Activity;
import wood.mike.sbstravaapi.entities.athlete.Athlete;


import java.util.ArrayList;
import java.util.List;

public class ActivitySpecification {

    public static Specification<Activity> withFilters(
            ActivityFilter filter, Athlete athlete) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get("athlete"), athlete));

            if (filter.getFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("startDate"), filter.getFrom()));
            }

            if (filter.getTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("startDate"), filter.getTo()));
            }

            if (filter.getActivityType() != null && !filter.getActivityType().isBlank()) {
                predicates.add(cb.equal(root.get("sportType"), filter.getActivityType()));
            }

            if (filter.getTargetDistance() != null) {
                double tolerance = filter.getTargetDistanceTolerance() != null ? filter.getTargetDistanceTolerance() : 5.0;

                double min = (filter.getTargetDistance() - tolerance) * 1000;
                double max = (filter.getTargetDistance() + tolerance) * 1000;

                predicates.add(cb.between(root.get("distance"), min, max));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}

