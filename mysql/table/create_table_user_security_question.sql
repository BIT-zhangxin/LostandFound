USE `LostandFound`;
CREATE TABLE `user_security_question` (
	`id` INT(11) NOT NULL,
	`security_question` VARCHAR(50) NOT NULL,
	`security_answer` VARCHAR(50) NOT NULL,
	PRIMARY KEY (`id`),
	CONSTRAINT `FK_user_security_question_user` FOREIGN KEY (`id`) REFERENCES `user` (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;
