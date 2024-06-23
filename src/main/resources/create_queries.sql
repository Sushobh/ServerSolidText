CREATE TABLE signup_attempt (
    id BIGSERIAL PRIMARY KEY,
    time TIMESTAMP WITH TIME ZONE,
    email TEXT,
    pwd TEXT
);