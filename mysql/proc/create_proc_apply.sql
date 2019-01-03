USE `LostandFound`;
delimiter //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_apply`(
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
	set tmp=(select count(1) from `sub_event` where
	`sub_event`.user_id=user_id and `sub_event`.main_event_id=main_event_id);
	if(tmp>0) then
		SIGNAL SQLSTATE 'HY000' SET MESSAGE_TEXT = "repeat";
	end if;
	insert into `sub_event`(main_event_id,event_type,user_id)
	values(main_event_id,2,user_id);
END //