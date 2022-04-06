INSERT INTO region(`name`) VALUES ('AMAZONAS');
INSERT INTO region(`name`) VALUES ('ANCASH');
INSERT INTO region(`name`) VALUES ('APURIMAC');
INSERT INTO region(`name`) VALUES ('AREQUIPA');
INSERT INTO region(`name`) VALUES ('AYACUCHO');
INSERT INTO region(`name`) VALUES ('CAJAMARCA');
INSERT INTO region(`name`) VALUES ('CALLAO');
INSERT INTO region(`name`) VALUES ('CUSCO');
INSERT INTO region(`name`) VALUES ('HUANCAVELICA');
INSERT INTO region(`name`) VALUES ('HUANUCO');
INSERT INTO region(`name`) VALUES ('ICA');
INSERT INTO region(`name`) VALUES ('JUNIN');
INSERT INTO region(`name`) VALUES ('LA LIBERTAD');
INSERT INTO region(`name`) VALUES ('LAMBAYEQUE');
INSERT INTO region(`name`) VALUES ('LIMA');
INSERT INTO region(`name`) VALUES ('LORETO');
INSERT INTO region(`name`) VALUES ('MADRE DE DIOS');
INSERT INTO region(`name`) VALUES ('MOQUEGUA');
INSERT INTO region(`name`) VALUES ('PASCO');
INSERT INTO region(`name`) VALUES ('PUNO');
INSERT INTO region(`name`) VALUES ('SAN MARTIN');
INSERT INTO region(`name`) VALUES ('TACNA');
INSERT INTO region(`name`) VALUES ('TUMBES');
INSERT INTO region(`name`) VALUES ('UCAYALI');





INSERT INTO category(`name`) VALUES ('CARBOHIDRATOS');
INSERT INTO category(`name`) VALUES ('PROTEINAS');
INSERT INTO category(`name`) VALUES ('GRASAS');
INSERT INTO category(`name`) VALUES ('FRUTAS');

INSERT INTO ingredient (`name`,`category_id`) VALUES ('papa',   1);
INSERT INTO ingredient (`name`,`category_id`) VALUES ('arroz',  1);
INSERT INTO ingredient (`name`,`category_id`) VALUES ('fideo',  1);
INSERT INTO ingredient (`name`,`category_id`) VALUES ('choclo', 1);
INSERT INTO ingredient (`name`,`category_id`) VALUES ('lenteja',1);
INSERT INTO ingredient (`name`,`category_id`) VALUES ('frijol', 1);
INSERT INTO ingredient (`name`,`category_id`) VALUES ('camote', 1);
INSERT INTO ingredient (`name`,`category_id`) VALUES ('yuca',   1);
INSERT INTO ingredient (`name`,`category_id`) VALUES ('avena',  1);
INSERT INTO ingredient (`name`,`category_id`) VALUES ('quinua', 1);

INSERT INTO ingredient (`name`,`category_id`) VALUES ('pollo',   2);
INSERT INTO ingredient (`name`,`category_id`) VALUES ('carne',   2);
INSERT INTO ingredient (`name`,`category_id`) VALUES ('huevo',   2);
INSERT INTO ingredient (`name`,`category_id`) VALUES ('pescado', 2);
INSERT INTO ingredient (`name`,`category_id`) VALUES ('atún',    2);
INSERT INTO ingredient (`name`,`category_id`) VALUES ('pavita',  2);
INSERT INTO ingredient (`name`,`category_id`) VALUES ('cerdo',   2);
INSERT INTO ingredient (`name`,`category_id`) VALUES ('jamón',   2);
INSERT INTO ingredient (`name`,`category_id`) VALUES ('leche',   2);
INSERT INTO ingredient (`name`,`category_id`) VALUES ('yogurt',  2);

INSERT INTO ingredient (`name`,`category_id`) VALUES ('palta',      3);
INSERT INTO ingredient (`name`,`category_id`) VALUES ('aceituna',   3);
INSERT INTO ingredient (`name`,`category_id`) VALUES ('leche',      3);
INSERT INTO ingredient (`name`,`category_id`) VALUES ('mantequilla',3);
INSERT INTO ingredient (`name`,`category_id`) VALUES ('queso',      3);
INSERT INTO ingredient (`name`,`category_id`) VALUES ('coco',       3);


INSERT INTO ingredient (`name`,`category_id`) VALUES ('naranja',    4);
INSERT INTO ingredient (`name`,`category_id`) VALUES ('mandarina',  4);
INSERT INTO ingredient (`name`,`category_id`) VALUES ('maracuyá',   4);
INSERT INTO ingredient (`name`,`category_id`) VALUES ('manzana',    4);
INSERT INTO ingredient (`name`,`category_id`) VALUES ('plátano',    4);
INSERT INTO ingredient (`name`,`category_id`) VALUES ('uva',        4);
INSERT INTO ingredient (`name`,`category_id`) VALUES ('cocona',     4);
INSERT INTO ingredient (`name`,`category_id`) VALUES ('mango',      4);
INSERT INTO ingredient (`name`,`category_id`) VALUES ('papaya',     4);
INSERT INTO ingredient (`name`,`category_id`) VALUES ('granadilla', 4);
INSERT INTO ingredient (`name`,`category_id`) VALUES ('piña',       4);

INSERT INTO personal_treatment(`active`, `end_date`, `start_date`, `doctor_id`, `patient_id`)
VALUES (1,now(),now(), 4,4);



INSERT INTO nutritional_plan (`meal_date`,`personal_treatments_id`,`is_active`)
VALUES (now(),4,0);


SET FOREIGN_KEY_CHECKS=0;