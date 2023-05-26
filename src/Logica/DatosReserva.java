package Logica;

import java.sql.PreparedStatement;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import infra.MySQLConnection;

public class DatosReserva {
	
	private static final String SQL_INSERT= "INSERT INTO reservas (FechaEntrada,FechaSalida, Valor,FormaDePago) VALUES (?,?,?,?)";
	private PreparedStatement PS;
	private final  MySQLConnection CN;
	
	public DatosReserva() {
		PS = null;
		CN= new MySQLConnection();
	}
	
	public int insertDatos(String fEntrada, String fSalida, float valor, String pago ) {
		try {
			PS = CN.Conexion().prepareStatement(SQL_INSERT);
			PS.setString(1, fEntrada);
			PS.setString(2,fSalida);
			PS.setFloat(3, valor);
			PS.setString(4, pago);
			int res = PS.executeUpdate();
			if(res>0) {
				JOptionPane.showMessageDialog(null, "se guardo la reserva");
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

