create table Services(
id int NOT NULL,
name varchar(50) NOT NULL,
description varchar(250) NOT NULL,
price decimal(12,2) NOT NULL,
primary key(id)
);

create table Order_lines(
order_id int NOT NULL,
service_id int NOT NULL,
quantity int NOT NULL,
primary key(order_id, service_id)
);

create table Orders(
id int NOT NULL,
customer_email varchar(100),
payday date NOT NULL,
primary key(id)
);

create table Customers(
email varchar(100) NOT NULL,
street varchar(50) NOT NULL,
street_number varchar(10) NOT NULL,
phone_number varchar(20) NOT NULL,
zip_code varchar(10) NOT NULL,
customer_type varchar(100) NOT NULL,
primary key(email)
);

create table Cities(
zip_code varchar(10) NOT NULL,
city varchar(100) NOT NULL,
primary key(zip_code)
);

create table Private_individuals(
customer_email varchar(100) NOT NULL,
id varchar(10) NOT NULL,
vat_identificator varchar(30) NOT NULL,
first_name varchar(50) NOT NULL,
second_name varchar(50) NOT NULL,
primary key(customer_email)
);

create table Self_employeed(
customer_email varchar(100) NOT NULL,
market_number varchar(15) NOT NULL,
vat_identificator varchar(30) NOT NULL,
first_name varchar(50) NOT NULL,
second_name varchar(50) NOT NULL,
primary key(customer_email)
);

create table LTDs(
customer_email varchar(100) NOT NULL,
market_registration_number varchar(15) NOT NULL,
market_number varchar(15) NOT NULL,
are_payers bit NOT NULL,
company_name varchar(50) NOT NULL,
primary key(customer_email)
);

create table LTD_employees(
id varchar(10) NOT NULL,
first_name varchar(20) NOT NULL,
second_name varchar(40) NOT NULL,
salary decimal(12,2) NOT NULL,
generated_income decimal(20,2) NOT NULL,
LTD_email varchar(100),
primary key(id)
);

create table LTD_ownerships(
LTD_email varchar(100) NOT NULL,
owner_id varchar(10) NOT NULL,
primary key(LTD_email, owner_id)
);

create table LTD_owners(
id varchar(10) NOT NULL,
first_name varchar(20) NOT NULL,
second_name varchar(40) NOT NULL,
relation varchar(50) NOT NULL,
primary key(id)
);

alter table Customers add foreign key(zip_code) references Cities(zip_code);
alter table order_lines add foreign key(order_id) references Orders(id) on delete cascade on update cascade;
alter table order_lines add foreign key(service_id) references Services(id) on update cascade;
alter table Orders add foreign key(customer_email) references Customers(email) on delete set null on update cascade;
alter table private_individuals add foreign key(customer_email) references Customers(email) on delete cascade on update cascade;
alter table self_employeed add foreign key(customer_email) references Customers(email) on delete cascade on update cascade;
alter table LTDs add foreign key(customer_email) references Customers(email) on delete cascade on update cascade;
alter table LTD_employees add foreign key(LTD_email) references LTDs(customer_email) on delete set null on update cascade;
alter table LTD_ownerships add foreign key(LTD_email) references LTDs(customer_email) on delete cascade on update cascade;
alter table LTD_ownerships add foreign key(owner_id) references LTD_owners(id) on delete cascade on update cascade;
