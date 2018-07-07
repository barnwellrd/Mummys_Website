create table users(
	user_id varchar(4000) primary key,
	first varchar(4000),
	last varchar(4000),
	phone varchar(4000),
	email varchar(4000) not null unique,
	password varchar(4000) not null,
    banned boolean,
    admin boolean
);

CTEATE TABLE addresses(
    user_id varchar(4000) references users(user_id),
    street1 varchar(4000) not null,
    street2 varchar(4000),
    city varchar(4000) not null
    state varchar(4000) not null,
    zipcode varchar(4000) not null
);

create table orders(
	order_id varchar(4000) primary key,
	user_id varchar(4000) references users(user_id),
    extraItem_id varchar(4000) references users(extraItem_id)
	total_price number(7,2),
	placed_timestamp int,
	delivery_timestamp int,
	card_id varchar(4000) references cards(card_id),
	instructions varchar(4000),
	store_id varchar(4000) references stores(store_id),
	delivery_status_id varchar(4000) references,
    delivery_statuses(delivery_status_id),
    delivery_fee number(5,2),
    payment_type varchar(4000),		
    paid boolean,
);
create table items(
	item_id varchar(4000) not null primary key ,
	name varchar(4000),
	vegetarian char(1),
	item_type_id varchar(4000) references item_types(item_type_id),
	description varchar(4000),
	#time_slot_id varchar(4000) references time_slots(time_slot_id),
	photo varchar(4000),
	price number(5,2),
    meal_time varchar(4000),
	constraint check_vegetarian
	check(vegetarian in ('y', 'n'))
);



create table item_types(
	item_type_id varchar(4000) not null primary key,
	item_type varchar(4000)
);
create table extra_item(
extraItem_id varchar(4000) not null primary key,
extraItem_name varchar(4000),
price number(5,2)
);


create table specials(
	item_id varchar(4000) references items(item_id) ON DELETE CASCADE,
	discount_percentage int
);

create table delivery_statuses(
	delivery_status_id varchar(4000) primary key,
	delivery_status varchar(4000)
);
create table delivery_methods(
	delivery_method_id varchar(4000) primary key,
	delivery_method varchar(4000)
);

create table cards(
	card_id varchar(4000) primary key,
	user_id varchar(4000) references users(user_id) ON DELETE CASCADE,
    card_type varchar(4000),
	card_number int,
	expiry_date date,
	security_code int
);
create table stores(
	store_id varchar(4000) not null primary key,
	store varchar(4000),
    address	varchar(4000)
	phone_number int,
	open_time int,
	close_time int
);

create table order_items(
	order_id varchar(4000) references orders(order_id),
	item_id varchar(4000) references items(item_id) 
);

create table Cart(
user_id	varchar(4000) references orders(user_id),
item_id	varchar(4000) references orders(item_id),
duration number
);
