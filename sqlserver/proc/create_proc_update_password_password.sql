USE [LostandFound]
GO
--¸ù¾İÃÜÂëĞŞ¸ÄÃÜÂë
if (object_id('proc_update_password_password', 'P') is not null)
    drop proc proc_update_password_password
go
create proc proc_update_password_password(@id int,@old_password char(32),@new_password char(32))
as
begin
	if(select count(*) from LostandFound.dbo.[user]
	where LostandFound.dbo.[user].[id]=@id and
	LostandFound.dbo.[user].[password]=@old_password)=0
	begin
		raiserror('',12,1)
		return;
	end

	update LostandFound.dbo.[user]
	set LostandFound.dbo.[user].[password]=@new_password
	where LostandFound.dbo.[user].[id]=@id
end
