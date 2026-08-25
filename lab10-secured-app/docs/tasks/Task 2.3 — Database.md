# Task 2.3 — Database ✅ PASS

**Ngày:** 2026-08-25
**Password MySQL:** `123456`

## Acceptance Criteria

| # | Tiêu chí | Kết quả |
|---|---------|---------|
| 1 | Script chạy từ DB sạch (`DROP DATABASE IF EXISTS`) | ✅ PASS |
| 2 | Idempotent — chạy lại không lỗi | ✅ PASS |
| 3 | Email UNIQUE — insert trùng → `ERROR 1062` | ✅ PASS |
| 4 | Role ENUM — insert 'SUPER' → `ERROR 1265` | ✅ PASS |
| 5 | `active` CHECK constraint — insert 2 / -1 → `ERROR 3819` | ✅ PASS |

## Constraints đã tạo

| Constraint | Cột | Loại |
|-----------|-----|------|
| PRIMARY KEY | `id` | AUTO_INCREMENT INT |
| `uk_users_email` | `email` | UNIQUE |
| `ck_users_active` | `active` | CHECK `IN (0,1)` |
| ENUM | `role` | `('ADMIN','STAFF','USER')` |

## File liên quan

- `docs/db/lab10_users_role.sql` — script tạo bảng (Task 2.3 output)
- `src/main/java/vn/edu/eaut/lab10/model/User.java` — entity (Task 2.2)
- `src/main/java/vn/edu/eaut/lab10/model/Role.java` — enum (Task 2.1)
