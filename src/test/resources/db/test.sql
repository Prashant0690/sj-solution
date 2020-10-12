--'2020-10-10T02:12:34.171595600'

----------Floor1 Data---------------
INSERT INTO
FLOOR (movement_time_stamp,
       id)
VALUES (null, 1);


INSERT INTO main_corridor (corridor_num, floor_id, id)
VALUES (1, 1, 1);


INSERT INTO equipment (consume_power, equipment_type, default_power_status, present_power_status, main_corridor_id, id)
VALUES (5, 'LIGHT', 'ON', 'ON', 1, 1);


INSERT INTO equipment (consume_power, equipment_type, default_power_status, present_power_status, main_corridor_id, id)
VALUES (10, 'AC', 'ON', 'ON', 1, 2);


INSERT INTO sub_corridor (corridor_num, movement_time_stamp, floor_id, id)
VALUES (1, NULL, 1, 1);


INSERT INTO equipment (consume_power, equipment_type, default_power_status, present_power_status, sub_corridor_id, id)
VALUES (5, 'LIGHT', 'OFF', 'OFF', 1, 3);


INSERT INTO equipment (consume_power, equipment_type, default_power_status, present_power_status, sub_corridor_id, id)
VALUES (10, 'AC', 'ON', 'ON', 1, 4);


INSERT INTO sub_corridor (corridor_num, movement_time_stamp, floor_id, id)
VALUES (2, NULL, 1, 2);


INSERT INTO equipment (consume_power, equipment_type, default_power_status, present_power_status, sub_corridor_id, id)
VALUES (5, 'LIGHT', 'OFF', 'OFF', 2, 5);


INSERT INTO equipment (consume_power, equipment_type, default_power_status, present_power_status, sub_corridor_id, id)
VALUES (10, 'AC', 'ON', 'ON', 2, 6);


----------Floor2 Data---------------
INSERT INTO
FLOOR (movement_time_stamp,
       id)
VALUES ('2020-10-10T02:12:34.171596', 2);


INSERT INTO main_corridor (corridor_num, floor_id, id)
VALUES (1, 2, 2);


INSERT INTO equipment (consume_power, equipment_type, default_power_status, present_power_status, main_corridor_id, id)
VALUES (5, 'LIGHT', 'ON', 'ON', 2, 7);


INSERT INTO equipment (consume_power, equipment_type, default_power_status, present_power_status, main_corridor_id, id)
VALUES (10, 'AC', 'ON', 'ON', 2, 8);


INSERT INTO sub_corridor (corridor_num, movement_time_stamp, floor_id, id)
VALUES (1, NULL, 2, 3);


INSERT INTO equipment (consume_power, equipment_type, default_power_status, present_power_status, sub_corridor_id, id)
VALUES (5, 'LIGHT', 'OFF', 'OFF', 3, 9);


INSERT INTO equipment (consume_power, equipment_type, default_power_status, present_power_status, sub_corridor_id, id)
VALUES (10, 'AC', 'ON', 'ON', 3, 10);


INSERT INTO sub_corridor (corridor_num, movement_time_stamp, floor_id, id)
VALUES (2, NULL, 2, 4);


INSERT INTO equipment (consume_power, equipment_type, default_power_status, present_power_status, sub_corridor_id, id)
VALUES (5, 'LIGHT', 'OFF', 'OFF', 4, 11);


INSERT INTO equipment (consume_power, equipment_type, default_power_status, present_power_status, sub_corridor_id, id)
VALUES (10, 'AC', 'ON', 'ON', 4, 12);