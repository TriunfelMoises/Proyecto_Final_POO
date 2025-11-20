package logico;

import java.time.LocalDate;
import java.util.ArrayList;

public class Paciente extends Persona {

	private String codigoPaciente;
	private String tipoSangre;
	private ArrayList<Alergia> alergias;
	private LocalDate fechaRegistro;
	private HistoriaClinica historiaClinica;
	private ArrayList<RegistroVacuna> registrosVacunas;
	private boolean activo;
	private float peso;
	private float estatura;

	public Paciente(String cedula, String nombre, String apellido, String telefono, String direccion,
			LocalDate fechaNacimiento, char sexo, String codigoPaciente, String tipoSangre, LocalDate fechaRegistro, float peso, float estatura) {
		super(cedula, nombre, apellido, telefono, direccion, fechaNacimiento, sexo);
		this.codigoPaciente = codigoPaciente;
		this.tipoSangre = tipoSangre;
		this.alergias = new ArrayList<>();
		this.fechaRegistro = fechaRegistro;
		this.historiaClinica = new HistoriaClinica(this, fechaRegistro);
		this.registrosVacunas = new ArrayList<>();
		this.activo = true;
		this.peso = peso;
		this.estatura = estatura;
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

	public ArrayList<Alergia> getAlergias() {
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

	public void agregarAlergia(Alergia alergia) {

		alergias.add(alergia);

	}

	public boolean eliminarAlergia(Alergia alergia) {

		if (alergia != null && alergias.contains(alergia)) {

			alergias.remove(alergia);
			return true;
		}

		return false;

	}

	public void registrarVacuna(Vacuna vacuna, LocalDate fecha, String lote, String aplicadaPor, int numeroDosis) {

		RegistroVacuna registroVacuna = new RegistroVacuna(vacuna, fecha, lote, aplicadaPor, numeroDosis);

		registrosVacunas.add(registroVacuna);
	}

	public boolean tieneVacuna(Vacuna vacuna) {

		for (RegistroVacuna registroVacuna : registrosVacunas) {

			if (registroVacuna.getVacuna().equals(vacuna)) {
				return true;
			}

		}

		return false;
	}

	public ArrayList<Vacuna> obtenerVacunasFaltantes(ArrayList<Vacuna> vacunasActivas) {

		ArrayList<Vacuna> faltantes = new ArrayList<>();

		for (Vacuna vacunaActiva : vacunasActivas) {

			boolean tieneVacuna = false;

			for (RegistroVacuna registro : registrosVacunas) {

				if (registro.getVacuna().getCodigoVacuna().equals(vacunaActiva.getCodigoVacuna())) {
					tieneVacuna = true;
				}

			}

			if (tieneVacuna == false) {

				faltantes.add(vacunaActiva);

			}

		}

		return faltantes;

	}

	public void setAlergias(ArrayList<Alergia> alergias) {
		this.alergias = alergias;
	}

	public float getPeso() {
		return peso;
	}

	public void setPeso(float peso) {
		this.peso = peso;
	}

	public float getEstatura() {
		return estatura;
	}

	public void setEstatura(float estatura) {
		this.estatura = estatura;
	}

}
