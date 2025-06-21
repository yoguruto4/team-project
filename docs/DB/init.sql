-- 既存のテーブルを削除
DROP TABLE IF EXISTS notification;
DROP TABLE IF EXISTS paid_leave_request;
DROP TABLE IF EXISTS travel_expense;
DROP TABLE IF EXISTS document;
DROP TABLE IF EXISTS document_type;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS department;
DROP TABLE IF EXISTS travel_expense_template;
DROP TABLE IF EXISTS approval;

-- 部署マスタ
CREATE TABLE department (
    id_department INT AUTO_INCREMENT PRIMARY KEY,
    nm_department VARCHAR(50) NOT NULL,
    department_created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 社員マスタ
CREATE TABLE employee (
    id_employee INT AUTO_INCREMENT PRIMARY KEY,
    nm_employee VARCHAR(50) NOT NULL,
    kn_employee VARCHAR(50) NOT NULL,
    mail_address VARCHAR(100) NOT NULL,
    password VARCHAR(10) NOT NULL,
    admin_auth INT NOT NULL,
    id_department INT NOT NULL,
    employee_created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_department) REFERENCES department(id_department)
);

-- 申請書種類マスタ
CREATE TABLE document_type (
    id_document_type INT AUTO_INCREMENT PRIMARY KEY,
    nm_document_type VARCHAR(50) NOT NULL,
    document_created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 申請書マスタ
CREATE TABLE document (
    id_document INT AUTO_INCREMENT PRIMARY KEY,
    id_document_typ INT NOT NULL,
    id_employee INT NOT NULL,
    id_approval INT NOT NULL,
    date_submission DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    id_approved_by INT,
    FOREIGN KEY (id_document_typ) REFERENCES document_type(id_document_type),
    FOREIGN KEY (id_employee) REFERENCES employee(id_employee)
);

-- 交通費申請マスタ
CREATE TABLE travel_expense (
    id_travel_expense INT AUTO_INCREMENT PRIMARY KEY,
    id_document INT NOT NULL,
    id_column INT NOT NULL,
    type_vehicle ENUM('電車','バス','タクシー') NOT NULL,
    place_departure VARCHAR(50) NOT NULL,
    place_arrival VARCHAR(50) NOT NULL,
    trip_type ENUM('片道','往復') NOT NULL,
    route VARCHAR(255) NOT NULL,
    fare INT NOT NULL,
    date_travel DATETIME NOT NULL,
    nm_file VARCHAR(255),
    invoice BOOLEAN NOT NULL DEFAULT FALSE,
    travel_created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_document) REFERENCES document(id_document)
);

-- 有給申請マスタ
CREATE TABLE paid_leave_request (
    id_paid_leave_request INT AUTO_INCREMENT PRIMARY KEY,
    id_document INT NOT NULL,
    paid_leave_days DECIMAL(3,1) NOT NULL,
    leave_reason VARCHAR(255) NOT NULL,
    date_of_leave DATETIME NOT NULL,
    paid_leave_created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_document) REFERENCES document(id_document)
);

-- 交通費申請テンプレートマスタ
CREATE TABLE travel_expense_template (
    id_template INT AUTO_INCREMENT PRIMARY KEY,
    id_employee INT NOT NULL,
    departure_location VARCHAR(255) NOT NULL,
    arrival_location VARCHAR(255) NOT NULL,
    transport_type ENUM('電車','バス','タクシー') NOT NULL,
    trip_type ENUM('片道','往復') NOT NULL,
    route VARCHAR(255) NOT NULL,
    cost DECIMAL(10,2) NOT NULL,
    template_created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_employee) REFERENCES employee(id_employee)
);

-- 承認マスタ
CREATE TABLE approval (
    id_approval INT AUTO_INCREMENT PRIMARY KEY,
    id_document INT NOT NULL,
    approval_status ENUM('承認', '未承認', '却下') NOT NULL,
    date_approval_request DATETIME NOT NULL,
    date_approval DATETIME,
    FOREIGN KEY (id_document) REFERENCES document(id_document)
);

-- 通知マスタ
CREATE TABLE notification (
    id_notification INT AUTO_INCREMENT PRIMARY KEY,
    id_employee INT NOT NULL,
    id_document INT,
    comment VARCHAR(50) NOT NULL,
    message TEXT,
    status ENUM('unread', 'read') NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    read_at DATETIME,
    FOREIGN KEY (id_employee) REFERENCES employee(id_employee),
    FOREIGN KEY (id_document) REFERENCES document(id_document)
);