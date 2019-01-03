USE [LostandFound]
GO
--根据用户id查询用户所有信息
if (object_id('proc_select_userinfo', 'P') is not null)
    drop proc proc_select_userinfo
go
create proc proc_select_userinfo(@id int)
as
begin
	select
	LostandFound.dbo.[user].[id],
	LostandFound.dbo.[user].[phone_number],
	LostandFound.dbo.[user].[nickname],
	LostandFound.dbo.[user].[contact_information],
	LostandFound.dbo.[user].[credit_score]
	from LostandFound.dbo.[user]
	where LostandFound.dbo.[user].[id]=@id
end

