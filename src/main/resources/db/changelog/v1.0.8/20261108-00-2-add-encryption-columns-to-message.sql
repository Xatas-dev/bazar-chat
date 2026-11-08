ALTER TABLE message
    ADD COLUMN IF NOT EXISTS encrypted_content bytea,
    ADD COLUMN IF NOT EXISTS iv bytea,
    ADD COLUMN IF NOT EXISTS encryption_version SMALLINT,
    ALTER COLUMN content DROP NOT NULL;