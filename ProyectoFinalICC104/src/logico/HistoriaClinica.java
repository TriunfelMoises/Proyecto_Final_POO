package logico;

import java.time.LocalDate;
import java.util.ArrayList;

public class HistoriaClinica {
	
	private Paciente paciente;
	private ArrayList<Consulta> consultas;
	private LocalDate fechaCreacion;

	public HistoriaClinica(Paciente paciente, LocalDate fechaCreacion) {
		super();
		this.paciente = paciente;
		this.consultas = new ArrayList<>();
		this.fechaCreacion = fechaCreacion;

	}

	public Paciente getPaciente() {
		return paciente;
	}

	public ArrayList<Consulta> getConsultas() {
		return consultas;
	}

	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}

}
