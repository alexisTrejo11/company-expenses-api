-- Password is 'password123' hashed with BCrypt
INSERT INTO users (email, password, first_name, last_name, role, department, last_login, is_active, created_at, updated_at) VALUES
('john.doe@company.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'John', 'Doe', 'ADMIN', 'IT', '2026-02-20 10:30:00', TRUE, '2025-01-15 09:00:00', '2026-02-20 10:30:00'),
('jane.smith@company.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Jane', 'Smith', 'MANAGER', 'Finance', '2026-02-25 14:15:00', TRUE, '2025-01-16 09:00:00', '2026-02-25 14:15:00'),
('robert.johnson@company.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Robert', 'Johnson', 'EMPLOYEE', 'Sales', '2026-02-26 08:45:00', TRUE, '2025-02-01 09:00:00', '2026-02-26 08:45:00'),
('emily.davis@company.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Emily', 'Davis', 'EMPLOYEE', 'Marketing', '2026-02-27 07:20:00', TRUE, '2025-02-10 09:00:00', '2026-02-27 07:20:00'),
('michael.brown@company.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Michael', 'Brown', 'MANAGER', 'Sales', '2026-02-26 16:30:00', TRUE, '2025-03-01 09:00:00', '2026-02-26 16:30:00'),
('sarah.wilson@company.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Sarah', 'Wilson', 'EMPLOYEE', 'IT', '2026-02-27 09:00:00', TRUE, '2025-03-15 09:00:00', '2026-02-27 09:00:00'),
('david.martinez@company.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'David', 'Martinez', 'EMPLOYEE', 'Finance', '2026-02-25 11:45:00', TRUE, '2025-04-01 09:00:00', '2026-02-25 11:45:00'),
('lisa.anderson@company.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Lisa', 'Anderson', 'MANAGER', 'Marketing', '2026-02-27 10:15:00', TRUE, '2025-04-15 09:00:00', '2026-02-27 10:15:00'),
('james.taylor@company.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'James', 'Taylor', 'EMPLOYEE', 'IT', '2026-02-26 13:20:00', TRUE, '2025-05-01 09:00:00', '2026-02-26 13:20:00'),
('maria.garcia@company.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Maria', 'Garcia', 'EMPLOYEE', 'Sales', '2026-02-27 08:30:00', TRUE, '2025-05-15 09:00:00', '2026-02-27 08:30:00'),
('william.thomas@company.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'William', 'Thomas', 'EMPLOYEE', 'Marketing', '2026-02-26 15:40:00', TRUE, '2025-06-01 09:00:00', '2026-02-26 15:40:00'),
('jennifer.lee@company.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Jennifer', 'Lee', 'EMPLOYEE', 'Finance', '2026-02-27 09:50:00', TRUE, '2025-06-15 09:00:00', '2026-02-27 09:50:00');

-- Insert demo expenses (at least 10)
INSERT INTO expenses (user_id, amount, category, description, date, receipt_url, status, approved_by, reimbursement_id, rejection_reason, created_at, updated_at, deleted_at) VALUES
(3, 250.50, 'TRAVEL', 'Flight to client meeting in Boston', '2026-02-10', 'https://storage.example.com/receipts/flight-001.pdf', 'REIMBURSED', 2, NULL, NULL, '2026-02-10 14:30:00', '2026-02-15 10:00:00', NULL),
(4, 45.75, 'FOOD', 'Team lunch with marketing department', '2026-02-11', 'https://storage.example.com/receipts/lunch-001.pdf', 'REIMBURSED', 2, NULL, NULL, '2026-02-11 13:00:00', '2026-02-16 11:00:00', NULL),
(6, 89.99, 'OFFICE_SUPPLIES', 'Office chair and desk accessories', '2026-02-12', 'https://storage.example.com/receipts/supplies-001.pdf', 'APPROVED', 2, NULL, NULL, '2026-02-12 09:15:00', '2026-02-12 16:00:00', NULL),
(7, 120.00, 'SOFTWARE_LICENSES', 'Annual IntelliJ IDEA license', '2026-02-12', 'https://storage.example.com/receipts/software-001.pdf', 'PENDING', NULL, NULL, NULL, '2026-02-12 10:00:00', '2026-02-12 10:00:00', NULL),
(9, 75.25, 'FOOD', 'Client dinner at downtown restaurant', '2026-02-13', 'https://storage.example.com/receipts/dinner-001.pdf', 'REIMBURSED', 5, NULL, NULL, '2026-02-13 20:30:00', '2026-02-18 09:30:00', NULL),
(10, 35.50, 'ENTERTAINMENT', 'Team building event tickets', '2026-02-14', 'https://storage.example.com/receipts/event-001.pdf', 'REJECTED', 2, NULL, 'Exceeds entertainment budget', '2026-02-14 11:00:00', '2026-02-14 15:00:00', NULL),
(3, 180.00, 'TRAVEL', 'Hotel accommodation for conference', '2026-02-14', 'https://storage.example.com/receipts/hotel-001.pdf', 'REIMBURSED', 2, NULL, NULL, '2026-02-14 22:00:00', '2026-02-19 14:00:00', NULL),
(4, 25.99, 'OFFICE_SUPPLIES', 'Printer paper and toner cartridge', '2026-02-15', 'https://storage.example.com/receipts/supplies-002.pdf', 'APPROVED', 8, NULL, NULL, '2026-02-15 10:30:00', '2026-02-15 14:30:00', NULL),
(6, 299.99, 'SOFTWARE_LICENSES', 'Adobe Creative Cloud subscription', '2026-02-15', 'https://storage.example.com/receipts/software-002.pdf', 'PENDING', NULL, NULL, NULL, '2026-02-15 11:00:00', '2026-02-15 11:00:00', NULL),
(11, 65.00, 'FOOD', 'Working lunch with client', '2026-02-16', 'https://storage.example.com/receipts/lunch-002.pdf', 'REIMBURSED', 5, NULL, NULL, '2026-02-16 12:45:00', '2026-02-20 10:30:00', NULL),
(12, 150.00, 'TRAVEL', 'Taxi and parking fees for site visit', '2026-02-17', 'https://storage.example.com/receipts/travel-001.pdf', 'APPROVED', 2, NULL, NULL, '2026-02-17 17:00:00', '2026-02-17 18:00:00', NULL),
(3, 42.50, 'FOOD', 'Breakfast meeting with stakeholders', '2026-02-18', 'https://storage.example.com/receipts/breakfast-001.pdf', 'REIMBURSED', 8, NULL, NULL, '2026-02-18 08:30:00', '2026-02-21 15:00:00', NULL),
(7, 199.99, 'SOFTWARE_LICENSES', 'Jira and Confluence licenses', '2026-02-18', 'https://storage.example.com/receipts/software-003.pdf', 'PENDING', NULL, NULL, NULL, '2026-02-18 14:00:00', '2026-02-18 14:00:00', NULL),
(9, 88.75, 'OFFICE_SUPPLIES', 'Wireless keyboard and mouse', '2026-02-19', 'https://storage.example.com/receipts/supplies-003.pdf', 'APPROVED', 5, NULL, NULL, '2026-02-19 09:45:00', '2026-02-19 16:00:00', NULL),
(10, 320.00, 'TRAVEL', 'Round trip train tickets to NYC', '2026-02-19', 'https://storage.example.com/receipts/train-001.pdf', 'REIMBURSED', 2, NULL, NULL, '2026-02-19 19:00:00', '2026-02-22 11:30:00', NULL),
(11, 55.00, 'ENTERTAINMENT', 'Team appreciation coffee and snacks', '2026-02-20', 'https://storage.example.com/receipts/snacks-001.pdf', 'REJECTED', 8, NULL, 'Not a business expense', '2026-02-20 15:30:00', '2026-02-20 17:00:00', NULL),
(12, 125.00, 'OFFICE_SUPPLIES', 'Standing desk converter', '2026-02-21', 'https://storage.example.com/receipts/supplies-004.pdf', 'APPROVED', 2, NULL, NULL, '2026-02-21 10:15:00', '2026-02-21 14:00:00', NULL),
(4, 95.50, 'FOOD', 'Department team lunch', '2026-02-22', 'https://storage.example.com/receipts/lunch-003.pdf', 'REIMBURSED', 5, NULL, NULL, '2026-02-22 12:00:00', '2026-02-23 09:00:00', NULL),
(6, 450.00, 'TRAVEL', 'Flight and accommodation for training', '2026-02-23', 'https://storage.example.com/receipts/travel-002.pdf', 'PENDING', NULL, NULL, NULL, '2026-02-23 08:00:00', '2026-02-23 08:00:00', NULL),
(7, 75.00, 'OFFICE_SUPPLIES', 'Monitor stand and cable management', '2026-02-24', 'https://storage.example.com/receipts/supplies-005.pdf', 'REIMBURSED', 8, NULL, NULL, '2026-02-24 11:30:00', '2026-02-24 16:00:00', NULL),
(9, 50.00, 'ENTERTAINMENT', 'Client appreciation gift', '2026-02-25', 'https://storage.example.com/receipts/gift-001.pdf', 'APPROVED', 5, NULL, NULL, '2026-02-25 14:00:00', '2026-02-25 16:30:00', NULL),
(3, 210.00, 'TRAVEL', 'Car rental for business trip', '2026-02-25', 'https://storage.example.com/receipts/rental-001.pdf', 'REIMBURSED', 2, NULL, NULL, '2026-02-25 18:00:00', '2026-02-25 10:00:00', NULL);

-- Insert demo reimbursements (at least 10)
-- Note: Expenses must exist before reimbursements that reference them
INSERT INTO reimbursements (expense_id, processed_by, reimbursement_date, created_at, updated_at) VALUES
(1, 2, '2026-02-15', '2026-02-15 10:00:00', '2026-02-15 10:00:00'),
(2, 2, '2026-02-16', '2026-02-16 11:00:00', '2026-02-16 11:00:00'),
(5, 5, '2026-02-18', '2026-02-18 09:30:00', '2026-02-18 09:30:00'),
(7, 2, '2026-02-19', '2026-02-19 14:00:00', '2026-02-19 14:00:00'),
(10, 5, '2026-02-20', '2026-02-20 10:30:00', '2026-02-20 10:30:00'),
(12, 8, '2026-02-21', '2026-02-21 15:00:00', '2026-02-21 15:00:00'),
(15, 2, '2026-02-22', '2026-02-22 11:30:00', '2026-02-22 11:30:00'),
(18, 5, '2026-02-23', '2026-02-23 09:00:00', '2026-02-23 09:00:00'),
(20, 8, '2026-02-24', '2026-02-24 16:00:00', '2026-02-24 16:00:00'),
(22, 2, '2026-02-25', '2026-02-25 10:00:00', '2026-02-25 10:00:00');

-- Link expenses to their reimbursements
UPDATE expenses SET reimbursement_id = 1 WHERE id = 1;
UPDATE expenses SET reimbursement_id = 2 WHERE id = 2;
UPDATE expenses SET reimbursement_id = 3 WHERE id = 5;
UPDATE expenses SET reimbursement_id = 4 WHERE id = 7;
UPDATE expenses SET reimbursement_id = 5 WHERE id = 10;
UPDATE expenses SET reimbursement_id = 6 WHERE id = 12;
UPDATE expenses SET reimbursement_id = 7 WHERE id = 15;
UPDATE expenses SET reimbursement_id = 8 WHERE id = 18;
UPDATE expenses SET reimbursement_id = 9 WHERE id = 20;
UPDATE expenses SET reimbursement_id = 10 WHERE id = 22;

-- Insert demo expense_attachments (at least 10)
INSERT INTO expense_attachments (expense_id, attachment_url, uploaded_at) VALUES
(1, 'https://storage.example.com/attachments/flight-boarding-pass.pdf', '2026-02-10 14:35:00'),
(1, 'https://storage.example.com/attachments/flight-itinerary.pdf', '2026-02-10 14:36:00'),
(2, 'https://storage.example.com/attachments/restaurant-menu.pdf', '2026-02-11 13:05:00'),
(3, 'https://storage.example.com/attachments/chair-invoice.pdf', '2026-02-12 09:20:00'),
(5, 'https://storage.example.com/attachments/dinner-receipt.jpg', '2026-02-13 20:35:00'),
(7, 'https://storage.example.com/attachments/hotel-confirmation.pdf', '2026-02-14 22:05:00'),
(7, 'https://storage.example.com/attachments/hotel-folio.pdf', '2026-02-14 22:06:00'),
(10, 'https://storage.example.com/attachments/lunch-receipt.jpg', '2026-02-16 12:50:00'),
(12, 'https://storage.example.com/attachments/breakfast-bill.pdf', '2026-02-18 08:35:00'),
(15, 'https://storage.example.com/attachments/train-tickets.pdf', '2026-02-19 19:05:00'),
(15, 'https://storage.example.com/attachments/train-boarding-pass.pdf', '2026-02-19 19:06:00'),
(18, 'https://storage.example.com/attachments/team-lunch-photo.jpg', '2026-02-22 12:05:00'),
(20, 'https://storage.example.com/attachments/monitor-stand-receipt.pdf', '2026-02-24 11:35:00'),
(22, 'https://storage.example.com/attachments/car-rental-agreement.pdf', '2026-02-25 18:05:00'),
(22, 'https://storage.example.com/attachments/car-rental-receipt.pdf', '2026-02-25 18:06:00');

-- Insert demo notifications (at least 10)
INSERT INTO notifications (user_id, type, message, read, created_at) VALUES
(3, 'EXPENSE_APPROVED', 'Your expense for "Flight to client meeting in Boston" has been approved.', TRUE, '2026-02-12 16:00:00'),
(3, 'EXPENSE_REIMBURSED', 'Your expense for "Flight to client meeting in Boston" has been reimbursed.', TRUE, '2026-02-15 10:00:00'),
(4, 'EXPENSE_APPROVED', 'Your expense for "Team lunch with marketing department" has been approved.', TRUE, '2026-02-12 17:00:00'),
(4, 'EXPENSE_REIMBURSED', 'Your expense for "Team lunch with marketing department" has been reimbursed.', TRUE, '2026-02-16 11:00:00'),
(6, 'EXPENSE_APPROVED', 'Your expense for "Office chair and desk accessories" has been approved.', TRUE, '2026-02-12 16:00:00'),
(10, 'EXPENSE_REJECTED', 'Your expense for "Team building event tickets" has been rejected. Reason: Exceeds entertainment budget', TRUE, '2026-02-14 15:00:00'),
(3, 'EXPENSE_APPROVED', 'Your expense for "Hotel accommodation for conference" has been approved.', TRUE, '2026-02-15 10:00:00'),
(3, 'EXPENSE_REIMBURSED', 'Your expense for "Hotel accommodation for conference" has been reimbursed.', TRUE, '2026-02-19 14:00:00'),
(4, 'EXPENSE_APPROVED', 'Your expense for "Printer paper and toner cartridge" has been approved.', FALSE, '2026-02-15 14:30:00'),
(11, 'EXPENSE_APPROVED', 'Your expense for "Working lunch with client" has been approved.', TRUE, '2026-02-17 09:00:00'),
(11, 'EXPENSE_REIMBURSED', 'Your expense for "Working lunch with client" has been reimbursed.', TRUE, '2026-02-20 10:30:00'),
(12, 'EXPENSE_APPROVED', 'Your expense for "Taxi and parking fees for site visit" has been approved.', FALSE, '2026-02-17 18:00:00'),
(3, 'EXPENSE_APPROVED', 'Your expense for "Breakfast meeting with stakeholders" has been approved.', TRUE, '2026-02-19 10:00:00'),
(3, 'EXPENSE_REIMBURSED', 'Your expense for "Breakfast meeting with stakeholders" has been reimbursed.', TRUE, '2026-02-21 15:00:00'),
(9, 'EXPENSE_APPROVED', 'Your expense for "Wireless keyboard and mouse" has been approved.', FALSE, '2026-02-19 16:00:00'),
(10, 'EXPENSE_APPROVED', 'Your expense for "Round trip train tickets to NYC" has been approved.', TRUE, '2026-02-20 09:00:00'),
(10, 'EXPENSE_REIMBURSED', 'Your expense for "Round trip train tickets to NYC" has been reimbursed.', TRUE, '2026-02-22 11:30:00'),
(11, 'EXPENSE_REJECTED', 'Your expense for "Team appreciation coffee and snacks" has been rejected. Reason: Not a business expense', FALSE, '2026-02-20 17:00:00'),
(12, 'EXPENSE_APPROVED', 'Your expense for "Standing desk converter" has been approved.', FALSE, '2026-02-21 14:00:00'),
(4, 'EXPENSE_APPROVED', 'Your expense for "Department team lunch" has been approved.', FALSE, '2026-02-22 15:00:00');

