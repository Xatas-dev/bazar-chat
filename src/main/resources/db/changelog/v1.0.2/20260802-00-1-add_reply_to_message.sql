ALTER TABLE message
    ADD COLUMN IF NOT EXISTS reply_message_id bigint references message(id);