USE [LostandFound]
GO
--根据账户(id或手机号)密码查询用户id，查询不到则验证失败
if (object_id('proc_login', 'P') is not null)
    drop proc proc_login
go
create proc proc_login(@id_or_phone_number varchar(20),@password char(32))
as
begin
	if(len(@id_or_phone_number)=8)
		begin
			select LostandFound.dbo.[user].[id] from LostandFound.dbo.[user] where
			LostandFound.dbo.[user].[id]=@id_or_phone_number and
			LostandFound.dbo.[user].[password]=@password
		end
	else if(len(@id_or_phone_number)=11)
		begin
			select LostandFound.dbo.[user].[id] from LostandFound.dbo.[user] where
			LostandFound.dbo.[user].[phone_number]=@id_or_phone_number and
			LostandFound.dbo.[user].[password]=@password
		end
	else return
end

