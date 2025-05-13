DO
$$
    DECLARE
        start_date        DATE := CURRENT_DATE - interval '2 days';
        end_date          DATE := CURRENT_DATE + INTERVAL '29 days';
        d                 DATE := start_date;
        emp               RECORD;
        shift_counter     INT  := 1;
        new_work_shift_id INT;
        room_ids          INT[];
        room_idx          INT  := 1;
        rooms_per_day     INT  := 7;
        i                 INT;
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
                            FOR i IN 1..rooms_per_day
                                LOOP
                                    INSERT INTO housekeeper_shift (work_shift_id, room_id, room_cleaning_type)
                                    VALUES (new_work_shift_id,
                                            room_ids[room_idx],
                                            (floor(random() * 2))::INT -- 0 or 1
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
