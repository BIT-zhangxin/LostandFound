CREATE DATABASE `LostandFound` /*!40100 COLLATE 'utf8_general_ci' */;

create user 'LostandFound'@'%' identified by 'LostandFound';
grant all privileges on `LostandFound`.* to 'LostandFound'@'%' with grant option; 

USE `LostandFound`;
CREATE TABLE `user` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`phone_number` CHAR(11) NOT NULL,
	`password` CHAR(32) NOT NULL,
	`nickname` VARCHAR(20) NOT NULL,
	`contact_information` VARCHAR(50) NULL DEFAULT NULL,
	`credit_score` INT(11) NOT NULL DEFAULT '0',
	`profile_photo` BINARY(1) NULL DEFAULT NULL,
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=10000001
;

USE `LostandFound`;
CREATE TABLE `user_security_question` (
	`id` INT(11) NOT NULL,
	`security_question` VARCHAR(50) NOT NULL,
	`security_answer` VARCHAR(50) NOT NULL,
	PRIMARY KEY (`id`),
	CONSTRAINT `FK_user_security_question_user` FOREIGN KEY (`id`) REFERENCES `user` (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

USE `LostandFound`;
CREATE TABLE `object` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(20) NOT NULL,
	`time` VARCHAR(50) NULL DEFAULT NULL,
	`location` VARCHAR(50) NULL DEFAULT NULL,
	`description` VARCHAR(100) NULL DEFAULT NULL,
	`picture` BINARY(1) NULL DEFAULT NULL,
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

USE `LostandFound`;
CREATE TABLE `main_event` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`event_type` INT(11) NOT NULL,
	`user_id` INT(11) NOT NULL,
	`object_id` INT(11) NOT NULL,
	`time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
	`description` VARCHAR(100) NULL DEFAULT NULL,
	PRIMARY KEY (`id`),
	INDEX `FK_main_event_user` (`user_id`),
	INDEX `FK_main_event_object` (`object_id`),
	CONSTRAINT `FK_main_event_object` FOREIGN KEY (`object_id`) REFERENCES `object` (`id`),
	CONSTRAINT `FK_main_event_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

USE `LostandFound`;
CREATE TABLE `sub_event` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`main_event_id` INT(11) NOT NULL,
	`event_type` INT(11) NOT NULL,
	`user_id` INT(11) NOT NULL,
	`time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
	`description` VARCHAR(100) NULL DEFAULT NULL,
	PRIMARY KEY (`id`),
	INDEX `FK_sub_event_main_event` (`main_event_id`),
	INDEX `FK_sub_event_user` (`user_id`),
	CONSTRAINT `FK_sub_event_main_event` FOREIGN KEY (`main_event_id`) REFERENCES `main_event` (`id`),
	CONSTRAINT `FK_sub_event_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

USE `LostandFound`;
CREATE TABLE `report` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`main_event_id` INT(11) NOT NULL,
	`user_id` INT(11) NOT NULL,
	`time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
	`description` VARCHAR(100) NULL DEFAULT NULL,
	PRIMARY KEY (`id`),
	INDEX `FK_report_main_event` (`main_event_id`),
	INDEX `FK_report_user` (`user_id`),
	CONSTRAINT `FK_report_main_event` FOREIGN KEY (`main_event_id`) REFERENCES `main_event` (`id`),
	CONSTRAINT `FK_report_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

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

CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_insert_security_question`(
	IN `id` INT,
	IN `question` VARCHAR(50),
	IN `answer` VARCHAR(50)
)
LANGUAGE SQL
NOT DETERMINISTIC
CONTAINS SQL
SQL SECURITY DEFINER
COMMENT ''
BEGIN
	insert into `user_security_question`
	(id,security_question,security_answer)
	values(id,question,answer);
END //

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

CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_register`(
	IN `phone_number` CHAR(11),
	IN `password` CHAR(32)
)
LANGUAGE SQL
NOT DETERMINISTIC
CONTAINS SQL
SQL SECURITY DEFINER
COMMENT ''
BEGIN
	declare tmp int;
	set tmp=(select count(1) from `user`
	where `user`.`phone_number`=`phone_number`);
	if (tmp>0) then
		SIGNAL SQLSTATE 'HY000' SET MESSAGE_TEXT = "手机已被注册";
	end if;
	insert into `user`(phone_number,`password`,nickname)
	values(`phone_number`,`password`,'');
	set tmp=last_insert_id();
	update `user`
	set `user`.nickname=concat('用户',tmp)
	where `user`.id=tmp;
END //

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

CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_select_user_message`(
	IN `user_id` INT
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
	and `main_event`.user_id=user_id;
END //

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

CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_update_password_password`(
	IN `id` INT,
	IN `old_password` CHAR(32),
	IN `new_password` CHAR(32)
)
LANGUAGE SQL
NOT DETERMINISTIC
CONTAINS SQL
SQL SECURITY DEFINER
COMMENT ''
BEGIN
	declare tmp int;
	set tmp=(select count(1) from `user` where `user`.id=id and `user`.`password`=`old_password`);
	if (tmp=0) then
		SIGNAL SQLSTATE 'HY000' SET MESSAGE_TEXT = "密码错误";
	end if;
	update `user`
	set `user`.`password`=`new_password`
	where `user`.id=id;
END //

CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_update_password_question`(
	IN `id` INT,
	IN `security_answer` VARCHAR(50),
	IN `new_password` CHAR(32)
)
LANGUAGE SQL
NOT DETERMINISTIC
CONTAINS SQL
SQL SECURITY DEFINER
COMMENT ''
BEGIN
	declare tmp int;
	set tmp=(select count(1) from `user_security_question` where `user_security_question`.id=id
	and `user_security_question`.`security_answer`=`security_answer`);
	if (tmp=0) then
		SIGNAL SQLSTATE 'HY000' SET MESSAGE_TEXT = "密保答案错误";
	end if;
	update `user`
	set `user`.`password`=`new_password`
	where `user`.id=id;
END //

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
delimiter ;