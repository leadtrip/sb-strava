package wood.mike.sbstravaapi.repositories.segments;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import wood.mike.sbstravaapi.entities.athlete.Athlete;
import wood.mike.sbstravaapi.entities.segments.SummarySegment;

import java.util.ArrayList;
import java.util.List;

public class SummarySegmentSpecification {
    public static Specification<SummarySegment> withFilters(
            Athlete athlete, String activityType) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get("athlete"), athlete));

            if (activityType != null && !activityType.isEmpty()) {
                predicates.add(cb.equal(root.get("activityType"), activityType));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
