
SHOW ERRORS;

create or replace procedure sp_update_user(user_num varchar, firstN varchar, lastN varchar, pho varchar, eml varchar, psswd varchar)
as 
begin
  update users
  set first=firstN, last=lastN, phone=pho, email=eml, password=psswd
  where user_id=user_num;
end;

create or replace procedure sp_insert_user(user_id varchar, first varchar, last varchar, phone varchar, email varchar, password varchar)
as 
begin
  insert into users
  values(user_id, first, last, phone, email, password);
  
end;


create or replace procedure sp_delete_user_by_id(user_num number)
as 
begin
  Delete from users where user_id = user_num;
end;

