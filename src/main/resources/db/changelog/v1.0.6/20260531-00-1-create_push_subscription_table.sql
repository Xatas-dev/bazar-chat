CREATE TABLE IF NOT EXISTS push_subscription
(
    id          BIGSERIAL PRIMARY KEY,
    user_id     UUID NOT NULL,
    endpoint    VARCHAR NOT NULL,
    p256dh      VARCHAR NOT NULL,
    auth        VARCHAR NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);