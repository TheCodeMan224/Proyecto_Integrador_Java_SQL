USE Envios
GO

CREATE PROC Sp_MantCli
    @cliid INT OUTPUT,
    @clinombre VARCHAR(50),
    @cliApellidos VARCHAR(50),
    @cliSexo CHAR(1),
    @cliLimiteCredito NUMERIC(12,2),
    @tipid INT
AS
IF EXISTS (SELECT * FROM Clientes WHERE cliid = @cliid)
BEGIN
    UPDATE Clientes
    SET clinombre = @clinombre,
        cliApellidos = @cliApellidos,
        cliSexo = @cliSexo,
        cliLimiteCredito = @cliLimiteCredito,
        tipid = @tipid
    WHERE cliid = @cliid
END
ELSE
BEGIN
    SELECT @cliid = ISNULL(MAX(cliid), 0) + 1 FROM Clientes

    INSERT Clientes VALUES (@cliid, @clinombre, @cliApellidos, @cliSexo, @cliLimiteCredito, @tipid)
END
GO

--Como se usa
DECLARE @id INT
SELECT @id = 7
EXEC Sp_MantCli @id OUTPUT, 'ANA', 'GONZALEZ', 'F', 5000.00, 1
SELECT ClaveGenerada = @id
GO

