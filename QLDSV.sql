USE master

GO

CREATE DATABASE QLDSV

GO

USE QLDSV

GO

CREATE TABLE NguoiDung(
	MaND int primary key identity,
	TenND nvarchar(50) not null,
	Email varchar(50) unique not null,
	MatKhau varchar(100) not null,
	Quyen bit default(0)
)

GO

CREATE PROC sp_themNguoiDung(
    @_TenND nvarchar(50),
	@_Email varchar(50),
	@_MatKhau varchar(100),
	@_Quyen bit,
	@_KetQua nvarchar(200) output
)
AS
BEGIN TRY
    IF EXISTS (SELECT Email FROM NguoiDung WHERE Email = @_Email)
        SET @_KetQua = N'Email đã tồn tại, vui lòng nhập email khác'
    ELSE
        BEGIN
            INSERT INTO NguoiDung(TenND, Email, MatKhau, Quyen) VALUES (@_TenND, @_Email, @_MatKhau, @_Quyen)
        END
END TRY
BEGIN CATCH
        SET @_KetQua = N'Thêm không thành công: ' + ERROR_MESSAGE()
END CATCH


GO

CREATE TABLE SinhVien(
	MaND int primary key identity,
	TenND nvarchar(50) not null,
	Email varchar(50) unique not null,
	MatKhau varchar(100) not null,
	Quyen bit default(0)
)