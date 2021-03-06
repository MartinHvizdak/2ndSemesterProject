insert into Cities
values
('1000', 'Copenhagen'),
('2200', 'Aarhus'),
('9000', 'Aalborg')

insert into Customers
values
('customer1@gmail.com', 'Hobrovej', '11', '23452345', '1000', 'Private_individual'),
('customer2@gmail.com', 'Hobrovej', '3', '87654321', '2200', 'Self_employed'),
('customer3@gmail.com', 'Hobrovej', '2', '33445566', '9000', 'LTD'),
('customer4@gmail.com', 'Hobrovej', '2', '33445566', '9000', 'LTD'),
('customer5@gmail.com', 'Hobrovej', '2', '33445566', '9000', 'LTD')

insert into Orders
values
('111', 'customer1@gmail.com', '1-1-2001'),
('222', 'customer2@gmail.com', '2-2-2002'),
('333', 'customer3@gmail.com', '3-3-2003')

insert into Services
values
(7, 'a', 'service a', '101'),
(8, 'b', 'service b', '202'),
(9, 'c', 'service c', '303')

insert into Order_lines
values
('111', 7, '1'),
('222', 7, '1'),
('222', 8, '2'),
('333', 7, '1'),
('333', 8, '2'),
('333', 9, '3')

insert into Private_individuals
values
('customer1@gmail.com', '1234', '23DG', 'Fisher', 'Master')

insert into Self_employeed
values
('customer2@gmail.com', '5678', '67JR', 'first', 'second')

insert into LTDs
values
('customer3@gmail.com', '9087', '5275', 'True', 'thatsthename'),
('customer4@gmail.com', '9087', '5275', 'True', 'thatsthename'),
('customer5@gmail.com', '9087', '5275', 'True', 'thatsthename')

insert into LTD_employees
values
('1', 'Lorence', 'Kokot', '2143', '133700', 'customer3@gmail.com'),
('2', '2', '2', '2', '2', 'customer3@gmail.com')

insert into LTD_owners
values
('123', 'Jordan', 'Jordon', 'Unhappy marriage'),
('2', '2', '2', '2')

insert into LTD_ownerships
values
('customer3@gmail.com', '123')