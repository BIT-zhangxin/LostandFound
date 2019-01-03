USE [LostandFound]
GO
--修改个人信息
if (object_id('proc_update_user_information', 'P') is not null)
    drop proc proc_update_user_information
go
create proc proc_update_user_information(@id int,@nickname nvarchar(20),@contact_information nvarchar(50))
as
begin
	if(@nickname!='')
	update LostandFound.dbo.[user]
	set LostandFound.dbo.[user].nickname=@nickname
	where LostandFound.dbo.[user].id=@id

	if(@contact_information!='')
	update LostandFound.dbo.[user]
	set LostandFound.dbo.[user].contact_information=@contact_information
	where LostandFound.dbo.[user].id=@id
end

