package DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;

import conexion.ConexionBD;

import entidades.Cliente;

public class ClienteDAO extends ConexionBD {

	// Método para consultar en la base de datos en la tabla Clientes
	public static Cliente Consultar(int cliid) throws Exception {
		Cliente reg = new Cliente();
		Connection con = Conectar();

		String sql = "SELECT C.cliid, C.clinombre, C.cliApellidos, C.cliSexo, "
				+ "C.cliLimiteCredito, T.tipid, T.tipnombre " 
				+ "FROM Clientes C "
				+ "INNER JOIN Tipos T ON T.tipid = C.tipid " 
				+ "WHERE C.cliid = ?";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, cliid);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			reg.setCliid(rs.getInt(1));
			reg.setClinombre(rs.getString(2));
			reg.setCliApellidos(rs.getString(3));
			reg.setCliSexo(rs.getString(4).charAt(0));
			reg.setCliLimiteCredito(rs.getDouble(5));
			reg.setTipid(rs.getInt(6));
			reg.setTipnombre(rs.getString(7));
		}
		rs.close();
		ps.close();
		con.close();
		return reg;
	}

	// Método para eliminar un registro en la base de datos en la tabla Clientes
	public static void Elimina(int cliid) throws Exception {
		Connection con = Conectar();
		String sql = "DELETE " + "FROM Clientes " + "WHERE cliid = ?";

		PreparedStatement ps = con.prepareStatement(sql);

		ps.setInt(1, cliid);

		ps.executeUpdate();

		ps.close();
		con.close();
	}

	// Método para guardar un registro en la base de datos en la tabla Clientes
	public static int Guardar(Cliente reg) throws Exception {
		Connection con = Conectar();

		String sql = "EXEC Sp_MantCli ?,?,?,?,?,?";
		CallableStatement ps = con.prepareCall(sql); // Punto 1 en Explicaciones Callable
		ps.setInt(1, reg.getCliid());
		ps.registerOutParameter(1, Types.INTEGER); // Sirve para guardar el id del output. Punto 2 en Explicaciones Types
		ps.setString(2, reg.getClinombre());
		ps.setString(3, reg.getCliApellidos());
		ps.setString(4, String.valueOf(reg.getCliSexo()));
		ps.setDouble(5, reg.getCliLimiteCredito());
		ps.setInt(6, reg.getTipid());

		ps.execute();

		int idGenerado = ps.getInt(1);
		reg.setCliid(idGenerado);

		ps.close();
		con.close();
		return idGenerado;
	}
	
	// Método para consulta general con filtros por texto y combos
		public static ArrayList<Cliente> ConsultarGeneral(String nombre, String apellidos, String sexo, int tipid) throws Exception {
			ArrayList<Cliente> lista = new ArrayList<>();
			Connection con = Conectar();

			String sql = "SELECT C.cliid, C.clinombre, C.cliApellidos, C.cliSexo, "
					+ "C.cliLimiteCredito, T.tipid, T.tipnombre "
					+ "FROM Clientes C "
					+ "INNER JOIN Tipos T ON T.tipid = C.tipid "
					+ "WHERE C.clinombre LIKE ? "
					+ "AND C.cliApellidos LIKE ? ";

			// Punto 9: Filtro por combo de Sexo (solo si no eligió "TODOS")
			if (sexo != null && !sexo.trim().isEmpty() && !sexo.equalsIgnoreCase("TODOS")) {
				sql = sql + "AND C.cliSexo = ? ";
			}

			// Punto 9: Filtro por combo de Tipos (solo si eligió un tipo específico)
			if (tipid > 0) {
				sql = sql + "AND C.tipid = ? ";
			}

			sql = sql + "ORDER BY C.cliid";

			PreparedStatement ps = con.prepareStatement(sql);

			// Punto 8: Si vienen vacíos, manda "%%" y trae todos los registros
			ps.setString(1, "%" + (nombre == null ? "" : nombre.trim()) + "%");
			ps.setString(2, "%" + (apellidos == null ? "" : apellidos.trim()) + "%");

			int pos = 3; //contador de qué signo de interrogación toca rellenar en el PreparedStatement
			if (sexo != null && !sexo.trim().isEmpty() && !sexo.equalsIgnoreCase("TODOS")) {
				ps.setString(pos++, sexo.trim());
			}
			if (tipid > 0) {
				ps.setInt(pos++, tipid);
			}

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Cliente reg = new Cliente();
				reg.setCliid(rs.getInt(1));
				reg.setClinombre(rs.getString(2));
				reg.setCliApellidos(rs.getString(3));
				reg.setCliSexo(rs.getString(4).charAt(0));
				reg.setCliLimiteCredito(rs.getDouble(5));
				reg.setTipid(rs.getInt(6));
				reg.setTipnombre(rs.getString(7)); // Punto 10: Nombre del Tipo para la tabla
				lista.add(reg);
			}

			rs.close();
			ps.close();
			con.close();
			return lista;
		}
}

//EXPLICACIONES
/*
 * 1. No se utiliza el PrepareStatement porque en el método Guardar llamamos al
 * procedimiento almacenado que nos regresa un output, en los otros métodos
 * ejecutamos directamente líneas de sql, por eso se usa el PrepareStatement
 * 2.Types es un catálogo oficial de Java que contiene constantes para
 * representar los tipos de datos de SQL y poder traducirlos al lenguaje de
 * Java, ya que, al llegar el valor del output, java no sabe qué tipo es,
 * entonces con el Types le decimos qué tipo será
 * 
 */
