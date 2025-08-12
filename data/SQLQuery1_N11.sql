create database N11_PTUD_DB;

USE N11_PTUD_DB;
GO

CREATE TABLE [dbo].[DichVu](
	[IDDichVu] [varchar](5) NOT NULL,
	[TenSanPham] [nvarchar](50) NOT NULL,
	[SoLuong] [int] NOT NULL,
	[DonGia] [money] NOT NULL,
 CONSTRAINT [PK_DichVu] PRIMARY KEY CLUSTERED 
(
	[IDDichVu] ASC
)) ON [PRIMARY]
GO

CREATE TABLE [dbo].[HoaDon](
	[IDHoaDon] [varchar](13) NOT NULL,
	[IDNhanVien] [varchar](10) NOT NULL,
	[IDKhachHang] [varchar](10) NOT NULL,
	[IDKhuyenMai] [varchar](8) NULL,
	[ThoiGianTao] [datetime] NOT NULL,
	[ThoiGianCheckin] [datetime] NOT NULL,
 CONSTRAINT [PK_HoaDon] PRIMARY KEY CLUSTERED 
(
	[IDHoaDon] ASC
)) ON [PRIMARY]
GO

CREATE TABLE [dbo].[KhachHang](
	[IDKhachHang] [varchar](10) NOT NULL,
	[TenKhachHang] [nvarchar](50) NOT NULL,
	[SoDienThoai] [varchar](10) NOT NULL,
	[NgaySinh] [date] NOT NULL,
	[TichDiem] [int] NULL,
	[CCCD] [varchar](12) NOT NULL,
 CONSTRAINT [PK_KhachHang] PRIMARY KEY CLUSTERED 
(
	[IDKhachHang] ASC
)) ON [PRIMARY]
GO

CREATE TABLE [dbo].[KhuyenMai](
	[IDKhuyenMai] [varchar](8) NOT NULL,
	[TenKhuyenMai] [nvarchar](50) NOT NULL,
	[ChietKhau] [float] NOT NULL,
 CONSTRAINT [PK_KhuyenMai] PRIMARY KEY CLUSTERED 
(
	[IDKhuyenMai] ASC
)) ON [PRIMARY]
GO

CREATE TABLE [dbo].[NhanVien](
	[IDNhanVien] [varchar](10) NOT NULL,
	[TenNhanVien] [nvarchar](50) NOT NULL,
	[SoDienThoai] [varchar](10) NOT NULL,
	[NgaySinh] [date] NOT NULL,
	[GioiTinh] [bit] NULL,
	[CCCD] [varchar](12) NOT NULL,
	[ChucVu] [int] NOT NULL,
 CONSTRAINT [PK_NhanVien] PRIMARY KEY CLUSTERED 
(
	[IDNhanVien] ASC
)) ON [PRIMARY]

-- Sửa: Thêm cột IDHoaDon vào bảng PhieuThuePhong
CREATE TABLE [dbo].[PhieuThuePhong](
	[IDPhieuThue] [varchar](13) NOT NULL,
	[IDKhachHang] [varchar](10) NOT NULL,
	[IDPhong] [varchar](6) NOT NULL,
	[IDNhanVien] [varchar](10) NOT NULL,
	[ThoiGianNhanPhong] [datetime] NOT NULL,
	[ThoiHanGiaoPhong] [datetime] NOT NULL,
	[HieuLuc] [bit] NULL,
    [IDHoaDon] [varchar](13) NULL, -- Thêm cột này
 CONSTRAINT [PK_PhieuThuePhong] PRIMARY KEY CLUSTERED 
(
	[IDPhieuThue] ASC
)) ON [PRIMARY]
GO

-- Thêm khóa ngoại cho IDHoaDon nếu muốn liên kết với bảng HoaDon
ALTER TABLE [dbo].[PhieuThuePhong] 
ADD CONSTRAINT [FK_PhieuThuePhong_HoaDon] FOREIGN KEY([IDHoaDon]) REFERENCES [dbo].[HoaDon]([IDHoaDon]);
GO

CREATE TABLE [dbo].[Phong](
	[IDPhong] [varchar](6) NOT NULL,
	[LoaiPhong] [int] NOT NULL,
	[DonGia] [money] NOT NULL,
	[TrangThai] [int] NOT NULL,
	[TieuChi] [nvarchar](50) NULL,
 CONSTRAINT [PK_Phong] PRIMARY KEY CLUSTERED 
(
	[IDPhong] ASC
)) ON [PRIMARY]
GO

CREATE TABLE [dbo].[TaiKhoan](
	[IDTaiKhoan] [varchar](6) NOT NULL,
	[MatKhau] [varchar](50) NOT NULL,
	[TrangThai] [nvarchar](50) NULL,
	[IDNhanVien] [varchar](10) NOT NULL,
 CONSTRAINT [PK_TaiKhoan_1] PRIMARY KEY CLUSTERED 
(
	[IDTaiKhoan] ASC
)) ON [PRIMARY]
GO

CREATE TABLE [dbo].[ChiTietHD_DichVu](
	[IDHoaDon] [varchar](13) NOT NULL,
	[IDDichVu] [varchar](5) NOT NULL,
	[SoLuong] [int] NOT NULL,
 CONSTRAINT [PK_ChiTietHD_DichVu] PRIMARY KEY CLUSTERED 
(
	[IDHoaDon] ASC,
	[IDDichVu] ASC
)) ON [PRIMARY]
GO

CREATE TABLE [dbo].[ChiTietHD_Phong](
	[IDHoaDon] [varchar](13) NOT NULL,
	[IDPhong] [varchar](6) NOT NULL,
	[GioCheckout] [datetime] NOT NULL,
 CONSTRAINT [PK_ChiTietHD_Phong] PRIMARY KEY CLUSTERED 
(
	[IDHoaDon] ASC,
	[IDPhong] ASC
)) ON [PRIMARY]
GO

ALTER TABLE [dbo].[ChiTietHD_DichVu] WITH CHECK ADD CONSTRAINT [FK_ChiTietHD_DichVu_DichVu] FOREIGN KEY([IDDichVu]) REFERENCES [dbo].[DichVu] ([IDDichVu]);
GO

ALTER TABLE [dbo].[ChiTietHD_DichVu] CHECK CONSTRAINT [FK_ChiTietHD_DichVu_DichVu];
GO

ALTER TABLE [dbo].[ChiTietHD_DichVu] WITH CHECK ADD CONSTRAINT [FK_ChiTietHD_DichVu_HoaDon] FOREIGN KEY([IDHoaDon]) REFERENCES [dbo].[HoaDon] ([IDHoaDon]);
GO

ALTER TABLE [dbo].[ChiTietHD_DichVu] CHECK CONSTRAINT [FK_ChiTietHD_DichVu_HoaDon];
GO

ALTER TABLE [dbo].[ChiTietHD_Phong] WITH CHECK ADD CONSTRAINT [FK_ChiTietHD_Phong_HoaDon] FOREIGN KEY([IDHoaDon]) REFERENCES [dbo].[HoaDon] ([IDHoaDon]);
GO

ALTER TABLE [dbo].[ChiTietHD_Phong] CHECK CONSTRAINT [FK_ChiTietHD_Phong_HoaDon];
GO

ALTER TABLE [dbo].[ChiTietHD_Phong] WITH CHECK ADD CONSTRAINT [FK_ChiTietHD_Phong_Phong] FOREIGN KEY([IDPhong]) REFERENCES [dbo].[Phong] ([IDPhong]);
GO

ALTER TABLE [dbo].[ChiTietHD_Phong] CHECK CONSTRAINT [FK_ChiTietHD_Phong_Phong];
GO

ALTER TABLE [dbo].[HoaDon] WITH CHECK ADD CONSTRAINT [FK_HoaDon_KhachHang] FOREIGN KEY([IDKhachHang]) REFERENCES [dbo].[KhachHang] ([IDKhachHang]);
GO

ALTER TABLE [dbo].[HoaDon] CHECK CONSTRAINT [FK_HoaDon_KhachHang];
GO

ALTER TABLE [dbo].[HoaDon] WITH CHECK ADD CONSTRAINT [FK_HoaDon_KhuyenMai] FOREIGN KEY([IDKhuyenMai]) REFERENCES [dbo].[KhuyenMai] ([IDKhuyenMai]);
GO

ALTER TABLE [dbo].[HoaDon] CHECK CONSTRAINT [FK_HoaDon_KhuyenMai];
GO

ALTER TABLE [dbo].[HoaDon] WITH CHECK ADD CONSTRAINT [FK_HoaDon_NhanVien] FOREIGN KEY([IDNhanVien]) REFERENCES [dbo].[NhanVien] ([IDNhanVien]);
GO

ALTER TABLE [dbo].[HoaDon] CHECK CONSTRAINT [FK_HoaDon_NhanVien];
GO

ALTER TABLE [dbo].[PhieuThuePhong] WITH CHECK ADD CONSTRAINT [FK_PhieuThuePhong_KhachHang] FOREIGN KEY([IDKhachHang]) REFERENCES [dbo].[KhachHang] ([IDKhachHang]);
GO

ALTER TABLE [dbo].[PhieuThuePhong] CHECK CONSTRAINT [FK_PhieuThuePhong_KhachHang];
GO

ALTER TABLE [dbo].[PhieuThuePhong] WITH CHECK ADD CONSTRAINT [FK_PhieuThuePhong_NhanVien] FOREIGN KEY([IDNhanVien]) REFERENCES [dbo].[NhanVien] ([IDNhanVien]);
GO

ALTER TABLE [dbo].[PhieuThuePhong] CHECK CONSTRAINT [FK_PhieuThuePhong_NhanVien];
GO

ALTER TABLE [dbo].[PhieuThuePhong] WITH CHECK ADD CONSTRAINT [FK_PhieuThuePhong_Phong] FOREIGN KEY([IDPhong]) REFERENCES [dbo].[Phong] ([IDPhong]);
GO

ALTER TABLE [dbo].[PhieuThuePhong] CHECK CONSTRAINT [FK_PhieuThuePhong_Phong];
GO

ALTER TABLE [dbo].[TaiKhoan] WITH CHECK ADD CONSTRAINT [FK_TaiKhoan_NhanVien] FOREIGN KEY([IDNhanVien]) REFERENCES [dbo].[NhanVien] ([IDNhanVien]);
GO

ALTER TABLE [dbo].[TaiKhoan] CHECK CONSTRAINT [FK_TaiKhoan_NhanVien];
GO

UPDATE PhieuThuePhong SET IDHoaDon = 'HD25030101' WHERE IDPhieuThue = 'PT250301001';

INSERT [dbo].[KhachHang] ([IDKhachHang], [TenKhachHang], [SoDienThoai], [NgaySinh], [TichDiem], [CCCD]) VALUES (N'KH25030101', N'Hoa Thành An', N'0332060907', CAST(N'2004-09-19' AS Date), 0, N'087200001111')
INSERT [dbo].[KhachHang] ([IDKhachHang], [TenKhachHang], [SoDienThoai], [NgaySinh], [TichDiem], [CCCD]) VALUES (N'KH25030102', N'Huỳnh Nhật Hoàng', N'0783971953', CAST(N'2004-11-14' AS Date), 0, N'087200002222')
INSERT [dbo].[KhachHang] ([IDKhachHang], [TenKhachHang], [SoDienThoai], [NgaySinh], [TichDiem], [CCCD]) VALUES (N'KH25030103', N'Hồ Minh Hưng', N'0819575845', CAST(N'2004-11-14' AS Date), 0, N'087200003333')
INSERT [dbo].[KhachHang] ([IDKhachHang], [TenKhachHang], [SoDienThoai], [NgaySinh], [TichDiem], [CCCD]) VALUES (N'KH25030104', N'Nguyễn Hoàng Bảo', N'0962313853', CAST(N'2004-09-26' AS Date), 0, N'087200004444')
INSERT [dbo].[KhachHang] ([IDKhachHang], [TenKhachHang], [SoDienThoai], [NgaySinh], [TichDiem], [CCCD]) VALUES (N'KH25032701', N'Phan Tấn Duy', N'0362553366', CAST(N'2000-10-01' AS Date), 0, N'087204014444')
INSERT [dbo].[KhachHang] ([IDKhachHang], [TenKhachHang], [SoDienThoai], [NgaySinh], [TichDiem], [CCCD]) VALUES (N'KH25032801', N'Nguyễn Trung Hậu', N'0385412906', CAST(N'2004-12-08' AS Date), 0, N'087200003325')
GO
INSERT [dbo].[KhuyenMai] ([IDKhuyenMai], [TenKhuyenMai], [ChietKhau]) VALUES (N'KM250301', N'Khuyến mãi 1', 1)
GO 
INSERT [dbo].[NhanVien] ([IDNhanVien], [TenNhanVien], [SoDienThoai], [NgaySinh], [GioiTinh], [CCCD], [ChucVu]) VALUES (N'NV25030101', N'Nguyễn Hoàng Tấn', N'0701234567', CAST(N'2004-12-05' AS Date), 1, N'087300001111', 1)
INSERT [dbo].[NhanVien] ([IDNhanVien], [TenNhanVien], [SoDienThoai], [NgaySinh], [GioiTinh], [CCCD], [ChucVu]) VALUES (N'NV25030102', N'Huỳnh Lê Minh Duy', N'0801234765', CAST(N'1990-05-05' AS Date), 0, N'087400002222', 2)
INSERT [dbo].[NhanVien] ([IDNhanVien], [TenNhanVien], [SoDienThoai], [NgaySinh], [GioiTinh], [CCCD], [ChucVu]) VALUES (N'NV25032401', N'Huỳnh Nhật Hoàng', N'0783971953', CAST(N'2004-11-14' AS Date), 1, N'012345678955', 1)
GO

INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T01P02', 1, 200000.0000, 1, 'view biển')

GO

INSERT [dbo].[PhieuThuePhong] ([IDPhieuThue], [IDKhachHang], [IDPhong], [IDNhanVien], [ThoiGianNhanPhong], [ThoiHanGiaoPhong], [HieuLuc], [IDHoaDon]) VALUES (N'PT250301001', N'KH25030101', N'T01P02', N'NV25030101', CAST(N'2025-03-01T12:00:00.000' AS DateTime), CAST(N'2025-03-02T12:00:00.000' AS DateTime), NULL, NULL)
GO

INSERT [dbo].[DichVu] ([IDDichVu], [TenSanPham], [SoLuong], [DonGia]) VALUES (N'SP001', N'Nước suối', 240, 10000.0000)
GO
INSERT [dbo].[HoaDon] ([IDHoaDon], [IDNhanVien], [IDKhachHang], [IDKhuyenMai], [ThoiGianTao], [ThoiGianCheckin]) VALUES (N'HD25030101', N'NV25030101', N'KH25030101', N'KM250301', CAST(N'2025-03-01T12:00:00.000' AS DateTime), CAST(N'2025-03-01T12:00:00.000' AS DateTime))
GO
INSERT [dbo].[ChiTietHD_DichVu] ([IDHoaDon], [IDDichVu], [SoLuong]) VALUES (N'HD25030101', N'SP001', 2)
GO
INSERT [dbo].[ChiTietHD_Phong] ([IDHoaDon], [IDPhong], [GioCheckout]) VALUES (N'HD25030101', N'T01P02', CAST(N'2025-03-01T12:00:00.000' AS DateTime))
GO
INSERT [dbo].[TaiKhoan] ([IDTaiKhoan], [MatKhau], [TrangThai], [IDNhanVien]) VALUES (N'NV0101', N'123456789', N'Đang đăng nhập', N'NV25030101')
INSERT [dbo].[TaiKhoan] ([IDTaiKhoan], [MatKhau], [TrangThai], [IDNhanVien]) VALUES (N'NV0102', N'123456789', N'NULL', N'NV25030102')
GO
SELECT * FROM HoaDon WHERE IDHoaDon = 'HD25052715';

