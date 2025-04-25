create table athlete
(
    id INT NOT NULL AUTO_INCREMENT UNIQUE PRIMARY KEY,
    strava_athlete_id BIGINT UNIQUE NOT NULL,
    country VARCHAR(60) NOT NULL,
    firstname VARCHAR(100) NOT NULL,
    ftp TINYINT NULL,
    lastname VARCHAR(255) NOT NULL,
    profile VARCHAR(255) NULL,
    profile_medium VARCHAR(255) NULL,
    sex VARCHAR(1) NULL,
    weight FLOAT NULL
);


create table athlete_token
(
    id INT NOT NULL AUTO_INCREMENT UNIQUE PRIMARY KEY,
    athlete_id INT UNIQUE NOT NULL,
    access_token VARCHAR(255) NOT NULL,
    refresh_token VARCHAR(255) NOT NULL,
    expires_at INT NOT NULL,
    expires_in INT NOT NULL
);

alter table athlete_token add constraint foreign key (athlete_id) references athlete (id);