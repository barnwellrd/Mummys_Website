create or replace PROCEDURE "SP_UPDATE_LOCATION" (location_id_name varchar, user_id_name varchar, tax_rate_number number, street_name varchar, city_name varchar, state_name varchar, country_name varchar, zip_name varchar)
as 
begin
  update locations
  set street = street_name,
      city = city_name,
      state = state_name,
      country = country_name,
      zip = zip_name
  where location_id=location_id_name;
end;