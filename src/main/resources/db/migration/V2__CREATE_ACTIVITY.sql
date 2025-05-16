create table activity (
    id INT NOT NULL AUTO_INCREMENT UNIQUE PRIMARY KEY,
    strava_activity_id BIGINT NOT NULL UNIQUE,
    version INT NOT NULL DEFAULT 0,
    athlete_id INT NOT NULL,
    name VARCHAR(200) NOT NULL ,
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
    suffer_score INT,
    polyline_map_id INT
);

create table polyline_map (
    id INT NOT NULL AUTO_INCREMENT UNIQUE PRIMARY KEY,
    strava_id VARCHAR(100),
    polyline TEXT,
    summary_polyline TEXT
);

alter table activity add constraint foreign key (athlete_id) references athlete (id);

alter table activity add constraint foreign key (polyline_map_id) references polyline_map(id);