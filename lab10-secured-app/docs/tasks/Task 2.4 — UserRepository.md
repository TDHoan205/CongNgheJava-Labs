# Task 2.4 — UserRepository ✅ PASS

**Ngày:** 2026-08-25

## Giải thích

### Repository làm gì?

`UserRepository` là lớp duy nhất biết cách **đọc/ghi User từ database** qua JPA/Hibernate.

```
Service (AuthService)          Repository (UserRepository)         Database (MySQL)
     │                               │                                  │
     │  findByEmail(email)            │                                  │
     │──────────────────────────────►│  EntityManager.createQuery(JPQL)  │
     │                               │─────────────────────────────────►│
     │                               │       SELECT * FROM users        │
     │                               │◄─────────────────────────────────│
     │◄──────────────────────────────│  User entity                    │
     │  User object                  │                                  │
```

**Luật:** Controller/Service không bao giờ viết SQL trực tiếp. Tất cả truy vấn DB phải qua Repository.

### Tại sao Service không query DB trực tiếp?

1. **Tách biệt trách nhiệm (Separation of Concerns):** Service lo logic nghiệp vụ, Repository lo truy vấn. Mỗi class chỉ làm một việc.

2. **Tái sử dụng:** Nhiều Service có thể dùng cùng một Repository. VD: `AuthService` dùng `findByEmail`, sau này `UserManagementService` cũng dùng `findByEmail`.

3. **Dễ test:** Có thể mock Repository (thay JPA bằng fake data) mà không cần database thật.

4. **Thay đổi công nghệ lớp dưới không ảnh hưởng lớp trên:** Hôm nay dùng JPA, mai có thể đổi sang MyBatis mà Service không cần sửa gì.

## Các file đã tạo

| File | Vai trò |
|------|---------|
| `pom.xml` | Thêm JPA/Hibernate/MySQL/H2/JUnit5 |
| `persistence.xml` | Cấu hình JPA PU (MySQL + H2 test) |
| `config/JPAUtil.java` | Quản lý EntityManagerFactory |
| `repository/BaseRepository.java` | Generic CRUD cho mọi entity |
| `repository/UserRepository.java` | `findByEmail()` cho User |

## Các file đã sửa

| File | Thay đổi |
|------|---------|
| `RoleTest.java` | JUnit 3 → JUnit 5 |
| `UserTest.java` | JUnit 3 → JUnit 5 |

## Test result

```
Tests run: 7, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```
