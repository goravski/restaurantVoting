delete from user_roles;
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

insert into restaurants (name, votes)
values ('Mack', 0),
       ('Chicken', 0);

insert into meals (name, date, price, restaurant_id)
values('Potatoes','2020-01-30 10:00:00',10.0, 100003),
        ('Soup','2020-01-30 10:00:00',15.0, 100004);
