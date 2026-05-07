package wood.mike.sbstravaapi.dtos.activity;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;
import java.util.Optional;

public record SyncActivitiesRequest(
        int totalPagesToSync,
        @Nullable
        @PastOrPresent(message = "The fromDate cannot be in the future")
        LocalDate fromDate
) {
    public Optional<LocalDate> fromDateOptional() {
        return Optional.ofNullable(fromDate);
    }
}
