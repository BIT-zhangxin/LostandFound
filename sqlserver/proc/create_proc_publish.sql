USE [LostandFound]
GO
--发布事件
if (object_id('proc_publish', 'P') is not null)
    drop proc proc_publish
go
create proc proc_publish(@user_id int,@event_type int,@name nvarchar(20),@location nvarchar(50),@time nvarchar(50),@description nvarchar(100))
as
begin
	insert into LostandFound.dbo.[object](name,location,time,description)
	values(@name,@location,@time,@description);

	declare @object_id int
	set @object_id=ident_current('LostandFound.dbo.[object]');
	insert into LostandFound.dbo.[main_event](event_type,[user_id],[object_id])
	values(@event_type,@user_id,@object_id);
end


