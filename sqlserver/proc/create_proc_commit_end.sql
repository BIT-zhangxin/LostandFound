USE [LostandFound]
GO
--结束主事件
if (object_id('proc_commit_end', 'P') is not null)
    drop proc proc_commit_end
go
create proc proc_commit_end(@user_id int,@main_event_id int)
as
begin
	if((select LostandFound.dbo.[main_event].[event_type]
	from LostandFound.dbo.[main_event]
	where LostandFound.dbo.[main_event].[id]=@main_event_id)>10)
	begin
		raiserror('',12,1)
		return;
	end

	update LostandFound.dbo.[main_event]
	set LostandFound.dbo.[main_event].[event_type]+=10
	where LostandFound.dbo.[main_event].[id]=@main_event_id
end


