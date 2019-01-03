USE [LostandFound]
GO
--≤È—Ø…Í«Î–≈œ¢
if (object_id('proc_select_apply_info', 'P') is not null)
    drop proc proc_select_apply_info
go
create proc proc_select_apply_info(@id int)
as
begin
	create table #tmp
	(
		[object_id] int,
		[user_id] int
	);
	insert into #tmp
	select
	LostandFound.dbo.[main_event].[object_id],
	LostandFound.dbo.[sub_event].[user_id]
	from
	LostandFound.dbo.[sub_event],
	LostandFound.dbo.[main_event]
	where LostandFound.dbo.[sub_event].[main_event_id] in
	(
		select LostandFound.dbo.[main_event].[id]
		from LostandFound.dbo.[main_event]
		where LostandFound.dbo.[main_event].[user_id]=@id
		and LostandFound.dbo.[main_event].[event_type]<10
	)
	and LostandFound.dbo.[sub_event].[main_event_id]=
	LostandFound.dbo.[main_event].[id]

	select
	LostandFound.dbo.[object].[id] as [object_id],
	LostandFound.dbo.[object].[name] as [object_name],
	LostandFound.dbo.[user].[id] as [user_id],
	LostandFound.dbo.[user].[nickname],
	LostandFound.dbo.[user].[contact_information]
	from LostandFound.dbo.[object],LostandFound.dbo.[user],#tmp
	where 
	LostandFound.dbo.[object].[id]=#tmp.[object_id] and
	LostandFound.dbo.[user].[id]=#tmp.[user_id]
end
