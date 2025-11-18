package logico;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class HistoriaClinica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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

	public void agregarConsulta(Consulta consulta) {
		consultas.add(consulta);
	}

	public ArrayList<Consulta> obtenerTodasConsultas() {
		return consultas;
	}

	public ArrayList<Consulta> obtenerConsultasParaResumen() {
		ArrayList<Consulta> resumen = new ArrayList<>();

		for (Consulta consulta : consultas) {
			if (consulta.vaAlResumen()) {
				resumen.add(consulta);
			}
		}

		return resumen;
	}

	public ArrayList<Consulta> obtenerConsultasEnfermedadVigilancia() {
		ArrayList<Consulta> vigilancia = new ArrayList<>();

		for (Consulta consulta : consultas) {
			if (consulta.isEsEnfermedadVigilancia()) {
				vigilancia.add(consulta);
			}
		}

		return vigilancia;
	}

	public ArrayList<Consulta> obtenerConsultasPorFecha(LocalDate inicio, LocalDate fin) {
		ArrayList<Consulta> porFechas = new ArrayList<>();

		for (Consulta consulta : consultas) {
			LocalDate fecha = consulta.getFechaConsulta();
			if ((fecha.isEqual(inicio) || fecha.isAfter(inicio)) && (fecha.isEqual(fin) || fecha.isBefore(fin))) {
				porFechas.add(consulta);
			}
		}

		return porFechas;
	}

	public ArrayList<Consulta> obtenerUltimasConsultas(int cantidad) {
		ArrayList<Consulta> ultimas = new ArrayList<>();
		int total = consultas.size();

		if (cantidad > total) {
			cantidad = total;
		}

		for (int i = total - cantidad; i < total; i++) {
			ultimas.add(consultas.get(i));
		}

		return ultimas;
	}

	public int cantidadConsultas() {
		return consultas.size();
	}

	public String generarResumen() {

		return "El resumen";
	}

}
