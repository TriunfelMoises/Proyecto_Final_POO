package logico;

import java.time.LocalDate;
import java.time.LocalTime;

public class Cita {
	
	private String codigoCita;
	private Paciente paciente;
	private Doctor doctor;
	private LocalDate fechaCita;
	private LocalTime horaCita;
	private String estadoCita;
	private String motivoCita;
	private Consulta consulta;

	public Cita(String codigoCita, Paciente paciente, Doctor doctor, LocalDate fechaCita, LocalTime horaCita,
			String motivoCita) {
		super();
		this.codigoCita = codigoCita;
		this.paciente = paciente;
		this.doctor = doctor;
		this.fechaCita = fechaCita;
		this.horaCita = horaCita;
		this.estadoCita = "Pendiente";
		this.motivoCita = motivoCita;
		this.consulta = null;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public LocalDate getFechaCita() {
		return fechaCita;
	}

	public void setFechaCita(LocalDate fechaCita) {
		this.fechaCita = fechaCita;
	}

	public LocalTime getHoraCita() {
		return horaCita;
	}

	public void setHoraCita(LocalTime horaCita) {
		this.horaCita = horaCita;
	}

	public String getEstadoCita() {
		return estadoCita;
	}

	public void setEstadoCita(String estadoCita) {
		this.estadoCita = estadoCita;
	}

	public String getMotivoCita() {
		return motivoCita;
	}

	public void setMotivoCita(String motivoCita) {
		this.motivoCita = motivoCita;
	}

	public String getCodigoCita() {
		return codigoCita;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public Consulta getConsulta() {
		return consulta;
	}


}
