drop table page if exists;

create table page (
  id int IDENTITY,
  name varchar(45) not null,
  uniqueId varchar(200) not null unique);
  
 