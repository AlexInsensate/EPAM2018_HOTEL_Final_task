-- 
-- ��������� ���������, � �������������� ������� ������ ����� �������� ������� �� ������
--
SET NAMES 'utf8';

--
-- ��������� ���� ������ �� ���������
--
USE hoteldb;

--
-- ������� ������� `orders_menu`
--
DROP TABLE IF EXISTS orders_menu;

--
-- ������� ������� `order_statuses`
--
DROP TABLE IF EXISTS order_statuses;

--
-- ������� ������� `orders_selection`
--
DROP TABLE IF EXISTS orders_selection;

--
-- ������� ������� `apartments`
--
DROP TABLE IF EXISTS apartments;

--
-- ������� ������� `categories`
--
DROP TABLE IF EXISTS categories;

--
-- ������� ������� `seats`
--
DROP TABLE IF EXISTS seats;

--
-- ������� ������� `users`
--
DROP TABLE IF EXISTS users;

--
-- ������� ������� `roles`
--
DROP TABLE IF EXISTS roles;

--
-- ������� ������� `statuses`
--
DROP TABLE IF EXISTS statuses;

--
-- ������� ������� `statuses`
--
CREATE TABLE statuses (
  id int(11) NOT NULL AUTO_INCREMENT,
  statuses_name varchar(50) NOT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB,
AUTO_INCREMENT = 1,
AVG_ROW_LENGTH = 8192,
CHARACTER SET utf8,
COLLATE utf8_general_ci;

--
-- ������� ������ `name` ��� ������� ���� ������� `statuses`
--
ALTER TABLE statuses
ADD UNIQUE INDEX statuses_name (statuses_name);

--
-- ������� ������� `roles`
--
CREATE TABLE roles (
  id int(11) NOT NULL AUTO_INCREMENT,
  roles varchar(255) NOT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB,
AUTO_INCREMENT = 1,
AVG_ROW_LENGTH = 8192,
CHARACTER SET utf8,
COLLATE utf8_general_ci;

--
-- ������� ������ `roles` ��� ������� ���� ������� `roles`
--
ALTER TABLE roles
ADD UNIQUE INDEX roles (roles);

--
-- ������� ������� `users`
--
CREATE TABLE users (
  id int(11) NOT NULL AUTO_INCREMENT,
  login varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  first_name varchar(255) NOT NULL,
  last_name varchar(255) NOT NULL,
  role_id int(11) NOT NULL DEFAULT 2,
  PRIMARY KEY (id)
)
ENGINE = INNODB,
AUTO_INCREMENT = 1,
AVG_ROW_LENGTH = 5461,
CHARACTER SET utf8,
COLLATE utf8_general_ci;

--
-- ������� ������ `login` ��� ������� ���� ������� `users`
--
ALTER TABLE users
ADD UNIQUE INDEX login (login);

--
-- ������� ������� ����
--
ALTER TABLE users
ADD CONSTRAINT roles_id FOREIGN KEY (role_id)
REFERENCES roles (id) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- ������� ������� `seats`
--
CREATE TABLE seats (
  id int(11) NOT NULL AUTO_INCREMENT,
  number int(11) NOT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB,
AUTO_INCREMENT = 1,
AVG_ROW_LENGTH = 4096,
CHARACTER SET utf8,
COLLATE utf8_general_ci;

--
-- ������� ������ `number` ��� ������� ���� ������� `seats`
--
ALTER TABLE seats
ADD UNIQUE INDEX number (number);

--
-- ������� ������� `categories`
--
CREATE TABLE categories (
  id int(11) NOT NULL AUTO_INCREMENT,
  category_name varchar(50) NOT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB,
AUTO_INCREMENT = 1,
AVG_ROW_LENGTH = 4096,
CHARACTER SET utf8,
COLLATE utf8_general_ci;

--
-- ������� ������ `name` ��� ������� ���� ������� `categories`
--
ALTER TABLE categories
ADD UNIQUE INDEX category_name (category_name);

--
-- ������� ������� `apartments`
--
CREATE TABLE apartments (
  id int(11) NOT NULL AUTO_INCREMENT,
  title varchar(50) NOT NULL,
  seat_id int(11) NOT NULL,
  category_id int(11) NOT NULL,
  bill int(11) NOT NULL,
  status_id int(11) NOT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB,
AUTO_INCREMENT = 1,
AVG_ROW_LENGTH = 4096,
CHARACTER SET utf8,
COLLATE utf8_general_ci;

--
-- ������� ������ `title` ��� ������� ���� ������� `apartments`
--
ALTER TABLE apartments
ADD UNIQUE INDEX title (title);

--
-- ������� ������� ����
--
ALTER TABLE apartments
ADD CONSTRAINT FK_apartments_categories_id FOREIGN KEY (category_id)
REFERENCES categories (id) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- ������� ������� ����
--
ALTER TABLE apartments
ADD CONSTRAINT FK_apartments_seats_id FOREIGN KEY (seat_id)
REFERENCES seats (id) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- ������� ������� ����
--
ALTER TABLE apartments
ADD CONSTRAINT FK_apartments_statuses_id FOREIGN KEY (status_id)
REFERENCES statuses (id) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- ������� ������� `orders_selection`
--
CREATE TABLE orders_selection (
  id int(11) NOT NULL AUTO_INCREMENT,
  user_id int(11) NOT NULL,
  seat_id int(11) NOT NULL,
  category_id int(11) NOT NULL,
  arrival date NOT NULL,
  departure date NOT NULL,
  apartment_id int(11) DEFAULT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB,
AUTO_INCREMENT = 1,
AVG_ROW_LENGTH = 16384,
CHARACTER SET utf8,
COLLATE utf8_general_ci;

--
-- ������� ������� ����
--
ALTER TABLE orders_selection
ADD CONSTRAINT FK_orders_selection_apartments_id FOREIGN KEY (apartment_id)
REFERENCES apartments (id) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- ������� ������� ����
--
ALTER TABLE orders_selection
ADD CONSTRAINT FK_orders_selection_categories_id FOREIGN KEY (category_id)
REFERENCES categories (id) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- ������� ������� ����
--
ALTER TABLE orders_selection
ADD CONSTRAINT FK_orders_selection_seats_id FOREIGN KEY (seat_id)
REFERENCES seats (id) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- ������� ������� ����
--
ALTER TABLE orders_selection
ADD CONSTRAINT FK_orders_selection_users_id FOREIGN KEY (user_id)
REFERENCES users (id) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- ������� ������� `order_statuses`
--
CREATE TABLE order_statuses (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(50) NOT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB,
AUTO_INCREMENT = 1,
AVG_ROW_LENGTH = 4096,
CHARACTER SET utf8,
COLLATE utf8_general_ci;

--
-- ������� ������ `name` ��� ������� ���� ������� `order_statuses`
--
ALTER TABLE order_statuses
ADD UNIQUE INDEX name (name);

--
-- ������� ������� `orders_menu`
--
CREATE TABLE orders_menu (
  id int(11) NOT NULL AUTO_INCREMENT,
  user_id int(11) NOT NULL,
  order_id int(11) NOT NULL,
  apartment_id int(11) DEFAULT NULL,
  bill int(11) NOT NULL,
  status_order int(11) NOT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB,
AUTO_INCREMENT = 1,
CHARACTER SET utf8,
COLLATE utf8_general_ci;


--
-- ������� ������� ����
--
ALTER TABLE orders_menu
ADD CONSTRAINT FK_orders_menu_apartments_id FOREIGN KEY (apartment_id)
REFERENCES apartments (id) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- ������� ������� ����
--
ALTER TABLE orders_menu
ADD CONSTRAINT FK_orders_menu_order_statuses_id FOREIGN KEY (status_order)
REFERENCES order_statuses (id) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- ������� ������� ����
--
ALTER TABLE orders_menu
ADD CONSTRAINT FK_orders_menu_orders_selection_id FOREIGN KEY (order_id)
REFERENCES orders_selection (id) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- ������� ������� ����
--
ALTER TABLE orders_menu
ADD CONSTRAINT FK_orders_menu_users_id FOREIGN KEY (user_id)
REFERENCES users (id) ON DELETE NO ACTION ON UPDATE NO ACTION;


INSERT INTO roles VALUES(1, 'admin');
INSERT INTO roles VALUES(2, 'client');

INSERT INTO users VALUES(DEFAULT, 'admin', 'admin', 'Ivan', 'Ivanov', 1);
INSERT INTO users VALUES(DEFAULT, 'client', 'client', 'Petr', 'Petrov', 2);
INSERT INTO users VALUES(DEFAULT, 'test', 'test', 'Hgryhogyi', 'Hgryhogyi', 2);
INSERT INTO users VALUES(DEFAULT, 'testt', 'testt', 'Gena', 'Gena', 2);
INSERT INTO users VALUES(DEFAULT, 'testtt', 'testtt', 'Lara', 'Lara', 2);
INSERT INTO users VALUES(DEFAULT, 'testttt', 'testttt', 'Valia', 'Valia', 2);

INSERT INTO categories VALUES(1, 'econom');
INSERT INTO categories VALUES(2, 'standard');
INSERT INTO categories VALUES(3, 'luxe');
INSERT INTO categories VALUES(4, 'deluxe');

INSERT INTO statuses VALUES(1, 'free'); 
INSERT INTO statuses VALUES(2, 'reserved'); 

INSERT INTO order_statuses VALUES(1, 'handling');
INSERT INTO order_statuses VALUES(2, 'paid');
INSERT INTO order_statuses VALUES(3, 'payment_expectation');
INSERT INTO order_statuses VALUES(4, '�an�eled');

INSERT INTO seats VALUES(1, 1);
INSERT INTO seats VALUES(2, 2);
INSERT INTO seats VALUES(3, 3);
INSERT INTO seats VALUES(4, 4);


INSERT INTO apartments VALUES(DEFAULT, 'apartment1', 4, 4, 1000, 1); 
INSERT INTO apartments VALUES(DEFAULT, 'apartment2', 3, 3, 800, 2);
INSERT INTO apartments VALUES(DEFAULT, 'apartment3', 2, 2, 500, 2);
INSERT INTO apartments VALUES(DEFAULT, 'apartment4', 2, 3, 700, 1);
INSERT INTO apartments VALUES(DEFAULT, 'apartment5', 1, 1, 300, 2);
INSERT INTO apartments VALUES(DEFAULT, 'apartment6', 1, 1, 300, 2);

INSERT INTO orders_selection VALUES(DEFAULT,2,2,2,'2018-06-13', '2018-06-14',DEFAULT);
INSERT INTO orders_selection VALUES(DEFAULT,3,3,3,'2018-06-12', '2018-06-11',DEFAULT);


INSERT INTO orders_menu VALUES(DEFAULT, 2, 1, 2, 2000, 1);
INSERT INTO orders_menu VALUES(DEFAULT, 3, 2, 1, 1500, 1);
INSERT INTO orders_menu VALUES(DEFAULT, 4, 1, 3, 300, 2);
INSERT INTO orders_menu VALUES(DEFAULT, 5, 2, 4, 5000, 4);
INSERT INTO orders_menu VALUES(DEFAULT, 3, 2, 5, 700, 1);
INSERT INTO orders_menu VALUES(DEFAULT, 2, 1, 6, 1000, 3);



SELECT * FROM categories;
SELECT * FROM seats;
SELECT * FROM statuses;
SELECT * FROM apartments;
SELECT * FROM orders_selection;
SELECT * FROM orders_menu;
SELECT * FROM order_statuses;
SELECT * FROM roles;
SELECT * FROM users;


