CREATE TABLE activity_stream_data (
      id INT AUTO_INCREMENT PRIMARY KEY,
      activity_id INT NOT NULL,
      stream_type VARCHAR(50) NOT NULL,
      sequence_index INT NOT NULL,
      value INT NOT NULL,
      CONSTRAINT fk_activity FOREIGN KEY (activity_id) REFERENCES activity(id),
      UNIQUE(activity_id, stream_type, sequence_index)
);
