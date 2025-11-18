package logico;

import java.time.LocalDate;
import java.time.LocalTime;

public class Doctor extends Persona {

	private String codigoDoctor;
	private String especialidad;
	private String numeroLicencia;
	private int citasPorDia;
	private LocalTime horarioInicio;
	private LocalTime horarioFin;
	private boolean activo;

	public Doctor(String cedula, String nombre, String apellido, String telefono, String direccion,
			LocalDate fechaNacimiento, char sexo, String codigoDoctor, String especialidad, String numeroLicencia,
			int citasPorDia, LocalTime horarioInicio, LocalTime horarioFin, boolean activo) {
		super(cedula, nombre, apellido, telefono, direccion, fechaNacimiento, sexo);
		this.codigoDoctor = codigoDoctor;
		this.especialidad = especialidad;
		this.numeroLicencia = numeroLicencia;
		this.citasPorDia = citasPorDia;
		this.horarioInicio = horarioInicio;
		this.horarioFin = horarioFin;
		this.activo = activo;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	public int getCitasPorDia() {
		return citasPorDia;
	}

	public void setCitasPorDia(int citasPorDia) {
		this.citasPorDia = citasPorDia;
	}

	public LocalTime getHorarioInicio() {
		return horarioInicio;
	}

	public void setHorarioInicio(LocalTime horarioInicio) {
		this.horarioInicio = horarioInicio;
	}

	public LocalTime getHorarioFin() {
		return horarioFin;
	}

	public void setHorarioFin(LocalTime horarioFin) {
		this.horarioFin = horarioFin;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String getCodigoDoctor() {
		return codigoDoctor;
	}

	public String getNumeroLicencia() {
		return numeroLicencia;
	}

	public boolean puedeAtenderEnHorario(LocalTime hora) {

		if (hora.equals(horarioInicio) || hora.isAfter(horarioInicio) && hora.equals(horarioFin)
				|| hora.isBefore(horarioFin)) {

			return true;

		}

		else {
			return false;
		}

	}

}
