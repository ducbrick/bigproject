DROP TABLE IF EXISTS Document;

CREATE TABLE Document (
	document_id SMALLSERIAL PRIMARY KEY,
	document_name VARCHAR(100) NOT NULL CHECK(LENGTH(TRIM(document_name)) > 0),
	document_description BPCHAR
);

DROP TABLE IF EXISTS AppUser;

CREATE TABLE AppUser (
	user_id SMALLSERIAL PRIMARY KEY,
	user_name VARCHAR(32) NOT NULL CHECK(LENGTH(TRIM(user_name)) > 0),
	user_password VARCHAR(32) NOT NULL CHECK(LENGTH(TRIM(user_password)) > 0)
);

