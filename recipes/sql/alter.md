ALTER TABLE public.otp
ALTER COLUMN id SET DEFAULT nextval('public.signup_attempt_id_seq');
