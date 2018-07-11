CREATE OR REPLACE PROCEDURE sp_delete_location(LOCATIONID VARCHAR2)
AS
BEGIN
    DELETE 
    FROM Locations 
    WHERE LOCATION_ID=LOCATIONID;
END;

CREATE OR REPLACE PROCEDURE sp_update_location(locationId VARCHAR, userId VARCHAR, taxRate NUMBER, st VARCHAR, ct VARCHAR, stat VARCHAR, count VARCHAR, z VARCHAR)
AS
BEGIN
    UPDATE Locations SET location_id=locationId, user_id=userId, tax_rate=taxRate, street=st, city=ct, state=stat, country=count, zip=z
    WHERE location_id=locationId;
END;


CREATE OR REPLACE PROCEDURE sp_insert_location(locationId VARCHAR, userId VARCHAR, taxRate NUMBER, street VARCHAR, city VARCHAR, state VARCHAR, country VARCHAR, zip VARCHAR)
AS
BEGIN
    insert into Locations
    VALUES(locationId, userId, taxRate, street, city, state, country, zip);
END;