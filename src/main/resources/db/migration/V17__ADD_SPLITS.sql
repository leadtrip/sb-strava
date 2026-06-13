CREATE TABLE split (
         id int AUTO_INCREMENT,
         activity_id INT NOT NULL,
         distance FLOAT,
         elapsed_time INT,
         elevation_difference FLOAT,
         moving_time INT,
         split INT,
         average_speed FLOAT,
         average_grade_adjusted_speed FLOAT,
         average_heartrate FLOAT,
         pace_zone INT,
         CONSTRAINT pk_split PRIMARY KEY (id)
);