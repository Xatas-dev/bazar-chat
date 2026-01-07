--liquibase formatted sql
--changeset AsterYng:2
--description Create message table
create table message(
    id bigserial primary key,
    chat_id bigint not null references chat(id),
    content varchar(1028) not null,
    user_id uuid not null,
    created_at timestamp with time zone not null default now(),
    updated_at timestamp with time zone not null default now()
);

comment on column message.id IS 'message id';
comment on column message.chat_id IS 'chat id';
comment on column message.content IS 'message content';
comment on column message.user_id IS 'user id';
comment on column message.created_at IS 'message created timestamp';
comment on column message.updated_at IS 'message updated timestamp';