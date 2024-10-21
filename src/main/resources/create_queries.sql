create database solidtext
    with owner solidtext_owner;

grant connect, create, temporary on database solidtext to neon_superuser;

create table public.connection_request
(
    time      timestamp with time zone,
    status    text,
    from_user bigint,
    to_user   bigint,
    id        serial
        constraint pkey
            primary key
);

alter table public.connection_request
    owner to solidtext_owner;

create table public.fren_connection
(
    from_user bigint,
    to_user   bigint,
    time      timestamp with time zone
);

alter table public.fren_connection
    owner to solidtext_owner;

create table public.login_attempt
(
    id    bigint not null
        primary key,
    time  timestamp with time zone,
    email text   not null,
    pwd   text   not null
);

alter table public.login_attempt
    owner to solidtext_owner;

create table public.otp
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

alter table public.otp
    owner to solidtext_owner;

create table public.signup_attempt
(
    id     bigserial
        primary key,
    time   timestamp with time zone,
    email  text,
    pwd    text,
    otp_id bigint
        constraint signupattempt_otp
            references public.otp
);

alter table public.signup_attempt
    owner to solidtext_owner;

create table public.password
(
    id            bigint                   not null
        primary key,
    time          timestamp with time zone not null,
    password_text text                     not null
);

alter table public.password
    owner to solidtext_owner;

create table public.picture
(
    id   bigint                   not null
        primary key,
    url  text                     not null,
    time timestamp with time zone not null
);

alter table public.picture
    owner to solidtext_owner;

create table public.post_like
(
    post_id bigint not null,
    user_id bigint not null,
    time    timestamp with time zone,
    constraint post_like_pk
        primary key (post_id, user_id)
);

alter table public.post_like
    owner to solidtext_owner;

create table public.st_user
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
            references public.password,
    status_id   bigint,
    picture_id  bigint,
    user_props  jsonb
);

alter table public.st_user
    owner to solidtext_owner;

create table public.post
(
    id         bigint not null
        constraint post_user
            primary key,
    by_user_id bigint
        constraint by_user_id
            references public.st_user,
    post_text  text   not null,
    status     text   not null,
    time       timestamp with time zone
);

alter table public.post
    owner to solidtext_owner;

create index post_by_user_id_id_index
    on public.post (by_user_id asc, id desc);

create table public.token
(
    id          bigint                   not null
        primary key,
    time        timestamp with time zone not null,
    token_text  text                     not null,
    token_type  text                     not null,
    expires_in  integer                  not null,
    expiry_unit text                     not null
);

alter table public.token
    owner to solidtext_owner;

create table public.user_status
(
    id      bigint                   not null,
    time    timestamp with time zone not null,
    user_id bigint                   not null,
    status  text                     not null,
    primary key (id, user_id)
);

alter table public.user_status
    owner to solidtext_owner;

create table public.user_token_pair
(
    userid  bigint not null,
    tokenid bigint not null,
    primary key (userid, tokenid)
);

alter table public.user_token_pair
    owner to solidtext_owner;

create function public.uuid_nil() returns uuid
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

alter function public.uuid_nil() owner to cloud_admin;

create function public.uuid_ns_dns() returns uuid
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

alter function public.uuid_ns_dns() owner to cloud_admin;

create function public.uuid_ns_url() returns uuid
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

alter function public.uuid_ns_url() owner to cloud_admin;

create function public.uuid_ns_oid() returns uuid
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

alter function public.uuid_ns_oid() owner to cloud_admin;

create function public.uuid_ns_x500() returns uuid
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

alter function public.uuid_ns_x500() owner to cloud_admin;

create function public.uuid_generate_v1() returns uuid
    strict
    parallel safe
    language c
as
$$
begin
-- missing source code
end;
$$;

alter function public.uuid_generate_v1() owner to cloud_admin;

create function public.uuid_generate_v1mc() returns uuid
    strict
    parallel safe
    language c
as
$$
begin
-- missing source code
end;
$$;

alter function public.uuid_generate_v1mc() owner to cloud_admin;

create function public.uuid_generate_v3(namespace uuid, name text) returns uuid
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

alter function public.uuid_generate_v3(uuid, text) owner to cloud_admin;

create function public.uuid_generate_v4() returns uuid
    strict
    parallel safe
    language c
as
$$
begin
-- missing source code
end;
$$;

alter function public.uuid_generate_v4() owner to cloud_admin;

create function public.uuid_generate_v5(namespace uuid, name text) returns uuid
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

alter function public.uuid_generate_v5(uuid, text) owner to cloud_admin;

create function public.getpostfeed4(foruser numeric, howmany integer)
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

alter function public.getpostfeed4(numeric, integer) owner to solidtext_owner;

