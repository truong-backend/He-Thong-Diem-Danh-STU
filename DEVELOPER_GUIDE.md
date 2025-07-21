# Hướng dẫn dành cho Developers

## Mục lục
- [Cấu trúc dự án](#cấu-trúc-dự-án)
- [Yêu cầu kỹ thuật](#yêu-cầu-kỹ-thuật)
- [Cài đặt môi trường phát triển](#cài-đặt-môi-trường-phát-triển)
- [Cấu hình và triển khai](#cấu-hình-và-triển-khai)
- [API Documentation](#api-documentation)
- [Database Schema](#database-schema)
- [Quy trình phát triển](#quy-trình-phát-triển)

## Cấu trúc dự án

### Backend (Spring Boot)
```
He-Thong-Diem-Danh-STU/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── vn/diemdanh/hethong/
│   │   │       ├── config/          # Cấu hình Spring Boot
│   │   │       ├── controller/      # REST Controllers
│   │   │       ├── dto/            # Data Transfer Objects
│   │   │       ├── entity/         # JPA Entities
│   │   │       ├── repository/     # Spring Data Repositories
│   │   │       ├── service/        # Business Logic
│   │   │       └── security/       # Spring Security Config
│   │   └── resources/
│   │       └── application.properties
│   └── test/                      # Unit Tests
└── pom.xml                        # Maven Dependencies
```

### Frontend (React + Vite)
```
FRONT_END/
├── src/
│   ├── components/               # React Components
│   │   ├── QuanTriVien/         # Admin Components
│   │   ├── GiaoVien/           # Teacher Components
│   │   └── SinhVien/           # Student Components
│   ├── services/                # API Services
│   ├── contexts/                # React Contexts
│   └── pages/                   # Page Components
├── public/                      # Static Assets
└── package.json                 # NPM Dependencies
```

## Yêu cầu kỹ thuật

### Backend
- JDK 17+
- Maven 3.8.x+
- MySQL 8.0+
- IDE được đề xuất: IntelliJ IDEA hoặc Eclipse

### Frontend
- Node.js 16.x+
- npm hoặc yarn
- IDE được đề xuất: Visual Studio Code với các extensions:
  - ESLint
  - Prettier
  - React Developer Tools

## Cài đặt môi trường phát triển

### 1. Cài đặt Backend

1. Clone repository và cài đặt dependencies:
```bash
git clone <repository-url>
cd He-Thong-Diem-Danh-STU
mvn clean install
```

2. Cấu hình database trong `application.properties`:
```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/diemdanh_db
spring.datasource.username=your_username
spring.datasource.password=your_password

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# Server
server.port=8080
server.servlet.context-path=/api

# Security
jwt.secret=your_jwt_secret
jwt.expiration=86400000
```

3. Khởi tạo database:
```sql
CREATE DATABASE diemdanh_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

4. Chạy ứng dụng:
```bash
mvn spring-boot:run
```

### 2. Cài đặt Frontend

1. Cài đặt dependencies:
```bash
cd FRONT_END
npm install
```

2. Tạo file `.env`:
```env
VITE_API_URL=http://localhost:8080/api
VITE_APP_TITLE=Hệ thống điểm danh
```

3. Chạy ứng dụng trong development mode:
```bash
npm run dev
```

## Cấu hình và triển khai

### Backend Configuration

1. CORS Configuration (`SecurityConfig.java`):
```java
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(request -> {
            var cors = new CorsConfiguration();
            cors.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
            cors.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
            cors.setAllowedHeaders(Arrays.asList("*"));
            return cors;
        });
    }
}
```

2. JWT Configuration:
```java
@Configuration
public class JwtConfig {
    @Value("${jwt.secret}")
    private String secret;
    
    @Value("${jwt.expiration}")
    private Long expiration;
    
    // JWT Configuration methods
}
```

### Frontend Configuration

1. API Service Configuration (`services/api.js`):
```javascript
import axios from 'axios';

const api = axios.create({
    baseURL: import.meta.env.VITE_API_URL,
    headers: {
        'Content-Type': 'application/json'
    }
});

api.interceptors.request.use(config => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});
```

## API Documentation

### Authentication APIs

```
POST /api/auth/login
POST /api/auth/refresh-token
POST /api/auth/logout
```

### Admin APIs

```
GET    /api/admin/users
POST   /api/admin/users
PUT    /api/admin/users/{id}
DELETE /api/admin/users/{id}
```

### Teacher APIs

```
POST   /api/giaovien/qrcode
GET    /api/giaovien/diemdanh
PUT    /api/giaovien/diemdanh/{id}
```

### Student APIs

```
GET    /api/sinhvien/tkb
POST   /api/sinhvien/diemdanh
GET    /api/sinhvien/diemdanh/history
```

## Database Schema

### Core Tables

1. `users`:
```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

2. `diem_danh`:
```sql
CREATE TABLE diem_danh (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    ma_sv VARCHAR(50) NOT NULL,
    ma_lop VARCHAR(50) NOT NULL,
    ngay_diem_danh DATE NOT NULL,
    trang_thai VARCHAR(20) NOT NULL,
    FOREIGN KEY (ma_sv) REFERENCES sinh_vien(ma_sv)
);
```

## Quy trình phát triển

### 1. Coding Standards

#### Backend (Java)
- Tuân thủ Java Code Conventions
- Sử dụng Lombok để giảm boilerplate code
- Đặt tên method theo camelCase
- Đặt tên class theo PascalCase

#### Frontend (React)
- Sử dụng ESLint và Prettier
- Functional Components với Hooks
- Tên component theo PascalCase
- Tên file trùng với tên component

### 2. Git Workflow

1. Branch naming:
```
feature/ten-tinh-nang
bugfix/ma-bug
hotfix/van-de-khan
```

2. Commit message format:
```
feat: thêm tính năng mới
fix: sửa lỗi ABC
docs: cập nhật tài liệu
style: format code
```

### 3. Testing

#### Backend Testing
```bash
# Run unit tests
mvn test

# Run integration tests
mvn verify
```

#### Frontend Testing
```bash
# Run unit tests
npm run test

# Run e2e tests
npm run test:e2e
```

### 4. Build & Deploy

#### Backend
```bash
# Build JAR file
mvn clean package

# Run JAR
java -jar target/he-thong-diem-danh-1.0.0.jar
```

#### Frontend
```bash
# Build production
npm run build

# Preview build
npm run preview
```

### 5. Docker Support

```dockerfile
# Backend Dockerfile
FROM openjdk:17-jdk-slim
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

# Frontend Dockerfile
FROM node:16-alpine
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build
```

Docker Compose:
```yaml
version: '3.8'
services:
  backend:
    build: ./He-Thong-Diem-Danh-STU
    ports:
      - "8080:8080"
    environment:
      - SPRING_DATASOURCE_URL=jdbc:mysql://db:3306/diemdanh_db
    depends_on:
      - db
  
  frontend:
    build: ./FRONT_END
    ports:
      - "80:80"
    depends_on:
      - backend

  db:
    image: mysql:8.0
    environment:
      - MYSQL_DATABASE=diemdanh_db
      - MYSQL_ROOT_PASSWORD=root
``` 