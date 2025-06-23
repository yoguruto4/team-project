-- 部署マスタ
INSERT INTO department (nm_department) VALUES
('総務部'),
('営業部'),
('開発部');

-- 社員マスタ
INSERT INTO employee (nm_employee, kn_employee, mail_address, password, admin_auth, id_department) VALUES
('山田 太郎', 'やまだ たろう', 'yamada@example.com', 'pass1234', 1, 1),
('佐藤 花子', 'さとう はなこ', 'sato@example.com', 'pass5678', 0, 2);

-- 申請書種類マスタ
INSERT INTO document_type (nm_document_type) VALUES
('交通費申請'),
('有給申請');

-- 申請書マスタ
INSERT INTO document (id_document_type, id_employee, id_approval, id_approved_by) VALUES
(1, 1, 1, 2),
(2, 2, 2, NULL);

-- 交通費申請マスタ
INSERT INTO travel_expense (id_document, id_column, type_vehicle, place_departure, place_arrival, trip_type, route, fare, date_travel, nm_file, invoice) VALUES
(1, 1, '電車', '東京駅', '新宿駅', '片道', '山手線', 200, '2025-06-15 10:30:00', 'invoice_7001.jpg', TRUE);

-- 有給申請マスタ
INSERT INTO paid_leave_request (id_document, paid_leave_days, leave_reason, date_of_leave) VALUES
(2, 1.0, '通院のため', '2025-07-10 10:30:00');

-- 通知マスタ
INSERT INTO notification (id_employee, id_document, comment, message, status, created_at) VALUES
(1, 1, '申請時', '交通費申請が作成されました', 'unread', '2025-06-20 10:00:00'),
(2, 2, '承認時', '有給申請が承認されました', 'read', '2025-06-20 11:00:00');

-- 交通費申請テンプレートマスタ
INSERT INTO travel_expense_template (id_employee, departure_location, arrival_location, transport_type, trip_type, route, cost) VALUES
(1, '渋谷', '品川', '電車', '往復', '山手線', 480.00);

-- 承認マスタ
INSERT INTO approval ( approval_status, date_approval_request, date_approval) VALUES
('承認', '2025-06-19 10:00:00', '2025-06-19 12:00:00'),
('未承認', '2025-06-19 11:00:00', NULL);



-- 部署マスタ
SELECT * FROM department;

-- 社員マスタ
SELECT * FROM employee;

-- 申請書種類マスタ
SELECT * FROM document_type;

-- 申請書マスタ
SELECT * FROM document;

-- 交通費申請マスタ
SELECT * FROM travel_expense;

-- 有給申請マスタ
SELECT * FROM paid_leave_request;

-- 通知マスタ
SELECT * FROM notification;


SELECT * FROM travel_expense_template;

-- 承認マスタ
SELECT * FROM approval;


