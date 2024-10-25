

#### Add sequence

ALTER TABLE public.otp
ALTER COLUMN id SET DEFAULT nextval('public.signup_attempt_id_seq');

#### Add Foreign key

ALTER TABLE st_user
ADD CONSTRAINT stuser_password FOREIGN KEY (password_id) REFERENCES password (id);


#### UUID default value
ALTER TABLE otp
ALTER COLUMN string_id
SET DEFAULT uuid_generate_v4();