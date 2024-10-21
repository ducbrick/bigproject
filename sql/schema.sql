DROP TABLE IF EXISTS Document;

CREATE TABLE Document (
	document_id SMALLSERIAL PRIMARY KEY,
	document_name VARCHAR(100) NOT NULL CHECK(LENGTH(TRIM(document_name)) > 0),
	document_description BPCHAR
)