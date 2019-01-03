USE [LostandFound]
GO

/****** Object:  Table [dbo].[user_sucurity_question]    Script Date: 2018/12/17 23:14:54 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[user_sucurity_question](
	[id] [int] NOT NULL,
	[security_question] [nvarchar](50) NOT NULL,
	[security_answer] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_user_sucurity_question] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[user_sucurity_question]  WITH CHECK ADD  CONSTRAINT [FK_user_sucurity_question_user] FOREIGN KEY([id])
REFERENCES [dbo].[user] ([id])
GO

ALTER TABLE [dbo].[user_sucurity_question] CHECK CONSTRAINT [FK_user_sucurity_question_user]
GO

