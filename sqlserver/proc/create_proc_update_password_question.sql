USE [LostandFound]
GO
--根据密保问题修改密码
if (object_id('proc_update_password_question', 'P') is not null)
    drop proc proc_update_password_question
go
create proc proc_update_password_question(@id int,@security_answer nvarchar(50),@new_password char(32))
as
begin
	if(select count(*) from LostandFound.dbo.[user_sucurity_question]
	where LostandFound.dbo.[user_sucurity_question].[id]=@id and
	LostandFound.dbo.[user_sucurity_question].[security_answer]=@security_answer)=0
	begin
		raiserror('',12,1)
		return;
	end

	update LostandFound.dbo.[user]
	set LostandFound.dbo.[user].[password]=@new_password
	where LostandFound.dbo.[user].[id]=@id
end
