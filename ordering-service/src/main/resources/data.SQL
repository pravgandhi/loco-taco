DROP TABLE IF EXISTS MENU;
 
CREATE TABLE MENU (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  NAME VARCHAR(250) NOT NULL,
  PRICE DOUBLE
);
 
INSERT INTO MENU (NAME,PRICE) VALUES
  ('Veggie Taco', 2.50 ),
  ('Chicken Taco', 3.00),
  ('Beef Taco', 3.00),
  ('Chorizo Taco', 3.50);