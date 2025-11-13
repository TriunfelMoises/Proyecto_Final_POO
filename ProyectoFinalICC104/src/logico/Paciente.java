package logico;

import java.time.LocalDate;
import java.util.ArrayList;

public class Paciente extends Persona {
	
	private String codigoPaciente;
	private String tipoSangre;
	private ArrayList<String> alergias;
	private LocalDate fechaRegistro;
	private HistoriaClinica historiaClinica;
	private ArrayList<RegistroVacuna> registrosVacunas;
	private boolean activo;

	public Paciente(String cedula, String nombre, String apellido, String telefono, String direccion,
			LocalDate fechaNacimiento, char sexo, String codigoPaciente, String tipoSangre, LocalDate fechaRegistro) {
		super(cedula, nombre, apellido, telefono, direccion, fechaNacimiento, sexo);
		this.codigoPaciente = codigoPaciente;
		this.tipoSangre = tipoSangre;
		this.alergias = new ArrayList<>();
		this.fechaRegistro = fechaRegistro;
		this.historiaClinica = new HistoriaClinica( fechaRegistro);
		this.registrosVacunas = new ArrayList<>();
		this.activo = true;
	}

	public String getTipoSangre() {
		return tipoSangre;
	}

	public void setTipoSangre(String tipoSangre) {
		this.tipoSangre = tipoSangre;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String getCodigoPaciente() {
		return codigoPaciente;
	}

	public ArrayList<String> getAlergias() {
		return alergias;
	}

	public LocalDate getFechaRegistro() {
		return fechaRegistro;
	}

	public HistoriaClinica getHistoriaClinica() {
		return historiaClinica;
	}

	public ArrayList<RegistroVacuna> getRegistrosVacunas() {
		return registrosVacunas;
	}


}