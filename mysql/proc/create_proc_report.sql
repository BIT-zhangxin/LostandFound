USE `LostandFound`;
delimiter //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_report`(
	IN `user_id` INT,
	IN `main_event_id` INT
)
LANGUAGE SQL
NOT DETERMINISTIC
CONTAINS SQL
SQL SECURITY DEFINER
COMMENT ''
BEGIN
	declare tmp int;
	set tmp=(select count(1) from `main_event` where id=main_event_id and `main_event`.user_id=user_id);
	if(tmp>0) then
		SIGNAL SQLSTATE 'HY000' SET MESSAGE_TEXT = "self";
	end if;
	set tmp=(select count(1) from `report` where
	`report`.user_id=user_id and `report`.main_event_id=main_event_id);
	if(tmp>0) then
		SIGNAL SQLSTATE 'HY000' SET MESSAGE_TEXT = "repeat";
	end if;
	insert into `report`(user_id,main_event_id)
	values(user_id,main_event_id);
END //