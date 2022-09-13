delete from user_roles;
delete from VOTES;
delete from users;
delete from meals;
delete from restaurants;



insert into users (name, email, password)
values ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

insert into user_roles (role, user_id)
values ('USER', 100000),
       ('ADMIN', 100001),
       ('USER', 100001);

insert into restaurants (name)
values ('restaurant1'),
       ('restaurant2');

insert into meals (name, date, price, restaurant_id)
values ('Potatoes', '2020-01-30 10:00:00', 10.0, 100003),
       ('Soup', '2020-01-30 10:00:00', 15.0, 100004);

insert into VOTES (USER_ID, RESTAURANT_ID, DATE_VOTE)
values (100000, 100003, '2020-01-30 10:00:00'),
       (100000, 100003, '2020-01-31 10:00:00');
