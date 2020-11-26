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

CREATE TABLE Products(
	id int primary key identity,
	category_id int foreign key references Categories(id),
	[name] nvarchar(100) unique not null,
	price float default(0),
	[status] bit default(1)
)

GO

CREATE PROC sp_insertProduct(
	@_category_id int,
	@_name nvarchar(100),
	@_price float,
	@_status bit = 1,
	@_KetQua nvarchar(200) output
)
AS
BEGIN TRY
    IF NOT EXISTS (SELECT id FROM Categories WHERE id = @_category_id)
        SET @_KetQua = N'Mã danh mục không tồn tại, vui lòng nhập danh mục khác'
    ELSE IF EXISTS (SELECT [name] FROM Products WHERE [name] = @_name)
        SET @_KetQua = N'Tên sản phẩm đã tồn tại, vui lòng nhập sản phẩm khác'
    ELSE
        BEGIN
            INSERT INTO Products(category_id, [name], price, [status]) VALUES (@_category_id,@_name, @_price, @_status)
        END
END TRY
BEGIN CATCH
        SET @_KetQua = N'Thêm không thành công: ' + ERROR_MESSAGE()
END CATCH

GO

CREATE TABLE Areas(
	id int primary key identity,
	[name] nvarchar(100) unique not null,
	[status] bit default(1)
)

GO

CREATE PROC sp_insertArea(
	@_name nvarchar(100),
	@_status bit = 1,
	@_KetQua nvarchar(200) output
)
AS
BEGIN TRY
    IF EXISTS (SELECT [name] FROM Areas WHERE [name] = @_name)
        SET @_KetQua = N'Tên khu vực đã tồn tại, vui lòng nhập khu vực khác'
    ELSE
        BEGIN
            INSERT INTO Areas([name], [status]) VALUES (@_name, @_status)
        END
END TRY
BEGIN CATCH
        SET @_KetQua = N'Thêm không thành công: ' + ERROR_MESSAGE()
END CATCH

GO

EXEC sp_insertArea @_name = N'Tầng 1', @_KetQua = 'OK'

GO

EXEC sp_insertArea @_name = N'Tầng 2', @_KetQua = 'OK'

GO

CREATE TABLE [Tables](
	id int primary key identity,
	area_id int foreign key references Categories(id),
	[name] nvarchar(100) unique not null,
	note nvarchar(150),
	[status] bit default(1)
)

GO

CREATE PROC sp_insertTable(
	@_area_id int,
	@_name nvarchar(100),
	@_status bit = 1,
	@_note nvarchar(150) = '',
	@_KetQua nvarchar(200) output
)
AS
BEGIN TRY
    IF NOT EXISTS (SELECT id FROM Areas WHERE id = @_area_id)
        SET @_KetQua = N'Mã khu vực không tồn tại, vui lòng nhập khu vực khác'
    ELSE IF EXISTS (SELECT [name] FROM [Tables] WHERE [name] = @_name)
        SET @_KetQua = N'Tên bàn đã tồn tại, vui lòng nhập bàn khác'
    ELSE
        BEGIN
            INSERT INTO Tables(area_id, [name], note, [status]) VALUES (@_area_id, @_name, @_note, @_status)
			SET @_KetQua = N'Thêm bàn thành công'
        END
END TRY
BEGIN CATCH
        SET @_KetQua = N'Thêm không thành công: ' + ERROR_MESSAGE()
END CATCH

GO

EXEC sp_insertTable @_area_id = 1, @_name = N'Bàn 1', @_KetQua = 'OK'

GO

EXEC sp_insertTable @_area_id = 1, @_name = N'Bàn 2', @_KetQua = 'OK'

GO

EXEC sp_insertTable @_area_id = 1, @_name = N'Bàn 3', @_KetQua = 'OK'

GO

