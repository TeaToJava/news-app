--liquibase formatted sql

--changeset d:1
INSERT INTO news
    (id, "time", title, text)
VALUES ('825974bb-4ad1-4639-9bc2-abbdd9aa6ca8', '2014-10-19 10:23:54+02', 'Lloyds Office',
        'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. .'),
       ('fa4ff446-969f-4be7-8d85-82f3874cbf02', '2014-10-19 14:23:54+02', 'Lloyds Office',
        'Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. '),
       ('3e6a4c5a-9d93-4679-a41e-25fa16168fd6', '2024-10-19 15:23:54+02', 'London Office',
        'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa.'),
       ('e63aa487-0fd6-4a08-9d6e-221417dcf874', '2022-01-09 10:23:54+02', 'Bristol Office',
        'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa.'),
       ('b42f27ae-0bdb-4f41-b7d0-059a9b30513b', '2023-11-09 10:23:54+02', 'Lloyds Office',
        'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa.');