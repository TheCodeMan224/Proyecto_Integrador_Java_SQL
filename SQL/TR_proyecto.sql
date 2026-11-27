USE Envios
GO

CREATE TRIGGER tr_Clientes_ins
ON Clientes FOR INSERT AS
    ROLLBACK TRAN
    RAISERROR('No se permite insertar registros en la tabla Clientes', 16, 1)
GO