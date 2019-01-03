USE [LostandFound]
GO
--根据用户id查询密保问题
if (object_id('proc_select_security_question', 'P') is not null)
    drop proc proc_select_security_question
go
create proc proc_select_security_question(@id int)
as
begin
	select 
	LostandFound.dbo.[user_sucurity_question].[security_question]
	from LostandFound.dbo.[user_sucurity_question]
	where LostandFound.dbo.[user_sucurity_question].[id]=@id
end
