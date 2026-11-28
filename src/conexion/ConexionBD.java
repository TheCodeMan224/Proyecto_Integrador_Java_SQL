package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionBD {
	private static String servidor = "localhost";
	private static String baseDatos = "Envios";
	private static String usuario = "";
	private static String password = "";

	public static Connection Conectar(String srv, String bd, String usr, String pass) throws SQLException {
		servidor = srv;
		baseDatos = bd;
		usuario = usr;
		password = pass;

		Connection con = Conectar();
		try {
			validarPermisos(con);
		} catch (SQLException e) {
			cerrar(con);
			throw e;
		}
		return con;
	}

	public static Connection Conectar() throws SQLException {
		String urlSQL = "jdbc:sqlserver://" + servidor + ":1433;databaseName=" + baseDatos + ";encrypt=false";
		return DriverManager.getConnection(urlSQL, usuario, password);
	}

	public static void validarPermisos(Connection con) throws SQLException {
		String sql = "BEGIN TRAN " + "SELECT * FROM Clientes WHERE 1 = 0 " + "SELECT * FROM Tipos WHERE 1 = 0 "
				+ "UPDATE Clientes SET clinombre = clinombre WHERE 1 = 0 " + "DELETE FROM Clientes WHERE 1 = 0 "
				+ "DECLARE @id INT " + "SELECT @id = -1 "
				+ "EXEC Sp_MttoClientes @id OUTPUT, 'PRUEBA', 'PRUEBA', 'M', 0, 1 " + "ROLLBACK TRAN";

		Statement st = con.createStatement();
		st.execute(sql);
		st.close();

	}

	public static void cerrar(Connection con) throws SQLException {
		if (con != null) {
			con.close();
			System.out.println("Desconexion exitosa");
		}
	}
}