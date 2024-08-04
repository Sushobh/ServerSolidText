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

alter function public.uuid_nil() owner to postgres;

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

alter function public.uuid_ns_dns() owner to postgres;

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

alter function public.uuid_ns_url() owner to postgres;

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

alter function public.uuid_ns_oid() owner to postgres;

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

alter function public.uuid_ns_x500() owner to postgres;

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

alter function public.uuid_generate_v1() owner to postgres;

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

alter function public.uuid_generate_v1mc() owner to postgres;

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

alter function public.uuid_generate_v3(uuid, text) owner to postgres;

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

alter function public.uuid_generate_v4() owner to postgres;

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

alter function public.uuid_generate_v5(uuid, text) owner to postgres;

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

alter function public.getpostfeed4(numeric, integer) owner to postgres;

