USE [LostandFound]
GO

/****** Object:  Table [dbo].[report]    Script Date: 2018/12/18 21:15:38 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[report](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[main_event_id] [int] NOT NULL,
	[user_id] [int] NOT NULL,
	[time] [smalldatetime] NOT NULL,
	[description] [nvarchar](100) NULL,
 CONSTRAINT [PK_report] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[report] ADD  CONSTRAINT [DF_report_time]  DEFAULT (getdate()) FOR [time]
GO

ALTER TABLE [dbo].[report]  WITH CHECK ADD  CONSTRAINT [FK_report_main_event] FOREIGN KEY([main_event_id])
REFERENCES [dbo].[main_event] ([id])
GO

ALTER TABLE [dbo].[report] CHECK CONSTRAINT [FK_report_main_event]
GO

ALTER TABLE [dbo].[report]  WITH CHECK ADD  CONSTRAINT [FK_report_user] FOREIGN KEY([user_id])
REFERENCES [dbo].[user] ([id])
GO

ALTER TABLE [dbo].[report] CHECK CONSTRAINT [FK_report_user]
GO

