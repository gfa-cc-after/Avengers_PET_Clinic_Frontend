CREATE TABLE IF NOT EXISTS email_verifications
(
    id SERIAL PRIMARY KEY,
    /* UUID is 36 long */
    verification_id VARCHAR (36),
    email VARCHAR (255)
);