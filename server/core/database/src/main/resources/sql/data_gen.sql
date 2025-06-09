INSERT INTO "user" (id,
                    email,
                    phone_number,
                    registration_completed,
                    created_at,
                    updated_at)
VALUES ('c56a4180-65aa-42ec-a945-5fd21dec0538', 'alice.smith@example.com', '900000001', FALSE, '2022-01-10 08:00:00',
        '2022-01-10 08:00:00'),
       ('d290f1ee-6c54-4b01-90e6-d701748f0852', 'bob.johnson@example.com', '900000002', FALSE, '2021-03-15 09:00:00',
        '2021-03-15 09:00:00'),
       ('f47ac10b-58cc-4372-a567-0e02b2c3d479', 'carlos.mendez@example.com', '900000003', FALSE, '2023-06-20 10:00:00',
        '2023-06-20 10:00:00'),
       ('9a6b7f20-a3d3-4e17-8fae-1f2d3c4b5a67', 'diana.rodriguez@example.com', '900000004', FALSE,
        '2022-05-12 11:00:00', '2022-05-12 11:00:00'),
       ('3b8c9d40-2e1f-4a5b-9c0d-6e7f8a9b0c12', 'elena.garcia@example.com', '900000005', FALSE, '2021-11-08 12:00:00',
        '2021-11-08 12:00:00'),
       ('7e8f9a01-b2c3-4d5e-6f7a-8b9c0d1e2f34', 'frank.williams@example.com', '900000006', FALSE, '2023-02-15 13:00:00',
        '2023-02-15 13:00:00'),
       ('1a2b3c4d-5e6f-7a8b-9c0d-1e2f3a4b5c67', 'grace.taylor@example.com', '900000007', FALSE, '2022-07-19 14:00:00',
        '2022-07-19 14:00:00'),
       ('2b3c4d5e-6f7a-8b9c-0d1e-2f3a4b5c6d78', 'henry.martinez@example.com', '900000008', FALSE, '2021-09-03 15:00:00',
        '2021-09-03 15:00:00'),
       ('3c4d5e6f-7a8b-9c0d-1e2f-3a4b5c6d7e89', 'isabel.lopez@example.com', '900000009', FALSE, '2023-01-25 16:00:00',
        '2023-01-25 16:00:00'),
       ('4d5e6f7a-8b9c-0d1e-2f3a-4b5c6d7e8f90', 'jack.hernandez@example.com', '900000010', FALSE, '2022-04-30 17:00:00',
        '2022-04-30 17:00:00');

INSERT INTO employee (first_name,
                      last_name,
                      hire_date,
                      day_off,
                      occupation,
                      user_id)
VALUES ('Alice', 'Smith', '2022-01-10', 1, 0, 'c56a4180-65aa-42ec-a945-5fd21dec0538'),
       ('Bob', 'Johnson', '2021-03-15', 2, 0, 'd290f1ee-6c54-4b01-90e6-d701748f0852'),
       ('Carlos', 'Mendez', '2023-06-20', 6, 0, 'f47ac10b-58cc-4372-a567-0e02b2c3d479'),
       ('Diana', 'Rodriguez', '2022-05-12', 3, 0, '9a6b7f20-a3d3-4e17-8fae-1f2d3c4b5a67'),
       ('Elena', 'Garcia', '2021-11-08', 4, 0, '3b8c9d40-2e1f-4a5b-9c0d-6e7f8a9b0c12'),
       ('Frank', 'Williams', '2023-02-15', 5, 0, '7e8f9a01-b2c3-4d5e-6f7a-8b9c0d1e2f34'),
       ('Grace', 'Taylor', '2022-07-19', 1, 0, '1a2b3c4d-5e6f-7a8b-9c0d-1e2f3a4b5c67'),
       ('Henry', 'Martinez', '2021-09-03', 2, 0, '2b3c4d5e-6f7a-8b9c-0d1e-2f3a4b5c6d78'),
       ('Isabel', 'Lopez', '2023-01-25', 4, 0, '3c4d5e6f-7a8b-9c0d-1e2f-3a4b5c6d7e89'),
       ('Jack', 'Hernandez', '2022-04-30', 5, 0, '4d5e6f7a-8b9c-0d1e-2f3a-4b5c6d7e8f90');

INSERT INTO housekeeper (employee_id, housekeeper_role, preferred_floor)
VALUES (1, 0, '1'),
       (2, 1, '2'),
       (3, 1, '3'),
       (4, 0, '1'),
       (5, 1, '2'),
       (6, 2, '1'),
       (7, 0, '3'),
       (8, 1, '2'),
       (9, 2, '3'),
       (10, 0, '1');

DO
$$
    DECLARE
        start_date        DATE := CURRENT_DATE - interval '1 days';
        end_date          DATE := CURRENT_DATE + INTERVAL '29 days';
        d                 DATE := start_date;
        emp               RECORD;
        shift_counter     INT  := 1;
        new_work_shift_id INT;
        room_ids          INT[];
        room_idx          INT  := 1;
        rooms_per_day     INT  := 7;
    BEGIN
        -- Load room IDs dynamically
        SELECT array_agg(id ORDER BY id) INTO room_ids FROM room;

        WHILE d <= end_date
            LOOP
                FOR emp IN
                    SELECT e.id, e.day_off
                    FROM employee e
                             JOIN housekeeper h ON e.id = h.employee_id
                    LOOP
                        IF EXTRACT(DOW FROM d)::INT != emp.day_off THEN
                            -- Insert work shift
                            INSERT INTO work_shift (date, employee_id, shift)
                            VALUES (d, emp.id, shift_counter)
                            RETURNING id INTO new_work_shift_id;

                            -- Assign up to 7 rooms
                            FOR _ IN 1..rooms_per_day
                                LOOP
                                    INSERT INTO housekeeper_shift (work_shift_id, room_id, room_cleaning_type)
                                    VALUES (new_work_shift_id,
                                            room_ids[room_idx],
                                            (floor(random() * 2))::INT);

                                    -- Rotate room index
                                    room_idx := room_idx + 1;
                                    IF room_idx > array_length(room_ids, 1) THEN
                                        room_idx := 1;
                                    END IF;
                                END LOOP;

                            -- Rotate shift counter
                            shift_counter := shift_counter + 1;
                            IF shift_counter > 3 THEN
                                shift_counter := 1;
                            END IF;
                        END IF;
                    END LOOP;
                d := d + INTERVAL '1 day';
            END LOOP;
    END
$$;
-- Insert 10 sample guests
INSERT INTO guest (first_name, last_name, email, phone_number)
VALUES ('John', 'Doe', 'john.doe@example.com', '123456789'),
       ('Jane', 'Smith', 'jane.smith@example.com', '987654321'),
       ('Michael', 'Johnson', 'michael.johnson@example.com', '555123456'),
       ('Sarah', 'Williams', 'sarah.williams@example.com', '555987654'),
       ('David', 'Brown', 'david.brown@example.com', '555123987'),
       ('Emily', 'Jones', 'emily.jones@example.com', '555456789'),
       ('Robert', 'Miller', 'robert.miller@example.com', '555234567'),
       ('Susan', 'Davis', 'susan.davis@example.com', '555876543'),
       ('James', 'Garcia', 'james.garcia@example.com', '555345678'),
       ('Linda', 'Rodriguez', 'linda.rodriguez@example.com', '555765432');
-- Create room bookings with controlled occupancy rates
DO
$$
    DECLARE
        base_start_date  DATE := CURRENT_DATE;
        full_day         DATE := base_start_date + 7; -- 100% occupancy
        high_day         DATE := base_start_date + 14; -- 75% occupancy
        medium_day       DATE := base_start_date + 21; -- 50% occupancy
        low_day          DATE := base_start_date + 28; -- 25% occupancy

        total_rooms      INT;
        total_guests     INT;
        stay_duration    INT;
        room_id_val      INT;
        guest_id_val     INT;
        rooms_array      INT[];
        room_ids_to_book INT[];
    BEGIN
        -- Clear existing bookings
        DELETE FROM room_booking;

        -- Get total number of rooms and guests
        SELECT COUNT(*) INTO total_rooms FROM room;
        SELECT COUNT(*) INTO total_guests FROM guest;

        -- Get all room IDs and store in array - fixed to use array_agg properly
        SELECT array_agg(id ORDER BY id) INTO rooms_array FROM room;

        -- Create full occupancy day (100%)
        stay_duration := 3; -- 3 days stay
        FOR i IN 1..array_length(rooms_array, 1)
            LOOP
                guest_id_val := (i % total_guests) + 1;
                INSERT INTO room_booking (start_date, end_date, room_id, guest_id)
                VALUES (full_day - 1, full_day + stay_duration, rooms_array[i], guest_id_val);
            END LOOP;

        -- Create high occupancy day (75%)
        stay_duration := 2;
        room_ids_to_book := rooms_array[1:floor(total_rooms * 0.75)::int];
        FOR i IN 1..array_length(room_ids_to_book, 1)
            LOOP
                guest_id_val := (i % total_guests) + 1;
                INSERT INTO room_booking (start_date, end_date, room_id, guest_id)
                VALUES (high_day - 1, high_day + stay_duration, room_ids_to_book[i], guest_id_val);
            END LOOP;

        -- Create medium occupancy day (50%)
        stay_duration := 4;
        room_ids_to_book := rooms_array[1:floor(total_rooms * 0.5)::int];
        FOR i IN 1..array_length(room_ids_to_book, 1)
            LOOP
                guest_id_val := (i % total_guests) + 1;
                INSERT INTO room_booking (start_date, end_date, room_id, guest_id)
                VALUES (medium_day - 1, medium_day + stay_duration, room_ids_to_book[i], guest_id_val);
            END LOOP;

        -- Create low occupancy day (25%)
        stay_duration := 3;
        room_ids_to_book := rooms_array[1:floor(total_rooms * 0.25)::int];
        FOR i IN 1..array_length(room_ids_to_book, 1)
            LOOP
                guest_id_val := (i % total_guests) + 1;
                INSERT INTO room_booking (start_date, end_date, room_id, guest_id)
                VALUES (low_day - 1, low_day + stay_duration, room_ids_to_book[i], guest_id_val);
            END LOOP;

        -- Add some random bookings throughout other dates for variety
        FOR i IN 1..20
            LOOP
                stay_duration := floor(random() * 5) + 1;
                room_id_val := rooms_array[floor(random() * array_length(rooms_array, 1)) + 1];
                guest_id_val := floor(random() * total_guests) + 1;

                -- Generate random date excluding our special occupancy days
                DECLARE
                    booking_start DATE;
                    booking_end   DATE;
                    valid_date    BOOLEAN := FALSE;
                BEGIN
                    WHILE NOT valid_date
                        LOOP
                            booking_start := base_start_date + floor(random() * 60)::int;
                            booking_end := booking_start + stay_duration;

                            -- Check if booking overlaps with special occupancy days
                            IF NOT (
                                (booking_start <= full_day + 3 AND booking_end >= full_day - 1) OR
                                (booking_start <= high_day + 2 AND booking_end >= high_day - 1) OR
                                (booking_start <= medium_day + 4 AND booking_end >= medium_day - 1) OR
                                (booking_start <= low_day + 3 AND booking_end >= low_day - 1)
                                ) THEN
                                valid_date := TRUE;
                            END IF;
                        END LOOP;

                    -- Try to insert booking, ignore if constraint fails
                    BEGIN
                        INSERT INTO room_booking (start_date, end_date, room_id, guest_id)
                        VALUES (booking_start, booking_end, room_id_val, guest_id_val);
                    EXCEPTION
                        WHEN unique_violation THEN
                        -- Skip this booking if there's a conflict
                    END;
                END;
            END LOOP;
    END
$$;