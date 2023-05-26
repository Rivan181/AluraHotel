package Logica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import infra.MySQLConnection;

public class BuscarH {
	

	public DefaultTableModel buscarH (String buscarH) throws ClassNotFoundException {
		
		String [] nombreColH = {"Nombre","Apellido", "FechadeNacimiento", "Telefono", "IdReserva"};
		String [] datosH = new String [5];
		
		DefaultTableModel modeloH = new DefaultTableModel(null,nombreColH);
		String consulta = "SELECT * FROM huespedes WHERE IdReserva  like '%"+buscarH+"%' or Nombre like  '%"+buscarH+"%' or Apellido like  '%"+buscarH+"%'";
		//argumento de busqueda parcial  "SELECT * FROM reservas WHERE reservas.Id like '%"+buscar+"%' or FormaDePago like '%"+buscar+"%' ";
	   
		Connection cn = null;
		MySQLConnection CN = new MySQLConnection();
	    PreparedStatement PS = null;
	    ResultSet rs = null;
	    try {
			cn = CN.Conexion();
			PS = cn.prepareStatement(consulta);
			rs = PS.executeQuery();
			
			while (rs.next()) {
				
				datosH[0] = rs.getString("Nombre");
				datosH[1] = rs.getString("Apellido");
				datosH[2] = rs.getString( "FechadeNacimiento");
				datosH[3] = rs.getString("Telefono");
				datosH[4] = rs.getString("IdReserva");
				modeloH.addRow(datosH);
				
			}
			
		} catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar los recursos
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (PS != null) {
                try {
                    PS.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (cn != null) {
                try {
                    cn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
		}
		
	    
	
	    
	    return modeloH;
		
	}
}




//	
//	public  void BuscarR(JTable reservas) {
//		
//    MySQLConnection CN = new MySQLConnection();
//    Statement st;
//	DefaultTableModel modelo = new DefaultTableModel();
//	
//	String SQL_muestra= "SELECT * FROM reservas";
//	
//	modelo.addColumn("Id");
//	modelo.addColumn("Check In");
//	modelo.addColumn("Check Out");
//	modelo.addColumn("Valor");
//	modelo.addColumn("FormaDePago");
//	
//	reservas.setModel(modelo);
//	
//	String [] datos = new String [5];
//	try {
//		st = CN.Conexion().createStatement();
//		ResultSet  rs = st.executeQuery(SQL_muestra);
//		while (rs.next()) {
//			datos[0] = rs.getString(1);
//			datos[1] = rs.getString(2);
//			datos[2] = rs.getString(3);
//			datos[3] = rs.getString(4);
//			datos[4] = rs.getString(5);
//			
//			modelo.addRow(datos);
//		}
//		reservas.setModel(modelo);
//		
//		
//	} catch (Exception e) {
//		JOptionPane.showMessageDialog(null, "error de conexion");
//	}
//	}
//	
//	public void BNreserva(JTextField IDbusqueda, JTextField IDres, JTextField FEres, JTextField FSres, JTextField Vres, JTextField FPres) throws ClassNotFoundException {
//		String consulta = "SELECT * FROM reservas WHERE reservas.Id = (?)";
//		
//		 MySQLConnection CN = new MySQLConnection();
//		 try {
//			CallableStatement cs = (CallableStatement) CN.Conexion().prepareCall(consulta);
//			cs.setString(1, IDbusqueda.getText());
//			cs.execute();
//			
//			ResultSet rs = cs.executeQuery();
//			if(rs.next()) {
//				JOptionPane.showMessageDialog(null, "registro encontrado");
//				IDres.setText(rs.getString("Id"));
//				FEres.setText(rs.getString("FechaEntrada"));
//				FSres.setText(rs.getString("FechaSalida"));
//				Vres.setText(rs.getString("Valor"));
//				FPres.setText(rs.getString("FormaDePago"));
//				
//			}else {
//				JOptionPane.showMessageDialog(null, "registro encontrado");
//				IDres.setText(rs.getString(""));
//				FEres.setText(rs.getString(""));
//				FSres.setText(rs.getString(""));
//				Vres.setText(rs.getString(""));
//				FPres.setText(rs.getString(""));
//			}
//			
//			
//		} catch (SQLException ex) {
//			JOptionPane.showMessageDialog(null, "error "+ex.toString());
//		}
//		
//	}
//	

