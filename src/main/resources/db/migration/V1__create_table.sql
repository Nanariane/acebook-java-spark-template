create table posts(
 post_id VARCHAR,
 content VARCHAR
);

--In db acebook,table Posts:
ALTER TABLE posts ADD COLUMN time VARCHAR;




--Create new user table in acebook db:
CREATE TABLE person(user_id VARCHAR, user_name VARCHAR(60), password VARCHAR);

--create sign up table
create table signup(
 user_id VARCHAR,
 first_name VARCHAR,
 last_name VARCHAR,
 user_name VARCHAR,
 email VARCHAR,
 password VARCHAR
);
