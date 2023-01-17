INSERT INTO member (username, password, name, phone) VALUES ('reservation_exist_user', 'password', 'name', '010-1234-5678');
INSERT INTO member (username, password, name, phone) VALUES ('no_reservation_exist_user', 'password', 'name', '010-1234-5678');

INSERT INTO theme (name, desc, price) VALUES ('theme1', 'theme1', 1000);
INSERT INTO theme (name, desc, price) VALUES ('theme2', 'theme2', 2000);

INSERT INTO schedule (theme_id, date, time) VALUES (1, '2022-11-11', '12:00');
INSERT INTO schedule (theme_id, date, time) VALUES (1, '2022-11-11', '12:30');
INSERT INTO schedule (theme_id, date, time) VALUES (1, '2022-11-11', '13:00');

INSERT INTO reservation (schedule_id, name) VALUES (1, 'reservation_exist_user');
INSERT INTO reservation (schedule_id, name) VALUES (2, 'reservation_exist_user');