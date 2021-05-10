create table Services(
name varchar(50) NOT NULL,
description varchar(250) NOT NULL,
price decimal(12,2) NOT NULL,
primary key(name)
);

create table Order_lines(
order_id int NOT NULL,
service_name varchar(50) NOT NULL,
quantity int NOT NULL,
primary key(order_id)
);

create table Orders(
id int NOT NULL,
customer_email varchar(100) NOT NULL,
payday date NOT NULL,
primary key(id)
);

create table Customers(
email varchar(100) NOT NULL,
city varchar(30) NOT NULL,
street varchar(50) NOT NULL,
street_number varchar(10) NOT NULL,
name varchar(50) NOT NULL,
phone_no int NOT NULL,
primary key(email)
);

create table Private_individuals(
customer_email varchar(100) NOT NULL,
id varchar(10) NOT NULL,
vat_identificator varchar(30) NOT NULL,
primary key(customer_email)
);

create table Self_employeed(
customer_email varchar(100) NOT NULL,
market_number varchar(15) NOT NULL,
vat_identificator varchar(30) NOT NULL,
primary key(customer_email)
);

create table LTDs(
customer_email varchar(100) NOT NULL,
market_register_number varchar(15) NOT NULL,
market_number varchar(15) NOT NULL,
are_payers bit NOT NULL,
primary key(customer_email)
);

create table LTD_employees(
id varchar(10) NOT NULL,
first_name varchar(20) NOT NULL,
second_name varchar(40) NOT NULL,
salary decimal(12,2) NOT NULL,
generated_income decimal(20,2) NOT NULL,
LTD_email varchar(100) NOT NULL,
primary key(id)
);

create table Owned_LTDs(
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

alter table order_lines add foreign key(service_name) references Services(name);
alter table order_lines add foreign key(order_id) references Orders(id);
alter table Orders add foreign key(customer_email) references Customers(email);
alter table private_individuals add foreign key(customer_email) references Customers(email);
alter table self_employeed add foreign key(customer_email) references Customers(email);
alter table LTDs add foreign key(customer_email) references Customers(email);
alter table LTD_employees add foreign key(LTD_email) references LTDs(customer_email);
alter table Owned_LTDs add foreign key(LTD_email) references LTDs(customer_email);
alter table Owned_LTDs add foreign key(owner_id) references LTD_owners(id);
