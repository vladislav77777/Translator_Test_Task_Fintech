--changeset vladislav77777:1
DROP TABLE IF EXISTS translation_request;

--changeset vladislav77777:2
CREATE TABLE translation_request (
                                     id BIGSERIAL PRIMARY KEY,
                                     ip VARCHAR(255) NOT NULL,
                                     input TEXT NOT NULL,
                                     result TEXT NOT NULL,
                                     timestamp TIMESTAMP NOT NULL
);
