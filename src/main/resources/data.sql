INSERT INTO member(username, password, name, phone, admin) VALUES ('admin', 'P@ssw0rd', 'admin', '010-1234-5678', true);
INSERT INTO member(username, password, name, phone, admin) VALUES ('username', 'password', 'name', '010-0000-0000', false);
INSERT INTO theme(name, desc, price) VALUES ('테스트 테마', '테스트 테마 설명', 1000);
INSERT INTO schedule(theme_id, date, time) VALUES (1, '2022-08-11', '13:00');
INSERT INTO schedule(theme_id, date, time) VALUES (1, '2022-08-11', '14:00');
INSERT INTO reservation(schedule_id, member_id) VALUES (1, 2);
INSERT INTO reservation(schedule_id, member_id) VALUES (1, 1);
