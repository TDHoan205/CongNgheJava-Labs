-- ====================================================================
-- TC03-TC04: Warehouse Desktop (Swing JDBC) - simulate DAO operations
-- ====================================================================

USE java_integrated_lab;

SELECT '=== TC02: Warehouse sees PENDING order ===' AS step;
SELECT o.id, o.customer_id, u.full_name, o.total_amount, o.status
FROM orders o JOIN users u ON u.id=o.customer_id
WHERE o.status='PENDING' ORDER BY o.created_at ASC;

SELECT '=== TC03: acceptOrder - deduct stock + PENDING -> PROCESSING ===' AS step;

START TRANSACTION;

-- 1. Decrease stock for each item (with version check)
UPDATE products SET stock=stock-2, version=version+1 WHERE id=1 AND stock>=2 AND version=1;
SELECT ROW_COUNT() AS sp001_affected;

UPDATE products SET stock=stock-3, version=version+1 WHERE id=2 AND stock>=3 AND version=1;
SELECT ROW_COUNT() AS sp002_affected;

-- 2. Update order to PROCESSING
UPDATE orders SET status='PROCESSING', version=version+1 WHERE id=1 AND status='PENDING' AND version=1;
SELECT ROW_COUNT() AS order_affected;

-- 3. Insert history
INSERT INTO order_status_history (order_id, old_status, new_status, changed_by, platform, note)
VALUES (1, 'PENDING', 'PROCESSING', 2, 'JAVA_SWING', 'Kho tiep nhan don');

COMMIT;

SELECT '=== After acceptOrder (TC03 result) ===' AS step;
SELECT id, status, version FROM orders WHERE id=1;
SELECT id, code, stock, version FROM products WHERE id IN (1,2);
SELECT order_id, old_status, new_status, platform, changed_by FROM order_status_history WHERE order_id=1 ORDER BY id;

-- TC04: packOrder: PROCESSING -> READY
SELECT '=== TC04: packOrder - PROCESSING -> READY ===' AS step;
START TRANSACTION;
UPDATE orders SET status='READY', version=version+1 WHERE id=1 AND status='PROCESSING' AND version=2;
SELECT ROW_COUNT() AS order_affected;
INSERT INTO order_status_history (order_id, old_status, new_status, changed_by, platform, note)
VALUES (1, 'PROCESSING', 'READY', 2, 'JAVA_SWING', 'Kho dong goi');
COMMIT;

SELECT '=== After packOrder (TC04 result) ===' AS step;
SELECT id, status, version FROM orders WHERE id=1;
SELECT order_id, old_status, new_status, platform FROM order_status_history WHERE order_id=1 ORDER BY id;
