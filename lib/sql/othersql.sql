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