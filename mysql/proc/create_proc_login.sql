USE `LostandFound`;
delimiter //
CREATE PROCEDURE `proc_login`(
	IN `id_or_phone_number` VARCHAR(20),
	IN `password` CHAR(32)
)
LANGUAGE SQL
NOT DETERMINISTIC
CONTAINS SQL
SQL SECURITY DEFINER
COMMENT ''
BEGIN
	if char_length(id_or_phone_number)=11 then
		select id from `user` where
		phone_number=id_or_phone_number and
		`user`.`password`=`password`;
	else
		select id from `user` where
		id=id_or_phone_number and
		`user`.`password`=`password`;
	end if;
END //