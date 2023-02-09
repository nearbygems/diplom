CREATE TABLE company
(
    "id"   BIGINT PRIMARY KEY,
    "name" VARCHAR(256) NOT NULL,
    "city" VARCHAR(256) NOT NULL
);

CREATE TABLE vacancy
(
    "id"           BIGINT PRIMARY KEY,
    "title"        VARCHAR(256) NOT NULL,
    "salary"       BIGINT       NOT NULL,
    "description"  VARCHAR(512) NOT NULL,
    "requirements" VARCHAR(512) NOT NULL,
    "company_id"   BIGINT       NOT NULL,
    CONSTRAINT fk_company FOREIGN KEY ("company_id") REFERENCES company ("id")
);