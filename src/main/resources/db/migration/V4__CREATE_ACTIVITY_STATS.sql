CREATE TABLE activity_stats
(
    id                           BIGINT AUTO_INCREMENT NOT NULL,
    athlete_id                   BIGINT                NULL,
    biggest_ride_distance        DOUBLE                NULL,
    biggest_climb_elevation_gain DOUBLE                NULL,
    recent_ride_totals           BIGINT                NULL,
    recent_run_totals            BIGINT                NULL,
    recent_swim_totals           BIGINT                NULL,
    ytd_ride_totals              BIGINT                NULL,
    ytd_run_totals               BIGINT                NULL,
    ytd_swim_totals              BIGINT                NULL,
    all_ride_totals              BIGINT                NULL,
    all_run_totals               BIGINT                NULL,
    all_swim_totals              BIGINT                NULL,
    CONSTRAINT pk_activity_stats PRIMARY KEY (id)
);

CREATE TABLE activity_total
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    count             INT                   NULL,
    distance          DOUBLE                NULL,
    moving_time       DOUBLE                NULL,
    elapsed_time      DOUBLE                NULL,
    elevation_gain    DOUBLE                NULL,
    achievement_count INT                   NULL,
    CONSTRAINT pk_activity_total PRIMARY KEY (id)
);