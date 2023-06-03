package Logica;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.print.attribute.standard.JobMediaSheets;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.toedter.calendar.JDateChooser;

import infra.MySQLConnection;

public class ReservasDao {
	

	String fEntrada;
	String fSalida;
	 float valor;
	 String Pago;

	String Nombre;
	String Apellido;
	String Nacionalidad;
	String Telefono;
	String FechadeNacimiento;
	String IdReserva;
	 private static ResultSet IdGen;
	 MySQLConnection CN = new MySQLConnection();
	 PreparedStatement PS = null;
	 public  String txtFechaEntrada;
	 public  String txtFechaSalida;
	 public String txtNreserva;
	 float diaReserva = 25;
	 float ValorFinal;
	
	 

	 

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

	public String getFechadeNacimiento() {
		return FechadeNacimiento;
	}
	 public String getIdReserva() {
		return IdReserva;
	}
	public void setIdReserva(String idReserva) {
		IdReserva = idReserva;
	}
	public void setFechadeNacimiento(String fechadeNacimiento) {
		FechadeNacimiento = fechadeNacimiento;
	}

 public void GuardarMySQLR(String fEntrada, String fSalida, float valor, String pago) {

	
	 setfEntrada(fEntrada);
	 setfSalida(fSalida);
	 setValor(valor);
	 setPago(pago);
	 
	 
	 String SQL_INSERTR= "INSERT INTO reservas (FechaEntrada,FechaSalida, Valor,FormaDePago) VALUES (?,?,?,?)";
	 
	 try {
		CallableStatement cs = CN.Conexion().prepareCall(SQL_INSERTR);
		cs.setString(1, getfEntrada());
		cs.setString(2, getfSalida());
		cs.setFloat(3, getValor());
		cs.setString(4, getPago());
		cs.execute();
		IdGen= cs.getGeneratedKeys();
		int Id = 0;
		if (IdGen.next()) {
			Id = IdGen.getInt(1);
			JOptionPane.showMessageDialog(null, "reserva guardada con el Id "+ Id );
		}
	
	} catch (Exception e) {
		JOptionPane.showMessageDialog(null, "reserva no  insertada "+ e.toString());
	}
	
	 
 }

public DefaultTableModel buscarR(String buscar) throws ClassNotFoundException {
	
	String [] nombreCol = {"Id","FechaEntrada", "FechaSalida", "Valor", "FormaDePago"};
	String [] datos = new String [5];
	
	DefaultTableModel modelo = new DefaultTableModel(null,nombreCol);
	String consulta = "SELECT * FROM reservas WHERE Id  like '%"+buscar+"%' or FechaEntrada  like '%"+buscar+"%' or FechaSalida like '%"+buscar+"%' or FormaDePago like '%"+buscar+"%' "   ;
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
        System.out.println("error"+e.toString());
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

public  void GuardarMySQLH(String Nombre, String Apellido, String FechadeNacimiento, String Nacionalidad, String Telefono, String IdReserva ) {
		
		setNombre(Nombre);
		setApellido(Apellido);
		setFechadeNacimiento(FechadeNacimiento);
		setNacionalidad(Nacionalidad);
		setTelefono(Telefono);
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
		}
		
	}

public void verR(JTable tbReservas) {
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
	            datosFila[4] = tbReservas.getValueAt(fila, 4);
	            
	            
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
	       
 public void ModificarMySQLR (JTable tbReservas) throws ClassNotFoundException {
	    int fila = tbReservas.getSelectedRow();
	    int Id = Integer.parseInt(tbReservas.getValueAt(fila, 0).toString());
	    String fEntrada = tbReservas.getValueAt(fila, 1).toString();
	    String fSalida = tbReservas.getValueAt(fila, 2).toString();
	     Float valor =Float.parseFloat((String) tbReservas.getValueAt(fila, 3));
	    String fPago = tbReservas.getValueAt(fila, 4).toString();
	    
	        MySQLConnection CN = new MySQLConnection();

	        String SQL_UPDATE = "UPDATE reservas SET FechaEntrada = ' "+fEntrada+" ', FechaSalida =  ' "+fSalida+" ', Valor = ' "+valor+" ', FormaDePago =  ' "+fPago+" ' WHERE Id = ' "+Id+" '";

	        try {
	            PreparedStatement ps = CN.Conexion().prepareStatement(SQL_UPDATE);
	             int rowsAffected = ps.executeUpdate();
	            if (rowsAffected == 1) {
	                JOptionPane.showMessageDialog(null, "La reserva ha sido modificada correctamente.");
	            } else {
	                JOptionPane.showMessageDialog(null, "No se pudo modificar la reserva.");
	            }

	        } catch (SQLException e) {
	            JOptionPane.showMessageDialog(null, "Error al modificar la reserva: " + e.toString());
	        
	        }}
 
 public void EliminarMySQLR (JTable tbReservas) throws ClassNotFoundException {
	    int fila = tbReservas.getSelectedRow();
	    int Id = Integer.parseInt(tbReservas.getValueAt(fila, 0).toString());
	    

	    
	        MySQLConnection CN = new MySQLConnection();

	        String SQL_DELETE = "DELETE FROM reservas  WHERE Id = ' "+Id+" '";

	        try {
	            PreparedStatement ps = CN.Conexion().prepareStatement(SQL_DELETE);
	             int rowsAffected = ps.executeUpdate();
	            if (rowsAffected == 1) {
	                JOptionPane.showMessageDialog(null, "La reserva ha sido eliminada correctamente.");
	            } else {
	                JOptionPane.showMessageDialog(null, "No se pudo eliminar la reserva.");
	            }

	        } catch (SQLException e) {
	            JOptionPane.showMessageDialog(null, "Error al modificar la reserva: " + e.toString());
	        
	        }
			}

 public void EliminarMySQL (JTable tbReservas, JTable tbHuespedes) throws ClassNotFoundException {
	    int filaR = tbReservas.getSelectedRow();
	    int Id = Integer.parseInt(tbReservas.getValueAt(filaR, 0).toString());
	    int filaH = tbHuespedes.getSelectedRow();
	    int IdReserva = Integer.parseInt(tbHuespedes.getValueAt(filaH, 5).toString());


	    
	        MySQLConnection CN = new MySQLConnection();

	        String SQL_DELETER = "DELETE reservas FROM reservas JOIN  reservas.Id=huespedes.IdReserva WHERE  huespedes.IdReserva = ' "+IdReserva+"   '";
	        //String SQL_DELETEH = "DELETE FROM huespedes  WHERE IdReserva = ' "+IdReserva+" '";
	        try {
	            PreparedStatement ps = CN.Conexion().prepareStatement(SQL_DELETER);
	            int rowsAffected = ps.executeUpdate();
	          //  PreparedStatement pd = CN.Conexion().prepareStatement(SQL_DELETEH);
	            ps.executeUpdate();
	            
	             rowsAffected = ps.executeUpdate();
	         //    pd.executeUpdate();
	            if (rowsAffected == 1) {
	                JOptionPane.showMessageDialog(null, "La reserva ha sido eliminada correctamente.");
	            } else {
	                JOptionPane.showMessageDialog(null, "No se pudo eliminar la reserva.");
	            }

	        } catch (SQLException e) {
	            JOptionPane.showMessageDialog(null, "Error al modificar la reserva: " + e.toString());
	        
	        }
			}
 
public  void BuscaIdReserva(JTextField txtNreserva) throws SQLException, ClassNotFoundException {
	
	 try {
	 final String SQL_BUSCAR="Select MAX(Id) FROM reservas";
	   
	    MySQLConnection CN = new MySQLConnection();
	    PreparedStatement PS;
		PS = CN.Conexion().prepareStatement(SQL_BUSCAR);
		
		
		
		
		
		ResultSet rs = PS.executeQuery();
		if(rs.next()) {
			int idG =rs.getInt(1);
			
			txtNreserva.setText(Integer.toString(idG));
			
			
		}
	 }catch (Exception e){
		 JOptionPane.showMessageDialog(null, "no muestra "+ e.toString());
	 }
}
		
	


public float calculoValor (String  txtFechaEntrada, String txtFechaSalida) {
	
	try {
		SimpleDateFormat fechaf = new SimpleDateFormat("yyyy-MM-dd");
		Date fechaInicio = fechaf.parse(txtFechaEntrada);
		Date fechaFinal = fechaf.parse(txtFechaSalida);
		
		long transcurrido = fechaFinal.getTime()-fechaInicio.getTime();
		TimeUnit unidad = TimeUnit.DAYS;
		long dias = unidad.convert(transcurrido, TimeUnit.MILLISECONDS);
		ValorFinal = dias*diaReserva;
	} catch (Exception e) {
		System.out.println("no toma valor"+ e.toString());
		
	}
	return ValorFinal;

	

}
public float getValorFinal() {
	return ValorFinal;
}
public void setValorFinal(float valorFinal) {
	ValorFinal = valorFinal;
}

}


