USE `LostandFound`;
delimiter //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_select_message`(
	
)
LANGUAGE SQL
NOT DETERMINISTIC
CONTAINS SQL
SQL SECURITY DEFINER
COMMENT ''
BEGIN
	select
	`main_event`.id as main_event_id,
	`main_event`.event_type as main_event_type,
	`object`.id as object_id,
	`object`.name,
	`object`.location,
	`object`.`time`,
	`object`.description
	from `object`,`main_event`
	where `main_event`.object_id=`object`.id
	and `main_event`.event_type<10;
END //