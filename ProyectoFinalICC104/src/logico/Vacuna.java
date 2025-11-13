package logico;

public class Vacuna {
	
	private String codigoVacuna;
	private String nombre;
	private String descripcion;
	private int numeroDosis;
	private boolean activa;

	public Vacuna(String codigoVacuna, String nombre, String descripcion, int numeroDosis, boolean activa) {
		super();
		this.codigoVacuna = codigoVacuna;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.numeroDosis = numeroDosis;
		this.activa = activa;
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

	public int getNumeroDosis() {
		return numeroDosis;
	}

	public void setNumeroDosis(int numeroDosis) {
		this.numeroDosis = numeroDosis;
	}

	public boolean isActiva() {
		return activa;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}

	public String getCodigoVacuna() {
		return codigoVacuna;
	}
	
	public void activar() {
		this.activa = true;
	}

	public void desactivar() {

		this.activa = false;

	}


}
