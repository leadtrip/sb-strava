CREATE TABLE segment_effort (
         id int AUTO_INCREMENT,
         strava_segment_effort_id BIGINT UNIQUE NOT NULL,
         activity_id INT NOT NULL,
         athlete_id INT NOT NULL,
         segment_id BIGINT,
         name VARCHAR(255) NOT NULL,
         elapsed_time INT NOT NULL,
         moving_time INT NOT NULL,
         start_date DATETIME NOT NULL,
         start_date_local DATETIME NOT NULL,
         distance FLOAT NOT NULL,
         is_kom BOOLEAN DEFAULT FALSE,
         start_index INT,
         end_index INT,
         average_cadence FLOAT,
         average_watts FLOAT,
         device_watts BOOLEAN,
         average_heartrate FLOAT,
         max_heartrate FLOAT,
         kom_rank INT,
         pr_rank INT,
         hidden BOOLEAN,
         CONSTRAINT pk_segment_effort PRIMARY KEY (id)
);

ALTER TABLE activity
    ADD COLUMN segment_efforts_fetched BOOL default false;

-- alter table segment_effort add constraint foreign key (activity_id) references activity (id);
-- alter table segment_effort add constraint foreign key (athlete_id) references athlete (id);
-- alter table segment_effort add constraint foreign key (segment_id) references summary_segment (id);