

insert into Cities
values
('1000', 'Copenhagen'),
('2200', 'Aarhus'),
('9000', 'Aalborg')

insert into Customers
values
('customer1@gmail.com', 'Aalborg' , 'Hobrovej', '11', 'FisherMaster', '23452345', '1000', 'Private_individual'),
('customer2@gmail.com', 'Aalborg' , 'Hobrovej', '3', 'FisherKokot', '87654321', '2200', 'Self_employed'),
('customer3@gmail.com', 'Aalborg' , 'Hobrovej', '2', 'FisherNoob', '33445566', '9000', 'LTD')

insert into Orders
values
('111', 'customer1@gmail.com', '1-1-2001'),
('222', 'customer2@gmail.com', '2-2-2002'),
('333', 'customer3@gmail.com', '3-3-2003')

insert into Services
values
('a', 'service a', '101'),
('b', 'service b', '202'),
('c', 'service c', '303')

insert into Order_lines
values
('111', 'a', '1'),
('222', 'a', '1'),
('222', 'b', '2'),
('333', 'a', '1'),
('333', 'b', '2'),
('333', 'c', '3')

insert into Private_individuals
values
('customer1@gmail.com', '1234', '23DG')

insert into Self_employed
values
('customer2@gmail.com', '5678', '67JR')

insert into LTDs
values
('customer3@gmail.com', '9087', '5275', 'True')

insert into LTD_employees
values
('1', 'Lorence', 'Kokot', '2143', '133700', 'customer3@gmail.com')

insert into LTD_owners
values
('123', 'Jordan', 'Jordon', 'Unhappy marriage')

insert into LTD_ownerships
values
('customer3@gmail.com', '123')

select * from Customers;

select * from Orders;