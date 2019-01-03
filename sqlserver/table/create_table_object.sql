USE [LostandFound]
GO

/****** Object:  Table [dbo].[object]    Script Date: 2018/12/17 23:15:18 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[object](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](20) NOT NULL,
	[time] [nvarchar](50) NULL,
	[location] [nvarchar](50) NULL,
	[description] [nvarchar](100) NULL,
	[picture] [image] NULL,
 CONSTRAINT [PK_object] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

