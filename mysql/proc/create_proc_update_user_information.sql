USE `LostandFound`;
delimiter //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_update_user_information`(
	IN `id` INT,
	IN `nickname` VARCHAR(20),
	IN `contact_information` VARCHAR(50)
)
LANGUAGE SQL
NOT DETERMINISTIC
CONTAINS SQL
SQL SECURITY DEFINER
COMMENT ''
BEGIN
	if(`nickname`!='') then
		update `user`
		set `user`.nickname=nickname
		where `user`.id=id;
	end if;
	if(`contact_information`!='') then
		update `user`
		set `user`.contact_information=contact_information
		where `user`.id=id;
	end if;
END //