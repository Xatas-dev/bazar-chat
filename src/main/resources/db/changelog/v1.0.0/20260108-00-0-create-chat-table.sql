--liquibase formatted sql
--changeset AsterYng:1
--description Create chat table
create table chat(
    id bigserial primary key,
    space_id bigint not null unique,
    created_at timestamp with time zone not null default now(),
    updated_at timestamp with time zone not null default now()
);

comment on column chat.id IS 'Chat id';
comment on column chat.space_id IS 'Space id (as a foreign key for space service)';
comment on column chat.created_at IS 'chat created timestamp';
comment on column chat.updated_at IS 'chat updated timestamp';