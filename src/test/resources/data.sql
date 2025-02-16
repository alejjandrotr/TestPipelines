-- H2 does not support SET FOREIGN_KEY_CHECKS
-- TRUNCATE is supported, but be careful about potential FK issues (see comments below)

-- H2 does not support TRUNCATE with FK constraints enabled easily.  You'd need to disable constraints, truncate, then re-enable.  For testing purposes with H2, it's often better to use DELETE FROM <table>;

-- If you still wanted truncate commands, they are listed below, but you'd need to manage disabling/enabling FK checks yourself, which is more complex.

-- TRUNCATE TABLE store_inventory;
-- TRUNCATE TABLE warehouse_inventory;
-- TRUNCATE TABLE product;
-- TRUNCATE TABLE store_delivery_time;
-- TRUNCATE TABLE warehouse_delivery_time;
-- TRUNCATE TABLE store;
-- TRUNCATE TABLE warehouse;
-- TRUNCATE TABLE city;


-- Instead, using DELETE FROM which is generally simpler for H2 in-memory testing:
DELETE FROM store_inventory;
DELETE FROM warehouse_inventory;
DELETE FROM product;
DELETE FROM store_delivery_time;
DELETE FROM warehouse_delivery_time;
DELETE FROM store;
DELETE FROM warehouse;
DELETE FROM city;


-- Insert Cities
INSERT INTO city (city_code, city_name) VALUES
('CDMX', 'Ciudad de México'),
('GDL', 'Guadalajara'),
('MTY', 'Monterrey'),
('PUE', 'Puebla'),
('LEO', 'León'),
('QRO', 'Querétaro'),
('AGS', 'Aguascalientes'),
('SLP', 'San Luis Potosí'),
('TIJ', 'Tijuana'),
('CUN', 'Cancún');

-- Insert Warehouses
INSERT INTO warehouse (city_id, warehouse_type, warehouse_name) VALUES
(1, 'PRINCIPAL', 'Almacén Principal CDMX'),
(2, 'PRINCIPAL', 'Almacén Principal GDL'),
(3, 'PRINCIPAL', 'Almacén Principal MTY'),
(1, 'SECONDARY', 'Almacén Secundario CDMX Norte'),
(1, 'SECONDARY', 'Almacén Secundario CDMX Sur'),
(1, 'SECONDARY', 'Almacén Secundario CDMX Oriente'),
(2, 'SECONDARY', 'Almacén Secundario GDL Centro'),
(2, 'SECONDARY', 'Almacén Secundario GDL Sur'),
(3, 'SECONDARY', 'Almacén Secundario MTY Este'),
(3, 'SECONDARY', 'Almacén Secundario MTY Poniente'),
(4, 'SECONDARY', 'Almacén Secundario PUE'),
(5, 'SECONDARY', 'Almacén Secundario LEO'),
(6, 'SECONDARY', 'Almacén Secundario QRO'),
(7, 'SECONDARY', 'Almacén Secundario AGS'),
(8, 'SECONDARY', 'Almacén Secundario SLP'),
(9, 'SECONDARY', 'Almacén Secundario TIJ'),
(10, 'SECONDARY', 'Almacén Secundario CUN');

-- Insert Stores
INSERT INTO store (city_id, store_name) VALUES
(1, 'Tienda Centro CDMX'),
(1, 'Tienda Sur CDMX'),
(1, 'Tienda Norte CDMX'),
(2, 'Tienda Centro GDL'),
(2, 'Tienda Zapopan GDL'),
(3, 'Tienda Este MTY'),
(3, 'Tienda Centro MTY'),
(4, 'Tienda Principal PUE'),
(5, 'Tienda Principal LEO'),
(6, 'Tienda Principal QRO'),
(7, 'Tienda Principal AGS'),
(8, 'Tienda Principal SLP'),
(9, 'Tienda Principal TIJ'),
(10, 'Tienda Principal CUN');

-- Insert Warehouse Delivery Times (Warehouse to Warehouse) -  Expanded
INSERT INTO warehouse_delivery_time (warehouse_origin_id, warehouse_destination_id, delivery_time_hours) VALUES
(1, 4, 4),  -- CDMX Principal -> CDMX Norte
(1, 5, 6),  -- CDMX Principal -> CDMX Sur
(1, 6, 5),  -- CDMX Principal -> CDMX Oriente
(2, 7, 3),  -- GDL Principal -> GDL Centro
(2, 8, 4),  -- GDL Principal -> GDL Sur
(3, 9, 2),  -- MTY Principal -> MTY Este
(3, 10, 3), -- MTY Principal -> MTY Poniente
(1, 2, 12), -- CDMX Principal -> GDL Principal
(1, 3, 15), -- CDMX Principal -> MTY Principal
(2, 1, 12), -- GDL Principal -> CDMX Principal
(2, 3, 8),  -- GDL Principal -> MTY Principal
(3, 1, 15), -- MTY Principal -> CDMX Principal
(3, 2, 8),  -- MTY Principal -> GDL Principal
(4, 1, 5),  -- CDMX Norte -> CDMX Principal
(4, 2, 16), -- CDMX Norte -> GDL Principal
(4, 3, 20), -- CDMX Norte -> MTY Principal
(5, 1, 7),  -- CDMX Sur -> CDMX Principal
(6, 1, 6),  -- CDMX Oriente -> CDMX Principal
(7, 2, 4),  -- GDL Centro -> GDL Principal
(8, 2, 5),  -- GDL Sur -> GDL Principal
(9, 3, 3),  -- MTY Este -> MTY Principal
(10, 3, 4), -- MTY Poniente -> MTY Principal
(1, 11, 20), -- CDMX Principal -> PUE
(11, 1, 20), -- PUE -> CDMX Principal
(1, 12, 25), -- CDMX Principal -> LEO
(12, 1, 25), -- LEO -> CDMX Principal
(2, 13, 18), -- GDL Principal -> QRO
(13, 2, 18), -- QRO -> GDL Principal
(2, 14, 22), -- GDL Principal -> AGS
(14, 2, 22), -- AGS -> GDL Principal
(3, 15, 28), -- MTY Principal -> SLP
(15, 3, 28), -- SLP -> MTY Principal
(3, 16, 35), -- MTY Principal -> TIJ
(16, 3, 35), -- TIJ -> MTY Principal
(3, 17, 40), -- MTY Principal -> CUN
(17, 3, 40);

-- Insert Store Delivery Times (Warehouse to Store) - Expanded
INSERT INTO store_delivery_time (warehouse_origin_id, store_destination_id, delivery_time_hours) VALUES
(4, 1, 2),  -- CDMX Norte -> Tienda Centro CDMX
(4, 2, 3),  -- CDMX Norte -> Tienda Sur CDMX
(6, 3, 1),  -- GDL Centro -> Tienda Centro GDL
(7, 4, 2),  -- MTY Este -> Tienda Este MTY
(8, 5, 4),  -- PUE -> Tienda Principal PUE
(5, 1, 3), -- CDMX Sur -> Tienda Centro CDMX
(6, 4, 2), -- GDL Centro -> Tienda Principal GDL
(9, 7, 3), -- MTY Este -> Tienda Centro MTY
(11, 8, 1), -- PUE -> Tienda Principal PUE
(12, 9, 4), -- LEO -> Tienda Principal LEO
(13, 10, 2), -- QRO -> Tienda Principal QRO
(14, 11, 3), -- AGS -> Tienda Principal AGS
(15, 12, 4), -- SLP -> Tienda Principal SLP
(16, 13, 5), -- TIJ -> Tienda Principal TIJ
(17, 14, 6); -- CUN -> Tienda Principal CUN

-- Insert Products - Expanded
INSERT INTO product (product_code, product_name) VALUES
('PROD001', 'Laptop'),
('PROD002', 'Smartphone'),
('PROD003', 'Tablet'),
('PROD004', 'Headphones'),
('PROD005', 'Smartwatch'),
('PROD006', 'Keyboard'),
('PROD007', 'Mouse'),
('PROD008', 'Monitor'),
('PROD009', 'Printer'),
('PROD010', 'Webcam');

-- Insert Warehouse Inventory - Expanded
INSERT INTO warehouse_inventory (product_code, warehouse_id, quantity) VALUES
('PROD001', 1, 50),
('PROD001', 4, 20),
('PROD002', 1, 10),
('PROD002', 2, 100),
('PROD003', 3, 30),
('PROD003', 1, 30),
('PROD004', 1, 75),
('PROD005', 1, 40),
('PROD006', 2, 60),
('PROD007', 3, 25),
('PROD008', 1, 35),
('PROD009', 2, 45),
('PROD010', 3, 55),
('PROD001', 5, 15),
('PROD002', 6, 80),
('PROD003', 7, 20),
('PROD004', 8, 60),
('PROD005', 9, 30),
('PROD006', 10, 50),
('PROD007', 4, 10),
('PROD008', 5, 25),
('PROD009', 6, 35),
('PROD010', 7, 40);

-- Insert Store Inventory - Expanded
INSERT INTO store_inventory (product_code, store_id, quantity) VALUES
('PROD001', 1, 10),
('PROD002', 2, 25),
('PROD003', 3, 5),
('PROD004', 5, 15),
('PROD005', 8, 20),
('PROD006', 9, 30),
('PROD007', 10, 12),
('PROD008', 11, 18),
('PROD009', 12, 22),
('PROD010', 13, 28),
('PROD001', 2, 12),
('PROD002', 3, 28),
('PROD003', 4, 8),
('PROD004', 6, 18),
('PROD005', 9, 25),
('PROD006', 10, 35),
('PROD007', 11, 15),
('PROD008', 12, 20),
('PROD009', 13, 25),
('PROD010', 14, 30);
