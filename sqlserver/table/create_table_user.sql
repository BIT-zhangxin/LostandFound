USE [LostandFound]
GO

/****** Object:  Table [dbo].[user]    Script Date: 2018/12/17 23:55:59 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[user](
	[id] [int] IDENTITY(10000000,1) NOT NULL,
	[phone_number] [char](11) NOT NULL,
	[password] [char](32) NOT NULL,
	[nickname] [nvarchar](20) NOT NULL,
	[contact_information] [nvarchar](50) NULL,
	[credit_score] [int] NOT NULL,
	[profile_photo] [image] NULL,
 CONSTRAINT [PK_user] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

ALTER TABLE [dbo].[user] ADD  CONSTRAINT [DF_user_credit_score]  DEFAULT ((0)) FOR [credit_score]
GO

