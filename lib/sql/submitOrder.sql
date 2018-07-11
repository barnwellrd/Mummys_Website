CREATE OR REPLACE FUNCTION AssignOrderId RETURN VARCHAR2
AS
orderid VARCHAR2(20);
BEGIN
  SELECT (TO_NUMBER(MAX(order_id))+1) INTO orderid
  from orders;
  return orderid;
END;