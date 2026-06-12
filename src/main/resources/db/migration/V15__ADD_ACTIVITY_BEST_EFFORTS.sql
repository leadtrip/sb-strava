CREATE TABLE best_effort (
        id int AUTO_INCREMENT,
        strava_best_effort_id BIGINT UNIQUE NOT NULL,
        activity_id INT NOT NULL,
        athlete_id INT NOT NULL,
        name VARCHAR(255) NOT NULL,
        elapsed_time INT NOT NULL,
        moving_time INT NOT NULL,
        start_date DATETIME NOT NULL,
        start_date_local DATETIME NOT NULL,
        distance FLOAT NOT NULL,
        start_index INT,
        end_index INT,
        pr_rank INT,
        CONSTRAINT pk_best_effort PRIMARY KEY (id)
);