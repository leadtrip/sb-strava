create table footer_link(
    id INT NOT NULL AUTO_INCREMENT UNIQUE PRIMARY KEY,
    url VARCHAR(1000) NOT NULL,
    text VARCHAR(100) NOT NULL,
    is_active BIT(1) NULL
);