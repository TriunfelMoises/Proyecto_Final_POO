package logico;

public class Enfermedad {

	private String codigoEnfermedad;
	private String nombre;
	private String descripcion;
	private boolean bajoVigilancia;

	public Enfermedad(String codigoEnfermedad, String nombre, String descripcion, boolean bajoVigilancia) {
		super();
		this.codigoEnfermedad = codigoEnfermedad;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.bajoVigilancia = bajoVigilancia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isBajoVigilancia() {
		return bajoVigilancia;
	}

	public void setBajoVigilancia(boolean bajoVigilancia) {
		this.bajoVigilancia = bajoVigilancia;
	}

	public String getCodigoEnfermedad() {
		return codigoEnfermedad;
	}

	public void activarVigilancia() {

		this.bajoVigilancia = true;
	}

	public void desactivarVigilancia() {

		this.bajoVigilancia = false;
	}

}
