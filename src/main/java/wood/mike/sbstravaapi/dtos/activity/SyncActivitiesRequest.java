package wood.mike.sbstravaapi.dtos.activity;

import jakarta.annotation.Nullable;

import java.time.LocalDate;

public record SyncActivitiesRequest(
        int totalPagesToSync,
        @Nullable LocalDate fromDate
) {}
