USE [LostandFound]
GO
--设置密保问题，只能设置一次
if (object_id('proc_insert_security_question', 'P') is not null)
    drop proc proc_insert_security_question
go
create proc proc_insert_security_question(@id int,@question nvarchar(50),@answer nvarchar(50))
as
begin
	insert into LostandFound.dbo.[user_sucurity_question]
	(id,security_question,security_answer)
	values(@id,@question,@answer);
end

