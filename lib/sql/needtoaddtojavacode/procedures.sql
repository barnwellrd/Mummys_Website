-- LocationService.java
CREATE OR REPLACE PROCEDURE sp_delete_location(LOCATIONID VARCHAR2)
AS
BEGIN
    DELETE FROM Locations 
    WHERE location_id=locationId;
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

CREATE OR REPLACE PROCEDURE sp_getById_location(locationId VARCHAR)
AS
BEGIN
    SELECT * FROM Locations
    WHERE location_id=locationId;
END;

CREATE OR REPLACE PROCEDURE sp_getUserLocations_location(userId VARCHAR)
AS
BEGIN
    SELECT * FROM Locations
    WHERE user_id=userId;
END;


-- CardService.java
-- sp_insert_card
-- sp_update_card

CREATE OR REPLACE PROCEDURE sp_getById_card(cardId VARCHAR)
AS
BEGIN
    SELECT * FROM Cards
    WHERE card_id=cardId;
END;

CREATE OR REPLACE PROCEDURE sp_getUserCards_card(userId VARCHAR)
AS
BEGIN
    SELECT * FROM Cards
    WHERE user_id=userId;
END;


-- DeliveryMethodService.java
-- AddDeliveryMethod
-- DeleteDeliveryMethod
CREATE OR REPLACE PROCEDURE sp_update_deliveryMethod(deliMeth VARCHAR, deliMethId VARCHAR)
AS
BEGIN
    UPDATE Delivery_Methods SET delivery_method=deliMeth
    WHERE delivery_method_id=deliMethId;
END;

CREATE OR REPLACE PROCEDURE sp_getById_deliveryMethod(deliMethId VARCHAR)
AS
BEGIN
    SELECT * FROM Delivery_Methods
    WHERE delivery_method_id=deliMethId;
END;

-- DeliveryStatusService.java
--AddDeliveryStatus
--DeleteDeliveryStatus
CREATE OR REPLACE PROCEDURE sp_update_deliveryStatus(deliStatus VARCHAR, deliStatusId VARCHAR)
AS
BEGIN
    UPDATE Delivery_Statuses SET delivery_Status=deliStatus
    WHERE delivery_status_id=deliStatusId;
END;

CREATE OR REPLACE PROCEDURE sp_getById_deliveryStatus(deliStatus VARCHAR)
AS
BEGIN
    SELECT * FROM Delivery_Status
    WHERE delivery_status_id=deliStatusId;
END;

-- MenuService.java
CREATE OR REPLACE PROCEDURE sp_getById_menu(itemId VARCHAR)
AS
BEGIN
    SELECT * FROM Items
    WHERE item_id=itemId;
END;

CREATE OR REPLACE PROCEDURE sp_getByType_menu(itemsTypeId VARCHAR)
AS
BEGIN
    SELECT * FROM Items
    WHERE items_type_id=itemsTypeId;
END;

CREATE OR REPLACE PROCEDURE sp_delete_menu(itemId VARCHAR2)
AS
BEGIN
    DELETE FROM Items 
    WHERE item_id=itemId;
END;

CREATE OR REPLACE PROCEDURE sp_insert_menu(itemId VARCHAR, itemName VARCHAR, isVeg VARCHAR, itemType VARCHAR, itemDesc VARCHAR, slotId VARCHAR, pic VARCHAR, prices varchar)
AS
BEGIN
    insert into Items
    VALUES(itemId, itemName, isVeg, itemType, itemDesc, slotId, pic, prices);
END;

CREATE OR REPLACE PROCEDURE sp_update_menu(itemId VARCHAR, itemName VARCHAR, isVeg VARCHAR, itemType VARCHAR, itemDesc VARCHAR, slotId VARCHAR, pic VARCHAR, prices varchar)
AS
BEGIN
    UPDATE Locations SET item_id=itemId, name=itemName, vegetarian=isVeg, item_type_id=itemType, description=itemDesc, item_slot_id=slotId, photo=pic, price=prices
    WHERE item_id=itemId;
END;

-- OrderService.java
-- AssignOrderId
-- AddOrder
-- AddOrderItem
-- DeleteOrderItems
-- DeleteOrder
-- UpdateOrder

CREATE OR REPLACE PROCEDURE sp_getUserOrders_menu(userId VARCHAR)
AS
BEGIN
    SELECT * FROM Orders
    WHERE user_id=userId;
END;

-- SpecialServices.java
CREATE OR REPLACE PROCEDURE sp_insert_special(itemId VARCHAR, discPercent NUMBER)
AS
BEGIN
    INSERT INTO Specials
    VALUES(itemId, discPercent);
END;

CREATE OR REPLACE PROCEDURE sp_delete_special(itemId VARCHAR)
AS
BEGIN
    DELETE FROM Specials
    WHERE item_id=ItemId;
END;

-- StoreService.java
CREATE OR REPLACE PROCEDURE sp_update_store(storeId VARCHAR, locationId VARCHAR, storeName VARCHAR, phoneNum VARCHAR, manId VARCHAR, openTime NUMBER, closeTime NUMBER)
AS
BEGIN
    UPDATE Stores SET store_id=storeId, location_id=locationId, store=storeName, phone_number=phoneNum, manager_id=manId, open_time=openTime, close_time=closeTime
    WHERE store_id=storeId;
END;

CREATE OR REPLACE PROCEDURE sp_insert_store(storeId VARCHAR, locationId VARCHAR, storeName VARCHAR, phoneNum VARCHAR, manId VARCHAR, openTime NUMBER, closeTime NUMBER)
AS
BEGIN
    INSERT INTO Stores
    VALUES(storeId, locationId, storeName, phoneNum, manId, openTime, closeTime);
END;

CREATE OR REPLACE PROCEDURE sp_delete_store(storeId VARCHAR)
AS
BEGIN
    DELETE FROM Stores
    WHERE store_id=storeId;
END;

-- TimeSlotServices.java
CREATE OR REPLACE PROCEDURE sp_deleteById_timeSlot(timeSlotId VARCHAR)
AS
BEGIN
    DELETE FROM item_slot_id
    WHERE time_slot_id=timeSlotId;
END;

CREATE OR REPLACE PROCEDURE sp_insert_timeSlot(slotId VARCHAR, timeStart VARCHAR, timeEnd NUMBER, timeName VARCHAR)
AS
BEGIN
    insert into item_slot_id
    VALUES(slotId, timeStart, timeEnd, timeName);
END;

CREATE OR REPLACE PROCEDURE sp_getAll_timeSlot()
AS
BEGIN
    SELECT * FROM item_slot_id;
END;

-- UserService.java
--sp_insert_user
--sp_update_user
CREATE OR REPLACE PROCEDURE sp_deleteById_user(userId VARCHAR)
AS
BEGIN
    DELETE FROM Users
    WHERE user_id=userId;
END;


-- UserStatusService.java
--sp_insert_user_status
--sp_update_user_status
CREATE OR REPLACE PROCEDURE sp_deleteById_user_status(userStatusId VARCHAR)
AS
BEGIN
    DELETE FROM User_statuses
    WHERE user_status_id=userStatusId;
END;