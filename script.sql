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

create table login_attempt
(
    id    bigint not null
        primary key,
    time  timestamp with time zone,
    email text   not null,
    pwd   text   not null
);

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

create table user_status
(
    id      bigint                   not null,
    time    timestamp with time zone not null,
    user_id bigint                   not null,
    status  text                     not null,
    primary key (id, user_id)
);

create table password
(
    id            bigint                   not null
        primary key,
    time          timestamp with time zone not null,
    password_text text                     not null
);

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
    picture_id  bigint,
    user_props  jsonb
);

create table picture
(
    id   bigint                   not null
        primary key,
    url  text                     not null,
    time timestamp with time zone not null
);

create table user_token_pair
(
    userid  bigint not null,
    tokenid bigint not null,
    primary key (userid, tokenid)
);

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

create index post_by_user_id_id_index
    on post (by_user_id asc, id desc);

create table fren_connection
(
    from_user bigint,
    to_user   bigint,
    time      timestamp with time zone
);

create table post_like
(
    post_id bigint not null,
    user_id bigint not null,
    time    timestamp with time zone,
    constraint post_like_pk
        primary key (post_id, user_id)
);

create view active_fren_connection_requests(time, status, from_user, to_user, id) as
SELECT connection_request."time",
       connection_request.status,
       connection_request.from_user,
       connection_request.to_user,
       connection_request.id
FROM connection_request
WHERE connection_request.status <> 'InActive'::text;

create view fren_requests_by_user(senttime, username, receiver_id, sender_id) as
SELECT cr."time"    AS senttime,
       st_user.username,
       st_user.id   AS receiver_id,
       cr.from_user AS sender_id
FROM st_user
         JOIN active_fren_connection_requests cr ON st_user.id = cr.to_user;

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

create function getpostfeed4(foruser numeric, howmany integer)
    returns TABLE(id integer, byuser integer, addedtime timestamp with time zone, posttext text, status text)
    language sql
as
$$
(SELECT id,post.by_user_id as byUser,post.time,post_text as postText,post.status
 FROM post
          INNER JOIN public.fren_connection fc ON fc.to_user = post.by_user_id
 WHERE fc.from_user = forUser
 UNION
 SELECT id,post.by_user_id as byUser,post.time,post_text as postText,post.status
 FROM post
          INNER JOIN public.fren_connection fc ON fc.from_user = post.by_user_id
 WHERE fc.to_user = forUser)
    ORDER BY id DESC
    LIMIT howMany;
$$;

