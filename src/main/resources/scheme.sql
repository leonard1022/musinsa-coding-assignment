-- BRAND 테이블
CREATE TABLE brands
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

-- CATEGORY 테이블
CREATE TABLE categories
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

-- PRODUCT 테이블
CREATE TABLE products
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    brand_id    BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    price       INT    NOT NULL,

    CONSTRAINT fk_products_brand
        FOREIGN KEY (brand_id) REFERENCES brands (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,

    CONSTRAINT fk_products_category
        FOREIGN KEY (category_id) REFERENCES categories (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);
