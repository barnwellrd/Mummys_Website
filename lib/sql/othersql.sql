CREATE OR REPLACE FUNCTION AssignOrderId RETURN VARCHAR2
AS
orderid VARCHAR2(20);
BEGIN
  SELECT (TO_NUMBER(MAX(order_id))+1) INTO orderid
  from orders;
  return orderid;
END AssignOrderId;


CREATE OR REPLACE FUNCTION AssignCreditCardId RETURN VARCHAR2
AS
cardid VARCHAR2(20);
BEGIN
  SELECT (TO_NUMBER(MAX(card_id))+1) INTO cardid
  from cards;
  return cardid;
END AssignCreditCardId;

CREATE OR REPLACE FUNCTION AssignLocationId RETURN VARCHAR2
AS
locid VARCHAR2(20);
BEGIN
  SELECT (TO_NUMBER(MAX(location_id))+1) INTO locid
  from locations;
  return locid;
END AssignLocationId;


CREATE OR REPLACE FUNCTION getCreditCard(u_id VARCHAR2) RETURN VARCHAR2
AS
-- step 1: create cursor
  CURSOR u_cards IS
    SELECT card_id
    FROM cards
    WHERE user_id=u_id;
BEGIN
  FOR i IN u_cards
  LOOP
    return i.card_id;
  END LOOP;
  return -1;
END getCreditCard;

CREATE OR REPLACE PROCEDURE sp_delete_card(cid VARCHAR2)
AS
BEGIN
    DELETE 
    FROM cards 
    WHERE card_id=cid;
END sp_delete_card;

CREATE OR REPLACE PROCEDURE sp_insert_card(cardId VARCHAR, userId VARCHAR, cNum NUMBER, eD DATE, sCode NUMBER)
AS
BEGIN
    insert into cards
    VALUES(cardId, userId, cNum,eD, sCode);
END sp_insert_card;


CREATE OR REPLACE FUNCTION getLocationId(u_id VARCHAR2) RETURN VARCHAR2
AS
-- step 1: create cursor
  CURSOR l_ids IS
    SELECT location_id
    FROM locations
    WHERE user_id=u_id;
BEGIN
  FOR i IN l_ids
  LOOP
    return i.location_id;
  END LOOP;
  return -1;
END getLocationId;

CREATE OR REPLACE PROCEDURE sp_insert_location(locationId VARCHAR, userId VARCHAR, tr NUMBER, street VARCHAR, city VARCHAR, state VARCHAR, country VARCHAR, zip VARCHAR)
AS
BEGIN
    insert into locations
    VALUES(locationId, userId, tr, street, city, state, country, zip);
END sp_insert_location;