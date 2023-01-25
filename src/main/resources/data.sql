INSERT INTO ADMIN (username, password, name, phone) values ('admin1', 'admin1', 'john', '010-1234-5678');

INSERT INTO ROLE (name) values ('ADMIN');
INSERT INTO ROLE (name) values ('USER');

INSERT INTO MEMBER (username, password, name, phone, role_id) values ('admin2', 'admin2', 'kim', '010-8765-4321', 1);
