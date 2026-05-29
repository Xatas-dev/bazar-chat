CREATE TABLE reaction
(
    id          BIGSERIAL                   PRIMARY KEY,
    code        VARCHAR                     NOT NULL UNIQUE,
    value       VARCHAR                     NOT NULL,
    type        VARCHAR                     NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE    NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP WITH TIME ZONE    NOT NULL DEFAULT NOW()
)
