# Hệ Thống Điểm Danh STU

Hệ thống điểm danh thông minh sử dụng QR Code cho Trường Đại học.

## Mục lục

- [Tổng quan](#tổng-quan)
- [Công nghệ sử dụng](#công-nghệ-sử-dụng)
- [Hướng dẫn cài đặt](#hướng-dẫn-cài-đặt)
  - [Yêu cầu hệ thống](#yêu-cầu-hệ-thống)
  - [Cài đặt Backend](#cài-đặt-backend)
  - [Cài đặt Frontend](#cài-đặt-frontend)
- [Hướng dẫn sử dụng](#hướng-dẫn-sử-dụng)
  - [Dành cho Quản trị viên](#dành-cho-quản-trị-viên)
  - [Dành cho Giảng viên](#dành-cho-giảng-viên)
  - [Dành cho Sinh viên](#dành-cho-sinh-viên)

## Tổng quan

Hệ thống điểm danh thông minh được phát triển nhằm mục đích:
- Tự động hóa quá trình điểm danh sinh viên
- Quản lý thời khóa biểu và lịch học hiệu quả
- Theo dõi tình trạng điểm danh của sinh viên
- Tạo báo cáo và thống kê điểm danh

## Công nghệ sử dụng

### Backend
- Java Spring Boot
- Spring Security
- Spring Data JPA
- MySQL Database
- Maven

### Frontend
- React.js
- Vite
- Axios
- React Router DOM
- Material-UI/Ant Design

## Hướng dẫn cài đặt

### Yêu cầu hệ thống

- JDK 17 hoặc cao hơn
- Node.js 16.x hoặc cao hơn
- MySQL 8.0 hoặc cao hơn
- Maven 3.8.x hoặc cao hơn

### Cài đặt Backend

1. Clone repository:
```bash
git clone <repository-url>
cd He-Thong-Diem-Danh-STU
```

2. Cấu hình database trong file `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
spring.datasource.username=your_username
spring.datasource.password=your_password
```

3. Build và chạy ứng dụng:
```bash
mvn clean install
mvn spring-boot:run
```

Ứng dụng backend sẽ chạy tại `http://localhost:8080`

### Cài đặt Frontend

1. Di chuyển vào thư mục frontend:
```bash
cd FRONT_END
```

2. Cài đặt dependencies:
```bash
npm install
```

3. Cấu hình API endpoint trong file `.env`:
```env
VITE_API_URL=http://localhost:8080/api
```

4. Chạy ứng dụng trong môi trường development:
```bash
npm run dev
```

Ứng dụng frontend sẽ chạy tại `http://localhost:5173`

## Hướng dẫn sử dụng

### Dành cho Quản trị viên

1. Quản lý tài khoản
   - Đăng nhập với tài khoản admin
   - Quản lý tài khoản giảng viên và sinh viên
   - Phân quyền người dùng

2. Quản lý khoa/lớp
   - Thêm, sửa, xóa thông tin khoa
   - Quản lý danh sách lớp học
   - Phân công giảng viên

3. Quản lý môn học
   - Thêm môn học mới
   - Cập nhật thông tin môn học
   - Xem danh sách môn học

4. Báo cáo và thống kê
   - Xem báo cáo điểm danh
   - Xuất báo cáo theo lớp/môn học
   - Thống kê tình hình điểm danh

### Dành cho Giảng viên

1. Quản lý lớp học
   - Xem danh sách lớp phụ trách
   - Quản lý thời khóa biểu
   - Tạo QR code điểm danh

2. Điểm danh
   - Tạo mã QR cho buổi học
   - Theo dõi sinh viên điểm danh
   - Điểm danh thủ công nếu cần

3. Báo cáo
   - Xem danh sách sinh viên vắng mặt
   - Xuất báo cáo điểm danh
   - Thống kê tình hình lớp học

### Dành cho Sinh viên

1. Xem thông tin
   - Xem thời khóa biểu
   - Xem lịch học
   - Kiểm tra thông tin cá nhân

2. Điểm danh
   - Quét mã QR để điểm danh
   - Xem lịch sử điểm danh
   - Kiểm tra tình trạng điểm danh

3. Thông báo
   - Nhận thông báo về lịch học
   - Xem thông báo từ giảng viên
   - Cập nhật thông tin cá nhân

## Đóng góp

Nếu bạn muốn đóng góp vào dự án, vui lòng:
1. Fork repository
2. Tạo branch mới (`git checkout -b feature/AmazingFeature`)
3. Commit thay đổi (`git commit -m 'Add some AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Tạo Pull Request

## Giấy phép

Dự án được phân phối dưới giấy phép [MIT](LICENSE). 