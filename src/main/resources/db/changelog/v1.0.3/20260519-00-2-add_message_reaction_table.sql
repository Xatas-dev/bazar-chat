CREATE TABLE message_reaction
(
    id          BIGSERIAL                   PRIMARY KEY,
    message_id  BIGINT                      NOT NULL REFERENCES message(id),
    reaction_id BIGINT                      NOT NULL REFERENCES reaction(id),
    user_id     UUID                        NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE    NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP WITH TIME ZONE    NOT NULL DEFAULT NOW()
);

ALTER TABLE message_reaction
ADD CONSTRAINT uq_message_user_reaction_key UNIQUE (message_id, user_id, reaction_id);
