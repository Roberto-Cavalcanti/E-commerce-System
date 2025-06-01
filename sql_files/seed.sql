-- Insert shops (3 records)
INSERT INTO shop (name, cnpj, evaluation) VALUES
('Loja Esportiva', '12.345.678/0001-99', 4.50),
('Moda Casual', '98.765.432/0001-88', 4.20),
('Acessórios Premium', '11.222.333/0001-77', 4.80);

-- Insert products (6 records, 2 per shop)
INSERT INTO product (name, description, starting_price, image, category, shop_id) VALUES
-- Shop 1: Loja Esportiva
('Tênis de Corrida', 'Tênis leve para corrida', 20,'https://example.com/tenis.jpg',  'SHOES', 1),
('Camiseta Esportiva', 'Camiseta dry-fit para esportes', 13, 'https://example.com/camiseta.jpg',  'SHIRTS', 1),
-- Shop 2: Moda Casual
('Calça Jeans', 'Calça jeans slim fit', 20, 'https://example.com/jeans.jpg',  'PANTS', 2),
('Relógio Casual', 'Relógio analógico com pulseira de couro', 249.90,'https://example.com/relogio.jpg',  'WATCHES', 2),
-- Shop 3: Acessórios Premium
('Brinco de Prata', 'Brinco de prata com pedra', 20, 'https://example.com/brinco.jpg',  'JEWELLERY', 3),
('Shorts de Academia', 'Shorts confortável para treinos', 23,'https://example.com/shorts.jpg',  'SHORTS', 3);

-- Insert product variants (12 records, 2 per product)
INSERT INTO product_variant (quantity, size, price, product_id) VALUES
-- Product 1: Tênis de Corrida
(50, '40', 199.90,1),
(30, '42', 199.90,1),
-- Product 2: Camiseta Esportiva
(100, 'M', 59.90,2),
(80, 'L', 59.90,2),
-- Product 3: Calça Jeans
(60, '38', 129.90,3),
(40, '40',129.90, 3),
-- Product 4: Relógio Casual
(20, 'Único',249.90, 4),
(15, 'Único',249.90, 4),
-- Product 5: Brinco de Prata
(25, 'Pequeno', 89.90,5),
(20, 'Médio', 89.90, 5),
-- Product 6: Shorts de Academia
(70, 'M', 79.90,6),
(50, 'G', 79.90,6);

-- Insert users (4 records)
INSERT INTO users (name, email, password) VALUES
('Roberto Henri', 'roberto@live.com', '123456789'),
('Test User', 'test@example.com', '124356789');