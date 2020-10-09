-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema shopdb2
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `shopdb2` ;

-- -----------------------------------------------------
-- Schema shopdb2
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `shopdb2` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;
USE `shopdb2` ;

-- -----------------------------------------------------
-- Table `shopdb2`.`addresses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `shopdb2`.`addresses` ;

CREATE TABLE IF NOT EXISTS `shopdb2`.`addresses` (
  `id` BIGINT(10) NOT NULL,
  `country` VARCHAR(128) NOT NULL,
  `city` VARCHAR(128) NOT NULL,
  `building` VARCHAR(16) NOT NULL,
  `apartment` INT(10) UNSIGNED NULL DEFAULT NULL,
  `create_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

CREATE UNIQUE INDEX `country_city_building__apartment_UNIQUE` ON `shopdb2`.`addresses` (`country` ASC, `city` ASC, `building` ASC, `apartment` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `shopdb2`.`categories`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `shopdb2`.`categories` ;

CREATE TABLE IF NOT EXISTS `shopdb2`.`categories` (
  `id` BIGINT(10) NOT NULL,
  `name` VARCHAR(256) NOT NULL,
  `picture` VARCHAR(128) NOT NULL,
  `parent_id` BIGINT(10) NULL DEFAULT NULL,
  `create_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_categories_categories1`
    FOREIGN KEY (`parent_id`)
    REFERENCES `shopdb2`.`categories` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

CREATE UNIQUE INDEX `name_UNIQUE` ON `shopdb2`.`categories` (`name` ASC) INVISIBLE;

CREATE INDEX `fk_categories_categories1_idx` ON `shopdb2`.`categories` (`parent_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `shopdb2`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `shopdb2`.`users` ;

CREATE TABLE IF NOT EXISTS `shopdb2`.`users` (
  `id` BIGINT(10) NOT NULL,
  `email` VARCHAR(256) NOT NULL,
  `password` VARCHAR(64) NOT NULL,
  `role` VARCHAR(16) NOT NULL,
  `create_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

CREATE UNIQUE INDEX `email_UNIQUE` ON `shopdb2`.`users` (`email` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `shopdb2`.`orders`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `shopdb2`.`orders` ;

CREATE TABLE IF NOT EXISTS `shopdb2`.`orders` (
  `id` BIGINT(10) NOT NULL,
  `user_id` BIGINT(10) NOT NULL,
  `total` DOUBLE UNSIGNED NOT NULL DEFAULT '0',
  `status` VARCHAR(16) NOT NULL DEFAULT 'REGISTERED',
  `create_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_orders_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `shopdb2`.`users` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

CREATE INDEX `fk_orders_users1_idx` ON `shopdb2`.`orders` (`user_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `shopdb2`.`deliveries`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `shopdb2`.`deliveries` ;

CREATE TABLE IF NOT EXISTS `shopdb2`.`deliveries` (
  `order_id` BIGINT(10) NOT NULL,
  `begin_date` DATE NOT NULL,
  `end_date` DATE NOT NULL,
  `details` VARCHAR(2048) NULL DEFAULT NULL,
  `address_id` BIGINT(10) NOT NULL,
  `create_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_id`),
  CONSTRAINT `fk_deliveries_addresses1`
    FOREIGN KEY (`address_id`)
    REFERENCES `shopdb2`.`addresses` (`id`),
  CONSTRAINT `fk_deliveries_orders1`
    FOREIGN KEY (`order_id`)
    REFERENCES `shopdb2`.`orders` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

CREATE INDEX `fk_deliveries_addresses1_idx` ON `shopdb2`.`deliveries` (`address_id` ASC) VISIBLE;

CREATE INDEX `fk_deliveries_orders1_idx` ON `shopdb2`.`deliveries` (`order_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `shopdb2`.`products`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `shopdb2`.`products` ;

CREATE TABLE IF NOT EXISTS `shopdb2`.`products` (
  `id` BIGINT(10) NOT NULL,
  `name` VARCHAR(256) NOT NULL,
  `picture` VARCHAR(128) NOT NULL,
  `color` VARCHAR(16) NOT NULL,
  `manufacture_date` DATE NOT NULL,
  `description` VARCHAR(4096) NULL DEFAULT NULL,
  `price` DOUBLE UNSIGNED NOT NULL,
  `amount` INT(10) UNSIGNED NOT NULL DEFAULT '0',
  `category_id` BIGINT(10) NOT NULL,
  `create_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_products_categories1`
    FOREIGN KEY (`category_id`)
    REFERENCES `shopdb2`.`categories` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

CREATE INDEX `fk_products_categories1_idx` ON `shopdb2`.`products` (`category_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `shopdb2`.`orders_products`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `shopdb2`.`orders_products` ;

CREATE TABLE IF NOT EXISTS `shopdb2`.`orders_products` (
  `order_id` BIGINT(10) NOT NULL,
  `product_id` BIGINT(10) NOT NULL,
  CONSTRAINT `fk_cart_has_products_cart1`
    FOREIGN KEY (`order_id`)
    REFERENCES `shopdb2`.`orders` (`id`),
  CONSTRAINT `fk_cart_has_products_products1`
    FOREIGN KEY (`product_id`)
    REFERENCES `shopdb2`.`products` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

CREATE INDEX `fk_cart_has_products_products1_idx` ON `shopdb2`.`orders_products` (`product_id` ASC) VISIBLE;

CREATE INDEX `fk_cart_has_products_cart1_idx` ON `shopdb2`.`orders_products` (`order_id` ASC) VISIBLE;

USE `shopdb2`;

DELIMITER $$

USE `shopdb2`$$
DROP TRIGGER IF EXISTS `shopdb2`.`orders_products_BEFORE_INSERT` $$
USE `shopdb2`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `shopdb2`.`orders_products_BEFORE_INSERT`
BEFORE INSERT ON `shopdb2`.`orders_products`
FOR EACH ROW
BEGIN
    DECLARE product_price DOUBLE;
    SET product_price = (SELECT price FROM products WHERE id = NEW.product_id);
    UPDATE products SET amount = amount - 1 WHERE id = NEW.product_id;
    UPDATE orders SET total = total + product_price WHERE id = NEW.order_id;
END$$


USE `shopdb2`$$
DROP TRIGGER IF EXISTS `shopdb2`.`orders_products_AFTER_DELETE` $$
USE `shopdb2`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `shopdb2`.`orders_products_AFTER_DELETE`
AFTER DELETE ON `shopdb2`.`orders_products`
FOR EACH ROW
BEGIN
    DECLARE product_price DOUBLE;
	SET product_price = (SELECT price FROM products WHERE id = OLD.product_id);
	UPDATE products SET amount = amount + 1 WHERE id = OLD.product_id;
    UPDATE orders SET total = total - product_price WHERE id = OLD.order_id;
END$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `shopdb2`.`categories`
-- -----------------------------------------------------
START TRANSACTION;
USE `shopdb2`;
INSERT INTO `shopdb2`.`categories` (`id`, `name`, `picture`, `parent_id`, `create_date`, `last_update`) VALUES (1, 'cars', '/images/cars/main.jpg', NULL, DEFAULT, DEFAULT);
INSERT INTO `shopdb2`.`categories` (`id`, `name`, `picture`, `parent_id`, `create_date`, `last_update`) VALUES (2, 'category', '/images/category/main.png', NULL, DEFAULT, DEFAULT);
INSERT INTO `shopdb2`.`categories` (`id`, `name`, `picture`, `parent_id`, `create_date`, `last_update`) VALUES (3, 'big', '/images/cars/big.png', 1, DEFAULT, DEFAULT);
INSERT INTO `shopdb2`.`categories` (`id`, `name`, `picture`, `parent_id`, `create_date`, `last_update`) VALUES (4, 'small', '/images/cars/small.png', 1, DEFAULT, DEFAULT);
INSERT INTO `shopdb2`.`categories` (`id`, `name`, `picture`, `parent_id`, `create_date`, `last_update`) VALUES (5, 'products', '/images/category/products.jpg', 2, DEFAULT, DEFAULT);
INSERT INTO `shopdb2`.`categories` (`id`, `name`, `picture`, `parent_id`, `create_date`, `last_update`) VALUES (6, 'men', '/images/category/men.jpg', 2, DEFAULT, DEFAULT);

COMMIT;


-- -----------------------------------------------------
-- Data for table `shopdb2`.`products`
-- -----------------------------------------------------
START TRANSACTION;
USE `shopdb2`;
INSERT INTO `shopdb2`.`products` (`id`, `name`, `picture`, `color`, `manufacture_date`, `description`, `price`, `amount`, `category_id`, `create_date`, `last_update`) VALUES (1, 'Mango 77055933-TO', '/images/category/Mango77055933-TO.jpg', 'BLUE', '2019-02-02', NULL, 499.99, 100, 5, DEFAULT, DEFAULT);
INSERT INTO `shopdb2`.`products` (`id`, `name`, `picture`, `color`, `manufacture_date`, `description`, `price`, `amount`, `category_id`, `create_date`, `last_update`) VALUES (2, 'Mango 77072888-08', '/images/category/Mango77072888-08.jpg', 'WHITE', '2018-01-01', NULL, 1199.49, 150, 5, DEFAULT, DEFAULT);
INSERT INTO `shopdb2`.`products` (`id`, `name`, `picture`, `color`, `manufacture_date`, `description`, `price`, `amount`, `category_id`, `create_date`, `last_update`) VALUES (3, 'Tom Tailor 19243430107', '/images/category/tom_tailor19243430107.jpg', 'WHITE', '2020-03-05', NULL, 899.29, 120, 5, DEFAULT, DEFAULT);
INSERT INTO `shopdb2`.`products` (`id`, `name`, `picture`, `color`, `manufacture_date`, `description`, `price`, `amount`, `category_id`, `create_date`, `last_update`) VALUES (4, 'Leo Pride 2000660001481', '/images/category/leo_pride_2000660001481.jpg', 'BLACK', '2017-08-01', NULL, 1999.00, 50, 5, DEFAULT, DEFAULT);

COMMIT;

