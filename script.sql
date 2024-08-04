create table connection_request
(
    time      timestamp with time zone,
    status    text,
    from_user bigint,
    to_user   bigint,
    id        serial
        constraint pkey
            primary key
);

alter table connection_request
    owner to postgres;

create table login_attempt
(
    id    bigint not null
        primary key,
    time  timestamp with time zone,
    email text   not null,
    pwd   text   not null
);

alter table login_attempt
    owner to postgres;

create table otp
(
    id           bigint default nextval('signup_attempt_id_seq'::regclass) not null
        primary key,
    time         timestamp with time zone                                  not null,
    otp          text                                                      not null
        constraint unique_otp
            unique,
    request_type text                                                      not null,
    stringid     text   default uuid_generate_v4()                         not null
);

alter table otp
    owner to postgres;

create table signup_attempt
(
    id     bigserial
        primary key,
    time   timestamp with time zone,
    email  text,
    pwd    text,
    otp_id bigint
        constraint signupattempt_otp
            references otp
);

alter table signup_attempt
    owner to postgres;

create table token
(
    id          bigint                   not null
        primary key,
    time        timestamp with time zone not null,
    token_text  text                     not null,
    token_type  text                     not null,
    expires_in  integer                  not null,
    expiry_unit text                     not null
);

alter table token
    owner to postgres;

create table user_status
(
    id      bigint                   not null,
    time    timestamp with time zone not null,
    user_id bigint                   not null,
    status  text                     not null,
    primary key (id, user_id)
);

alter table user_status
    owner to postgres;

create table password
(
    id            bigint                   not null
        primary key,
    time          timestamp with time zone not null,
    password_text text                     not null
);

alter table password
    owner to postgres;

create table st_user
(
    id          bigint                   not null
        constraint user_pkey
            primary key,
    time        timestamp with time zone not null,
    email       text                     not null
        constraint constraintname
            unique,
    username    text                     not null,
    password_id bigint                   not null
        constraint stuser_password
            references password,
    status_id   bigint,
    picture_id  bigint
);

alter table st_user
    owner to postgres;

create table picture
(
    id   bigint                   not null
        primary key,
    url  text                     not null,
    time timestamp with time zone not null
);

alter table picture
    owner to postgres;

create table user_token_pair
(
    userid  bigint not null,
    tokenid bigint not null,
    primary key (userid, tokenid)
);

alter table user_token_pair
    owner to postgres;

create table post
(
    id         bigint not null
        constraint post_user
            primary key,
    by_user_id bigint
        constraint by_user_id
            references st_user,
    post_text  text   not null,
    status     text   not null,
    time       timestamp with time zone
);

alter table post
    owner to postgres;

create table fren_connection
(
    from_user bigint,
    to_user   bigint,
    time      timestamp with time zone
);

alter table fren_connection
    owner to postgres;

create view active_fren_connection_requests(time, status, from_user, to_user, id) as
SELECT connection_request."time",
       connection_request.status,
       connection_request.from_user,
       connection_request.to_user,
       connection_request.id
FROM connection_request
WHERE connection_request.status <> 'InActive'::text;

alter table active_fren_connection_requests
    owner to postgres;

create view fren_requests_by_user(senttime, username, receiver_id, sender_id) as
SELECT cr."time"    AS senttime,
       st_user.username,
       st_user.id   AS receiver_id,
       cr.from_user AS sender_id
FROM st_user
         JOIN active_fren_connection_requests cr ON st_user.id = cr.to_user;

alter table fren_requests_by_user
    owner to postgres;

create function uuid_nil() returns uuid
    immutable
    strict
    parallel safe
    language c
as
$$
begin
-- missing source code
end;
$$;

alter function uuid_nil() owner to postgres;

create function uuid_ns_dns() returns uuid
    immutable
    strict
    parallel safe
    language c
as
$$
begin
-- missing source code
end;
$$;

alter function uuid_ns_dns() owner to postgres;

create function uuid_ns_url() returns uuid
    immutable
    strict
    parallel safe
    language c
as
$$
begin
-- missing source code
end;
$$;

alter function uuid_ns_url() owner to postgres;

create function uuid_ns_oid() returns uuid
    immutable
    strict
    parallel safe
    language c
as
$$
begin
-- missing source code
end;
$$;

alter function uuid_ns_oid() owner to postgres;

create function uuid_ns_x500() returns uuid
    immutable
    strict
    parallel safe
    language c
as
$$
begin
-- missing source code
end;
$$;

alter function uuid_ns_x500() owner to postgres;

create function uuid_generate_v1() returns uuid
    strict
    parallel safe
    language c
as
$$
begin
-- missing source code
end;
$$;

alter function uuid_generate_v1() owner to postgres;

create function uuid_generate_v1mc() returns uuid
    strict
    parallel safe
    language c
as
$$
begin
-- missing source code
end;
$$;

alter function uuid_generate_v1mc() owner to postgres;

create function uuid_generate_v3(namespace uuid, name text) returns uuid
    immutable
    strict
    parallel safe
    language c
as
$$
begin
-- missing source code
end;
$$;

alter function uuid_generate_v3(uuid, text) owner to postgres;

create function uuid_generate_v4() returns uuid
    strict
    parallel safe
    language c
as
$$
begin
-- missing source code
end;
$$;

alter function uuid_generate_v4() owner to postgres;

create function uuid_generate_v5(namespace uuid, name text) returns uuid
    immutable
    strict
    parallel safe
    language c
as
$$
begin
-- missing source code
end;
$$;

alter function uuid_generate_v5(uuid, text) owner to postgres;

