--liquibase formatted sql

--changeset d:1
CREATE TABLE IF NOT EXISTS comments(
id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
"time" timestamp with time zone,
text varchar(10000) NOT NULL,
"username" varchar(255) NOT NULL,
news_id UUID);