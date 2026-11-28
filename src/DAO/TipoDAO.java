package DAO;

import entidades.Tipo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import conexion.ConexionBD;

public class TipoDAO extends ConexionBD {

	// Método para consultar en la base de datos en la tabla Tipos
	public static Tipo Consultar(int tipid) throws Exception {
		Tipo reg = new Tipo();
		Connection con = Conectar();

		String sql = "SELECT * " 
				+ "FROM Tipos " 
				+ "WHERE tipid =?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, tipid);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			reg.setTipid(rs.getInt(1));
			reg.setTipnombre(rs.getString(2));
		}
		ps.close();
		con.close();
		return reg;
	}

	// Método para traer todos los tipos
	public static ArrayList<Tipo> ConsultarTodos() throws Exception {
		ArrayList<Tipo> lista = new ArrayList<>();
		Connection con = Conectar();

		String sql = "SELECT tipid, tipnombre " 
				+ "FROM Tipos " 
				+ "ORDER BY tipnombre";

		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Tipo reg = new Tipo();
			reg.setTipid(rs.getInt(1));
			reg.setTipnombre(rs.getString(2));
			lista.add(reg);
		}

		rs.close();
		ps.close();
		con.close();
		return lista;
	}
}
