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

CREATE OR REPLACE PROCEDURE sp_gettAll_location()
AS
BEGIN
    SELECT * FROM Locations;
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
CREATE OR REPLACE PROCEDURE sp_getAll_card()
AS
BEGIN
    SELECT * FROM Cards;
END;

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

CREATE OR REPLACE PROCEDURE sp_getAll_deliveryMethod()
AS
BEGIN
    SELECT * FROM Delivery_Methods;
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

CREATE OR REPLACE PROCEDURE sp_getAll_deliveryStatus()
AS
BEGIN
    SELECT * FROM Delivery_Statuses
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

CREATE OR REPLACE PROCEDURE sp_getAll_menu()
AS
BEGIN
    SELECT * FROM Items;
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
CREATE OR REPLACE PROCEDURE sp_getById_menu(orderId VARCHAR)
AS
BEGIN
    SELECT * FROM Orders
    WHERE order_id=orderId;
END;

CREATE OR REPLACE PROCEDURE sp_getAll_order()
AS
BEGIN
    SELECT * FROM Orders;
END;

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

CREATE OR REPLACE PROCEDURE sp_getById_special(itemId VARCHAR)
AS
BEGIN
    SELECT * FROM Specials
    WHERE item_id=itemId;
END;

CREATE OR REPLACE PROCEDURE sp_getAll_special()
AS
BEGIN
    SELECT * FROM Specials;
END;

-- StoreService.java
--sp_insert_store
CREATE OR REPLACE PROCEDURE sp_deleteById_store(storeId VARCHAR)
AS
BEGIN
    DELETE FROM Stores
    WHERE store_id=storeId;
END;

CREATE OR REPLACE PROCEDURE sp_getById_store(storeId VARCHAR)
AS
BEGIN
    SELECT * FROM Stores
    WHERE store_id=storeId;
END;

CREATE OR REPLACE PROCEDURE sp_getAll_store()
AS
BEGIN
    SELECT * FROM Stores;
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

CREATE OR REPLACE PROCEDURE sp_getById_timeSlot(timeSlotId VARCHAR)
AS
BEGIN
    SELECT * FROM item_slot_id
    WHERE time_slot_id=timeSlotId;
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

CREATE OR REPLACE PROCEDURE sp_getById_user(userId VARCHAR)
AS
BEGIN
    SELECT * FROM Users
    WHERE user_id=userId;
END;

CREATE OR REPLACE PROCEDURE sp_getAll_user()
AS
BEGIN
    SELECT * FROM Users;
END;

CREATE OR REPLACE PROCEDURE sp_getByEmail_user(emailName VARCHAR)
AS
BEGIN
    SELECT * FROM Users
    WHERE email=emailName;
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

CREATE OR REPLACE PROCEDURE sp_getById_user_status(userStatusId VARCHAR)
AS
BEGIN
    SELECT * FROM User_statuses
    WHERE user_status_id=userStatusId;
END;

CREATE OR REPLACE PROCEDURE sp_getAll_user_status()
AS
BEGIN
    SELECT * FROM User_statuses;
END;
