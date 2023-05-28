package Logica;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.toedter.calendar.JDateChooser;

import infra.MySQLConnection;

public class DatosFormR {
	
	String fEntrada;
	String fSalida;
	 float valor = 3;
	 String Pago;
	
	
	 

	 
	public String getfEntrada() {
		return fEntrada;
	}
	public void setfEntrada(String fEntrada) {
		this.fEntrada = fEntrada;
	}
	public String getfSalida() {
		return fSalida;
	}
	public void setfSalida(String fSalida) {
		this.fSalida = fSalida;
	}
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	public String getPago() {
		return Pago;
	}
	public void setPago(String pago) {
		Pago = pago;
	}
	

 public void GuardarMySQLR(String fEntrada, String fSalida, float valor, String pago) {

	
	 setfEntrada(fEntrada);
	 setfSalida(fSalida);
	 setValor(valor);
	 setPago(pago);
	 
	 MySQLConnection CN = new MySQLConnection();
	 
	 String SQL_INSERTR= "INSERT INTO reservas (FechaEntrada,FechaSalida, Valor,FormaDePago) VALUES (?,?,?,?)";
	 
	 try {
		CallableStatement cs = CN.Conexion().prepareCall(SQL_INSERTR);
		cs.setString(1, getfEntrada());
		cs.setString(2, getfSalida());
		cs.setFloat(3, getValor());
		cs.setString(4, getPago());
		
		cs.execute();
		

		
	} catch (Exception e) {
		JOptionPane.showMessageDialog(null, "reserva no  insertada "+ e.toString());
	}
	 
 }

 public DefaultTableModel buscar (String buscar) throws ClassNotFoundException {
		
		String [] nombreCol = {"Id","FechaEntrada", "FechaSalida", "Valor", "FormaDePago"};
		String [] datos = new String [5];
		
		DefaultTableModel modelo = new DefaultTableModel(null,nombreCol);
		String consulta = "SELECT * FROM reservas WHERE Id  like '%"+buscar+"%' ";
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
				
				datos[0] = rs.getString("Id");
				datos[1] = rs.getString("FechaEntrada");
				datos[2] = rs.getString( "FechaSalida");
				datos[3] = rs.getString("Valor");
				datos[4] = rs.getString("FormaDePago");
				modelo.addRow(datos);
				
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
		
	    
	
	    
	    return modelo;
		
	}
 public void verH(JTable tbReservas) {
	 MySQLConnection CN = new MySQLConnection();
	 DefaultTableModel modelo = new DefaultTableModel();
	 
	 TableRowSorter<TableModel> OrdenarTabla = new TableRowSorter<>(modelo);
	 tbReservas.setRowSorter(OrdenarTabla);
	 
	 	modelo.addColumn("Numero de Reserva");
		modelo.addColumn("Fecha Check In");
		modelo.addColumn("Fecha Check Out");
		modelo.addColumn("Valor");
		modelo.addColumn("Forma de Pago");
	 
	 tbReservas.setModel(modelo);
	 String sql = "SELECT * FROM reservas";
	 
	 String[] datos = new String[5];
	 Statement st;
	 try {
		st = CN.Conexion().createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		while (rs.next()){
			datos[0] = rs.getString(1);
			datos[1] = rs.getString(2);
			datos[2] = rs.getString(3);
			datos[3] = rs.getString(4);
			datos[4] = rs.getString(5);
			modelo.addRow(datos);
		}
		tbReservas.setModel(modelo);
		
		
	} catch (Exception e) {
	JOptionPane.showMessageDialog(null, "error");
	}
	 
	 
 }
 public void selecR(JTable tbReservas, JTextField txtFechaEntrada, JTextField txtFechaSalida, JTextField Valor, JTextField txtFormaPago) {
	    try {
	        int fila = tbReservas.getSelectedRow();
	        int columna = tbReservas.getColumnCount();
	        
	        if (fila >= 0) {
	            Object[] datosFila = new Object[columna];
	            datosFila[0] = tbReservas.getValueAt(fila, 0);
	            datosFila[1] = tbReservas.getValueAt(fila, 1);
	            datosFila[2] = tbReservas.getValueAt(fila, 2);
	            datosFila[3] = tbReservas.getValueAt(fila, 3);
	            
	            // Asignar los valores a los JTextField correspondientes
	            txtFechaEntrada.setText(datosFila[0].toString());
	            txtFechaSalida.setText(datosFila[1].toString());
	            Valor.setText(datosFila[2].toString());
	            txtFormaPago.setText(datosFila[3].toString());
	            
	            // Imprimir los datos de la fila seleccionada
	            for (Object dato : datosFila) {
	                if (dato != null) {
	                    System.out.print(dato + " ");
	                }
	            }
	            System.out.println();
	        } else {
	            JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna reserva.");
	        }
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "Error al seleccionar la reserva: " + e.toString());
	    }
	}
	       

 
 
 public void ModificarMySQLR(JTable tbReservas, JDateChooser txtFechaEntrada, JDateChooser txtFechaSalida, JTextField valor, JComboBox<String> txtFormaPago) throws ClassNotFoundException {
	    int fila = tbReservas.getSelectedRow();
	    if (fila >= 0) {
	        String fechaEntrada = ((JTextField) txtFechaEntrada.getDateEditor().getUiComponent()).getText();
	        String fechaSalida = ((JTextField) txtFechaSalida.getDateEditor().getUiComponent()).getText();
	        float valorReserva = Float.parseFloat(valor.getText());
	        String formaPago = txtFormaPago.getSelectedItem().toString();

	        int idReserva = Integer.parseInt(tbReservas.getValueAt(fila, 0).toString());

	        MySQLConnection CN = new MySQLConnection();

	        String SQL_UPDATE = "UPDATE reservas SET FechaEntrada = ?, FechaSalida = ?, Valor = ?, FormaDePago = ? WHERE Id = ?";

	        try {
	            PreparedStatement ps = CN.Conexion().prepareStatement(SQL_UPDATE);
	            ps.setString(1, fechaEntrada);
	            ps.setString(2, fechaSalida);
	            ps.setFloat(3, valorReserva);
	            ps.setString(4, formaPago);
	            ps.setInt(5, idReserva);

	            int rowsAffected = ps.executeUpdate();

	            if (rowsAffected == 1) {
	                JOptionPane.showMessageDialog(null, "La reserva ha sido modificada correctamente.");
	            } else {
	                JOptionPane.showMessageDialog(null, "No se pudo modificar la reserva.");
	            }

	        } catch (SQLException e) {
	            JOptionPane.showMessageDialog(null, "Error al modificar la reserva: " + e.toString());
	        }
	    } else {
	        JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna reserva.");
	    }
	}
}



