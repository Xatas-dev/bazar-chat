--liquibase formatted sql
--changeset seny1:3
--description Create visible column into message
ALTER TABLE message
    ADD COLUMN IF NOT EXISTS visible boolean DEFAULT true;