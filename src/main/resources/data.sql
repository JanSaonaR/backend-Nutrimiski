INSERT INTO category(`category_id`,`name`) VALUES (1,'CEREALSES, TUBERCULOS Y MENESTRAS');
INSERT INTO category(`category_id`,`name`) VALUES (2,'CARNES');
INSERT INTO category(`category_id`,`name`) VALUES (3,'LÁCTEOS Y DERIVADOS');
INSERT INTO category(`category_id`,`name`) VALUES (4,'GRASAS');
INSERT INTO category(`category_id`,`name`) VALUES (5,'FRUTAS');

INSERT INTO ingredient (`ingredient_id`,`name`,`category_id`) VALUES (1,'PAPA',   1);
INSERT INTO ingredient (`ingredient_id`,`name`,`category_id`) VALUES (2,'ARROZ',  1);
INSERT INTO ingredient (`ingredient_id`,`name`,`category_id`) VALUES (3,'FIDEO',  1);
INSERT INTO ingredient (`ingredient_id`,`name`,`category_id`) VALUES (4,'PAN',    1);
INSERT INTO ingredient (`ingredient_id`,`name`,`category_id`) VALUES (5,'CHOCLO', 1);
INSERT INTO ingredient (`ingredient_id`,`name`,`category_id`) VALUES (6,'LENTEJA',1);
INSERT INTO ingredient (`ingredient_id`,`name`,`category_id`) VALUES (7,'FRIJOL', 1);
INSERT INTO ingredient (`ingredient_id`,`name`,`category_id`) VALUES (8,'CAMOTE', 1);
INSERT INTO ingredient (`ingredient_id`,`name`,`category_id`) VALUES (9,'YUCA',   1);
INSERT INTO ingredient (`ingredient_id`,`name`,`category_id`) VALUES (10,'AVENA',  1);
INSERT INTO ingredient (`ingredient_id`,`name`,`category_id`) VALUES (11,'QUINUA', 1);

INSERT INTO ingredient (`ingredient_id`,`name`,`category_id`) VALUES (12,'POLLO',   2);
INSERT INTO ingredient (`ingredient_id`,`name`,`category_id`) VALUES (13,'CARNE',   2);
INSERT INTO ingredient (`ingredient_id`,`name`,`category_id`) VALUES (14,'HUEVO',   2);
INSERT INTO ingredient (`ingredient_id`,`name`,`category_id`) VALUES (15,'PESCADO', 2);
INSERT INTO ingredient (`ingredient_id`,`name`,`category_id`) VALUES (16,'ATÚN',    2);
INSERT INTO ingredient (`ingredient_id`,`name`,`category_id`) VALUES (17,'PAVITA',  2);
INSERT INTO ingredient (`ingredient_id`,`name`,`category_id`) VALUES (18,'CERDO',   2);
INSERT INTO ingredient (`ingredient_id`,`name`,`category_id`) VALUES (19,'JAMÓN',   2);

INSERT INTO ingredient (`ingredient_id`,`name`,`category_id`) VALUES (20,'LECHE',   3);
INSERT INTO ingredient (`ingredient_id`,`name`,`category_id`) VALUES (21,'YOGURT',  3);
INSERT INTO ingredient (`ingredient_id`,`name`,`category_id`) VALUES (22,'QUESO',   3);

INSERT INTO ingredient (`ingredient_id`,`name`,`category_id`) VALUES (23,'PALTA',      4);
INSERT INTO ingredient (`ingredient_id`,`name`,`category_id`) VALUES (24,'ACEITUNA',   4);
INSERT INTO ingredient (`ingredient_id`,`name`,`category_id`) VALUES (25,'MANTEQUILLA',4);
INSERT INTO ingredient (`ingredient_id`,`name`,`category_id`) VALUES (26,'COCO',       4);

INSERT INTO ingredient (`ingredient_id`,`name`,`category_id`) VALUES (27,'NARANJA',    5);
INSERT INTO ingredient (`ingredient_id`,`name`,`category_id`) VALUES (28,'MANDARINA',  5);
INSERT INTO ingredient (`ingredient_id`,`name`,`category_id`) VALUES (29,'MARACUYÁ',   5);
INSERT INTO ingredient (`ingredient_id`,`name`,`category_id`) VALUES (30,'MANZANA',    5);
INSERT INTO ingredient (`ingredient_id`,`name`,`category_id`) VALUES (31,'plátano',    5);
INSERT INTO ingredient (`ingredient_id`,`name`,`category_id`) VALUES (32,'UVA',        5);
INSERT INTO ingredient (`ingredient_id`,`name`,`category_id`) VALUES (33,'COCONA',     5);
INSERT INTO ingredient (`ingredient_id`,`name`,`category_id`) VALUES (34,'MANGO',      5);
INSERT INTO ingredient (`ingredient_id`,`name`,`category_id`) VALUES (35,'PAPAYA',     5);
INSERT INTO ingredient (`ingredient_id`,`name`,`category_id`) VALUES (36,'GRANADILLA', 5);
INSERT INTO ingredient (`ingredient_id`,`name`,`category_id`) VALUES (37,'PIÑA',       5);

SET FOREIGN_KEY_CHECKS=0;