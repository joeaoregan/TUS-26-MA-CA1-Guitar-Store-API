-- INSERT BRANDS
INSERT INTO brand (name, country, created_at, created_by) VALUES 
('Dean', 'USA', NOW(), 'DB_INIT'),
('Encore', 'UK', NOW(), 'DB_INIT'),
('ESP', 'Japan', NOW(), 'DB_INIT'),
('Fender', 'USA', NOW(), 'DB_INIT'),
('Ibanez', 'Japan', NOW(), 'DB_INIT'),
('Music Man', 'USA', NOW(), 'DB_INIT'),
('Sire', 'South Korea', NOW(), 'DB_INIT'),
('Washburn', 'USA', NOW(), 'DB_INIT');

-- INSERT GUITARS
INSERT INTO guitar (model_name, price, manufacture_date, brand_id, created_at, created_by) VALUES 
('Player Telecaster', 569.00, '2022-01-15', 4, NOW(), 'DB_INIT'),
('Player Plus Stratocaster', 999.00, '2023-05-20', 4, NOW(), 'DB_INIT'),
('Deluxe Jazz Bass', 1799.00, '2021-11-10', 4, NOW(), 'DB_INIT'),
('Edge 4 String Bass', 399.00, '2020-03-12', 1, NOW(), 'DB_INIT'),
('Dime-O-Flame ML', 699.00, '2019-08-25', 1, NOW(), 'DB_INIT'),
('Razorback Two Tone', 1199.00, '2021-06-30', 1, NOW(), 'DB_INIT'),
('Stealth Dime Slime', 1199.99, '2022-04-14', 1, NOW(), 'DB_INIT'),
('Edge 8 String Bass', 579.00, '2023-02-01', 1, NOW(), 'DB_INIT'),
('Stealth 2ST', 1249.00, '2005-09-10', 8, NOW(), 'DB_INIT'),
('Tom Morello Soul Power', 1439.99, '2020-07-22', 4, NOW(), 'DB_INIT'),
('Razorback Bumblebee', 650.00, '2021-05-05', 1, NOW(), 'DB_INIT'),
('Strat Copy', 80.00, '2015-12-12', 2, NOW(), 'DB_INIT'),
('Ltd KH-502', 499.00, '2002-10-10', 3, NOW(), 'DB_INIT'),
('Ltd KH-2020', 299.00, '2020-11-11', 3, NOW(), 'DB_INIT'),
('Dime Stealth Trans Black', 1199.00, '2004-03-03', 8, NOW(), 'DB_INIT'),
('Dime Bolt', 999.00, '2006-07-07', 8, NOW(), 'DB_INIT'),
('Acoustic Bass CB-60SCE', 234.00, '2022-08-08', 4, NOW(), 'DB_INIT'),
('Marcus Miller V7', 555.00, '2021-09-09', 7, NOW(), 'DB_INIT'),
('Stingray Bass', 1599.00, '2020-01-01', 6, NOW(), 'DB_INIT'),
('XB-400', 439.00, '1998-05-05', 8, NOW(), 'DB_INIT'),
('XB-105', 289.00, '2001-04-04', 8, NOW(), 'DB_INIT');