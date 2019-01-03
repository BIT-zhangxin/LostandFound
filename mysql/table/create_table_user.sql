USE `LostandFound`;
CREATE TABLE `user` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`phone_number` CHAR(11) NOT NULL,
	`password` CHAR(32) NOT NULL,
	`nickname` VARCHAR(20) NOT NULL,
	`contact_information` VARCHAR(50) NULL DEFAULT NULL,
	`credit_score` INT(11) NOT NULL DEFAULT '0',
	`profile_photo` BINARY(1) NULL DEFAULT NULL,
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=10000001
;
