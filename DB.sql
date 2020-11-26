USE master

GO

CREATE DATABASE CoffeeShop

GO

USE CoffeeShop

GO

CREATE TABLE Users(
	id int primary key identity,
	[name] nvarchar(100) not null,
	email varchar(100) unique not null,
	[password] varchar(100) not null,
	[role] tinyint default(0),
	[status] bit default(1)
)

GO

CREATE PROC sp_insertUser(
	@_name nvarchar(100),
	@_email varchar(100),
	@_password varchar(100),
	@_role tinyint = 0,
	@_status bit = 1,
	@_KetQua nvarchar(200) output
)
AS
BEGIN TRY
    IF EXISTS (SELECT email FROM Users WHERE email = @_email)
        SET @_KetQua = N'Email đã tồn tại, vui lòng nhập email khác'
    ELSE
        BEGIN
            INSERT INTO Users([name], email, [password], [role], [status]) VALUES (@_name, @_email, @_password, @_role, @_status)
        END
END TRY
BEGIN CATCH
        SET @_KetQua = N'Thêm không thành công: ' + ERROR_MESSAGE()
END CATCH

GO

-- Them user admin
EXEC sp_insertUser @_name = N'Nguyễn Mạnh Tuấn Anh', @_email = 'xdorro@gmail.com', @_password = '1230123', @_role = 1, @_KetQua = 'OK'

GO

CREATE TABLE Categories(
	id int primary key identity,
	[name] nvarchar(100) unique not null,
	[status] bit default(1)
)

GO

CREATE PROC sp_insertCategory(
	@_name nvarchar(100),
	@_status bit = 1,
	@_KetQua nvarchar(200) output
)
AS
BEGIN TRY
    IF EXISTS (SELECT [name] FROM Categories WHERE [name] = @_name)
        SET @_KetQua = N'Tên danh mục đã tồn tại, vui lòng nhập danh mục khác'
    ELSE
        BEGIN
            INSERT INTO Categories([name], [status]) VALUES (@_name, @_status)
        END
END TRY
BEGIN CATCH
        SET @_KetQua = N'Thêm không thành công: ' + ERROR_MESSAGE()
END CATCH

GO

-- Thêm danh mục
EXEC sp_insertCategory @_name = N'Cà Phê Việt', @_KetQua = 'OK'

GO

EXEC sp_insertCategory @_name = N'Cà Phê Tây', @_KetQua = 'OK'

GO

EXEC sp_insertCategory @_name = N'Sinh Tố', @_KetQua = 'OK'

GO

EXEC sp_insertCategory @_name = N'Trà', @_KetQua = 'OK'

GO

EXEC sp_insertCategory @_name = N'Trái Cây', @_KetQua = 'OK'

GO

EXEC sp_insertCategory @_name = N'Sữa Chua', @_KetQua = 'OK'

GO

EXEC sp_insertCategory @_name = N'Khác', @_KetQua = 'OK'

GO

