USE [LostandFound]
GO
--�����û��ֻ�������ע��һ���˻�,�������ֻ����ظ�
if (object_id('proc_register', 'P') is not null)
    drop proc proc_register
go
create proc proc_register(@phone_number char(11),@password char(32))
as
begin
	if(select count(*) from LostandFound.dbo.[user]
	where LostandFound.dbo.[user].[phone_number]=@phone_number)>0
	begin
		raiserror('',12,1)
		return;
	end

	declare @id int;
	set @id=ident_current('LostandFound.dbo.[user]');
	insert into LostandFound.dbo.[user](phone_number,password,nickname)
	values(@phone_number,@password,'�û�'+convert(nvarchar(20),@id));
end

