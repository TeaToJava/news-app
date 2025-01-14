--liquibase formatted sql

--changeset d:1
INSERT INTO comments
    (id, "time", text, username, news_id)
VALUES ('41da72df-0aa3-4dd9-89dc-f8d4c8bf6642', '2014-10-29 10:23:54+02',
        'Text 1', 'User 1', '825974bb-4ad1-4639-9bc2-abbdd9aa6ca8'),
       ('0f57f535-e821-4cb4-b794-a8f0b412bbc6', '2014-10-29 14:23:54+02',
        'Text 2', 'User 2', 'fa4ff446-969f-4be7-8d85-82f3874cbf02'),
       ('a080de54-ea7d-42dd-9e79-2996e7b3ffbc', '2024-10-29 15:23:54+02',
        'Text 3,', 'User 3','3e6a4c5a-9d93-4679-a41e-25fa16168fd6'),
       ('8659f492-6ff7-488e-9a30-912aaf9867ac', '2022-01-19 10:23:54+02',
        'Text 4', 'User 4','e63aa487-0fd6-4a08-9d6e-221417dcf874'),
       ('75b61e47-1bd9-4f93-ab98-a05ad12b6e5e', '2023-11-19 10:23:54+02',
        'Text 5', 'User 5', 'b42f27ae-0bdb-4f41-b7d0-059a9b30513b');