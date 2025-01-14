--liquibase formatted sql

--changeset d:1
CREATE TABLE IF NOT EXISTS news(
id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
"time" timestamp with time zone,
title varchar(255) NOT NULL,
text varchar(10000) NOT NULL);