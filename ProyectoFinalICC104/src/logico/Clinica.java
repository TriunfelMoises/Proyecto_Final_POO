package logico;

import java.util.ArrayList;

public class Clinica {
	
	private static Clinica instance = null;

	private String nombreClinica;
	private String direccion;
	private String telefono;
	private ArrayList<Paciente> pacientes;
	private ArrayList<Doctor> doctores;
	private ArrayList<Enfermedad> enfermedadesVigilancia;
	private ArrayList<Vacuna> vacunasControladas;
	private ArrayList<Tratamiento> tratamientosPredefinidos;
	private ArrayList<Cita> citas;

	private Clinica() {
		super();
		this.nombreClinica = "Clinica Los tres mosqueteros";
		this.direccion = "";
		this.telefono = "";
		this.pacientes = new ArrayList<>();
		this.doctores = new ArrayList<>();
		this.enfermedadesVigilancia = new ArrayList<>();
		this.vacunasControladas = new ArrayList<>();
		this.tratamientosPredefinidos = new ArrayList<>();
		this.citas = new ArrayList<>();
	}

	public static Clinica getInstance() {
		if (instance == null) {
			instance = new Clinica();
		}
		return instance;
	}

	public String getNombreClinica() {
		return nombreClinica;
	}

	public String getDireccion() {
		return direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public ArrayList<Paciente> getPacientes() {
		return pacientes;
	}

	public ArrayList<Doctor> getDoctores() {
		return doctores;
	}

	public ArrayList<Enfermedad> getEnfermedadesVigilancia() {
		return enfermedadesVigilancia;
	}

	public ArrayList<Vacuna> getVacunasControladas() {
		return vacunasControladas;
	}

	public ArrayList<Tratamiento> getTratamientosPredefinidos() {
		return tratamientosPredefinidos;
	}

	public ArrayList<Cita> getCitas() {
		return citas;
	}


}
