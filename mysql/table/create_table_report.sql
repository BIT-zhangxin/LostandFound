USE `LostandFound`;
CREATE TABLE `report` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`main_event_id` INT(11) NOT NULL,
	`user_id` INT(11) NOT NULL,
	`time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
	`description` VARCHAR(100) NULL DEFAULT NULL,
	PRIMARY KEY (`id`),
	INDEX `FK_report_main_event` (`main_event_id`),
	INDEX `FK_report_user` (`user_id`),
	CONSTRAINT `FK_report_main_event` FOREIGN KEY (`main_event_id`) REFERENCES `main_event` (`id`),
	CONSTRAINT `FK_report_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

