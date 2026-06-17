
CREATE TABLE IF NOT EXISTS tenants (
    id            BIGSERIAL PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    on_boarded_at TIMESTAMP,
    is_active     BOOLEAN NOT NULL DEFAULT TRUE
);

-- Tenant-side users (POC, Operational Head, BPO staff, etc.)
CREATE TABLE IF NOT EXISTS tenant_users (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    email       VARCHAR(255) NOT NULL UNIQUE,
    password    VARCHAR(255),
    schema_name VARCHAR(100),
    role        VARCHAR(50)  NOT NULL,
    tenants_id  BIGINT REFERENCES tenants(id)
);

-- Meter type configuration (Household, Solar, Industrial …)
CREATE TABLE IF NOT EXISTS meter_types (
    id                    BIGSERIAL PRIMARY KEY,
    meter_type            VARCHAR(100) NOT NULL,
    rate_per_unit         BIGINT       NOT NULL,
    photo_count           BIGINT       NOT NULL,
    photo_interval_seconds INTEGER     NOT NULL,
    tenant_id             BIGINT REFERENCES tenants(id)
);

-- State-level BPO (call centre) of the electricity provider
CREATE TABLE IF NOT EXISTS bpo_states (
    id             BIGSERIAL PRIMARY KEY,
    state_name     VARCHAR(255) NOT NULL,
    is_active      BOOLEAN NOT NULL DEFAULT TRUE,
    master_state_id BIGINT,                   -- cross-schema ref to master.state.id
    tenant_id      BIGINT REFERENCES tenants(id) NOT NULL
);

-- BPO personnel assignment (higher-manager / manager / personnel)
--   mapped to a district + city inside the state call-centre
CREATE TABLE IF NOT EXISTS bpo (
    id                 BIGSERIAL PRIMARY KEY,
    tenant_user_id     BIGINT REFERENCES tenant_users(id) NOT NULL,
    bpo_state_id       BIGINT REFERENCES bpo_states(id)   NOT NULL,
    master_district_id BIGINT,   -- cross-schema ref to master.district.id
    master_city_id     BIGINT    -- cross-schema ref to master.city.id
);

-- Customers (enrolled via CRM)
CREATE TABLE IF NOT EXISTS customers (
    id                    BIGSERIAL PRIMARY KEY,
    customer_name         VARCHAR(255) NOT NULL,
    email                 VARCHAR(255) NOT NULL UNIQUE,
    mobile                VARCHAR(20),
    address               VARCHAR(500),
    pincode               BIGINT,
    master_state_id       BIGINT,
    master_district_id    BIGINT,
    master_city_id        BIGINT,
    master_service_area_id BIGINT,
    tenants_id            BIGINT REFERENCES tenants(id),
    meter_type_id         BIGINT REFERENCES meter_types(id)
);

-- New-connection requests from customers
CREATE TABLE IF NOT EXISTS connection_request (
    id                     BIGSERIAL PRIMARY KEY,
    status                 VARCHAR(50) NOT NULL,
    requested_at           TIMESTAMP,
    approved_at            TIMESTAMP,
    customers_id           BIGINT REFERENCES customers(id),
    meter_type_id          BIGINT REFERENCES meter_types(id),
    approved_by_id         BIGINT REFERENCES tenant_users(id),
    master_service_area_id BIGINT    -- determines which technician is assigned
);

-- Physical meter installation record
CREATE TABLE IF NOT EXISTS meter_installation (
    id                  BIGSERIAL PRIMARY KEY,
    customers_id        BIGINT REFERENCES customers(id),
    meter_number        INTEGER NOT NULL UNIQUE,
    master_technician_id BIGINT,              -- cross-schema ref to master.users.id
    connection_request_id BIGINT REFERENCES connection_request(id),
    installation_date   DATE
);

-- Meter readings captured by billers
CREATE TABLE IF NOT EXISTS meter_reading (
    id                     BIGSERIAL PRIMARY KEY,
    previous_reading       DOUBLE PRECISION,
    current_reading        DOUBLE PRECISION,
    consumed_units         DOUBLE PRECISION,
    rate_snapshot_per_unit DOUBLE PRECISION,
    installation_id        BIGINT REFERENCES meter_installation(id),
    reading_date           TIMESTAMP,
    master_biller_id       BIGINT   -- cross-schema ref to master.users.id
);

-- Bills generated from meter readings
CREATE TABLE IF NOT EXISTS bills (
    id              BIGSERIAL PRIMARY KEY,
    total_units     DOUBLE PRECISION,
    amount          DOUBLE PRECISION,
    bill_date       DATE,
    due_date        DATE,
    status          VARCHAR(50),
    customers_id    BIGINT REFERENCES customers(id),
    meter_reading_id BIGINT REFERENCES meter_reading(id)
);

-- Customer complaints
CREATE TABLE IF NOT EXISTS complaints (
    id                    BIGSERIAL PRIMARY KEY,
    customers_id          BIGINT REFERENCES customers(id),
    title                 VARCHAR(255) NOT NULL,
    complaint_description VARCHAR(1000),
    status                VARCHAR(50),
    assigned_personnel_id BIGINT REFERENCES tenant_users(id),
    master_technician_id  BIGINT,     -- cross-schema ref to master.users.id
    escalation_level      INTEGER DEFAULT 0,
    raised_at             TIMESTAMP,
    resolved_at           TIMESTAMP,
    last_updated_at       TIMESTAMP
);

-- for inviting BPO staff, operational heads, etc.
CREATE TABLE IF NOT EXISTS tenant_invitation (
    id               BIGSERIAL PRIMARY KEY,
    invitation_token VARCHAR(255) NOT NULL,
    issued_at        TIMESTAMP    NOT NULL,
    expires_at       TIMESTAMP    NOT NULL,
    issued_to        VARCHAR(255) NOT NULL,
    role             VARCHAR(50)  NOT NULL,
    issuer_id        BIGINT REFERENCES tenant_users(id) NOT NULL
);
