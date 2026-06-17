
CREATE TABLE IF NOT EXISTS users (
                                     id            BIGSERIAL PRIMARY KEY,
                                     name          VARCHAR(255) NOT NULL,
                                     email         VARCHAR(255) NOT NULL UNIQUE,
                                     password      VARCHAR(255),
                                     role          VARCHAR(50)  NOT NULL,
                                     is_active     BOOLEAN      NOT NULL DEFAULT TRUE,
                                     created_at    TIMESTAMP
);

CREATE TABLE IF NOT EXISTS tenant (
                                      id          BIGSERIAL PRIMARY KEY,
                                      name        VARCHAR(255) NOT NULL,
                                      email       VARCHAR(255) NOT NULL UNIQUE,
                                      schema_name VARCHAR(100) NOT NULL UNIQUE,
                                      active      BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS state (
                                     id            BIGSERIAL PRIMARY KEY,
                                     name          VARCHAR(255) NOT NULL UNIQUE,
                                     is_active     BOOLEAN NOT NULL DEFAULT TRUE,
                                     state_head_id BIGINT REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS district (
                                        id               BIGSERIAL PRIMARY KEY,
                                        district_name    VARCHAR(255) NOT NULL,
                                        is_active        BOOLEAN NOT NULL DEFAULT TRUE,
                                        state_id         BIGINT REFERENCES state(id),
                                        district_head_id BIGINT REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS city (
                                    id           BIGSERIAL PRIMARY KEY,
                                    name         VARCHAR(255) NOT NULL,
                                    pincode      VARCHAR(20),
                                    is_active    BOOLEAN NOT NULL DEFAULT TRUE,
                                    city_head_id BIGINT REFERENCES users(id),
                                    district_id  BIGINT REFERENCES district(id)
);

CREATE TABLE IF NOT EXISTS area (
                                    id        BIGSERIAL PRIMARY KEY,
                                    name      VARCHAR(255) NOT NULL,
                                    area_code VARCHAR(50) UNIQUE,
                                    is_active BOOLEAN NOT NULL DEFAULT TRUE,
                                    city_id   BIGINT REFERENCES city(id)
);

CREATE TABLE IF NOT EXISTS service_area (
                                            id             BIGSERIAL PRIMARY KEY,
                                            code           VARCHAR(100),
                                            area_id        INTEGER REFERENCES area(id),
                                            technician_id  BIGINT REFERENCES users(id),
                                            biller_id      BIGINT REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS portfolios (
                                          id                   BIGSERIAL PRIMARY KEY,
                                          sales_head_users_id  BIGINT REFERENCES users(id),
                                          tenant_id            BIGINT REFERENCES tenant(id),
                                          assigned_at          TIMESTAMP
);

CREATE TABLE IF NOT EXISTS invitation (
                                          id                BIGSERIAL PRIMARY KEY,
                                          invitation_token  VARCHAR(255) NOT NULL UNIQUE,
                                          issued_at         TIMESTAMP    NOT NULL,
                                          expires_at        TIMESTAMP    NOT NULL,
                                          issued_to         VARCHAR(255) NOT NULL,
                                          role              VARCHAR(50)  NOT NULL,
                                          issuer_id         BIGINT REFERENCES users(id) NOT NULL
);
