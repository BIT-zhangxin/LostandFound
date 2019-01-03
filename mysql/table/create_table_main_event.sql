USE `LostandFound`;
CREATE TABLE `main_event` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`event_type` INT(11) NOT NULL,
	`user_id` INT(11) NOT NULL,
	`object_id` INT(11) NOT NULL,
	`time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
	`description` VARCHAR(100) NULL DEFAULT NULL,
	PRIMARY KEY (`id`),
	INDEX `FK_main_event_user` (`user_id`),
	INDEX `FK_main_event_object` (`object_id`),
	CONSTRAINT `FK_main_event_object` FOREIGN KEY (`object_id`) REFERENCES `object` (`id`),
	CONSTRAINT `FK_main_event_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;
