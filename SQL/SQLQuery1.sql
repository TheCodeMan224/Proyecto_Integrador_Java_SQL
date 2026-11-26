create table materiales(
  clave int primary key,
  nombre char(50),
  precio numeric(12,2),
  contador int default 0
)
go


--1
create trigger tr_materiales_ins 
on materiales for insert as
	declare @precio int
	select @precio= precio from inserted
	if(@precio<=0)
	begin
		rollback tran
		raiserror('El precio debe ser mayor a cero',16,1)
	end

go

insert materiales values(1,'papel',-8,4)
insert materiales values(1,'lapiz',12,4)
go

--2
create trigger tr_materiales_del
on materiales for delete as
declare @cont int
select @cont =count(*) from deleted
if(@cont>1)
begin
	rollback tran
	raiserror('No se permiten eliminaciones masivas',16,1)
end
go

--3
create trigger tr_materiales_upd
on materiales for update as
if update(clave)
begin
	rollback tran
	raiserror('No se puede modificar la clave',16,1)
end
if update (precio)
begin
	declare @precioA int,@precioN int
	select @precioA=precio from deleted
	select @precioN=precio from inserted
	if(@precioN > 2*@precioA)
	begin
		rollback tran
		raiserror('El precio nuevo no puede ser el doble del anterior',16,1)
	end

end
go

update materiales set clave=5 where clave=1

update materiales set precio=24 where clave=1
select * from materiales
go

--4
create trigger tr_products_del 
on products for delete as
declare @dis int
declare @idmin int
select @dis=1

select @idmin=min(productid) 
from deleted

while (@idmin is not null and @dis=1)
begin
	select @dis=Discontinued
	from deleted
	where ProductID = @idmin

	select @idmin=min(productid) 
	from deleted
	where productid>@idmin

end
if(@dis=0)
begin
rollback transaction
raiserror('No se pueden eliminar productos activos',16,1)
end

-- 1. Solo el descontinuado: debe permitirse
delete products where productname = 'Prueba Descontinuado'

-- 2. Solo un activo: debe rechazarse
delete products where productname = 'Prueba Activo'

-- 3. Caso mixto (activo + descontinuado): debe rechazarse TODO
delete products where productname like 'Prueba%'


select productid, productname, discontinued
from products
where productname like 'Prueba%'
go

--5
create trigger tr_ventas_limite
on [Order Details] for delete,update as
if exists(
select od.OrderID
from [Order Details] od
where od.OrderID in (select orderid from deleted)
group by od.OrderID
having sum(od.UnitPrice*od.Quantity * (1-od.Discount)) > 1000
)
rollback tran
raiserror('El importe total de la orden no puede ser mayor a $10,000',16,1)
begin
end

go

--ejemplos 
--1
create trigger tr_products_precio
on products for insert,update as
if exists(
select unitprice 
from inserted
where UnitPrice<=0
)
begin
	rollback tran
	raiserror('No puede haber precio 0 o negativo',16,1)
end
if exists(
select Diferencia=i.UnitPrice-d.UnitPrice
from deleted d
inner join inserted i on i.ProductID = d.ProductID
where (i.UnitPrice-d.UnitPrice)/d.UnitPrice >.5
)
begin 
	rollback tran
	raiserror('No puede aumentar el precio en mas de 50%',16,1)
end
go

--2
create trigger tr_inventario_revisar
on orders for insert,update as
declare @stock int,@pedido int
select @stock=
products
go
--3
create trigger tr_empleados_edad
on employees for insert,update as
if update(birthdate)
begin
	if exists(
	select 1
	from inserted
	where datediff(year,birthdate,getdate())<18
	)
	begin
		rollback tran
		raiserror('No se permiten empleados menores de edad',16,1)
	end
end

if update(hiredate)
begin
	if exists(
	select 1
	from inserted
	where hiredate>getdate()
	)
	begin
		rollback tran
		raiserror('No se permiten fechas posteriores a la actual',16,1)
	end
end


