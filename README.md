This is a Kotlin Multiplatform project targeting Android, iOS, Desktop, Server.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

* `/server` is for the Ktor server application.

* `/shared` is for the code that will be shared between all targets in the project.
  The most important subfolder is `commonMain`. If preferred, you can add code to the platform-specific folders here too.

TODO: FIX CLIENT SIDE

docker exec -t vientos-del-sur-db pg_dump -U postgres -d vientosdelsur -s -F p -E UTF-8 > backup.sql

sample data
-- Sample employees
INSERT INTO employee (first_name, last_name, email, phone_number, hire_date, day_off, occupation)
VALUES
('Alice', 'Smith', 'alice.smith@example.com', '123456789', '2022-01-10', 1, 0),
('Bob', 'Johnson', 'bob.johnson@example.com', '987654321', '2021-03-15', 0, 0),
('Carlos', 'Mendez', 'carlos.mendez@example.com', '555123456', '2023-06-20', 6, 0);

INSERT INTO housekeeper (employee_id, housekeeper_role, preferred_floor)
VALUES
(1, 0, '1'),
(2, 1, '2'),
(3, 1, '3');

-- Sample guests
INSERT INTO guest (first_name, last_name, email, phone_number)
VALUES
('Diana', 'Lopez', 'diana.lopez@example.com', '321456987'),
('Ethan', 'Brown', 'ethan.brown@example.com', '456789123');

-- Sample room bookings (assume room_id 1 and 2 exist)
INSERT INTO room_booking (start_date, end_date, room_id, guest_id)
VALUES
(CURRENT_DATE, CURRENT_DATE + interval '5 day', 1, 1),
(CURRENT_DATE, CURRENT_DATE + interval '5 day', 2, 2);

-- Sample work shifts
INSERT INTO work_shift (date, employee_id, shift)
VALUES
(CURRENT_DATE, 1, 1),
(CURRENT_DATE, 2, 2);

-- Sample housekeeper shifts (room_id 1 and 2)
INSERT INTO housekeeper_shift (work_shift_id, room_id, room_cleaning_type)
VALUES
(1, 1, 1),
(2, 2, 2);

INSERT INTO room_status (room_status, room_id, updated_at)
VALUES
(1, 1, (CURRENT_DATE)::timestamp + interval '5 hours'),
(2, 2, (CURRENT_DATE)::timestamp);

INSERT INTO room_status (room_status, room_id, updated_at)
VALUES
(2, 1, (CURRENT_DATE)::timestamp + interval '5 hours');