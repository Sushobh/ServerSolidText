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
    picture_id  bigint
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

create table fren_connection
(
    from_user bigint,
    to_user   bigint,
    time      timestamp with time zone
);


