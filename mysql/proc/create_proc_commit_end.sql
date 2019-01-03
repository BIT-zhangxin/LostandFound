USE `LostandFound`;
delimiter //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_commit_end`(
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
	set tmp=(select event_type from `main_event` where id=main_event_id);
	if(tmp>10) then
		SIGNAL SQLSTATE 'HY000' SET MESSAGE_TEXT = "事件已经结束，结束失败";
	end if;
	update `main_event`
	set `main_event`.event_type=`main_event`.event_type+10
	where `main_event`.id=main_event_id;
END //