CREATE TABLE `miniWeebly`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `uuid` VARCHAR(200) NOT NULL,
  `firstname` VARCHAR(45) NULL,
  `lastname` VARCHAR(45) NULL,
  `gender` VARCHAR(45) NULL,
  `apikey` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`id`));