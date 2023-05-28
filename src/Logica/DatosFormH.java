package Logica;

import java.sql.CallableStatement;

import infra.MySQLConnection;

public class DatosFormH {
	

	String Nombre;
	String Apellido;
	String Nacionalidad;
	String Telefono;
	String IdReserva;
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
		return IdReserva;
	}
	public void setIdReserva(String idReserva) {
		IdReserva = idReserva;
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
	
	
	
}
