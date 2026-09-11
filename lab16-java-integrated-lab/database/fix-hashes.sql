USE java_integrated_lab;
UPDATE users SET password_hash = '$2a$10$nUoxpwPtksrSgwyQmgv8EeSx06Flgz3mehvplnCyoG9EudmqXOl2m';
SELECT username, role, LEFT(password_hash, 25) as hash FROM users;
