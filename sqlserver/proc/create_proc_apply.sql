USE [LostandFound]
GO
--·¢ÆğÉêÇë
if (object_id('proc_apply', 'P') is not null)
    drop proc proc_apply
go
create proc proc_apply(@user_id int,@main_event_id int)
as
begin
	if(@user_id=(select LostandFound.dbo.[main_event].[user_id]
	from LostandFound.dbo.[main_event]
	where LostandFound.dbo.[main_event].[id]=@main_event_id))
	begin
		raiserror('self',13,1)
		return;
	end

	if(select count(*) from LostandFound.dbo.[sub_event] 
	where LostandFound.dbo.[sub_event].[user_id]=@user_id and
	LostandFound.dbo.[sub_event].[main_event_id]=@main_event_id)>0
	begin
		raiserror('repeat',12,1)
		return;
	end

	insert into LostandFound.dbo.[sub_event](main_event_id,event_type,[user_id])
	values(@main_event_id,2,@user_id);
end


