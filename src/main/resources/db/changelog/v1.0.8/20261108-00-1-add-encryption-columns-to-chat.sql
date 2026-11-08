ALTER TABLE chat
    ADD COLUMN IF NOT EXISTS encrypted_dek bytea,
    ADD COLUMN IF NOT EXISTS dek_iv bytea,
    ADD COLUMN IF NOT EXISTS kek_version varchar(32);