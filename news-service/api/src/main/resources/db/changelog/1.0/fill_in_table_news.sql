--liquibase formatted sql

--changeset d:1
INSERT INTO news
    (id, "time", title, text)
VALUES ('825974bb-4ad1-4639-9bc2-abbdd9aa6ca8', '2014-10-19 10:23:54+02', 'Lloyds Office',
        'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. '),
       ('fa4ff446-969f-4be7-8d85-82f3874cbf02', '2014-10-19 14:23:54+02', 'Lloyds Office',
        'Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. '),
       ('3e6a4c5a-9d93-4679-a41e-25fa16168fd6', '2024-10-19 15:23:54+02', 'London Office',
        'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa.'),
       ('e63aa487-0fd6-4a08-9d6e-221417dcf874', '2022-01-09 10:23:54+02', 'Bristol Office',
        'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa.'),
       ('b42f27ae-0bdb-4f41-b7d0-059a9b30513b', '2023-11-09 10:23:54+02', 'Lloyds Office',
        'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa.'),
		('cb555fbf-a44f-495b-ae21-fbdc777b42bc', '2014-10-19 10:23:54+02', 'News 10',
        'Text 10 '),
       ('451f5bac-bc0d-489e-b1f1-02ed67b1cd98', '2014-10-19 14:23:54+02', 'News 11',
        'Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.'),
       ('6c816151-8f0e-4802-99d0-2ec53936dc26', '2024-10-19 15:23:54+02', 'News 12',
        'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa.'),
       ('42e28bf2-c68a-49cb-a584-f5aad1610bf5', '2022-02-19 10:23:54+02', 'News 13',
        'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa.'),
       ('e622e9d1-bfe3-43f3-901d-6abff8da1fda', '2023-10-09 10:23:54+02', 'News 14',
        'Text 14'),
       ('1d7a0843-1b72-47db-b7a3-d4367c7005ae', '2023-10-09 10:23:54+02', 'News 15',
        'Text 15'),
       ('7e516f27-cb6a-482c-9ab5-618c2e80119b', '2023-10-09 10:23:54+02', 'News 16',
         'Text 16'),
       ('39ddc552-2516-49c9-9c96-fe341db8ccf0', '2023-10-09 10:23:54+02', 'News 17',
         'Text 17');