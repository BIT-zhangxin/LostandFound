USE `LostandFound`;
delimiter //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_select_userinfo`(
	IN `id` INT
)
LANGUAGE SQL
NOT DETERMINISTIC
CONTAINS SQL
SQL SECURITY DEFINER
COMMENT ''
BEGIN
	select
	`user`.id,
	`user`.phone_number,
	`user`.nickname,
	`user`.contact_information,
	`user`.credit_score
	from `user`
	where `user`.id=id;
END //