CREATE TABLE reaction
(
    id          BIGSERIAL                   PRIMARY KEY,
    code        VARCHAR(64)                 NOT NULL UNIQUE,
    value       VARCHAR(512)                NOT NULL,
    type        VARCHAR(16)                 NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE    NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP WITH TIME ZONE    NOT NULL DEFAULT NOW()
)
