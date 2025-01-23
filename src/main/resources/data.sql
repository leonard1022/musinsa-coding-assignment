-- Brand
INSERT INTO brands(name) VALUES ('A');
INSERT INTO brands(name) VALUES ('B');
INSERT INTO brands(name) VALUES ('C');
INSERT INTO brands(name) VALUES ('D');
INSERT INTO brands(name) VALUES ('E');
INSERT INTO brands(name) VALUES ('F');
INSERT INTO brands(name) VALUES ('G');
INSERT INTO brands(name) VALUES ('H');
INSERT INTO brands(name) VALUES ('I');

-- Category
INSERT INTO categories(name) VALUES ('상의');
INSERT INTO categories(name) VALUES ('아우터');
INSERT INTO categories(name) VALUES ('바지');
INSERT INTO categories(name) VALUES ('스니커즈');
INSERT INTO categories(name) VALUES ('가방');
INSERT INTO categories(name) VALUES ('모자');
INSERT INTO categories(name) VALUES ('양말');
INSERT INTO categories(name) VALUES ('액세서리');

-- A(1)
INSERT INTO products(brand_id, category_id, price) VALUES (1, 1, 11200); -- 상의
INSERT INTO products(brand_id, category_id, price) VALUES (1, 2, 5500); -- 아우터
INSERT INTO products(brand_id, category_id, price) VALUES (1, 3, 4200); -- 바지
INSERT INTO products(brand_id, category_id, price) VALUES (1, 4, 9000); -- 스니커즈
INSERT INTO products(brand_id, category_id, price) VALUES (1, 5, 2000); -- 가방
INSERT INTO products(brand_id, category_id, price) VALUES (1, 6, 1700); -- 모자
INSERT INTO products(brand_id, category_id, price) VALUES (1, 7, 1800); -- 양말
INSERT INTO products(brand_id, category_id, price) VALUES (1, 8, 2300); -- 액세서리

-- B(2)
INSERT INTO products(brand_id, category_id, price) VALUES (2, 1, 10500);
INSERT INTO products(brand_id, category_id, price) VALUES (2, 2, 5900);
INSERT INTO products(brand_id, category_id, price) VALUES (2, 3, 3800);
INSERT INTO products(brand_id, category_id, price) VALUES (2, 4, 9100);
INSERT INTO products(brand_id, category_id, price) VALUES (2, 5, 2100);
INSERT INTO products(brand_id, category_id, price) VALUES (2, 6, 2000);
INSERT INTO products(brand_id, category_id, price) VALUES (2, 7, 2000);
INSERT INTO products(brand_id, category_id, price) VALUES (2, 8, 2200);

-- ... 이하 동일한 방식으로 C ~ I까지 INSERT ...
