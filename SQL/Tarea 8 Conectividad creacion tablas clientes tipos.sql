create database Envios
go
use Envios
go
create table Tipos(
tipid int not null,
tipnombre varchar( 20) not null )
go
alter table tipos add constraint pk_tipos primary key ( tipid)
go
create table clientes(
cliid int not null,
clinombre varchar( 50 ) not null, 
cliApellidos varchar( 50 ) not null, 
cliSexo char(1) not null,
cliLimiteCredito numeric( 12,2) not null,
tipid int not null
)
go
alter table clientes add 
constraint pk_clientes primary key ( cliid),
constraint fk_clientes_tipos foreign key ( tipid ) references tipos(tipid) ,
constraint fk_clientes_check check( clisexo in ( 'f','m' )) 
go
insert tipos values( 1, 'Chico' ) 
insert tipos values( 2, 'Mediano' ) 
insert tipos values( 3, 'Grande' ) 
insert tipos values( 4, 'Extra Grande' ) 
go
insert clientes values( 1, 'Carlos', 'Perez', 'M' , 12000  , 1) 
insert clientes values( 2, 'Ana', 'Castro', 'F' , 10000, 2) 
insert clientes values( 3, 'Pedro', 'Castro', 'M' , 5000, 3) 
insert clientes values( 4, 'Luis', 'Lugo', 'M' , 12000, 2) 
insert clientes values( 5, 'Maria', 'Lara', 'F' , 5000  , 1) 
