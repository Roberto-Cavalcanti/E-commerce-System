-- Users
INSERT INTO users (name, email, password) VALUES
('Test User', 'test@example.com', '12345'),
('Roberto Henrique', 'Roberto@live.com', '12345');

-- Shop
INSERT INTO shop (name, cnpj, evaluation) VALUES
('Loja A', '12.345.678/0001-99', 4.5),
('Loja B', '12.345.678/0001-79', 5);

-- Stock or storage
INSERT INTO stock (shop_id) VALUES
(1),
(2);

-- Product
INSERT INTO product (name, description,category, price, shop_id) VALUES
('Camiseta Nike', 'Camiseta de algodão','SHIRTS', 50.00, 1),
('Camiseta Adidas', 'Camiseta de linho', 'SHIRTS', 150.00, 2);

-- Product Variant
INSERT INTO product_variant (size, product_id) VALUES ('M', 1),
('M', 2),
('P', 2),
('PP', 2),
('G', 2),
('GG', 2);

-- Stock Item
INSERT INTO stock_item (variant_id, stock_id, quantity) VALUES
(1, 1, 10),
(3, 2, 10),
(2, 2, 10),
(4, 2, 10),
(5, 2, 10),
(6, 2, 10);


