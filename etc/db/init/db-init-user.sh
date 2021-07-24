#!/bin/bash

set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER elarge WITH ENCRYPTED PASSWORD 'elarge';
    CREATE DATABASE elarge;
    GRANT ALL PRIVILEGES ON DATABASE elarge TO elarge;
EOSQL

psql -U ON_ERROR_STOP=1 --username "elarge" --dbname "elarge" <<EOSQL
 create table users
 (
 	id serial
 		constraint user_pk
 			primary key,
 	username text not null,
 	password text not null,
 	first_name text not null,
 	last_name text not null,
 	email text not null,
  digital_self_link text
 );

create table topic (
  id serial constraint topic_pk primary key,
  title text not null,
  description text not null
);

create table topic_user_subscription (
  id serial constraint topic_user_pk primary key,
  user_id integer constraint user_id_fk references users(id),
  topic_id integer constraint topic_id_fk references topic(id)
);

create table question(
  id serial constraint question_pk primary key,
  title text ,
  content text not null,
  topic_id integer constraint question_topic_id_fk references topic(id)
);

EOSQL
