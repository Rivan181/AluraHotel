package Logica;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import infra.MySQLConnection;

public class HuespedesDao {
	

	String Nombre;
	String Apellido;
	String Nacionalidad;
	String Telefono;
	String Id;
	String FechadeNacimiento;
	

	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	public String getApellido() {
		return Apellido;
	}
	public void setApellido(String apellido) {
		Apellido = apellido;
	}
	public String getNacionalidad() {
		return Nacionalidad;
	}
	public void setNacionalidad(String nacionalidad) {
		Nacionalidad = nacionalidad;
	}
	public String getTelefono() {
		return Telefono;
	}
	public void setTelefono(String telefono) {
		Telefono = telefono;
	}
	public String getIdReserva() {
		return Id;
	}
	public void setIdReserva(String idReserva) {
		Id = idReserva;
	}
	public String getFechadeNacimiento() {
		return FechadeNacimiento;
	}
	public void setFechadeNacimiento(String fechadeNacimiento) {
		FechadeNacimiento = fechadeNacimiento;
	}
	public  void GuardarMySQLH(String Nombre, String Apellido, String FechadeNacimiento, String Nacionalidad, String Telefono, String IdReserva ) {
		
		setNombre(Nombre);
		setApellido(Apellido);
		setFechadeNacimiento(FechadeNacimiento);
		setNacionalidad(Nacionalidad);
		setTelefono(Telefono);;
		setIdReserva(IdReserva);
		
		MySQLConnection CN = new MySQLConnection();
		
		String SQL_INSERTH= "INSERT INTO huespedes (Nombre,Apellido, FechadeNacimiento,Nacionalidad, Telefono, IdReserva) VALUES (?,?,?,?,?,?)";
		
		try {
			CallableStatement cs = CN.Conexion().prepareCall(SQL_INSERTH);
			
			cs.setString(1, getNombre());
			cs.setString(2, getApellido());
			cs.setString(3, getFechadeNacimiento());
			cs.setString(4, getNacionalidad());
			cs.setString(5, getTelefono());
			cs.setString(6, getIdReserva());
			
			cs.execute();
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public DefaultTableModel buscarH(String buscar) throws ClassNotFoundException {
		
		String [] nombreCol = {"Nombre","Apellido", "FechadeNacimiento", "Nacionalidad", "Telefono", "IdReserva"};
		String [] datos = new String [6];
		
		DefaultTableModel modeloH = new DefaultTableModel(null,nombreCol);
		String consulta = "SELECT * FROM huespedes WHERE  Id  like '%"+buscar+"%' ";
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
				datos[0] = rs.getString("Nombre");
				datos[1] = rs.getString("Apellido");
				datos[2] = rs.getString("FechadeNacimiento");
				datos[3] = rs.getString( "Nacionalidad");
				datos[4] = rs.getString("Telefono");
				datos[5] = rs.getString("IdReserva");
				modeloH.addRow(datos);
				
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
	
	public void verH(JTable tbHuespedes) {
		 MySQLConnection CN = new MySQLConnection();
		 DefaultTableModel modeloH = new DefaultTableModel();
		 
		 TableRowSorter<TableModel> OrdenarTabla = new TableRowSorter<>(modeloH);
		 tbHuespedes.setRowSorter(OrdenarTabla);
		 
		 modeloH.addColumn("Nombre");
		 modeloH.addColumn("Apellido");
		 modeloH.addColumn("Fecha de Nacimiento");
		 modeloH.addColumn("Nacionalidad");
		 modeloH.addColumn("Telefono");
		 modeloH.addColumn("Número de Reserva");
		 
			tbHuespedes.setModel(modeloH);
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
				datos[5] = rs.getString(6);
				modeloH.addRow(datos);
				
			}
			tbHuespedes.setModel(modeloH);
			
			
		} catch (Exception e) {
		JOptionPane.showMessageDialog(null, "error");
		}
		 
	 }
	
	public void selecH(JTable tbHuespedes, JTextField Nombre, JTextField Apellido, JTextField FechadeNacimiento, JTextField Nacionalidad,JTextField Telefono , JTextField IdReserva) {
	    try {
	        int fila = tbHuespedes.getSelectedRow();
	        int columna = tbHuespedes.getColumnCount();
	        
	        if (fila >= 0) {
	            Object[] datosFila = new Object[columna];
	            datosFila[0] = tbHuespedes.getValueAt(fila, 0);
	            datosFila[1] = tbHuespedes.getValueAt(fila, 1);
	            datosFila[2] = tbHuespedes.getValueAt(fila, 2);
	            datosFila[3] = tbHuespedes.getValueAt(fila, 3);
	            datosFila[4] = tbHuespedes.getValueAt(fila, 4);
	            datosFila[5] = tbHuespedes.getValueAt(fila, 5);
	            
	            
	            // Asignar los valores a los JTextField correspondientes
	            Nombre.setText(datosFila[0].toString());
	            Apellido.setText(datosFila[1].toString());
	            FechadeNacimiento.setText(datosFila[2].toString());
	            Nacionalidad.setText(datosFila[3].toString());
	            Telefono.setText(datosFila[4].toString());
	            IdReserva.setText(datosFila[5].toString());
	            
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

	public void ModificarMySQLH (JTable tbHuespedes) throws ClassNotFoundException {
	    int fila = tbHuespedes.getSelectedRow();
	    
	    String Nombre = tbHuespedes.getValueAt(fila, 0).toString();
	    String Apellido = tbHuespedes.getValueAt(fila, 1).toString();
	    String FechadeNacimiento = tbHuespedes.getValueAt(fila, 2).toString();
	    String Nacionalidad = tbHuespedes.getValueAt(fila, 3).toString();
	     int Telefono =Integer.parseInt((String) tbHuespedes.getValueAt(fila, 4));
	     int IdReserva =Integer.parseInt((String) tbHuespedes.getValueAt(fila, 5));
		  
	        MySQLConnection CN = new MySQLConnection();

	        String SQL_UPDATE = "UPDATE huespedes SET Nombre = ' "+Nombre+" ', Apellido =  ' "+Apellido+" ', FechadeNacimiento = ' "+FechadeNacimiento+" ', Nacionalidad =  ' "+Nacionalidad+" 'Telefono = ' "+Telefono+" ' WHERE IdReserva = ' "+IdReserva+" '";

	        try {
	            PreparedStatement ps = CN.Conexion().prepareStatement(SQL_UPDATE);
	             int rowsAffected = ps.executeUpdate();
	            if (rowsAffected == 1) {
	                JOptionPane.showMessageDialog(null, "El husped ha sido modificado correctamente.");
	            } else {
	                JOptionPane.showMessageDialog(null, "No se pudo modificar el huesped.");
	            }

	        } catch (SQLException e) {
	            JOptionPane.showMessageDialog(null, "Error al modificar la reserva: " + e.toString());
	        
	        }}






}
