USE `LostandFound`;
delimiter //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_publish`(
	IN `user_id` INT,
	IN `event_type` INT,
	IN `name` VARCHAR(20),
	IN `location` VARCHAR(50),
	IN `time` VARCHAR(50),
	IN `description` VARCHAR(100)
)
LANGUAGE SQL
NOT DETERMINISTIC
CONTAINS SQL
SQL SECURITY DEFINER
COMMENT ''
BEGIN
	declare object_id int;
	insert into `object`(name,location,`time`,description)
	values(name,location,`time`,description);
	set object_id=last_insert_id();
	insert into `main_event`(event_type,user_id,object_id)
	values(event_type,user_id,object_id);
END //