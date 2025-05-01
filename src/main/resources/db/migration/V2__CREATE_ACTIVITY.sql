create table activity (
    id INT NOT NULL AUTO_INCREMENT UNIQUE PRIMARY KEY,
    strava_activity_id BIGINT NOT NULL UNIQUE,
    athlete_id INT NOT NULL,
    name VARCHAR(100) NOT NULL ,
    distance FLOAT NOT NULL ,
    moving_time INT NOT NULL ,
    elapsed_time INT NOT NULL ,
    total_elevation_gain FLOAT,
    elev_high FLOAT,
    elev_low FLOAT,
    sport_type VARCHAR(50) NOT NULL ,
    start_date DATETIME NOT NULL ,
    timezone VARCHAR(100) NOT NULL ,
    average_speed FLOAT,
    max_speed FLOAT,
    gear_id VARCHAR(50),
    kilojoules FLOAT,
    average_watts FLOAT,
    max_watts INT,
    weighted_average_watts INT,
    suffer_score INT
);

alter table activity add constraint foreign key (athlete_id) references athlete (id);