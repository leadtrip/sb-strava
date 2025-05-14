CREATE TABLE summary_segment
(
    id                           INT AUTO_INCREMENT NOT NULL,
    strava_segment_id            BIGINT NOT NULL,
    athlete_id                   INT NOT NULL,
    name                         VARCHAR(255) NULL,
    activity_type                VARCHAR(255) NULL,
    distance                     FLOAT NULL,
    average_grade                FLOAT NULL,
    maximum_grade                FLOAT NULL,
    elevation_high               FLOAT NULL,
    elevation_low                FLOAT NULL,
    climb_category               INT NULL,
    city                         VARCHAR(255) NULL,
    state                        VARCHAR(255) NULL,
    country                      VARCHAR(255) NULL,
    summary_pr_segment_effort_id BIGINT NULL,
    summary_segment_effort_id    BIGINT NULL,
    CONSTRAINT pk_summary_segment PRIMARY KEY (id)
);

CREATE TABLE summary_pr_segment_effort
(
    id                                  INT AUTO_INCREMENT NOT NULL,
    strava_summary_pr_segment_effort_id BIGINT NOT NULL,
    activity_id                      BIGINT NULL,
    elapsed_time                     INT NULL,
    start_date                       datetime NULL,
    start_date_local                 datetime NULL,
    distance                         FLOAT NULL,
    is_kom                           BIT(1) NULL,
    CONSTRAINT pk_summary_pr_segment_effort PRIMARY KEY (id)
);

CREATE TABLE summary_segment_effort
(
    id                               INT AUTO_INCREMENT NOT NULL,
    strava_summary_segment_effort_id BIGINT NOT NULL,
    activity_id                      BIGINT NULL,
    elapsed_time                     INT NULL,
    start_date                       datetime NULL,
    start_date_local                 datetime NULL,
    distance                         FLOAT NULL,
    is_kom                           BIT(1) NULL,
    CONSTRAINT pk_summary_segment_effort PRIMARY KEY (id)
);

alter table summary_segment add constraint foreign key (athlete_id) references athlete (id);

ALTER TABLE summary_pr_segment_effort
    ADD CONSTRAINT uc_summary_pr_segment_effort_pr_activity UNIQUE (activity_id);

ALTER TABLE summary_pr_segment_effort
    ADD CONSTRAINT uc_summary_pr_segment_effort_strava_summary_pr_segment_effort UNIQUE (strava_summary_pr_segment_effort_id);

ALTER TABLE summary_segment_effort
    ADD CONSTRAINT uc_summary_segment_effort_activity UNIQUE (activity_id);

ALTER TABLE summary_segment_effort
    ADD CONSTRAINT uc_summary_segment_effort_strava_summary_segment_effort UNIQUE (strava_summary_segment_effort_id);

ALTER TABLE summary_segment
    ADD CONSTRAINT uc_summary_segment_strava_segment UNIQUE (strava_segment_id);

ALTER TABLE summary_segment
    ADD CONSTRAINT uc_summary_segment_summary_pr_segment_effort UNIQUE (summary_pr_segment_effort_id);

ALTER TABLE summary_segment
    ADD CONSTRAINT uc_summary_segment_summary_segment_effort UNIQUE (summary_segment_effort_id);

-- ALTER TABLE summary_pr_segment_effort
--    ADD CONSTRAINT FK_SUMMARY_PR_SEGMENT_EFFORT_ON_PR_ACTIVITY FOREIGN KEY (pr_activity_id) REFERENCES activity (strava_activity_id);

-- ALTER TABLE summary_segment_effort
--    ADD CONSTRAINT FK_SUMMARY_SEGMENT_EFFORT_ON_ACTIVITY FOREIGN KEY (activity_id) REFERENCES activity (strava_activity_id);

-- ALTER TABLE summary_segment
--    ADD CONSTRAINT FK_SUMMARY_SEGMENT_ON_SUMMARY_PR_SEGMENT_EFFORT FOREIGN KEY (summary_pr_segment_effort_id) REFERENCES summary_pr_segment_effort (strava_summary_pr_segment_effort_id);

-- ALTER TABLE summary_segment
--    ADD CONSTRAINT FK_SUMMARY_SEGMENT_ON_SUMMARY_SEGMENT_EFFORT FOREIGN KEY (summary_segment_effort_id) REFERENCES summary_segment_effort (strava_summary_segment_effort_id);
