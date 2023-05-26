package Logica;

import java.sql.PreparedStatement;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import infra.MySQLConnection;

public class DatosHuespedes {
	
	private static final String SQL_INSERTH= "INSERT INTO huespedes (Nombre,Apellido, FechadeNacimiento,Nacionalidad, Telefono, IdReserva) VALUES (?,?,?,?,?,?)";
	private PreparedStatement PS;
	private final  MySQLConnection CN;
	
	public DatosHuespedes() {
		PS = null;
		CN= new MySQLConnection();
	}
	
	public int insertDatosH(String Nombre, String Apellido, String FechadeNacimiento, String Nacionalidad, String Telefono, String IdReserva ) {
		try {
			PS = CN.Conexion().prepareStatement(SQL_INSERTH);
			PS.setString(1, Nombre);
			PS.setString(2,Apellido);
			PS.setString(3, FechadeNacimiento);
			PS.setString(4, Nacionalidad);
			PS.setString(5, Telefono);
			PS.setString(6, IdReserva);
			int res = PS.executeUpdate();
			if(res>0) {
				JOptionPane.showMessageDialog(null, "se guardo el huesped");
			}
			
		} catch (Exception e) {
			System.out.println("Error al guardar "+e.getMessage());
		}finally {
			PS = null;
			CN.close();
		}
		return 0;
	}
}
	