USE `LostandFound`;
delimiter //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_select_security_question`(
	IN `id` INT
)
LANGUAGE SQL
NOT DETERMINISTIC
CONTAINS SQL
SQL SECURITY DEFINER
COMMENT ''
BEGIN
	select
	`user_security_question`.security_question
	from `user_security_question`
	where `user_security_question`.id=id;
END //