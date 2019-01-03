USE [LostandFound]
GO
--发起举报
if (object_id('proc_report', 'P') is not null)
    drop proc proc_report
go
create proc proc_report(@user_id int,@main_event_id int)
as
begin
	if(@user_id=(select LostandFound.dbo.[main_event].[user_id]
	from LostandFound.dbo.[main_event]
	where LostandFound.dbo.[main_event].[id]=@main_event_id))
	begin
		raiserror('self',13,1)
		return;
	end

	if(select count(*) from LostandFound.dbo.[report] 
	where LostandFound.dbo.[report].[user_id]=@user_id and
	LostandFound.dbo.[report].[main_event_id]=@main_event_id)>0
	begin
		raiserror('repeat',12,1)
		return;
	end

	insert into LostandFound.dbo.[report]([user_id],[main_event_id])
	values(@user_id,@main_event_id);
end
