-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jul 20, 2025 at 05:36 PM
-- Server version: 9.1.0
-- PHP Version: 8.3.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `quan_ly_diem_danh`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
CREATE TABLE IF NOT EXISTS `admin` (
  `admin_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `full_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `role` tinytext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `remember_token` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`admin_id`) USING BTREE,
  UNIQUE KEY `username` (`username`) USING BTREE,
  UNIQUE KEY `email` (`email`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`admin_id`, `username`, `password`, `email`, `full_name`, `role`, `remember_token`, `created_at`, `updated_at`, `avatar`) VALUES
(7, 'truong_ba_do', '$2a$10$8t.UU2eGxGTLcUWzc8hGW.0a3hMxnf7hyBT2ln35.eG0mV494/3e6', 'honguyententhanhtruong@gmail.com', 'Nguyễn Thanh Trường', 'admin', NULL, '2025-07-18 09:38:35', '2025-07-19 09:23:24', NULL),
(8, 'TranNgocHiep', '$2a$10$vl0Li3esqzhNjtNJ1NGJtueJev.kRDSBQco..MvsWswVhVvd9WMVK', 'hiepsktz101@gmail.com', 'Trân Ngọc Hiêp', 'admin', NULL, '2025-07-19 09:21:55', '2025-07-19 09:21:55', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `admin_password_resets`
--

DROP TABLE IF EXISTS `admin_password_resets`;
CREATE TABLE IF NOT EXISTS `admin_password_resets` (
  `email` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `token` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`email`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- Table structure for table `diem_danh`
--

DROP TABLE IF EXISTS `diem_danh`;
CREATE TABLE IF NOT EXISTS `diem_danh` (
  `ma_dd` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `ma_tkb` int UNSIGNED NOT NULL,
  `ma_sv` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `ngay_hoc` date NOT NULL,
  `diem_danh1` datetime DEFAULT NULL,
  `ghi_chu` tinytext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`ma_dd`) USING BTREE,
  KEY `diem_danh_ma_tkb_foreign` (`ma_tkb`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=165 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- Table structure for table `diem_danh_log`
--

DROP TABLE IF EXISTS `diem_danh_log`;
CREATE TABLE IF NOT EXISTS `diem_danh_log` (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `ma_dd` int UNSIGNED NOT NULL,
  `lan_diem_danh` int UNSIGNED NOT NULL,
  `thoi_gian_diem_danh` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_dd_log_ma_dd` (`ma_dd`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `giao_vien`
--

DROP TABLE IF EXISTS `giao_vien`;
CREATE TABLE IF NOT EXISTS `giao_vien` (
  `ma_gv` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `ten_gv` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `ngay_sinh` date NOT NULL,
  `phai` tinyint NOT NULL,
  `dia_chi` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `sdt` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ma_gv`) USING BTREE,
  UNIQUE KEY `giao_vien_sdt_unique` (`sdt`) USING BTREE,
  UNIQUE KEY `giao_vien_email_unique` (`email`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

--
-- Dumping data for table `giao_vien`
--

INSERT INTO `giao_vien` (`ma_gv`, `ten_gv`, `ngay_sinh`, `phai`, `dia_chi`, `sdt`, `email`, `avatar`) VALUES
('hiep', 'Thầy Hiệp', '2025-07-08', 1, 'Củ Chi, HCM', '0847639229', 'tranngochiep@gmail.com', NULL),
('hung', 'Thầy Trường', '1995-07-08', 1, 'HCM', '0907898806', 'truonggenz2003@gmail.com', NULL);

--
-- Triggers `giao_vien`
--
DROP TRIGGER IF EXISTS `before_delete_giao_vien`;
DELIMITER $$
CREATE TRIGGER `before_delete_giao_vien` BEFORE DELETE ON `giao_vien` FOR EACH ROW BEGIN
  -- Xóa user liên quan giảng viên
  DELETE FROM users WHERE ma_gv = OLD.ma_gv;

  -- Tìm và xóa các lịch giảng dạy của giảng viên
  DELETE FROM lich_hoc WHERE ma_gd IN (
    SELECT ma_gd FROM lich_gd WHERE ma_gv = OLD.ma_gv
  );

  DELETE FROM diem_danh WHERE ma_tkb IN (
    SELECT ma_tkb FROM tkb WHERE ma_gd IN (
      SELECT ma_gd FROM lich_gd WHERE ma_gv = OLD.ma_gv
    )
  );

  DELETE FROM qrcode WHERE ma_tkb IN (
    SELECT ma_tkb FROM tkb WHERE ma_gd IN (
      SELECT ma_gd FROM lich_gd WHERE ma_gv = OLD.ma_gv
    )
  );

  DELETE FROM tkb WHERE ma_gd IN (
    SELECT ma_gd FROM lich_gd WHERE ma_gv = OLD.ma_gv
  );

  DELETE FROM lich_gd WHERE ma_gv = OLD.ma_gv;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `khoa`
--

DROP TABLE IF EXISTS `khoa`;
CREATE TABLE IF NOT EXISTS `khoa` (
  `ma_khoa` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `ten_khoa` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`ma_khoa`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

--
-- Dumping data for table `khoa`
--

INSERT INTO `khoa` (`ma_khoa`, `ten_khoa`) VALUES
('CK', 'Khoa Cơ Khí'),
('CNTP', 'Khoa Công Nghệ Thực Phẩm'),
('CNTT', 'Khoa Công Nghệ Thông Tin'),
('D-DT', 'Khoa Điện - Điện Tử'),
('DE', 'Khoa Design'),
('KTCT', 'Khoa Kỹ Thuật Công Trình'),
('QTKD', 'Khoa Quản Trị Kinh Doanh');

-- --------------------------------------------------------

--
-- Table structure for table `lich_gd`
--

DROP TABLE IF EXISTS `lich_gd`;
CREATE TABLE IF NOT EXISTS `lich_gd` (
  `ma_gd` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `ma_gv` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `ma_mh` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `nmh` int NOT NULL,
  `phong_hoc` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `ngay_bd` date NOT NULL,
  `ngay_kt` date NOT NULL,
  `st_bd` int NOT NULL,
  `st_kt` int NOT NULL,
  `hoc_ky` int NOT NULL,
  PRIMARY KEY (`ma_gd`) USING BTREE,
  KEY `lich_gd_ma_gv_foreign` (`ma_gv`) USING BTREE,
  KEY `lich_gd_ma_mh_foreign` (`ma_mh`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=483 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

--
-- Dumping data for table `lich_gd`
--

INSERT INTO `lich_gd` (`ma_gd`, `ma_gv`, `ma_mh`, `nmh`, `phong_hoc`, `ngay_bd`, `ngay_kt`, `st_bd`, `st_kt`, `hoc_ky`) VALUES
(482, 'hung', 'CS03001', 1, 'C802', '2025-02-09', '2025-06-30', 1, 5, 1);

-- --------------------------------------------------------

--
-- Table structure for table `lich_hoc`
--

DROP TABLE IF EXISTS `lich_hoc`;
CREATE TABLE IF NOT EXISTS `lich_hoc` (
  `ma_sv` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `ma_gd` int UNSIGNED NOT NULL,
  PRIMARY KEY (`ma_sv`,`ma_gd`) USING BTREE,
  KEY `lich_hoc_ma_gd_foreign` (`ma_gd`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

--
-- Dumping data for table `lich_hoc`
--

INSERT INTO `lich_hoc` (`ma_sv`, `ma_gd`) VALUES
('DH52003421', 482),
('DH52005758', 482),
('DH52006924', 482),
('DH52009028', 482),
('DH52100001', 482),
('DH52100449', 482),
('DH52101018', 482),
('DH52101039', 482),
('DH52101465', 482),
('DH52101490', 482),
('DH52101856', 482),
('DH52101979', 482),
('DH52102050', 482),
('DH52102314', 482),
('DH52102644', 482),
('DH52102716', 482),
('DH52103137', 482),
('DH52103289', 482),
('DH52104108', 482),
('DH52104319', 482),
('DH52104569', 482),
('DH52105054', 482),
('DH52105079', 482),
('DH52105426', 482),
('DH52105684', 482),
('DH52106669', 482),
('DH52106813', 482),
('DH52107294', 482),
('DH52107527', 482),
('DH52107557', 482),
('DH52107715', 482),
('DH52107858', 482),
('DH52107926', 482),
('DH52108371', 482),
('DH52108549', 482),
('DH52108823', 482),
('DH52110568', 482),
('DH52110579', 482),
('DH52110602', 482),
('DH52110616', 482),
('DH52110640', 482),
('DH52110649', 482),
('DH52110742', 482),
('DH52110839', 482),
('DH52110891', 482),
('DH52110952', 482),
('DH52111036', 482),
('DH52111058', 482),
('DH52111115', 482),
('DH52112771', 482);

-- --------------------------------------------------------

--
-- Table structure for table `lop`
--

DROP TABLE IF EXISTS `lop`;
CREATE TABLE IF NOT EXISTS `lop` (
  `ma_lop` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `ten_lop` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `ma_khoa` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `gvcn` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `sdt_gvcn` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ma_lop`) USING BTREE,
  KEY `lop_ma_khoa_foreign` (`ma_khoa`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

--
-- Dumping data for table `lop`
--

INSERT INTO `lop` (`ma_lop`, `ten_lop`, `ma_khoa`, `gvcn`, `sdt_gvcn`) VALUES
('D20_TH01', 'D20_TH01', 'CNTT', 'Lương An Vinh', NULL),
('D20_TH02', 'D20_TH02', 'CNTT', 'Lương An Vinh', NULL),
('D20_TH03', 'D20_TH03', 'CNTT', 'Hồ Đình Khả', NULL),
('D20_TH04', 'D20_TH04', 'CNTT', 'Hồ Đình Khả', NULL),
('D20_TH05', 'D20_TH05', 'CNTT', 'Hà Anh Vũ', NULL),
('D20_TH06', 'D20_TH06', 'CNTT', 'Hà Anh Vũ', NULL),
('D20_TH07', 'D20_TH07', 'CNTT', 'Trần Thị Như Y', NULL),
('D20_TH08', 'D20_TH08', 'CNTT', 'Trần Thị Như Y', NULL),
('D20_TH09', 'D20_TH09', 'CNTT', 'Lê Triệu Ngọc Đức', NULL),
('D20_TH10', 'D20_TH10', 'CNTT', 'Lê Triệu Ngọc Đức', NULL),
('D20_TH11', 'D20_TH11', 'CNTT', 'Ngô Xuân Bách', NULL),
('D21_TH01', 'D21_TH01', 'CNTT', 'Nguyễn Thanh Tùng', NULL),
('D21_TH02', 'D21_TH02', 'CNTT', 'Nguyễn Thanh Tùng', NULL),
('D21_TH03', 'D21_TH03', 'CNTT', 'Hà Anh Vũ', NULL),
('D21_TH04', 'D21_TH04', 'CNTT', 'Hà Anh Vũ', NULL),
('D21_TH05', 'D21_TH05', 'CNTT', NULL, NULL),
('D21_TH06', 'D21_TH06', 'CNTT', NULL, NULL),
('D21_TH07', 'D21_TH07', 'CNTT', NULL, NULL),
('D21_TH08', 'D21_TH08', 'CNTT', NULL, NULL),
('D21_TH09', 'D21_TH09', 'CNTT', NULL, NULL),
('D21_TH10', 'D21_TH10', 'CNTT', NULL, NULL),
('D21_TH11', 'D21_TH11', 'CNTT', NULL, NULL),
('D21_TH12', 'D21_TH12', 'CNTT', NULL, NULL),
('D21_TH13', 'D21_TH13', 'CNTT', NULL, NULL),
('D21_TH14', 'D21_TH14', 'CNTT', NULL, NULL),
('D22_TH01', 'D22_TH01', 'CNTT', NULL, NULL),
('D22_TH02', 'D22_TH02', 'CNTT', NULL, NULL),
('D22_TH03', 'D22_TH03', 'CNTT', NULL, NULL),
('D22_TH04', 'D22_TH04', 'CNTT', NULL, NULL),
('D22_TH05', 'D22_TH05', 'CNTT', NULL, NULL),
('D22_TH06', 'D22_TH06', 'CNTT', NULL, NULL),
('D22_TH07', 'D22_TH07', 'CNTT', NULL, NULL),
('D22_TH08', 'D22_TH08', 'CNTT', NULL, NULL),
('D22_TH09', 'D22_TH09', 'CNTT', NULL, NULL),
('D22_TH10', 'D22_TH10', 'CNTT', NULL, NULL),
('D22_TH11', 'D22_TH11', 'CNTT', NULL, NULL),
('D22_TH12', 'D22_TH12', 'CNTT', NULL, NULL),
('D22_TH13', 'D22_TH13', 'CNTT', NULL, NULL),
('D22_TH14', 'D22_TH14', 'CNTT', NULL, NULL),
('D22_TH15', 'D22_TH15', 'CNTT', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `mon_hoc`
--

DROP TABLE IF EXISTS `mon_hoc`;
CREATE TABLE IF NOT EXISTS `mon_hoc` (
  `ma_mh` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `ten_mh` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `so_tiet` int NOT NULL,
  PRIMARY KEY (`ma_mh`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

--
-- Dumping data for table `mon_hoc`
--

INSERT INTO `mon_hoc` (`ma_mh`, `ten_mh`, `so_tiet`) VALUES
('CS03001', 'Kỹ thuật số', 45),
('CS03002', 'Thí nghiệm Kỹ thuật số', 30),
('CS03003', 'Kỹ thuật lập trình', 45),
('CS03004', 'Thực hành Kỹ thuật lập trình', 30),
('CS03005', 'Toán tin học', 45),
('CS03007', 'Cấu trúc dữ liệu và thuật giải', 45),
('CS03008', 'Cơ sở dữ liệu', 45),
('CS03009', 'Hệ điều hành', 45),
('CS03010', 'Thực hành Cấu trúc dữ liệu và thuật giải', 30),
('CS03011', 'Thực hành Cơ sở dữ liệu', 30),
('CS03012', 'Thực hành Hệ điều hành', 30),
('CS03013', 'Công nghệ phần mềm', 45),
('CS03014', 'Đồ án tin học', 45),
('CS03015', 'Lập trình hướng đối tượng', 45),
('CS03016', 'Thực hành Lập trình hướng đối tượng', 30),
('CS03017', 'Lập trình ứng dụng cơ sở dữ liệu', 45),
('CS03020', 'Quản trị cơ sở dữ liệu', 45),
('CS03021', 'Seminar nghề nghiệp', 45),
('CS03022', 'Quản lý dự án', 45),
('CS03023', 'Thương mại điện tử', 45),
('CS03024', 'An ninh máy tính', 45),
('CS03025', 'Thực tập An ninh máy tính', 30),
('CS03026', 'Mã hóa ứng dụng', 45),
('CS03027', 'Thực hành Hệ quản trị cơ sơ dữ liệu', 30),
('CS03028', 'Thực hành Lập trình ứng dụng cơ sở dữ liệu', 30),
('CS03029', 'Đồ án Chuyên ngành', 45),
('CS03030', 'Đồ án Phân tích thiết kế hệ thống thông tin', 45),
('CS03033', 'Phát triển phần mềm nguồn mở', 45),
('CS03034', 'Thực hành Phát triển phần mềm nguồn mở', 30),
('CS03036', 'Lập trình Web', 45),
('CS03037', 'Lập trình Windows', 45),
('CS03038', 'Lập trình cho thiết bị di động', 45),
('CS03039', 'Thực hành Lập trình Web', 30),
('CS03040', 'Thực hành Lập trình Windows', 30),
('CS03041', 'Thực hành Lập trình cho thiết bị di động', 30),
('CS03042', 'Triển khai hệ thống thông tin', 45),
('CS03043', 'Xây dựng phần mềm Web', 45),
('CS03044', 'Xây dựng phần mềm Windows', 45),
('CS03045', 'Kiểm thử phần mềm', 45),
('CS03047', 'Nhập môn công tác kỹ sư', 45),
('CS03057', 'AI cơ bản và ứng dụng', 45),
('CS03058', 'Xây dựng phần mềm thiết bị di động', 45),
('CS03151', 'Thực tập tốt nghiệp', 45),
('CS03153', 'Đồ án / Khóa luận tốt nghiệp', 45),
('CS09001', 'Nhập môn lập trình', 45),
('CS09002', 'Thực hành Nhập môn lập trình', 30),
('CS09003', 'Nhập môn Web và ứng dụng', 45),
('CS09004', 'Thực hành Nhập môn Web và ứng dụng', 30),
('CS09005', 'Nhập môn cấu trúc dữ liệu', 45),
('CS09006', 'Tổ chức cấu trúc máy tính', 45),
('CS09007', 'Thực hành Nhập môn cấu trúc dữ liệu', 30),
('CS09008', 'Thực hành Tổ chức cấu trúc máy tính', 30),
('CS09009', 'Mạng máy tính', 45),
('CS09010', 'Phân tích thiết kế hệ thống thông tin', 45),
('GS09011', 'Đại cương văn hóa Việt Nam', 45),
('GS09012', 'Kỹ năng giao tiếp', 45),
('GS19001', 'Tiếng Anh 1', 45),
('GS19002', 'Tiếng Anh 2', 45),
('GS19003', 'Tiếng Anh 3', 45),
('GS19004', 'Tiếng Anh 4', 45),
('GS19006', 'Tiếng Anh cuối khóa', 45),
('GS29001', 'Pháp luật Việt Nam đại cương', 45),
('GS33001', 'Toán A1 (Hàm 1 biến, chuỗi)', 45),
('GS33002', 'Toán A2 (Hàm nhiều biến, giải tích vec tơ)', 45),
('GS33003', 'Toán A3 (Đại số tuyến tính)', 45),
('GS43001', 'Vật lý 1', 45),
('GS43002', 'Vật lý 2', 45),
('GS49004', 'Thí nghiệm Vật lý_Phần 1', 30),
('GS49005', 'Thí nghiệm Vật lý_Phần 2', 40),
('GS59001', 'Tin học đại cương', 45),
('GS59002', 'Thực hành Tin học đại cương', 30),
('GS79005', 'Triết học Mác - Lênin', 45),
('GS79006', 'Kinh tế chính trị Mác - Lênin', 45),
('GS79007', 'Chủ nghĩa xã hội khoa học', 45),
('GS79008', 'Lịch sử Đảng Cộng sản Việt Nam', 45),
('GS79009', 'Tư tưởng Hồ Chí Minh', 45),
('GS93003', 'Giáo dục thể chất 3', 45),
('GS93004', 'Giáo dục thể chất 4', 45),
('GS99001', 'Giáo dục thể chất 1', 45),
('GS99002', 'Giáo dục thể chất 2', 45);

-- --------------------------------------------------------

--
-- Table structure for table `ngay_le`
--

DROP TABLE IF EXISTS `ngay_le`;
CREATE TABLE IF NOT EXISTS `ngay_le` (
  `ngay` date NOT NULL,
  `so_ngay_nghi` int DEFAULT NULL,
  PRIMARY KEY (`ngay`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `ngay_le`
--

INSERT INTO `ngay_le` (`ngay`, `so_ngay_nghi`) VALUES
('2025-01-01', 1),
('2025-01-25', 9),
('2025-04-07', 1),
('2025-04-30', 1),
('2025-05-01', 1),
('2025-08-30', 4);

-- --------------------------------------------------------

--
-- Table structure for table `password_resets`
--

DROP TABLE IF EXISTS `password_resets`;
CREATE TABLE IF NOT EXISTS `password_resets` (
  `email` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `token` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`email`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

--
-- Dumping data for table `password_resets`
--

INSERT INTO `password_resets` (`email`, `token`, `created_at`) VALUES
('dh52002286@student.stu.edu.vn', '$2y$10$Rnofa8F/h1QIjfIryQnL6u/.nlumDJrnnIt3acBy1VWKTMXYTNvX6', '2024-08-13 03:17:59'),
('maiduchuy24092002@gmai.com', '$2y$10$fs5oGTpHBCmUIZwj.IMgiesoVn2RiyydEtwc2jDb30FmFnPCdo6cm', '2024-08-13 03:20:53'),
('hung.tranvan@stu.edu.vn', '$2y$10$JAB6Z5aAFVQefqUhqCVqK.IQLrIHKFJvkrCh9TX2ua.KjR91SMZWG', '2025-02-28 14:04:16');

-- --------------------------------------------------------

--
-- Table structure for table `qrcode`
--

DROP TABLE IF EXISTS `qrcode`;
CREATE TABLE IF NOT EXISTS `qrcode` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ma_tkb` int UNSIGNED NOT NULL,
  `thoi_gian_kt` timestamp NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_qrcode_tkb` (`ma_tkb`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=301 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- Table structure for table `sinh_vien`
--

DROP TABLE IF EXISTS `sinh_vien`;
CREATE TABLE IF NOT EXISTS `sinh_vien` (
  `ma_sv` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `ten_sv` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `ngay_sinh` date DEFAULT NULL,
  `phai` tinyint NOT NULL,
  `dia_chi` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `sdt` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `ma_lop` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ma_sv`) USING BTREE,
  UNIQUE KEY `sinh_vien_sdt_unique` (`sdt`) USING BTREE,
  UNIQUE KEY `sinh_vien_email_unique` (`email`) USING BTREE,
  KEY `sinh_vien_ma_lop_foreign` (`ma_lop`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

--
-- Dumping data for table `sinh_vien`
--

INSERT INTO `sinh_vien` (`ma_sv`, `ten_sv`, `ngay_sinh`, `phai`, `dia_chi`, `sdt`, `email`, `ma_lop`, `avatar`) VALUES
('DH52003250', 'Nguyễn Sơn Đăng Khoa', '1996-08-13', 1, 'Trà Vinh', '0994564187', 'DH52003250@student.stu.edu.vn', 'D20_TH03', NULL),
('DH52003421', 'Nguyễn Hữu Ngân', '1996-12-18', 0, 'Tây Ninh', '0311243657', 'DH52003421@student.stu.edu.vn', 'D21_TH01', NULL),
('DH52003606', 'Hồ Lê Đại Lượng', '1998-10-05', 1, 'Tuyên Quang', '0348356762', 'DH52003606@student.stu.edu.vn', 'D21_TH01', NULL),
('DH52005758', 'Nguyễn Vũ Ân Điển', '1995-03-22', 1, 'Bình Thuận', '0377467499', 'DH52005758@student.stu.edu.vn', 'D21_TH02', NULL),
('DH52005977', 'Nguyễn Ngọc Nghĩa', '1996-05-28', 1, 'Hà Nam', '0919063697', 'DH52005977@student.stu.edu.vn', 'D20_TH09', NULL),
('DH52006233', 'Võ Nhật Trường', '2000-12-26', 1, 'Hà Tĩnh', '0351037502', 'DH52006233@student.stu.edu.vn', 'D20_TH10', NULL),
('DH52006924', 'Trần Minh Hiếu', '2003-07-26', 1, 'Tiền Giang', '0392332352', 'DH52006924@student.stu.edu.vn', 'D20_TH11', NULL),
('DH52007055', 'Đặng Ngọc Long', '1995-09-29', 0, 'Vĩnh Phúc', '0354705635', 'DH52007055@student.stu.edu.vn', 'D20_TH11', NULL),
('DH52009028', 'Phạm Tuấn Đạt', '1995-07-12', 0, 'Phú Yên', '0357968889', 'DH52009028@student.stu.edu.vn', 'D21_TH01', NULL),
('DH52100001', 'Nguyễn Văn Trường An', '1999-04-04', 1, 'TP. Hồ Chí Minh', '0333591154', 'DH52100001@student.stu.edu.vn', 'D21_TH01', NULL),
('DH52100002', 'Trịnh Tiến Phúc', '1997-02-26', 0, 'Vĩnh Phúc', '0985893590', 'DH52100002@student.stu.edu.vn', 'D21_TH01', NULL),
('DH52100136', 'Khâu Minh Thư', '1997-10-02', 0, 'Hà Nam', '0926283303', 'DH52100136@student.stu.edu.vn', 'D21_TH01', NULL),
('DH52100180', 'Lý Kim Long', '2001-03-03', 0, 'Hưng Yên', '0988052550', 'DH52100180@student.stu.edu.vn', 'D21_TH02', NULL),
('DH52100231', 'Nguyễn Hoàng Phúc', '1999-03-10', 0, 'Phú Thọ', '0377255858', 'DH52100231@student.stu.edu.vn', 'D21_TH01', NULL),
('DH52100282', 'Nguyễn Trần Thanh Nhàn', '1998-01-12', 0, 'Nghệ An', '0953900637', 'DH52100282@student.stu.edu.vn', 'D21_TH01', NULL),
('DH52100402', 'Thái Ngọc Yên', '1995-03-31', 1, 'Vĩnh Long', '0977923336', 'DH52100402@student.stu.edu.vn', 'D21_TH01', NULL),
('DH52100449', 'Nguyễn Trần Gia Bảo', '2001-10-14', 0, 'Bình Dương', '0989513731', 'DH52100449@student.stu.edu.vn', 'D21_TH01', NULL),
('DH52100465', 'Nguyễn Ngọc Minh', '2003-02-26', 0, 'Thái Nguyên', '0395987954', 'DH52100465@student.stu.edu.vn', 'D21_TH02', NULL),
('DH52100524', 'Hàng Gia Thuận', '1995-08-18', 0, 'Ninh Bình', '0375584829', 'DH52100524@student.stu.edu.vn', 'D21_TH01', NULL),
('DH52100715', 'Phạm Minh Sang', '1995-07-22', 0, 'Cao Bằng', '0356376818', 'DH52100715@student.stu.edu.vn', 'D21_TH01', NULL),
('DH52100800', 'Thân Quốc Tú', '2004-02-15', 1, 'Quảng Bình', '0398768702', 'DH52100800@student.stu.edu.vn', 'D21_TH02', NULL),
('DH52100937', 'Nguyễn Xuân Long', '1998-12-09', 1, 'Bắc Giang', '0980873665', 'DH52100937@student.stu.edu.vn', 'D21_TH02', NULL),
('DH52100953', 'Đàm Huỳnh Minh Nghĩa', '2005-02-18', 1, 'Ninh Bình', '0966485768', 'DH52100953@student.stu.edu.vn', 'D21_TH01', NULL),
('DH52101018', 'Ôn Chung Kiên', '2004-02-23', 0, 'Sóc Trăng', '0957247154', 'DH52101018@student.stu.edu.vn', 'D21_TH01', NULL),
('DH52101039', 'Trần Quí Kiệt', '2005-04-16', 0, 'Bạc Liêu', '0999598476', 'DH52101039@student.stu.edu.vn', 'D21_TH03', NULL),
('DH52101246', 'Nguyễn Anh Minh', '2002-07-08', 1, 'Lào Cai', '0396168050', 'DH52101246@student.stu.edu.vn', 'D21_TH01', NULL),
('DH52101267', 'Lưu Ngọc Lan', '2005-05-16', 1, 'Cà Mau', '0990833077', 'DH52101267@student.stu.edu.vn', 'D21_TH02', NULL),
('DH52101402', 'Nguyễn Văn Hoàng Long', '2003-09-18', 0, 'Hải Dương', '0945950505', 'DH52101402@student.stu.edu.vn', 'D21_TH02', NULL),
('DH52101465', 'Quách Thái Hùng', '2005-10-02', 0, 'An Giang', '0354294112', 'DH52101465@student.stu.edu.vn', 'D21_TH02', NULL),
('DH52101490', 'Phan Dương Khang', '1996-07-31', 1, 'Nghệ An', '0357212532', 'DH52101490@student.stu.edu.vn', 'D21_TH03', NULL),
('DH52101498', 'Phạm Hoàng Lịch', '2004-02-12', 1, 'Phú Thọ', '0312326998', 'DH52101498@student.stu.edu.vn', 'D21_TH01', NULL),
('DH52101856', 'Nguyễn Duy Bản', '1995-11-24', 1, 'Huế', '0990145551', 'DH52101856@student.stu.edu.vn', 'D21_TH03', NULL),
('DH52101870', 'Hứa Vinh Thắng', '2005-05-27', 0, 'Yên Bái', '0375684134', 'DH52101870@student.stu.edu.vn', 'D21_TH02', NULL),
('DH52101979', 'Phạm Thị Ánh Hồng', '2004-06-13', 1, 'Quảng Ngãi', '0327876326', 'DH52101979@student.stu.edu.vn', 'D21_TH02', NULL),
('DH52102050', 'Nguyễn Quốc Hào', '2001-06-10', 1, 'Bình Dương', '0388630405', 'DH52102050@student.stu.edu.vn', 'D21_TH07', NULL),
('DH52102172', 'Văn Thị Thu Oanh', '1998-06-02', 1, 'Quảng Bình', '0916130413', 'DH52102172@student.stu.edu.vn', 'D21_TH01', NULL),
('DH52102314', 'Tống Thanh Bình', '1999-06-09', 1, 'Quảng Nam', '0313570982', 'DH52102314@student.stu.edu.vn', 'D21_TH01', NULL),
('DH52102644', 'Phạm Đình Lan Khương', '2001-10-26', 0, 'Hậu Giang', '0333581161', 'DH52102644@student.stu.edu.vn', 'D21_TH01', NULL),
('DH52102716', 'Thái Tín Khang', '1999-01-09', 1, 'Hà Tĩnh', '0322860275', 'DH52102716@student.stu.edu.vn', 'D21_TH01', NULL),
('DH52102758', 'Lê Thị Nguyên', '1996-02-05', 1, 'Thanh Hóa', '0396722539', 'DH52102758@student.stu.edu.vn', 'D21_TH01', NULL),
('DH52102853', 'Dương Lê Văn', '1998-08-04', 0, 'Đồng Tháp', '0958274909', 'DH52102853@student.stu.edu.vn', 'D21_TH01', NULL),
('DH52103137', 'Phan Tuấn Dũng', '1995-07-14', 0, 'Kiên Giang', '0314372781', 'DH52103137@student.stu.edu.vn', 'D21_TH01', NULL),
('DH52103289', 'Nguyễn Quốc Hoàng', '1995-10-27', 1, 'Quảng Ninh', '0927402326', 'DH52103289@student.stu.edu.vn', 'D21_TH03', NULL),
('DH52103378', 'Mai Thiện Thành', '1998-12-29', 0, 'Lào Cai', '0338357143', 'DH52103378@student.stu.edu.vn', 'D21_TH01', NULL),
('DH52103544', 'Nguyễn Đức Phong', '2002-01-28', 1, 'Vĩnh Long', '0969006874', 'DH52103544@student.stu.edu.vn', 'D21_TH01', NULL),
('DH52103613', 'Lầu Ngọc Phú', '1999-05-29', 1, 'Trà Vinh', '0932291859', 'DH52103613@student.stu.edu.vn', 'D21_TH02', NULL),
('DH52103679', 'Lê Hoàng Phúc', '1995-07-22', 1, 'Cà Mau', '0985681108', 'DH52103679@student.stu.edu.vn', 'D21_TH01', NULL),
('DH52103682', 'Bùi Minh Phúc', '2003-09-09', 1, 'Hậu Giang', '0944652621', 'DH52103682@student.stu.edu.vn', 'D21_TH01', NULL),
('DH52103699', 'Nguyễn Minh Luân', '1999-01-14', 1, 'Bắc Kạn', '0958222433', 'DH52103699@student.stu.edu.vn', 'D21_TH01', NULL),
('DH52103727', 'Đào Duy Hoàng Vương', '1997-05-25', 1, 'Trà Vinh', '0950625435', 'DH52103727@student.stu.edu.vn', 'D21_TH03', NULL),
('DH52103820', 'Trương Trần Anh Phúc', '2002-07-27', 0, 'Hưng Yên', '0344795382', 'DH52103820@student.stu.edu.vn', 'D21_TH02', NULL),
('DH52104108', 'Nguyễn Đăng Khoa', '1998-09-29', 0, 'Vĩnh Long', '0994118607', 'DH52104108@student.stu.edu.vn', 'D21_TH02', NULL),
('DH52104132', 'Phan Thành Nam', '2001-06-07', 0, 'Bình Phước', '0340539760', 'DH52104132@student.stu.edu.vn', 'D21_TH01', NULL),
('DH52104319', 'Lê Văn Điều', '1998-04-29', 1, 'Chưa cập nhật', '0926788064', 'DH52104319@student.stu.edu.vn', 'D21_TH01', NULL),
('DH52104569', 'Nguyễn Trọng Khiêm', '2004-04-09', 0, 'Đồng Tháp', '0337643813', 'DH52104569@student.stu.edu.vn', 'D21_TH02', NULL),
('DH52104952', 'Lê Anh Vũ', '1998-03-19', 0, 'Vĩnh Long', '0986294377', 'DH52104952@student.stu.edu.vn', 'D21_TH01', NULL),
('DH52105054', 'Đặng Anh Hào', '2000-09-11', 1, 'Nha Trang', '0312517916', 'DH52105054@student.stu.edu.vn', 'D21_TH01', NULL),
('DH52105079', 'Lê Nhựt Anh', '1998-04-28', 0, 'Đà Nẵng', '0950874151', 'DH52105079@student.stu.edu.vn', 'D21_TH02', NULL),
('DH52105095', 'Nguyễn Cảnh Thịnh', '2004-03-20', 0, 'Thái Nguyên', '0344367960', 'DH52105095@student.stu.edu.vn', 'D21_TH01', NULL),
('DH52105426', 'Nguyễn Lê Tiến Dũng', '2004-11-05', 0, 'Tiền Giang', '0922694283', 'DH52105426@student.stu.edu.vn', 'D21_TH01', NULL),
('DH52105659', 'Bạch Đức Phước', '2003-09-10', 1, 'Hải Dương', '0397764974', 'DH52105659@student.stu.edu.vn', 'D21_TH03', NULL),
('DH52105684', 'Lê Văn Hoàng Hiệp', '1999-09-06', 1, 'Quảng Nam', '0347777411', 'DH52105684@student.stu.edu.vn', 'D21_TH02', NULL),
('DH52105864', 'Mô Ham Mách A Ra Pát', '2005-09-01', 1, 'Quảng Trị', '0914660460', 'DH52105864@student.stu.edu.vn', 'D21_TH02', NULL),
('DH52106235', 'Nguyễn Thành Thông', '1998-02-27', 0, 'Tây Ninh', '0341025762', 'DH52106235@student.stu.edu.vn', 'D21_TH01', NULL),
('DH52106256', 'Lê Thanh Nguyên', '1996-11-19', 1, 'Nam Định', '0987599657', 'DH52106256@student.stu.edu.vn', 'D21_TH02', NULL),
('DH52106268', 'Trịnh Thanh Trúc', '2004-09-25', 0, 'Nghệ An', '0332771209', 'DH52106268@student.stu.edu.vn', 'D21_TH04', NULL),
('DH52106284', 'Châu Vĩnh Phong', '2002-02-04', 1, 'Đồng Tháp', '0394765932', 'DH52106284@student.stu.edu.vn', 'D21_TH02', NULL),
('DH52106669', 'Nguyễn Phan Thành Đồng', '1999-11-22', 0, 'Đà Nẵng', '0387997871', 'DH52106669@student.stu.edu.vn', 'D21_TH06', NULL),
('DH52106734', 'Đặng Đức Trí', '2005-09-03', 1, 'Thanh Hóa', '0967666939', 'DH52106734@student.stu.edu.vn', 'D21_TH02', NULL),
('DH52106740', 'Trần Hoàng Long', '2004-06-08', 1, 'Lạng Sơn', '0346987513', 'DH52106740@student.stu.edu.vn', 'D21_TH03', NULL),
('DH52106813', 'Đỗ Ngọc Đình', '2000-09-14', 1, 'Hà Nội', '0332696170', 'DH52106813@student.stu.edu.vn', 'D21_TH02', NULL),
('DH52107207', 'Lê Đức Tâm', '2002-05-21', 1, 'Tuyên Quang', '0386679074', 'DH52107207@student.stu.edu.vn', 'D21_TH01', NULL),
('DH52107294', 'Lê Võ Đại', '1995-08-20', 1, 'Quảng Ninh', '0344942291', 'DH52107294@student.stu.edu.vn', 'D21_TH02', NULL),
('DH52107408', 'Trần Minh Tú', '2002-05-09', 1, 'Quảng Trị', '0917665462', 'DH52107408@student.stu.edu.vn', 'D21_TH02', NULL),
('DH52107414', 'Nguyễn Thành Nam', '1995-10-13', 1, 'Quảng Trị', '0369546702', 'DH52107414@student.stu.edu.vn', 'D21_TH05', NULL),
('DH52107527', 'Võ Trường Giang', '2005-09-21', 0, 'Hải Phòng', '0351292355', 'DH52107527@student.stu.edu.vn', 'D21_TH02', NULL),
('DH52107557', 'Võ Xuân Huy', '1997-01-11', 0, 'Thái Bình', '0344020148', 'DH52107557@student.stu.edu.vn', 'D21_TH03', NULL),
('DH52107715', 'Trần Văn Hoàng', '1996-10-26', 1, 'Bình Thuận', '0311344953', 'DH52107715@student.stu.edu.vn', 'D21_TH03', NULL),
('DH52107853', 'Bùi Quang Quý', '1996-12-17', 0, 'Lạng Sơn', '0943214612', 'DH52107853@student.stu.edu.vn', 'D21_TH04', NULL),
('DH52107858', 'Mai Hữu Hiếu', '2001-03-12', 0, 'Long An', '0911877999', 'DH52107858@student.stu.edu.vn', 'D21_TH04', NULL),
('DH52107926', 'Nguyễn Văn Huy', '2004-11-03', 1, 'Bắc Ninh', '0939969171', 'DH52107926@student.stu.edu.vn', 'D21_TH05', NULL),
('DH52108371', 'Nguyễn Tấn Hoàng', '2004-02-13', 1, 'Đắk Lắk', '0958423886', 'DH52108371@student.stu.edu.vn', 'D21_TH04', NULL),
('DH52108549', 'Đặng Thành Hải', '1997-03-16', 0, 'Huế', '0361905171', 'DH52108549@student.stu.edu.vn', 'D21_TH06', NULL),
('DH52108640', 'Nguyễn Thanh Trường', '2003-02-08', 1, 'Q8, HCM', '0981907754', 'dh52108640@student.stu.edu.vn', 'D21_TH06', NULL),
('DH52108750', 'Phan Thế Quang', '1996-06-23', 1, 'Bắc Giang', '0341469231', 'DH52108750@student.stu.edu.vn', 'D21_TH07', NULL),
('DH52108789', 'Hà Mạnh Lộc', '1998-10-31', 0, 'Cao Bằng', '0382890571', 'DH52108789@student.stu.edu.vn', 'D21_TH06', NULL),
('DH52108823', 'Kiều Quang Hiệp', '1997-02-18', 1, 'Vũng Tàu', '0340592431', 'DH52108823@student.stu.edu.vn', 'D21_TH06', NULL),
('DH52110568', 'Phạm Minh Anh', '1999-02-14', 0, 'Cần Thơ', '0978118253', 'DH52110568@student.stu.edu.vn', 'D21_TH05', NULL),
('DH52110579', 'Đinh Xuân Phước Ân', '1996-10-10', 0, 'Hải Phòng', '0948406453', 'DH52110579@student.stu.edu.vn', 'D21_TH13', NULL),
('DH52110602', 'Nguyễn Hoàng Bảo', '1998-05-06', 0, 'Nha Trang', '0382410735', 'DH52110602@student.stu.edu.vn', 'D21_TH05', NULL),
('DH52110616', 'Vương Hữu Quốc Bảo', '2000-07-13', 0, 'Vũng Tàu', '0943654659', 'DH52110616@student.stu.edu.vn', 'D21_TH05', NULL),
('DH52110640', 'Hà Thị Mỹ Châu', '1996-01-29', 1, 'Đồng Nai', '0317435508', 'DH52110640@student.stu.edu.vn', 'D21_TH05', NULL),
('DH52110649', 'Nguyễn Thành Công', '2002-12-30', 0, 'Long An', '0390450793', 'DH52110649@student.stu.edu.vn', 'D21_TH05', NULL),
('DH52110742', 'Nguyễn Quốc Đại', '2003-11-15', 1, 'Đắk Lắk', '0381228889', 'DH52110742@student.stu.edu.vn', 'D21_TH14', NULL),
('DH52110839', 'Lê Thanh Giang', '1995-10-13', 1, 'Cần Thơ', '0361787976', 'DH52110839@student.stu.edu.vn', 'D21_TH06', NULL),
('DH52110891', 'Nguyễn Hoàng Hiệp', '2000-09-15', 0, 'Đồng Nai', '0968459131', 'DH52110891@student.stu.edu.vn', 'D21_TH06', NULL),
('DH52110952', 'Đoàn Việt Hoàng', '2005-07-15', 1, 'Kiên Giang', '0367626499', 'DH52110952@student.stu.edu.vn', 'D21_TH07', NULL),
('DH52111036', 'Nguyễn Xuân Huy', '2003-12-11', 1, 'Bến Tre', '0325782353', 'DH52111036@student.stu.edu.vn', 'D21_TH11', NULL),
('DH52111058', 'Đào Ngọc Hưng', '2002-05-27', 0, 'Nam Định', '0999281921', 'DH52111058@student.stu.edu.vn', 'D21_TH14', NULL),
('DH52111115', 'Mai Lâm Quang Khánh', '2003-09-04', 0, 'Quảng Bình', '0351880328', 'DH52111115@student.stu.edu.vn', 'D21_TH10', NULL),
('DH52111306', 'Nguyễn Bảo Minh', '1998-06-19', 0, 'Yên Bái', '0925866203', 'DH52111306@student.stu.edu.vn', 'D21_TH13', NULL),
('DH52111438', 'Trần Tâm Nhiên', '2003-11-01', 0, 'Hà Tĩnh', '0370415007', 'DH52111438@student.stu.edu.vn', 'D21_TH10', NULL),
('DH52111515', 'Cao Hoàng Phúc', '2004-09-29', 0, 'Sóc Trăng', '0943536214', 'DH52111515@student.stu.edu.vn', 'D21_TH13', NULL),
('DH52111518', 'Hồ Nguyễn Hoàng Phúc', '2003-09-25', 1, 'Bạc Liêu', '0323668079', 'DH52111518@student.stu.edu.vn', 'D21_TH13', NULL),
('DH52111686', 'Nguyễn Đình Tài', '1999-02-22', 0, 'Bắc Kạn', '0929267712', 'DH52111686@student.stu.edu.vn', 'D21_TH13', NULL),
('DH52111814', 'Nguyễn Trần Phúc Thịnh', '1995-09-10', 1, 'Quảng Trị', '0396324813', 'DH52111814@student.stu.edu.vn', 'D21_TH12', NULL),
('DH52111817', 'Trần Gia Thịnh', '2002-09-27', 1, 'Bình Phước', '0353824925', 'DH52111817@student.stu.edu.vn', 'D21_TH12', NULL),
('DH52111900', 'Nguyễn Công Toại', '2001-02-17', 1, 'Nam Định', '0934215285', 'DH52111900@student.stu.edu.vn', 'D21_TH12', NULL),
('DH52112771', 'Phan Văn Đông', '1998-01-11', 0, 'TP. Hồ Chí Minh', '0344561329', 'DH52112771@student.stu.edu.vn', 'D21_TH06', NULL);

--
-- Triggers `sinh_vien`
--
DROP TRIGGER IF EXISTS `after_update_sinh_vien`;
DELIMITER $$
CREATE TRIGGER `after_update_sinh_vien` AFTER UPDATE ON `sinh_vien` FOR EACH ROW BEGIN
  UPDATE users
  SET 
    email = NEW.email
  WHERE ma_sv = NEW.ma_sv;
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `before_delete_sinh_vien`;
DELIMITER $$
CREATE TRIGGER `before_delete_sinh_vien` BEFORE DELETE ON `sinh_vien` FOR EACH ROW BEGIN
  -- Xóa trước các dòng liên quan trong bảng 'lich_hoc'
  DELETE FROM lich_hoc WHERE ma_sv = OLD.ma_sv;

  -- Xóa các dòng liên quan trong bảng 'users'
  DELETE FROM users WHERE ma_sv = OLD.ma_sv;

  -- Xóa điểm danh (diem_danh) theo ma_sv
  DELETE FROM diem_danh WHERE ma_sv = OLD.ma_sv;

  -- Nếu có bảng khác chứa ma_sv thì xử lý thêm tại đây
  -- Ví dụ: DELETE FROM some_table WHERE ma_sv = OLD.ma_sv;

END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `tkb`
--

DROP TABLE IF EXISTS `tkb`;
CREATE TABLE IF NOT EXISTS `tkb` (
  `ma_tkb` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `ma_gd` int UNSIGNED NOT NULL,
  `ngay_hoc` date NOT NULL,
  `phong_hoc` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `st_bd` int NOT NULL,
  `st_kt` int NOT NULL,
  `ghi_chu` tinytext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`ma_tkb`) USING BTREE,
  KEY `tkb_ma_gd_foreign` (`ma_gd`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1449 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `ma_sv` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ma_gv` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `email_verified_at` timestamp NULL DEFAULT NULL,
  `password` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `role` tinytext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `remember_token` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `users_email_unique` (`email`) USING BTREE,
  UNIQUE KEY `users_username_unique` (`username`) USING BTREE,
  KEY `users_ma_sv_foreign` (`ma_sv`) USING BTREE,
  KEY `users_ma_gv_foreign` (`ma_gv`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10438 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `ma_sv`, `ma_gv`, `email`, `email_verified_at`, `password`, `role`, `remember_token`, `created_at`, `updated_at`) VALUES
(10326, 'hung', NULL, 'hung', 'truonggenz2003@gmail.com', NULL, '$2a$10$6qn6xESH/Sv3.dR1W.8Ph.qW8vVQBCU6b6km2g.U4/vQuUJ9tnLna', 'teacher', NULL, '2025-07-19 11:26:06', '2025-07-19 11:26:40'),
(10327, 'Nguyễn Tha', 'DH52108640', NULL, 'dh52108640@student.stu.edu.vn', NULL, '$2a$10$MteIyhzypNJPKZsBMBFVAeREP4l/UgRchWEPatjKmHGAU2./bKr5i', 'student', NULL, '2025-07-19 11:57:38', '2025-07-19 12:07:04'),
(10328, 'DH52100001', 'DH52100001', NULL, 'DH52100001@student.stu.edu.vn', NULL, '$2a$10$SYEsS/7mZKl5zJgTA0G2guO5L5YhP78TiK8O22/SB5k50Woj4nNsi', 'student', NULL, '2025-07-19 12:08:55', '2025-07-19 12:08:55'),
(10329, 'DH52105079', 'DH52105079', NULL, 'DH52105079@student.stu.edu.vn', NULL, '$2a$10$7uLWgEUskwo.tauX1tuXHefN9AQWThcPNBhJia4/TNVzUHmIJQbWC', 'student', NULL, '2025-07-19 12:08:55', '2025-07-19 12:08:55'),
(10330, 'DH52110568', 'DH52110568', NULL, 'DH52110568@student.stu.edu.vn', NULL, '$2a$10$bvuhpnrHGtoka8dKxCWpte1blijthKlHzpHgpIY0DZ166Ng8zqGAu', 'student', NULL, '2025-07-19 12:08:55', '2025-07-19 12:08:55'),
(10331, 'DH52110579', 'DH52110579', NULL, 'DH52110579@student.stu.edu.vn', NULL, '$2a$10$DDRWf99Q1HojrTJjbYdmYenPio4VkflsukIa37iHCEGkWhI5iZ4Sq', 'student', NULL, '2025-07-19 12:08:55', '2025-07-19 12:08:55'),
(10332, 'DH52101856', 'DH52101856', NULL, 'DH52101856@student.stu.edu.vn', NULL, '$2a$10$RpzhBRIRJ7KQmmQKPNp/m.M0.qCO88pU8t3.JG66q7W691a9ih3hW', 'student', NULL, '2025-07-19 12:08:55', '2025-07-19 12:08:55'),
(10333, 'DH52110602', 'DH52110602', NULL, 'DH52110602@student.stu.edu.vn', NULL, '$2a$10$eeABvwgd1BN5NgmZM1Q4IeJ.IrnIe.CqElB1h2HnMF63ugpmgET7y', 'student', NULL, '2025-07-19 12:08:55', '2025-07-19 12:08:55'),
(10334, 'DH52100449', 'DH52100449', NULL, 'DH52100449@student.stu.edu.vn', NULL, '$2a$10$PjYDNfgnRhqtRt3vYQV/Z.vgsjMAVQWM/gbnU0rUCn2oFSVLLg29S', 'student', NULL, '2025-07-19 12:08:55', '2025-07-19 12:08:55'),
(10335, 'DH52110616', 'DH52110616', NULL, 'DH52110616@student.stu.edu.vn', NULL, '$2a$10$1EtVuRTT0Zg0Qh5s3J3.W.w1ltoqqIzG1PT1bT4VDi/v.b2razeSC', 'student', NULL, '2025-07-19 12:08:55', '2025-07-19 12:08:55'),
(10336, 'DH52102314', 'DH52102314', NULL, 'DH52102314@student.stu.edu.vn', NULL, '$2a$10$gyzsLcQOfO1Ocl5V26l2nuIsTdx8TxfRA7XzKqC6i/7yTuDA7vb1.', 'student', NULL, '2025-07-19 12:08:56', '2025-07-19 12:08:56'),
(10337, 'DH52110640', 'DH52110640', NULL, 'DH52110640@student.stu.edu.vn', NULL, '$2a$10$pW/PWhsrGy9URHMoBC4v8O2m//4Z7q9Vw5Z2Y/HnCy7xwgShZJZdu', 'student', NULL, '2025-07-19 12:08:56', '2025-07-19 12:08:56'),
(10338, 'DH52110649', 'DH52110649', NULL, 'DH52110649@student.stu.edu.vn', NULL, '$2a$10$Yd/0Dj25dfuX1ao2.fcv1ejR7dNDHiWY1GVVtbXd0y1axwmBBzM/y', 'student', NULL, '2025-07-19 12:08:56', '2025-07-19 12:08:56'),
(10339, 'DH52105426', 'DH52105426', NULL, 'DH52105426@student.stu.edu.vn', NULL, '$2a$10$yR.8WGe2qPdhPpQCq2IrzOggdqxjmQXB6FKoSJX1XNqaqrcCeU54q', 'student', NULL, '2025-07-19 12:08:56', '2025-07-19 12:08:56'),
(10340, 'DH52103137', 'DH52103137', NULL, 'DH52103137@student.stu.edu.vn', NULL, '$2a$10$zKV4QDZAVrAZp9Hn9toBlO06ncgI7B1dkLb.EApAGIzLkogOvzEbS', 'student', NULL, '2025-07-19 12:08:56', '2025-07-19 12:08:56'),
(10341, 'DH52107294', 'DH52107294', NULL, 'DH52107294@student.stu.edu.vn', NULL, '$2a$10$v081apFcyGFZ3ELZF5JsoOHoewZ4UaUpowUKCS6FdisfogX3syU6O', 'student', NULL, '2025-07-19 12:08:56', '2025-07-19 12:08:56'),
(10342, 'DH52110742', 'DH52110742', NULL, 'DH52110742@student.stu.edu.vn', NULL, '$2a$10$SQG.JB//rmQY3RruO0QIg.XRd9sAcit5RGZFGDrhsym0DotliUI3a', 'student', NULL, '2025-07-19 12:08:56', '2025-07-19 12:08:56'),
(10343, 'DH52009028', 'DH52009028', NULL, 'DH52009028@student.stu.edu.vn', NULL, '$2a$10$8TjLMBS6SYAalR8BbKV.PuzWhztJNeT6UAaxE1yUMzMyfyfiJz2Fa', 'student', NULL, '2025-07-19 12:08:56', '2025-07-19 12:08:56'),
(10344, 'DH52005758', 'DH52005758', NULL, 'DH52005758@student.stu.edu.vn', NULL, '$2a$10$u8xe4HN3MSMoz3uJGfu7d.gKgfZUz08zhDEDXsi8JyTx3dSeoBjRG', 'student', NULL, '2025-07-19 12:08:57', '2025-07-19 12:08:57'),
(10345, 'DH52104319', 'DH52104319', NULL, 'DH52104319@student.stu.edu.vn', NULL, '$2a$10$flA/m.br8isFkX/y0b4BMuflV/8XWXakCjURlH3pHNo96H9gQMqcW', 'student', NULL, '2025-07-19 12:08:57', '2025-07-19 12:08:57'),
(10346, 'DH52106813', 'DH52106813', NULL, 'DH52106813@student.stu.edu.vn', NULL, '$2a$10$dZbkunnNUmAxBzQBCITN1el1cgOhx0wFM.SG3X5qwA4zhc3syKxEa', 'student', NULL, '2025-07-19 12:08:57', '2025-07-19 12:08:57'),
(10347, 'DH52112771', 'DH52112771', NULL, 'DH52112771@student.stu.edu.vn', NULL, '$2a$10$yRcdLaQv40qRrU/jHDG1sOlS.8AXTZVArhvEdqfvUKpXiYXAhu1ya', 'student', NULL, '2025-07-19 12:08:57', '2025-07-19 12:08:57'),
(10348, 'DH52106669', 'DH52106669', NULL, 'DH52106669@student.stu.edu.vn', NULL, '$2a$10$UcUa1MHwA2CaGQtcLMvxVeULCpLAkjhWXKPUwWL75.bKXzJphOOXG', 'student', NULL, '2025-07-19 12:08:57', '2025-07-19 12:08:57'),
(10349, 'DH52110839', 'DH52110839', NULL, 'DH52110839@student.stu.edu.vn', NULL, '$2a$10$7CbsN/i81vZFYj3f71Gsxe2cnmFoxhSp89rZqzHYXh7WDNDVVlnO6', 'student', NULL, '2025-07-19 12:08:57', '2025-07-19 12:08:57'),
(10350, 'DH52107527', 'DH52107527', NULL, 'DH52107527@student.stu.edu.vn', NULL, '$2a$10$eUc3Jtaq2c81QZqG5HmhoOG9Z8nnCod3DPPFNgPUzQJGjfhduYnPy', 'student', NULL, '2025-07-19 12:08:57', '2025-07-19 12:08:57'),
(10351, 'DH52108549', 'DH52108549', NULL, 'DH52108549@student.stu.edu.vn', NULL, '$2a$10$osmYgWBvuh6oX5J2qN7U0.Z.DFLF.5VcRE76aiNpewVu/RqDsy.6e', 'student', NULL, '2025-07-19 12:08:57', '2025-07-19 12:08:57'),
(10352, 'DH52105054', 'DH52105054', NULL, 'DH52105054@student.stu.edu.vn', NULL, '$2a$10$v9SwrgE7A7bo6QY2MC2gmOQ0AdQLAkkkoRaUjtTp.d4QG88nASdgu', 'student', NULL, '2025-07-19 12:08:58', '2025-07-19 12:08:58'),
(10353, 'DH52102050', 'DH52102050', NULL, 'DH52102050@student.stu.edu.vn', NULL, '$2a$10$4wKVdPVAb2/rUxitxDwRFuTBdJlyeeQbfcU8qx/vJd6UdaenUNeX.', 'student', NULL, '2025-07-19 12:08:58', '2025-07-19 12:08:58'),
(10354, 'DH52108823', 'DH52108823', NULL, 'DH52108823@student.stu.edu.vn', NULL, '$2a$10$7.7K0hcDQgAOrL7oolhCFOZauAy6aTxA8CGkJ7ty9Df7Fnn7ElZSW', 'student', NULL, '2025-07-19 12:08:58', '2025-07-19 12:08:58'),
(10355, 'DH52105684', 'DH52105684', NULL, 'DH52105684@student.stu.edu.vn', NULL, '$2a$10$B.ES1lQ6BuLYbXizXz49senNVGHp4DSZEs8hbmEtuyqKE/jvZeeCy', 'student', NULL, '2025-07-19 12:08:58', '2025-07-19 12:08:58'),
(10356, 'DH52110891', 'DH52110891', NULL, 'DH52110891@student.stu.edu.vn', NULL, '$2a$10$2EuNIZwav1xnoc09h6t6zub6LnltHe.Jv9KjhbCMDywc06iYtd3k2', 'student', NULL, '2025-07-19 12:08:58', '2025-07-19 12:08:58'),
(10357, 'DH52107858', 'DH52107858', NULL, 'DH52107858@student.stu.edu.vn', NULL, '$2a$10$8E4kQAkCZuwcc1c2pkPw5uczb/P6cmxq623t/qcOzi9Z0Y.RShic2', 'student', NULL, '2025-07-19 12:08:58', '2025-07-19 12:08:58'),
(10358, 'DH52006924', 'DH52006924', NULL, 'DH52006924@student.stu.edu.vn', NULL, '$2a$10$502Wc62y5gO5fTekQrGoguYuowT7f26P8F98Qm/dn.iCRzEXlle4q', 'student', NULL, '2025-07-19 12:08:58', '2025-07-19 12:08:58'),
(10359, 'DH52110952', 'DH52110952', NULL, 'DH52110952@student.stu.edu.vn', NULL, '$2a$10$4E3akT7.y0v/J6hxdcMM4eQ1xzGUNhqXttcSPFhUoW70/p8J/3Pq.', 'student', NULL, '2025-07-19 12:08:58', '2025-07-19 12:08:58'),
(10360, 'DH52103289', 'DH52103289', NULL, 'DH52103289@student.stu.edu.vn', NULL, '$2a$10$X.rVczuielsCUgs7RAgqxeP2OLDw.299RnxEKHhYhr6CgRoCK7Bdy', 'student', NULL, '2025-07-19 12:08:59', '2025-07-19 12:08:59'),
(10361, 'DH52108371', 'DH52108371', NULL, 'DH52108371@student.stu.edu.vn', NULL, '$2a$10$rsxPRHxBXuGmxz4gIMykLOAN8N2zLiw35uyWfJOylcQoJ/PZQ0yXq', 'student', NULL, '2025-07-19 12:08:59', '2025-07-19 12:08:59'),
(10362, 'DH52107715', 'DH52107715', NULL, 'DH52107715@student.stu.edu.vn', NULL, '$2a$10$5lKLNOzrj7OizmCM8xLKHO9XBkKU51k2KB00f6jGV/MAlOA41pQlC', 'student', NULL, '2025-07-19 12:08:59', '2025-07-19 12:08:59'),
(10363, 'DH52101979', 'DH52101979', NULL, 'DH52101979@student.stu.edu.vn', NULL, '$2a$10$A40nnEylNIwthGRo5Nl4ne3nJFuwMb1XjRjse/8e6dHxo6od9fcI2', 'student', NULL, '2025-07-19 12:08:59', '2025-07-19 12:08:59'),
(10364, 'DH52101465', 'DH52101465', NULL, 'DH52101465@student.stu.edu.vn', NULL, '$2a$10$PnTdXAJko/LIeyVXMlJdK.TjbBwjSjrDkqq3sAdTY1Uatsj3hFrXC', 'student', NULL, '2025-07-19 12:08:59', '2025-07-19 12:08:59'),
(10365, 'DH52107926', 'DH52107926', NULL, 'DH52107926@student.stu.edu.vn', NULL, '$2a$10$y81gR2c/B7DkdwcpBiJgb.K9HUfBdzcq0QsOmT6VV5YB0OUs49dzG', 'student', NULL, '2025-07-19 12:08:59', '2025-07-19 12:08:59'),
(10366, 'DH52111036', 'DH52111036', NULL, 'DH52111036@student.stu.edu.vn', NULL, '$2a$10$0.cjSLKEYb15pFS9YPI2e.irDyKV5WihQ9x1jgTNB7IFai7l6JEUW', 'student', NULL, '2025-07-19 12:08:59', '2025-07-19 12:08:59'),
(10367, 'DH52107557', 'DH52107557', NULL, 'DH52107557@student.stu.edu.vn', NULL, '$2a$10$Okjy09Dej2/G710jyg233uJNKDO27CZi9UQkpQYR6CENYe9CGQxFa', 'student', NULL, '2025-07-19 12:08:59', '2025-07-19 12:08:59'),
(10368, 'DH52111058', 'DH52111058', NULL, 'DH52111058@student.stu.edu.vn', NULL, '$2a$10$DoQ2svpALmAOZf/VGa5y/.FNHsM6mcn201X3JLJdOI8U5O7KGJCm2', 'student', NULL, '2025-07-19 12:09:00', '2025-07-19 12:09:00'),
(10369, 'DH52101490', 'DH52101490', NULL, 'DH52101490@student.stu.edu.vn', NULL, '$2a$10$VGCgbfE.shr/2EJ0m7H8XObGjMdpq810FBLKtBu2dbHiLo22hRc62', 'student', NULL, '2025-07-19 12:09:00', '2025-07-19 12:09:00'),
(10370, 'DH52102716', 'DH52102716', NULL, 'DH52102716@student.stu.edu.vn', NULL, '$2a$10$yEyQEDSqLKnDhWGC2OmG/.HUOt9axnOSIc7t4dffLs0NrkkOkf.a6', 'student', NULL, '2025-07-19 12:09:00', '2025-07-19 12:09:00'),
(10371, 'DH52111115', 'DH52111115', NULL, 'DH52111115@student.stu.edu.vn', NULL, '$2a$10$mnDm1cEHfKWapE6p/pJl.OJ5uPEFQY2jHbAVGWj9kiMEPLeMjMhOi', 'student', NULL, '2025-07-19 12:09:00', '2025-07-19 12:09:00'),
(10372, 'DH52104569', 'DH52104569', NULL, 'DH52104569@student.stu.edu.vn', NULL, '$2a$10$.VQtutZiiegeMjRFmCbYRuVi3Oyj/iGRXQVoEueI.PLXX.gVfY8da', 'student', NULL, '2025-07-19 12:09:00', '2025-07-19 12:09:00'),
(10373, 'DH52104108', 'DH52104108', NULL, 'DH52104108@student.stu.edu.vn', NULL, '$2a$10$uKMouoqHP.LWtGv.t1ILt.iNj5MtJGg1xnbwvuONNMwJ.8aaXB8La', 'student', NULL, '2025-07-19 12:09:00', '2025-07-19 12:09:00'),
(10375, 'DH52102644', 'DH52102644', NULL, 'DH52102644@student.stu.edu.vn', NULL, '$2a$10$2./3oJ24.5UrYcgy1FI3Xe3MGyGfL65.9FF/.OCi6/zbGYkS6qs9G', 'student', NULL, '2025-07-19 12:09:00', '2025-07-19 12:09:00'),
(10376, 'DH52101018', 'DH52101018', NULL, 'DH52101018@student.stu.edu.vn', NULL, '$2a$10$2nlXXbGiHPo5EYVhZX5/9e6VuB8YmQIlXqXJY4qALF2BVZUKM3PPO', 'student', NULL, '2025-07-19 12:09:01', '2025-07-19 12:09:01'),
(10377, 'DH52101039', 'DH52101039', NULL, 'DH52101039@student.stu.edu.vn', NULL, '$2a$10$bnbbAfOVTzObbq49R7bNcOARAmSu4EmUO6Lx/7OFAHvNdBr10Muqu', 'student', NULL, '2025-07-19 12:09:01', '2025-07-19 12:09:01'),
(10378, 'DH52101267', 'DH52101267', NULL, 'DH52101267@student.stu.edu.vn', NULL, '$2a$10$6SDr3um0SamgGFGp4xloyO6QyO.fkwc4m7xPubPd4j.mhbiAShYjK', 'student', NULL, '2025-07-19 12:09:01', '2025-07-19 12:09:01'),
(10379, 'DH52101498', 'DH52101498', NULL, 'DH52101498@student.stu.edu.vn', NULL, '$2a$10$ef8l86AoOgmE42LfEqCW/OUkfEPniutCcHm.CUZZREAuM.y/pi0Ky', 'student', NULL, '2025-07-19 12:09:01', '2025-07-19 12:09:01'),
(10380, 'DH52007055', 'DH52007055', NULL, 'DH52007055@student.stu.edu.vn', NULL, '$2a$10$j6Uy2N1/CJZA0WhsuMC3l.OVvXwIe5bcoanwZWCCCjSyFah4ZSoAO', 'student', NULL, '2025-07-19 12:09:01', '2025-07-19 12:09:01'),
(10381, 'DH52100180', 'DH52100180', NULL, 'DH52100180@student.stu.edu.vn', NULL, '$2a$10$/bWmkrIFjvw3OqA10QG7ROwRd..MUJyO3sCEw77BJWQkvP69LI6Bq', 'student', NULL, '2025-07-19 12:09:01', '2025-07-19 12:09:01'),
(10382, 'DH52101402', 'DH52101402', NULL, 'DH52101402@student.stu.edu.vn', NULL, '$2a$10$LPy9unvrjpY5QbJsQKfC/OtebSd2SfHv2HWZG/7kM0QEgtb7G3Bu2', 'student', NULL, '2025-07-19 12:09:01', '2025-07-19 12:09:01'),
(10383, 'DH52100937', 'DH52100937', NULL, 'DH52100937@student.stu.edu.vn', NULL, '$2a$10$CBACuv6ODYiHgtfGqrFOQOofdfgY8H9zz4JY02iTHSMwdqJTCyQOa', 'student', NULL, '2025-07-19 12:09:01', '2025-07-19 12:09:01'),
(10384, 'DH52106740', 'DH52106740', NULL, 'DH52106740@student.stu.edu.vn', NULL, '$2a$10$01BNYwGC11D3pv4aPRw9v.5VVgi14GXHfMhBH7fJFWO8kPJnh22VK', 'student', NULL, '2025-07-19 12:09:02', '2025-07-19 12:09:02'),
(10385, 'DH52108789', 'DH52108789', NULL, 'DH52108789@student.stu.edu.vn', NULL, '$2a$10$tmfOs90DQ2ReB0zkJp.fy.mRXWqRdjhXUZ6sMua67PIcKCtqmCrQW', 'student', NULL, '2025-07-19 12:09:02', '2025-07-19 12:09:02'),
(10386, 'DH52103699', 'DH52103699', NULL, 'DH52103699@student.stu.edu.vn', NULL, '$2a$10$kA3ZigvgpMf7.CxTJkjwQ.cwka2IKPytFT0JUxNUsRsCjqomKxS5S', 'student', NULL, '2025-07-19 12:09:02', '2025-07-19 12:09:02'),
(10387, 'DH52003606', 'DH52003606', NULL, 'DH52003606@student.stu.edu.vn', NULL, '$2a$10$BLcsO.4mLwQDY.39ZtUed.1QM/4/CVStrvLoFJmJpLteFXD.UQYHq', 'student', NULL, '2025-07-19 12:09:02', '2025-07-19 12:09:02'),
(10388, 'DH52101246', 'DH52101246', NULL, 'DH52101246@student.stu.edu.vn', NULL, '$2a$10$WtDTlfHkIqIF86c7nG92eO68WbkwGJh9vFk9B2Cd7lpPr6rWNut76', 'student', NULL, '2025-07-19 12:09:02', '2025-07-19 12:09:02'),
(10389, 'DH52111306', 'DH52111306', NULL, 'DH52111306@student.stu.edu.vn', NULL, '$2a$10$4VT3WqCRdwvYClAAKH0VdOkzDh2qkv.oezB/8lS894oMzRbEV2HCu', 'student', NULL, '2025-07-19 12:09:02', '2025-07-19 12:09:02'),
(10390, 'DH52100465', 'DH52100465', NULL, 'DH52100465@student.stu.edu.vn', NULL, '$2a$10$Giwi8T8yAteprJ2RJlj5bOIqlDoURX5jjhSvtr7ld8oSBgghWsaKe', 'student', NULL, '2025-07-19 12:09:02', '2025-07-19 12:09:02'),
(10391, 'DH52107414', 'DH52107414', NULL, 'DH52107414@student.stu.edu.vn', NULL, '$2a$10$VibGdzcxAMEAcyB20FJU/e8nH0GqP.JbHXuYpoiYPpQp0TpB8pMdO', 'student', NULL, '2025-07-19 12:09:02', '2025-07-19 12:09:02'),
(10392, 'DH52104132', 'DH52104132', NULL, 'DH52104132@student.stu.edu.vn', NULL, '$2a$10$YJmZd4Z.Dr5HQ5jYRrz44O93AogL0sXvwTgjh95O9N5XMn.r22Dva', 'student', NULL, '2025-07-19 12:09:03', '2025-07-19 12:09:03'),
(10393, 'DH52003421', 'DH52003421', NULL, 'DH52003421@student.stu.edu.vn', NULL, '$2a$10$g60GJt4C4TD511lRc7kKn.5P2troK2Z/MusS5eogZq8wL0MegS3gy', 'student', NULL, '2025-07-19 12:09:03', '2025-07-19 12:09:03'),
(10394, 'DH52100953', 'DH52100953', NULL, 'DH52100953@student.stu.edu.vn', NULL, '$2a$10$wOBzOCfxRsmzp5SuPHMXmelA3x9Kd5dRpwKkWZFyoe5fAyEflYQpa', 'student', NULL, '2025-07-19 12:09:03', '2025-07-19 12:09:03'),
(10395, 'DH52005977', 'DH52005977', NULL, 'DH52005977@student.stu.edu.vn', NULL, '$2a$10$Cbe5g9KkhJUJYsIIdV4xH.QIpIguQNW..lCCDf1erIAZuFViD1NFa', 'student', NULL, '2025-07-19 12:09:03', '2025-07-19 12:09:03'),
(10396, 'DH52106256', 'DH52106256', NULL, 'DH52106256@student.stu.edu.vn', NULL, '$2a$10$Jemtg0drPS4g0s/3cMA.c.wluKYQhFITLzh.G9abrltin5uct37y2', 'student', NULL, '2025-07-19 12:09:03', '2025-07-19 12:09:03'),
(10397, 'DH52102758', 'DH52102758', NULL, 'DH52102758@student.stu.edu.vn', NULL, '$2a$10$XpE2s2dGnrx/1HGqr1d.oeWn8wDv4QL6UF6nUDVxIfv8P4GDEawE6', 'student', NULL, '2025-07-19 12:09:03', '2025-07-19 12:09:03'),
(10398, 'DH52100282', 'DH52100282', NULL, 'DH52100282@student.stu.edu.vn', NULL, '$2a$10$6m6f/ivLSIXRsGBXRLe6SutiS9O9h8btmLq8urmo3T9uk10BgTZk.', 'student', NULL, '2025-07-19 12:09:03', '2025-07-19 12:09:03'),
(10399, 'DH52111438', 'DH52111438', NULL, 'DH52111438@student.stu.edu.vn', NULL, '$2a$10$pMjhKnOVqkarPD1k18P0YuewwMQycva/nYDqDciuBvRakq/Ocxfn.', 'student', NULL, '2025-07-19 12:09:03', '2025-07-19 12:09:03'),
(10400, 'DH52102172', 'DH52102172', NULL, 'DH52102172@student.stu.edu.vn', NULL, '$2a$10$c13tkW2IEEO7XA5Lc8S0GuASb1NQ.3hhgVnipPF2z01VL62HC8u52', 'student', NULL, '2025-07-19 12:09:04', '2025-07-19 12:09:04'),
(10401, 'DH52105864', 'DH52105864', NULL, 'DH52105864@student.stu.edu.vn', NULL, '$2a$10$HURG.YBVAXEDRJb8yE7TgeP6OlL7X.Ts3C4GAwjjHqGdFWDWaB.6O', 'student', NULL, '2025-07-19 12:09:04', '2025-07-19 12:09:04'),
(10402, 'DH52106284', 'DH52106284', NULL, 'DH52106284@student.stu.edu.vn', NULL, '$2a$10$VNtmIyJP7Kgs0sI7l4YfMOLuxbZnmYlR94XLytcBE7YLGLZn4jJKi', 'student', NULL, '2025-07-19 12:09:04', '2025-07-19 12:09:04'),
(10403, 'DH52103544', 'DH52103544', NULL, 'DH52103544@student.stu.edu.vn', NULL, '$2a$10$KuuUZrjR7PRUWlIsDArE/.YuEoafk7voYOE/My.Koj35wyla6Bl9G', 'student', NULL, '2025-07-19 12:09:04', '2025-07-19 12:09:04'),
(10404, 'DH52103613', 'DH52103613', NULL, 'DH52103613@student.stu.edu.vn', NULL, '$2a$10$pE80oNk9Uo2Tl0v6CmV0cuLBSuuCUbtT7sRo66B6UT4FaNWMWIPUe', 'student', NULL, '2025-07-19 12:09:04', '2025-07-19 12:09:04'),
(10405, 'DH52103682', 'DH52103682', NULL, 'DH52103682@student.stu.edu.vn', NULL, '$2a$10$7nd195kOZHPoklBjnZQH/.U73DzfNOKiXzkmPdi4fiBvmS403jfS6', 'student', NULL, '2025-07-19 12:09:04', '2025-07-19 12:09:04'),
(10406, 'DH52111515', 'DH52111515', NULL, 'DH52111515@student.stu.edu.vn', NULL, '$2a$10$v..I9kX06G13Wpvjm0zNPuSUya/GQeRe4sJNJKZoLPDvPWDoL3zEG', 'student', NULL, '2025-07-19 12:09:04', '2025-07-19 12:09:04'),
(10407, 'DH52111518', 'DH52111518', NULL, 'DH52111518@student.stu.edu.vn', NULL, '$2a$10$8GiZq1yRQW3rEIRNF/QXu.GiN.7FrbRRf/Ny/BHhGmQ9cDAnCgrjy', 'student', NULL, '2025-07-19 12:09:04', '2025-07-19 12:09:04'),
(10408, 'DH52103679', 'DH52103679', NULL, 'DH52103679@student.stu.edu.vn', NULL, '$2a$10$elcCbwVrOSi9FRfAUlDHHeQtxCOEcK8N.hSQNw0T0AXWP7qNcbE4q', 'student', NULL, '2025-07-19 12:09:05', '2025-07-19 12:09:05'),
(10409, 'DH52100231', 'DH52100231', NULL, 'DH52100231@student.stu.edu.vn', NULL, '$2a$10$SY4dWx9.UVeUYpI7duwo6uJkPpICnUOxy3wRCY8yHZ5bKUWKCGHri', 'student', NULL, '2025-07-19 12:09:05', '2025-07-19 12:09:05'),
(10410, 'DH52100002', 'DH52100002', NULL, 'DH52100002@student.stu.edu.vn', NULL, '$2a$10$QF.uZauTLYiYi/pRk8ebG.FOVIih0SeKKjiR3NIbVgDbW1CG1x9QK', 'student', NULL, '2025-07-19 12:09:05', '2025-07-19 12:09:05'),
(10411, 'DH52103820', 'DH52103820', NULL, 'DH52103820@student.stu.edu.vn', NULL, '$2a$10$FKDtwV/NPeDXIZiuKlZhVu15C1FDvky/kyT90CVs8gozf04fQ0/mm', 'student', NULL, '2025-07-19 12:09:05', '2025-07-19 12:09:05'),
(10412, 'DH52105659', 'DH52105659', NULL, 'DH52105659@student.stu.edu.vn', NULL, '$2a$10$g6AgxrZyIoWQcto9KNZJkuybTjhHKzFFYNs0kXI3ov/IMAQBbZIAm', 'student', NULL, '2025-07-19 12:09:05', '2025-07-19 12:09:05'),
(10413, 'DH52108750', 'DH52108750', NULL, 'DH52108750@student.stu.edu.vn', NULL, '$2a$10$CnNlLNZafOmNKMz6SVth.Ofx6x.pXEWzlX9O6tpsov.KoZvJ8zW.C', 'student', NULL, '2025-07-19 12:09:05', '2025-07-19 12:09:05'),
(10414, 'DH52107853', 'DH52107853', NULL, 'DH52107853@student.stu.edu.vn', NULL, '$2a$10$YoLbxjiqq/SHdINRWRezReC1X4LlhQwQMa5BOkvxhTSPWfyuy8MBS', 'student', NULL, '2025-07-19 12:09:05', '2025-07-19 12:09:05'),
(10415, 'DH52100715', 'DH52100715', NULL, 'DH52100715@student.stu.edu.vn', NULL, '$2a$10$G4Ei36vyqU57RHA48kXfbe0nzy2h8BHdg4zbu/MkpO7dX7Bz.hYzC', 'student', NULL, '2025-07-19 12:09:05', '2025-07-19 12:09:05'),
(10416, 'DH52111686', 'DH52111686', NULL, 'DH52111686@student.stu.edu.vn', NULL, '$2a$10$aeiv81fAU7XvFvuo8tRAa.0/KXbrGDIBPRlCpQDBe151klThlMUTG', 'student', NULL, '2025-07-19 12:09:06', '2025-07-19 12:09:06'),
(10417, 'DH52107207', 'DH52107207', NULL, 'DH52107207@student.stu.edu.vn', NULL, '$2a$10$8UCH9nkn238R4Ds8KF6SFucQPF.jjvYSqkJGTm6yvalScEffefe32', 'student', NULL, '2025-07-19 12:09:06', '2025-07-19 12:09:06'),
(10418, 'DH52103378', 'DH52103378', NULL, 'DH52103378@student.stu.edu.vn', NULL, '$2a$10$Aw7yf/EoH0eVsC60SeUeU.jl56Rz3Gl.CPGic9skSmBgjwpANvyEy', 'student', NULL, '2025-07-19 12:09:06', '2025-07-19 12:09:06'),
(10419, 'DH52101870', 'DH52101870', NULL, 'DH52101870@student.stu.edu.vn', NULL, '$2a$10$Rf2pvi1kJLInkipn10jdwujuvdAaergfSTFY8lA0Zh/VuV03jkRuy', 'student', NULL, '2025-07-19 12:09:06', '2025-07-19 12:09:06'),
(10420, 'DH52105095', 'DH52105095', NULL, 'DH52105095@student.stu.edu.vn', NULL, '$2a$10$w1i5YK2lMVDTZUMMBkxLvu4pmaTLTikZVwfkz3XYQDYg0kCxsMDty', 'student', NULL, '2025-07-19 12:09:06', '2025-07-19 12:09:06'),
(10421, 'DH52111814', 'DH52111814', NULL, 'DH52111814@student.stu.edu.vn', NULL, '$2a$10$DfVPi7a5K4oujQi7TgxOO.Q26RQQQosAi7/A3qgzkxPqnFaSPDSIq', 'student', NULL, '2025-07-19 12:09:06', '2025-07-19 12:09:06'),
(10422, 'DH52111817', 'DH52111817', NULL, 'DH52111817@student.stu.edu.vn', NULL, '$2a$10$0pUZp3NjHUNQga5G6.DAoOaIThGYcgM5pR6dz.dLs8rz/yXz7oTli', 'student', NULL, '2025-07-19 12:09:06', '2025-07-19 12:09:06'),
(10423, 'DH52106235', 'DH52106235', NULL, 'DH52106235@student.stu.edu.vn', NULL, '$2a$10$tqg0WXNLFiCuzpkkwfJg4ub4YC/iMVAHaIpslVcMOwMgUJ08BPdui', 'student', NULL, '2025-07-19 12:09:06', '2025-07-19 12:09:06'),
(10424, 'DH52100524', 'DH52100524', NULL, 'DH52100524@student.stu.edu.vn', NULL, '$2a$10$18fNNV67A1QVi5B57XV3LO5hG7CDa6OrxjRwBw.6akFNgHSS825Rq', 'student', NULL, '2025-07-19 12:09:07', '2025-07-19 12:09:07'),
(10425, 'DH52100136', 'DH52100136', NULL, 'DH52100136@student.stu.edu.vn', NULL, '$2a$10$5/vJQfWEJoUJ8ySkWuk3weGe9WqoLfTupkW6fUVAY9bQjG4rXrzPy', 'student', NULL, '2025-07-19 12:09:07', '2025-07-19 12:09:07'),
(10426, 'DH52111900', 'DH52111900', NULL, 'DH52111900@student.stu.edu.vn', NULL, '$2a$10$D/JhUK8wbdv7ma3ZzvIkMusUJ6DBxJwsQk/cyAVe0ruWPK2OZ.KB.', 'student', NULL, '2025-07-19 12:09:07', '2025-07-19 12:09:07'),
(10427, 'DH52106734', 'DH52106734', NULL, 'DH52106734@student.stu.edu.vn', NULL, '$2a$10$CQEr4zVZq3vYdALBvNepuOhyqPkJ0fqS9aX4j986qWjbNK.fQkqY6', 'student', NULL, '2025-07-19 12:09:07', '2025-07-19 12:09:07'),
(10428, 'DH52106268', 'DH52106268', NULL, 'DH52106268@student.stu.edu.vn', NULL, '$2a$10$3gp527MBKO.hXNTlsHgibex231SvrNdWUg/.a0ZCDusZQVOV9BAGO', 'student', NULL, '2025-07-19 12:09:07', '2025-07-19 12:09:07'),
(10429, 'DH52006233', 'DH52006233', NULL, 'DH52006233@student.stu.edu.vn', NULL, '$2a$10$R9O.zdocuBiXeelGM0BEsOoajKkC0.8/A.pMUWMzmu/cYFzXh/8p.', 'student', NULL, '2025-07-19 12:09:07', '2025-07-19 12:09:07'),
(10430, 'DH52100800', 'DH52100800', NULL, 'DH52100800@student.stu.edu.vn', NULL, '$2a$10$F6b6Jn8iYPCmm8NOze77LOVroztDmXQfhQPylEvngNpaG7/O2g/nm', 'student', NULL, '2025-07-19 12:09:07', '2025-07-19 12:09:07'),
(10431, 'DH52107408', 'DH52107408', NULL, 'DH52107408@student.stu.edu.vn', NULL, '$2a$10$NH0F3.mR9wJeuMzLtVcL6.JyIyALf5kX2FvJlH3YSZZIqSG30cOza', 'student', NULL, '2025-07-19 12:09:08', '2025-07-19 12:09:08'),
(10432, 'DH52102853', 'DH52102853', NULL, 'DH52102853@student.stu.edu.vn', NULL, '$2a$10$ivk0zcz9yIIRrrqWlzsF1.DGuxjj9exH6J1b5pPk7/FwpJmAHXmIm', 'student', NULL, '2025-07-19 12:09:08', '2025-07-19 12:09:08'),
(10433, 'DH52104952', 'DH52104952', NULL, 'DH52104952@student.stu.edu.vn', NULL, '$2a$10$E8y59gHE0cqAeOWVlfBqXecxFRy7vHDpUYG2wW6BCY32DK4PJJSxi', 'student', NULL, '2025-07-19 12:09:08', '2025-07-19 12:09:08'),
(10434, 'DH52103727', 'DH52103727', NULL, 'DH52103727@student.stu.edu.vn', NULL, '$2a$10$1r6Xxnt0gcjYtTI6jeSUcOOSr97ATy.yw6PauYrJna41082J3Ysau', 'student', NULL, '2025-07-19 12:09:08', '2025-07-19 12:09:08'),
(10435, 'DH52100402', 'DH52100402', NULL, 'DH52100402@student.stu.edu.vn', NULL, '$2a$10$1qFQDGhFWps6dvoGw5Wj/OI4NG3BaqPyrDBRBKR4onvxrQM/HQaCa', 'student', NULL, '2025-07-19 12:09:08', '2025-07-19 12:09:08'),
(10436, 'DH52003250', 'DH52003250', NULL, 'DH52003250@student.stu.edu.vn', NULL, '$2a$10$8txn8uuYMOiPPgoNudNAbuMFuf6N37td/EE.um/ef2lfRmq8HNm5O', 'student', NULL, '2025-07-19 12:21:58', '2025-07-19 12:21:58'),
(10437, 'hiep', NULL, 'hiep', 'tranngochiep@gmail.com', NULL, '$2a$10$J7y/PpcP4S80pisM4hsgNe9TplzpWRsapQDqmyiz7cxnpoZctHJTy', 'teacher', NULL, '2025-07-19 12:25:57', '2025-07-19 12:25:57');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `diem_danh`
--
ALTER TABLE `diem_danh`
  ADD CONSTRAINT `diem_danh_ma_tkb_foreign` FOREIGN KEY (`ma_tkb`) REFERENCES `tkb` (`ma_tkb`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `diem_danh_log`
--
ALTER TABLE `diem_danh_log`
  ADD CONSTRAINT `fk_dd_log_ma_dd` FOREIGN KEY (`ma_dd`) REFERENCES `diem_danh` (`ma_dd`) ON DELETE CASCADE;

--
-- Constraints for table `lich_gd`
--
ALTER TABLE `lich_gd`
  ADD CONSTRAINT `lich_gd_ma_gv_foreign` FOREIGN KEY (`ma_gv`) REFERENCES `giao_vien` (`ma_gv`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `lich_gd_ma_mh_foreign` FOREIGN KEY (`ma_mh`) REFERENCES `mon_hoc` (`ma_mh`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `lich_hoc`
--
ALTER TABLE `lich_hoc`
  ADD CONSTRAINT `lich_hoc_ma_gd_foreign` FOREIGN KEY (`ma_gd`) REFERENCES `lich_gd` (`ma_gd`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `lich_hoc_ma_sv_foreign` FOREIGN KEY (`ma_sv`) REFERENCES `sinh_vien` (`ma_sv`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `lop`
--
ALTER TABLE `lop`
  ADD CONSTRAINT `lop_ma_khoa_foreign` FOREIGN KEY (`ma_khoa`) REFERENCES `khoa` (`ma_khoa`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `qrcode`
--
ALTER TABLE `qrcode`
  ADD CONSTRAINT `fk_qrcode_tkb` FOREIGN KEY (`ma_tkb`) REFERENCES `tkb` (`ma_tkb`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `sinh_vien`
--
ALTER TABLE `sinh_vien`
  ADD CONSTRAINT `sinh_vien_ma_lop_foreign` FOREIGN KEY (`ma_lop`) REFERENCES `lop` (`ma_lop`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `tkb`
--
ALTER TABLE `tkb`
  ADD CONSTRAINT `tkb_ma_gd_foreign` FOREIGN KEY (`ma_gd`) REFERENCES `lich_gd` (`ma_gd`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ma_gv_foreign` FOREIGN KEY (`ma_gv`) REFERENCES `giao_vien` (`ma_gv`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `users_ma_sv_foreign` FOREIGN KEY (`ma_sv`) REFERENCES `sinh_vien` (`ma_sv`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
