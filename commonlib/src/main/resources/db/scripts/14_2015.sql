CREATE SCHEMA `mini_website_builder` ;

CREATE TABLE `mini_website_builder`.`page` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `uniqueId` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uniqueId_UNIQUE` (`uniqueId` ASC));

  CREATE TABLE `mini_website_builder`.`elemenet` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `eUniqueId` VARCHAR(200) NOT NULL,
  `pUniqueId` VARCHAR(200) NOT NULL,
  `type` VARCHAR(45) NULL,
  `content` TEXT(5000) NULL,
  `xcord` VARCHAR(45) NULL,
  `ycord` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `eUniqueId_UNIQUE` (`eUniqueId` ASC));
  
  ALTER TABLE `mini_website_builder`.`page` 
ADD UNIQUE INDEX `id_UNIQUE` (`id` ASC);
 
 ALTER TABLE `mini_website_builder`.`element` 
ADD CONSTRAINT `puniqueId`
  FOREIGN KEY (`pUniqueId`)
  REFERENCES `mini_website_builder`.`page` (`uniqueId`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
  CREATE TABLE `mini_website_builder`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `uuid` VARCHAR(200) NOT NULL,
  `firstname` VARCHAR(45) NULL,
  `lastname` VARCHAR(45) NULL,
  `gender` VARCHAR(45) NULL,
  `googleId` VARCHAR(200) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `uuid_UNIQUE` (`uuid` ASC));
  