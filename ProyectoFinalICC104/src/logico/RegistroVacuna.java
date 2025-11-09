package logico;

import java.time.LocalDate;

public class RegistroVacuna {

	private Vacuna vacuna;
	private LocalDate fechaAplicacion;
	private String numeroLote;
	private String aplicadaPor;
	private int numeroDosis;

	public RegistroVacuna(Vacuna vacuna, LocalDate fechaAplicacion, String numeroLote, String aplicadaPor,
			int numeroDosis) {
		super();
		this.vacuna = vacuna;
		this.fechaAplicacion = fechaAplicacion;
		this.numeroLote = numeroLote;
		this.aplicadaPor = aplicadaPor;
		this.numeroDosis = numeroDosis;
	}

	public Vacuna getVacuna() {
		return vacuna;
	}

	public void setVacuna(Vacuna vacuna) {
		this.vacuna = vacuna;
	}

	public LocalDate getFechaAplicacion() {
		return fechaAplicacion;
	}

	public void setFechaAplicacion(LocalDate fechaAplicacion) {
		this.fechaAplicacion = fechaAplicacion;
	}

	public String getNumeroLote() {
		return numeroLote;
	}

	public void setNumeroLote(String numeroLote) {
		this.numeroLote = numeroLote;
	}

	public String getAplicadaPor() {
		return aplicadaPor;
	}

	public void setAplicadaPor(String aplicadaPor) {
		this.aplicadaPor = aplicadaPor;
	}

	public int getNumeroDosis() {
		return numeroDosis;
	}

	public void setNumeroDosis(int numeroDosis) {
		this.numeroDosis = numeroDosis;
	}

}
