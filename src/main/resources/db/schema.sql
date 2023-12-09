-- database trips
BEGIN TRANSACTION;

-- *************************************************************************************************
-- Drop all db objects in the proper order
-- *************************************************************************************************
DROP TABLE IF EXISTS trip_flight, trip_hotel, flights, hotel, trips CASCADE;



-- *************************************************************************************************
-- Create the tables and constraints
-- *************************************************************************************************
-- trips
CREATE TABLE trips (
	trip_id SERIAL,
    start_date date,
    end_date date,
	CONSTRAINT PK_trip PRIMARY KEY (trip_id)
);


-- flights
CREATE TABLE flights (
	flight_id SERIAL,
    departing_from varchar(50),
    arrival_to varchar(50),
    departure_date date,
    return_date date,
	flight_price decimal(8,2) NOT NULL,
	CONSTRAINT PK_flight_id PRIMARY KEY (flight_id)
);

-- hotels
CREATE TABLE hotels (
	hotel_id SERIAL,
    hotel_name varchar(50),
    check_in_date date,
    checkout_date date,
    number_of_nights int,
	hotel_price decimal(8,2) NOT NULL,
	CONSTRAINT PK_hotel_id PRIMARY KEY (hotel_id)
);

CREATE TABLE trip_flight (
	trip_id int NOT NULL,
    flight_id int NOT NULL,
	CONSTRAINT PK_trip_flight PRIMARY KEY (trip_id, flight_id),
    CONSTRAINT FK_trip_flight_trip FOREIGN KEY (trip_id) REFERENCES trips(trip_id),
    CONSTRAINT FK_trip_flight_flight FOREIGN KEY (flight_id) REFERENCES flights(flight_id)
);

CREATE TABLE trip_hotel (
	trip_id int NOT NULL,
    hotel_id int NOT NULL,
	CONSTRAINT PK_trip_hotel PRIMARY KEY (trip_id, hotel_id),
    CONSTRAINT FK_trip_hotel_trip FOREIGN KEY (trip_id) REFERENCES trips(trip_id),
    CONSTRAINT FK_trip_hotel_hotel FOREIGN KEY (hotel_id) REFERENCES hotels(hotel_id)
);
COMMIT;