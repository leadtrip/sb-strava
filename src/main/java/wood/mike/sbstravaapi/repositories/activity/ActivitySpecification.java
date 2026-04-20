package wood.mike.sbstravaapi.repositories.activity;

import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import wood.mike.sbstravaapi.filters.activity.ActivityFilter;
import wood.mike.sbstravaapi.entities.activity.Activity;
import wood.mike.sbstravaapi.entities.athlete.Athlete;


import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ActivitySpecification {

    private static final Integer KM_MULTIPLIER = 1000;

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
                double minMeters;
                double maxMeters;

                if (filter.getTargetDistanceTolerance() != null) {
                    minMeters = (filter.getTargetDistance() - filter.getTargetDistanceTolerance()) * KM_MULTIPLIER;
                    maxMeters = (filter.getTargetDistance() + filter.getTargetDistanceTolerance()) * KM_MULTIPLIER;

                    predicates.add(cb.between(root.get("distance"), minMeters, maxMeters));
                } else {
                    double baseKm = Math.floor(filter.getTargetDistance());
                    minMeters = baseKm * KM_MULTIPLIER;
                    maxMeters = (baseKm + 1.0) * KM_MULTIPLIER;

                    predicates.add(cb.greaterThanOrEqualTo(root.get("distance"), minMeters));
                    predicates.add(cb.lessThan(root.get("distance"), maxMeters));
                }

                log.info("Target distance meters: min={}, max={}", minMeters, maxMeters);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}

