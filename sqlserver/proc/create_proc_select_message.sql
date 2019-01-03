USE [LostandFound]
GO
--查询所有主事件
if (object_id('proc_select_message', 'P') is not null)
    drop proc proc_select_message
go
create proc proc_select_message
as
begin
	select 
	LostandFound.dbo.[main_event].[id] as main_event_id,
	LostandFound.dbo.[main_event].[event_type] as main_event_type,
	LostandFound.dbo.[object].[id] as [object_id],
	LostandFound.dbo.[object].[name],
	LostandFound.dbo.[object].[location],
	LostandFound.dbo.[object].[time],
	LostandFound.dbo.[object].[description]
	from LostandFound.dbo.[object],LostandFound.dbo.[main_event]
	where LostandFound.dbo.[main_event].[object_id]=LostandFound.dbo.[object].[id]
	and LostandFound.dbo.[main_event].[event_type]<10;
end
