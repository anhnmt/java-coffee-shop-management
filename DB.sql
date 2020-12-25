USE master;

GO

DROP DATABASE IF EXISTS CoffeeShop;

GO

CREATE DATABASE CoffeeShop;

GO

USE CoffeeShop;

GO

CREATE TABLE Users
(
    id INT PRIMARY KEY IDENTITY,
    [name] NVARCHAR(100) NOT NULL,
    email VARCHAR(100)
        UNIQUE NOT NULL,
    [password] VARCHAR(100) NOT NULL,
    [role] TINYINT
        DEFAULT (0),
    [status] BIT
        DEFAULT (1)
);

GO

CREATE PROC sp_insertUser
(
    @_name NVARCHAR(100),
    @_email VARCHAR(100),
    @_password VARCHAR(100),
    @_role TINYINT = 0,
    @_status BIT = 1,
    @_outStt BIT = 1 OUTPUT,
    @_outMsg NVARCHAR(200) = '' OUTPUT
)
AS
BEGIN TRY
    IF EXISTS (SELECT email FROM Users WHERE email = @_email)
    BEGIN
        SET @_outStt = 0;
        SET @_outMsg = N'Email đã tồn tại, vui lòng nhập lại';
    END;
    ELSE
    BEGIN
        INSERT INTO Users
        (
            [name],
            email,
            [password],
            [role],
            [status]
        )
        VALUES
        (@_name, @_email, @_password, @_role, @_status);
        SET @_outStt = 1;
        SET @_outMsg = N'Thêm người dùng thành công';
    END;
END TRY
BEGIN CATCH
    BEGIN
        SET @_outStt = 0;
        SET @_outMsg = N'Thêm không thành công: ' + ERROR_MESSAGE();
    END;
END CATCH;

GO

-- Them user admin
EXEC sp_insertUser @_name = N'Quản trị viên',
                   @_email = 'admin@gmail.com',
                   @_password = '123456',
                   @_role = 1;

GO

EXEC sp_insertUser @_name = N'Nhân viên',
                   @_email = 'user@gmail.com',
                   @_password = '123456',
                   @_role = 0;

GO

CREATE PROC sp_updateUser(@_id int,
						  @_name nvarchar(100),
                          @_email varchar(100),
                          @_password varchar(100),
                          @_role tinyint = 0,
                          @_status bit = 1,
                          @_outStt bit = 1 output,
                          @_outMsg nvarchar(200) = '' output)
AS
BEGIN TRY
    IF EXISTS(SELECT email FROM Users WHERE email = @_email and id != @_id)
        BEGIN
            SET @_outStt = 0;
            SET @_outMsg = N'Email đã tồn tại, vui lòng nhập lại';
        END
    ELSE
        BEGIN
            UPDATE Users SET [name] = @_name,
							 email = @_email,
							 [password] = @_password,
							 [role] = @_role,
							 [status] = @_status
			WHERE id = @_id

            SET @_outStt = 1;
            SET @_outMsg = N'Cập nhật người dùng thành công';
        END
END TRY
BEGIN CATCH
    BEGIN
        SET @_outStt = 0;
        SET @_outMsg = ERROR_MESSAGE()
    END
END CATCH

GO

CREATE PROC sp_deleteUser(@_id int,
                          @_outStt bit = 1 output,
                          @_outMsg nvarchar(200) = '' output)
AS
BEGIN TRY
    BEGIN
        DELETE Users WHERE id = @_id

        SET @_outStt = 1;
        SET @_outMsg = N'Xoá người dùng thành công';
    END
END TRY
BEGIN CATCH
    BEGIN
        SET @_outStt = 0;
        SET @_outMsg = ERROR_MESSAGE()
    END
END CATCH

GO

CREATE PROC sp_checkUser(
	@_email varchar(100),
    @_password varchar(100)
)
AS
BEGIN
    SELECT TOP 1
           *
    FROM Users
    WHERE email = @_email
          AND [password] = @_password;
END;

GO

EXEC sp_checkUser 'admin@gmail.com', '123456';

GO

CREATE TABLE Categories
(
    id INT PRIMARY KEY IDENTITY,
    [name] NVARCHAR(100)
        UNIQUE NOT NULL,
    [status] BIT
        DEFAULT (1)
);

GO

CREATE PROC sp_insertCategory
(
    @_name NVARCHAR(100),
    @_status BIT = 1,
    @_outStt BIT = 1 OUTPUT,
    @_outMsg NVARCHAR(200) = '' OUTPUT
)
AS
BEGIN TRY
    IF EXISTS (SELECT [name] FROM Categories WHERE [name] = @_name)
    BEGIN
        SET @_outStt = 0;
        SET @_outMsg = N'Tên danh mục đã tồn tại, vui lòng nhập lại';
    END;
    ELSE
    BEGIN
        INSERT INTO Categories
        (
            [name],
            [status]
        )
        VALUES
        (@_name, @_status);
        SET @_outStt = 1;
        SET @_outMsg = N'Thêm thành công';
    END;
END TRY
BEGIN CATCH
    SET @_outStt = 0;
    SET @_outMsg = ERROR_MESSAGE();
END CATCH;

GO

-- Thêm danh mục
EXEC sp_insertCategory @_name = N'Cà Phê Việt';

GO

EXEC sp_insertCategory @_name = N'Cà Phê Tây';

GO

EXEC sp_insertCategory @_name = N'Sinh Tố';

GO

EXEC sp_insertCategory @_name = N'Trà';

GO

EXEC sp_insertCategory @_name = N'Trái Cây';

GO

EXEC sp_insertCategory @_name = N'Sữa Chua';

GO

EXEC sp_insertCategory @_name = N'Khác';

GO

CREATE PROC sp_updateCategory
(
    @_id INT,
    @_name NVARCHAR(100),
    @_status BIT = 1,
    @_outStt BIT = 1 OUTPUT,
    @_outMsg NVARCHAR(200) = '' OUTPUT
)
AS
BEGIN TRY
    IF EXISTS
    (
        SELECT [name]
        FROM Categories
        WHERE [name] = @_name
              AND id != @_id
    )
    BEGIN
        SET @_outStt = 0;
        SET @_outMsg = N'Tên danh mục đã tồn tại, vui lòng nhập lại';
    END;
    ELSE
    BEGIN
        UPDATE Categories
        SET [name] = @_name,
            [status] = @_status
        WHERE id = @_id;

        SET @_outStt = 1;
        SET @_outMsg = N'Cập nhật thành công';
    END;
END TRY
BEGIN CATCH
    SET @_outStt = 0;
    SET @_outMsg = ERROR_MESSAGE();
END CATCH;

GO

CREATE PROC sp_deleteCategory
(
    @_id INT,
    @_outStt BIT = 1 OUTPUT,
    @_outMsg NVARCHAR(200) = '' OUTPUT
)
AS
BEGIN TRY
    IF EXISTS
    (
        SELECT *
        FROM Categories
            JOIN Products
                ON Products.category_id = Categories.id
        WHERE Categories.id = @_id
    )
    BEGIN
        SET @_outStt = 0;
        SET @_outMsg = N'Danh mục đang có sản phẩm, không thể xoá';
    END;
    ELSE
    BEGIN
        DELETE Categories
        WHERE id = @_id;

        SET @_outStt = 1;
        SET @_outMsg = N'Xoá thành công';
    END;
END TRY
BEGIN CATCH
    SET @_outStt = 0;
    SET @_outMsg = ERROR_MESSAGE();
END CATCH;

GO

CREATE PROC sp_getAllCategory
(
    @_name NVARCHAR(100) = NULL,
    @_status BIT = NULL
)
AS
DECLARE @sql NVARCHAR(MAX) = N'SELECT * FROM Categories WHERE 1=1';

IF (@_name IS NOT NULL)
    SET @sql = CONCAT(@sql, N' AND name LIKE ''%', @_name, N'%''');

IF (@_status IS NOT NULL)
    SET @sql = CONCAT(@sql, N' AND status=', @_status);

EXEC (@sql);

GO

EXEC sp_getAllCategory N'';

GO

CREATE TABLE Products
(
    id INT PRIMARY KEY IDENTITY,
    category_id INT
        FOREIGN KEY REFERENCES Categories (id),
    [name] NVARCHAR(100)
        UNIQUE NOT NULL,
    price FLOAT
        DEFAULT (0),
    [status] BIT
        DEFAULT (1)
);

GO

CREATE PROC sp_getAllProduct
(
    @_name NVARCHAR(100) = NULL,
    @_category_id INT = NULL,
    @_fromPrice FLOAT = NULL,
    @_toPrice FLOAT = NULL,
    @_status BIT = NULL
)
AS
DECLARE @sql NVARCHAR(MAX)
    = N'
		SELECT p.*, c.[name] category_name
		FROM Products p
		JOIN Categories c
        ON c.id = p.category_id WHERE 1=1';

IF (@_name IS NOT NULL)
    SET @sql = CONCAT(@sql, N' AND p.name LIKE ''%', @_name, N'%''');

IF (@_category_id IS NOT NULL)
    SET @sql = CONCAT(@sql, N' AND p.category_id=', @_category_id);

IF (@_fromPrice IS NOT NULL)
    SET @sql = CONCAT(@sql, N' AND p.price>=', @_fromPrice);

IF (@_toPrice IS NOT NULL)
    SET @sql = CONCAT(@sql, N' AND p.price<=', @_toPrice);

IF (@_status IS NOT NULL)
    SET @sql = CONCAT(@sql, N' AND p.status=', @_status);

EXEC (@sql);

GO

EXEC dbo.sp_getAllProduct NULL, NULL, 35000, 40000, NULL;

GO

CREATE PROC sp_insertProduct
(
    @_category_id INT,
    @_name NVARCHAR(100),
    @_price FLOAT,
    @_status BIT = 1,
    @_outStt BIT = 1 OUTPUT,
    @_outMsg NVARCHAR(200) = '' OUTPUT
)
AS
BEGIN TRY
    IF NOT EXISTS (SELECT id FROM Categories WHERE id = @_category_id)
    BEGIN
        SET @_outStt = 0;
        SET @_outMsg = N'Mã danh mục không tồn tại, vui lòng nhập lại';
    END;
    ELSE IF EXISTS (SELECT [name] FROM Products WHERE [name] = @_name)
    BEGIN
        SET @_outStt = 0;
        SET @_outMsg = N'Tên sản phẩm đã tồn tại, vui lòng nhập lại';
    END;
    ELSE
    BEGIN
        INSERT INTO Products
        (
            category_id,
            [name],
            price,
            [status]
        )
        VALUES
        (@_category_id, @_name, @_price, @_status);
        SET @_outStt = 1;
        SET @_outMsg = N'Thêm sản phẩm thành công';
    END;
END TRY
BEGIN CATCH
    SET @_outStt = 0;
    SET @_outMsg = ERROR_MESSAGE();
END CATCH;

GO

EXEC sp_insertProduct 1, N'Bạc xỉu', 35000;

GO

EXEC sp_insertProduct 1, N'Cà phê sữa tươi', 35000;

GO

EXEC sp_insertProduct 1, N'Cà phê đen', 29000;

GO

EXEC sp_insertProduct 2, N'Latte', 45000;

GO

EXEC sp_insertProduct 2, N'Cappuchino', 45000;

GO

CREATE PROC sp_updateProduct
(
    @_id INT,
    @_category_id INT,
    @_name NVARCHAR(100),
    @_price FLOAT,
    @_status BIT = 1,
    @_outStt BIT = 1 OUTPUT,
    @_outMsg NVARCHAR(200) = '' OUTPUT
)
AS
BEGIN TRY
    IF EXISTS
    (
        SELECT [name]
        FROM Products
        WHERE [name] = @_name
              AND id != @_id
    )
    BEGIN
        SET @_outStt = 0;
        SET @_outMsg = N'Tên sản phẩm đã tồn tại, vui lòng nhập lại';
    END;
    ELSE
    BEGIN
        UPDATE Products
        SET category_id = @_category_id,
            [name] = @_name,
            [price] = @_price,
            [status] = @_status
        WHERE id = @_id;
        SET @_outStt = 1;
        SET @_outMsg = N'Sửa đổi danh mục thành công';
    END;
END TRY
BEGIN CATCH
    SET @_outStt = 0;
    SET @_outMsg = ERROR_MESSAGE();
END CATCH;

GO

CREATE PROC sp_deleteProduct
(
    @_id INT,
    @_outStt BIT = 1 OUTPUT,
    @_outMsg NVARCHAR(200) = '' OUTPUT
)
AS
BEGIN TRY
    IF EXISTS
    (
        SELECT *
        FROM Products p
            JOIN OrderDetails o
                ON o.product_id = p.id
        WHERE p.id = @_id
    )
    BEGIN
        SET @_outStt = 0;
        SET @_outMsg = N'Sản phẩm đang có liên kết hoá đơn, không thể xoá';
    END;
    ELSE
    BEGIN
        DELETE Products
        WHERE id = @_id;

        SET @_outStt = 1;
        SET @_outMsg = N'Xoá thành công';
    END;
END TRY
BEGIN CATCH
    SET @_outStt = 0;
    SET @_outMsg = ERROR_MESSAGE();
END CATCH;

GO

CREATE TABLE Areas
(
    id INT PRIMARY KEY IDENTITY,
    [name] NVARCHAR(100)
        UNIQUE NOT NULL,
    [status] BIT
        DEFAULT (1)
);

GO

CREATE PROC sp_getAllArea
AS
SELECT *
FROM Areas;

GO

CREATE PROC sp_insertArea
(
    @_name NVARCHAR(100),
    @_status BIT = 1,
    @_outStt BIT = 1 OUTPUT,
    @_outMsg NVARCHAR(200) = '' OUTPUT
)
AS
BEGIN TRY
    IF EXISTS (SELECT [name] FROM Areas WHERE [name] = @_name)
    BEGIN
        SET @_outStt = 0;
        SET @_outMsg = N'Tên khu vực đã tồn tại, vui lòng nhập lại';
    END;
    ELSE
    BEGIN
        INSERT INTO Areas
        (
            [name],
            [status]
        )
        VALUES
        (@_name, @_status);
        SET @_outStt = 1;
        SET @_outMsg = N'Thêm khu vực thành công';
    END;
END TRY
BEGIN CATCH
    SET @_outStt = 0;
    SET @_outMsg = ERROR_MESSAGE();
END CATCH;

GO

EXEC sp_insertArea @_name = N'Tầng 1';

GO

EXEC sp_insertArea @_name = N'Tầng 2';

GO

CREATE PROC sp_updateArea
(
    @_id INT,
    @_name NVARCHAR(100),
    @_status BIT = 1,
    @_outStt BIT = 1 OUTPUT,
    @_outMsg NVARCHAR(200) = '' OUTPUT
)
AS
BEGIN TRY
    IF EXISTS (SELECT [name] FROM Areas WHERE [name] = @_name AND id != @_id)
    BEGIN
        SET @_outStt = 0;
        SET @_outMsg = N'Tên khu vực đã tồn tại, vui lòng nhập lại';
    END;
    ELSE
    BEGIN
        UPDATE Areas
        SET [name] = @_name,
            [status] = @_status
        WHERE id = @_id;

        SET @_outStt = 1;
        SET @_outMsg = N'Sửa khu vực thành công';
    END;
END TRY
BEGIN CATCH
    SET @_outStt = 0;
    SET @_outMsg = ERROR_MESSAGE();
END CATCH;

GO

CREATE PROC sp_findAreaByName
(@_name NVARCHAR(100))
AS
SELECT *
FROM Areas
WHERE [name] = @_name;

GO

CREATE PROC sp_deleteArea
(
    @_id INT,
    @_outStt BIT = 1 OUTPUT,
    @_outMsg NVARCHAR(200) = '' OUTPUT
)
AS
BEGIN TRY
    IF EXISTS
    (
        SELECT *
        FROM Areas
            JOIN [Tables]
                ON [Tables].area_id = Areas.id
        WHERE Areas.id = @_id
    )
    BEGIN
        SET @_outStt = 0;
        SET @_outMsg = N'Khu vực đang có bàn, không thể xoá!';
    END;
    ELSE
    BEGIN
        DELETE Areas
        WHERE id = @_id;

        SET @_outStt = 1;
        SET @_outMsg = N'Xoá thành công';
    END;
END TRY
BEGIN CATCH
    SET @_outStt = 0;
    SET @_outMsg = ERROR_MESSAGE();
END CATCH;

GO

CREATE TABLE [Tables]
(
    id INT PRIMARY KEY IDENTITY,
    area_id INT
        FOREIGN KEY REFERENCES Areas (id),
    [name] NVARCHAR(100)
        UNIQUE NOT NULL,
    note NVARCHAR(150),
    [status] BIT
        DEFAULT (1)
);

GO

CREATE PROC sp_getAllTable
AS
SELECT *
FROM Tables;

GO

CREATE PROC sp_insertTable
(
    @_area_id INT,
    @_name NVARCHAR(100),
    @_note NVARCHAR(150) = '',
    @_status BIT = 1,
    @_outStt BIT = 1 OUTPUT,
    @_outMsg NVARCHAR(200) = '' OUTPUT
)
AS
BEGIN TRY
    IF NOT EXISTS (SELECT id FROM Areas WHERE id = @_area_id)
    BEGIN
        SET @_outStt = 0;
        SET @_outMsg = N'Mã khu vực không tồn tại, vui lòng nhập lại';
    END;
    ELSE IF EXISTS (SELECT [name] FROM [Tables] WHERE [name] = @_name)
    BEGIN
        SET @_outStt = 0;
        SET @_outMsg = N'Tên bàn đã tồn tại, vui lòng nhập lại';
    END;
    ELSE
    BEGIN
        INSERT INTO Tables
        (
            area_id,
            [name],
            note,
            [status]
        )
        VALUES
        (@_area_id, @_name, @_note, @_status);
        SET @_outStt = 1;
        SET @_outMsg = N'Thêm bàn thành công';
    END;
END TRY
BEGIN CATCH
    SET @_outStt = 0;
    SET @_outMsg = N'Thêm không thành công: ' + ERROR_MESSAGE();
END CATCH;

GO

EXEC sp_insertTable @_area_id = 1, @_name = N'Bàn 1';

GO

EXEC sp_insertTable @_area_id = 1, @_name = N'Bàn 2';

GO

EXEC sp_insertTable @_area_id = 1, @_name = N'Bàn 3';

GO

CREATE TABLE Orders
(
    id INT PRIMARY KEY IDENTITY,
    user_id INT NOT NULL
        FOREIGN KEY REFERENCES Users (id),
    table_id INT NOT NULL
        FOREIGN KEY REFERENCES Tables (id),
    total_price FLOAT,
    discount FLOAT
        DEFAULT (0),
    note NVARCHAR(150),
    status BIT
        DEFAULT (1),
    created_at DATETIME
        DEFAULT (GETDATE())
);

GO

CREATE PROC sp_insertOrder
(
    @_user_id INT,
    @_table_id INT,
    @_total_price FLOAT,
    @_discount FLOAT = 0,
    @_note NVARCHAR(150) = '',
    @_status BIT = 1,
    @_outStt BIT = 1 OUTPUT,
    @_outMsg NVARCHAR(200) = '' OUTPUT
)
AS
BEGIN TRY
    IF NOT EXISTS (SELECT id FROM Users WHERE id = @_user_id)
    BEGIN
        SET @_outStt = 0;
        SET @_outMsg = N'Mã người dùng không tồn tại, vui lòng nhập lại';
    END;
    ELSE IF NOT EXISTS (SELECT id FROM Tables WHERE id = @_table_id)
    BEGIN
        SET @_outStt = 0;
        SET @_outMsg = N'Mã bàn không tồn tại, vui lòng nhập lại';
    END;
    ELSE
    BEGIN
        INSERT INTO Orders
        (
            user_id,
            table_id,
            total_price,
            discount,
            note,
            status
        )
        VALUES
        (@_user_id, @_table_id, @_total_price, @_discount, @_note, @_status);
        BEGIN
            SET @_outStt = 1;
            SET @_outMsg = N'Thêm hoá đơn thành công';
        END;
    END;
END TRY
BEGIN CATCH
    SET @_outStt = 0;
    SET @_outMsg = N'Thêm không thành công: ' + ERROR_MESSAGE();
END CATCH;

GO

CREATE TABLE OrderDetails
(
    order_id INT NOT NULL
        FOREIGN KEY REFERENCES Orders (id),
    product_id INT NOT NULL
        FOREIGN KEY REFERENCES Products (id),
    amount INT
        DEFAULT (1)
);

GO

CREATE PROC sp_insertOrderDetail
(
    @_order_id INT,
    @_product_id INT,
    @_amount INT,
    @_outStt BIT = 1 OUTPUT,
    @_outMsg NVARCHAR(200) = '' OUTPUT
)
AS
BEGIN TRY
    IF NOT EXISTS (SELECT id FROM Orders WHERE id = @_order_id)
    BEGIN
        SET @_outStt = 0;
        SET @_outMsg = N'Mã đơn hàng không tồn tại, vui lòng nhập lại';
    END;
    ELSE IF NOT EXISTS (SELECT id FROM Products WHERE id = @_product_id)
    BEGIN
        SET @_outStt = 0;
        SET @_outMsg = N'Mã sản phẩm không tồn tại, vui lòng nhập lại';
    END;
    ELSE IF @_amount < 1
    BEGIN
        SET @_outStt = 0;
        SET @_outMsg = N'Số lượng sản phẩm phải lớn hơn 0';
    END;
    ELSE
    BEGIN
        INSERT INTO OrderDetails
        (
            order_id,
            product_id,
            amount
        )
        VALUES
        (@_order_id, @_product_id, @_amount);
        BEGIN
            SET @_outStt = 1;
            SET @_outMsg = N'Thêm chi tiết hoá đơn thành công';
        END;
    END;
END TRY
BEGIN CATCH
    SET @_outStt = 0;
    SET @_outMsg = N'Thêm không thành công: ' + ERROR_MESSAGE();
END CATCH;
