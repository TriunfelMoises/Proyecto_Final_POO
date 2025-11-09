package logico;

import java.time.LocalDate;

public class Consulta {
	
	private String codigoConsulta;
	private Paciente paciente;
	private Doctor doctor;
	private Cita cita;
	private LocalDate fechaConsulta;
	private String sintomas;
	private String diagnostico;
	private Tratamiento tratamiento;
	private String notasMedicas;
	private boolean incluidaEnResumen;
	private boolean esEnfermedadVigilancia;

	public Consulta(String codigoConsulta, Paciente paciente, Doctor doctor, Cita cita, LocalDate fechaConsulta,
			String sintomas, String diagnostico, Tratamiento tratamiento, String notasMedicas) {
		super();
		this.codigoConsulta = codigoConsulta;
		this.paciente = paciente;
		this.doctor = doctor;
		this.cita = cita;
		this.fechaConsulta = fechaConsulta;
		this.sintomas = sintomas;
		this.diagnostico = diagnostico;
		this.tratamiento = tratamiento;
		this.notasMedicas = notasMedicas;
		this.incluidaEnResumen = false;
		this.esEnfermedadVigilancia = false;
	}

	public String getSintomas() {
		return sintomas;
	}

	public void setSintomas(String sintomas) {
		this.sintomas = sintomas;
	}

	public String getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}

	public Tratamiento getTratamiento() {
		return tratamiento;
	}

	public void setTratamiento(Tratamiento tratamiento) {
		this.tratamiento = tratamiento;
	}

	public String getNotasMedicas() {
		return notasMedicas;
	}

	public void setNotasMedicas(String notasMedicas) {
		this.notasMedicas = notasMedicas;
	}

	public String getCodigoConsulta() {
		return codigoConsulta;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public Cita getCita() {
		return cita;
	}

	public LocalDate getFechaConsulta() {
		return fechaConsulta;
	}

	public boolean isIncluidaEnResumen() {
		return incluidaEnResumen;
	}

	public boolean isEsEnfermedadVigilancia() {
		return esEnfermedadVigilancia;
	}

	public void marcarParaResumen() {
		this.incluidaEnResumen = true;
	}

	public void marcarComoVigilancia() {
		this.esEnfermedadVigilancia = true;
	}


}
