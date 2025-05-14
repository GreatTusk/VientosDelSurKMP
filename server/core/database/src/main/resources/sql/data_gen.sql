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
                                            (floor(random() * 2))::INT
                                           );

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
