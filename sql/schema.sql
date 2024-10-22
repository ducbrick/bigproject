DROP TABLE IF EXISTS Document;

CREATE TABLE Document (
	document_id SMALLSERIAL PRIMARY KEY,
	document_name VARCHAR(100) NOT NULL CHECK(LENGTH(TRIM(document_name)) > 0),
	document_description BPCHAR
);

DROP TABLE IF EXISTS AppUser;

CREATE TABLE AppUser (
	user_id SMALLSERIAL PRIMARY KEY,
	login_name VARCHAR(32) NOT NULL CHECK(LENGTH(TRIM(login_name)) > 0),
	password VARCHAR(32) NOT NULL CHECK(LENGTH(TRIM(password)) > 0),
	display_name VARCHAR(64) NOT NULL CHECK(LENGTH(TRIM(display_name)) > 0)
);
