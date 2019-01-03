USE [LostandFound]
GO

/****** Object:  Table [dbo].[main_event]    Script Date: 2018/12/18 21:15:22 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[main_event](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[event_type] [tinyint] NOT NULL,
	[user_id] [int] NOT NULL,
	[object_id] [int] NOT NULL,
	[time] [smalldatetime] NOT NULL,
	[description] [nvarchar](100) NULL,
 CONSTRAINT [PK_main_event] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[main_event] ADD  CONSTRAINT [DF_main_event_time]  DEFAULT (getdate()) FOR [time]
GO

ALTER TABLE [dbo].[main_event]  WITH CHECK ADD  CONSTRAINT [FK_main_event_object] FOREIGN KEY([object_id])
REFERENCES [dbo].[object] ([id])
GO

ALTER TABLE [dbo].[main_event] CHECK CONSTRAINT [FK_main_event_object]
GO

ALTER TABLE [dbo].[main_event]  WITH CHECK ADD  CONSTRAINT [FK_main_event_user] FOREIGN KEY([user_id])
REFERENCES [dbo].[user] ([id])
GO

ALTER TABLE [dbo].[main_event] CHECK CONSTRAINT [FK_main_event_user]
GO

