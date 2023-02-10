CREATE TABLE IF NOT EXISTS company
(
    "id"   BIGINT PRIMARY KEY,
    "name" VARCHAR(256) NOT NULL,
    "city" VARCHAR(256) NOT NULL
);

CREATE TABLE IF NOT EXISTS vacancy
(
    "id"             BIGINT PRIMARY KEY,
    "title"          VARCHAR(256) NOT NULL,
    "salary"         BIGINT,
    "responsibility" VARCHAR(512),
    "requirement"    VARCHAR(512),
    "company_id"     BIGINT       NOT NULL,
    CONSTRAINT fk_company FOREIGN KEY ("company_id") REFERENCES company ("id")
);