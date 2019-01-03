USE [LostandFound]
GO

/****** Object:  Table [dbo].[sub_event]    Script Date: 2018/12/18 21:15:29 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[sub_event](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[main_event_id] [int] NOT NULL,
	[event_type] [tinyint] NOT NULL,
	[user_id] [int] NOT NULL,
	[time] [smalldatetime] NOT NULL,
	[description] [nvarchar](100) NULL,
 CONSTRAINT [PK_sub_event] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[sub_event] ADD  CONSTRAINT [DF_sub_event_time]  DEFAULT (getdate()) FOR [time]
GO

ALTER TABLE [dbo].[sub_event]  WITH CHECK ADD  CONSTRAINT [FK_sub_event_main_event] FOREIGN KEY([main_event_id])
REFERENCES [dbo].[main_event] ([id])
GO

ALTER TABLE [dbo].[sub_event] CHECK CONSTRAINT [FK_sub_event_main_event]
GO

ALTER TABLE [dbo].[sub_event]  WITH CHECK ADD  CONSTRAINT [FK_sub_event_user] FOREIGN KEY([user_id])
REFERENCES [dbo].[user] ([id])
GO

ALTER TABLE [dbo].[sub_event] CHECK CONSTRAINT [FK_sub_event_user]
GO

