package logico;

import java.util.ArrayList;

public class Tratamiento {

	private String codigoTratamiento;
	private String nombreTratamiento;
	private String descripcion;
	private ArrayList<String> medicamentos;
	private String indicaciones;
	private String duracion;

	public Tratamiento(String codigoTratamiento, String nombreTratamiento, String descripcion, String indicaciones,
			String duracion) {
		super();
		this.codigoTratamiento = codigoTratamiento;
		this.nombreTratamiento = nombreTratamiento;
		this.descripcion = descripcion;
		this.medicamentos = new ArrayList<>();
		this.indicaciones = indicaciones;
		this.duracion = duracion;
	}

	public String getNombreTratamiento() {
		return nombreTratamiento;
	}

	public void setNombreTratamiento(String nombreTratamiento) {
		this.nombreTratamiento = nombreTratamiento;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public ArrayList<String> getMedicamentos() {
		return medicamentos;
	}

	public void setMedicamentos(ArrayList<String> medicamentos) {
		this.medicamentos = medicamentos;
	}

	public String getIndicaciones() {
		return indicaciones;
	}

	public void setIndicaciones(String indicaciones) {
		this.indicaciones = indicaciones;
	}

	public String getDuracion() {
		return duracion;
	}

	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}

	public String getCodigoTratamiento() {
		return codigoTratamiento;
	}

	public void agregarMedicamentos(String medicamento) {

		medicamentos.add(medicamento);

	}

	public boolean eliminarMedicamento(String medicamento) {

		if (medicamento != null && medicamentos.contains(medicamento)) {

			medicamentos.remove(medicamento);
			return true;
		}

		return false;

	}

}
