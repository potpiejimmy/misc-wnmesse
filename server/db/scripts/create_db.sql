-- -----------------------------------------------------
-- Table `sample_entity`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sample_entity` ;

CREATE  TABLE IF NOT EXISTS `sample_entity` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(64) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;
