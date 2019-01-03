USE `LostandFound`;
delimiter //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_select_apply_info`(
	IN `id` INT
)
LANGUAGE SQL
NOT DETERMINISTIC
CONTAINS SQL
SQL SECURITY DEFINER
COMMENT ''
BEGIN
	create temporary table if not exists tmpTable
   (
      object_id int,
      user_id int
   ) ENGINE = MEMORY;
   
   insert into tmpTable
   select
   `main_event`.object_id,
   `sub_event`.user_id
   from `main_event`,`sub_event`
   where `sub_event`.main_event_id in
   (
		select `main_event`.id from `main_event`
		where `main_event`.user_id=id
		and `main_event`.event_type<10
	)
	and `sub_event`.main_event_id=`main_event`.id;
   
   select
	`object`.id as object_id,
	`object`.name as object_name,
	`user`.id as user_id,
	`user`.nickname,
	`user`.contact_information
	from `object`,`user`,tmpTable
	where `object`.id=tmpTable.object_id and
	`user`.id=tmpTable.user_id;
   
   truncate table tmpTable;
END //