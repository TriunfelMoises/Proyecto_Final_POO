package logico;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Clinica implements Serializable {

	private static final long serialVersionUID = 1L;

	private static Clinica instance = null;

	private String nombreClinica;
	private String direccion;
	private String telefono;
	private ArrayList<Paciente> pacientes;
	private ArrayList<Doctor> doctores;
	private ArrayList<Enfermedad> enfermedades;
	private ArrayList<Vacuna> vacunas;
	private ArrayList<Tratamiento> tratamientosPredefinidos;
	private ArrayList<Cita> citas;
	private ArrayList<Alergia> alergias;
	private boolean primerIngresp = false;

	private int contadorPacientes;
	private int contadorDoctores;
	private int contadorCitas;
	private int contadorConsultas;
	private int contadorTratamientos;
	private int contadorEnfermedades;
	private int contadorVacunas;

	private static final int DURACION_CITA_MINUTOS = 45;

	private Clinica() {
		super();
		this.nombreClinica = "Clinica Los tres mosqueteros";
		this.direccion = "";
		this.telefono = "";
		this.pacientes = new ArrayList<>();
		this.doctores = new ArrayList<>();
		this.enfermedades = new ArrayList<>();
		this.vacunas = new ArrayList<>();
		this.tratamientosPredefinidos = new ArrayList<>();
		this.citas = new ArrayList<>();
		this.alergias = new ArrayList<>();

		this.contadorPacientes = 1;
		this.contadorDoctores = 1;
		this.contadorCitas = 1;
		this.contadorConsultas = 1;
		this.contadorTratamientos = 1;
		this.contadorEnfermedades = 1;
		this.contadorVacunas = 1;
	}

	public static Clinica getInstance() {
		if (instance == null) {
			instance = new Clinica();
		}
		return instance;
	}

	public static int getDuracionCitaMinutos() {
		return DURACION_CITA_MINUTOS;
	}

	public static void setInstance(Clinica instance) {
		Clinica.instance = instance;
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

	public ArrayList<Enfermedad> getEnfermedades() {
		return enfermedades;
	}

	public ArrayList<Vacuna> getVacunas() {
		return vacunas;
	}

	public ArrayList<Tratamiento> getTratamientosPredefinidos() {
		return tratamientosPredefinidos;
	}

	public ArrayList<Cita> getCitas() {
		return citas;
	}

	// metodos para generar codigo

	public String generarCodigoPaciente() {
		String codigo = String.format("PAC-%04d", contadorPacientes);
		contadorPacientes++;
		return codigo;
	}

	public String generarCodigoDoctor() {
		String codigo = String.format("DOC-%04d", contadorDoctores);
		contadorDoctores++;
		return codigo;
	}

	public String generarCodigoCita() {
		String codigo = String.format("CIT-%04d", contadorCitas);
		contadorCitas++;
		return codigo;
	}

	public String generarCodigoConsulta() {
		String codigo = String.format("CON-%04d", contadorConsultas);
		contadorConsultas++;
		return codigo;
	}

	public String generarCodigoTratamiento() {
		String codigo = String.format("TRA-%04d", contadorTratamientos);
		contadorTratamientos++;
		return codigo;
	}

	public String generarCodigoEnfermedad() {
		String codigo = String.format("ENF-%04d", contadorEnfermedades);
		contadorEnfermedades++;
		return codigo;
	}

	public String generarCodigoVacuna() {
		String codigo = String.format("VAC-%04d", contadorVacunas);
		contadorVacunas++;
		return codigo;
	}

	// doctores

	public boolean registrarDoctor(Doctor doctor) {
		if (doctor == null) {
			return false;
		}

		for (Doctor doctorExistente : doctores) {
			if (doctorExistente.getCedula().equals(doctor.getCedula())) {
				return false;
			}
		}

		doctores.add(doctor);
		return true;
	}

	public Doctor buscarDoctorPorCedula(String cedula) {
		for (Doctor doctor : doctores) {
			if (doctor.getCedula().equals(cedula)) {
				return doctor;
			}
		}
		return null;
	}

	public Doctor buscarDoctorPorCodigo(String codigo) {
		for (Doctor doctor : doctores) {
			if (doctor.getCodigoDoctor().equals(codigo)) {
				return doctor;
			}
		}
		return null;
	}

	public boolean modificarDoctor(Doctor doctorActualizado) {
		if (doctorActualizado == null) {
			return false;
		}

		for (Doctor doctor : doctores) {
			if (doctor.getCedula().equals(doctorActualizado.getCedula())) {
				// Actualizar todos los atributos
				doctor.setNombre(doctorActualizado.getNombre());
				doctor.setApellido(doctorActualizado.getApellido());
				doctor.setTelefono(doctorActualizado.getTelefono());
				doctor.setDireccion(doctorActualizado.getDireccion());
				doctor.setFechaNacimiento(doctorActualizado.getFechaNacimiento());
				doctor.setSexo(doctorActualizado.getSexo());
				doctor.setEspecialidad(doctorActualizado.getEspecialidad());
				doctor.setCitasPorDia(doctorActualizado.getCitasPorDia());
				doctor.setHorarioInicio(doctorActualizado.getHorarioInicio());
				doctor.setHorarioFin(doctorActualizado.getHorarioFin());
				doctor.setActivo(doctorActualizado.isActivo());
				return true;
			}
		}

		return false;
	}

	public ArrayList<Doctor> listarDoctores() {
		return doctores;
	}

	public ArrayList<Doctor> listarDoctoresPorEspecialidad(String especialidad) {
		ArrayList<Doctor> doctoresFiltrados = new ArrayList<>();

		for (Doctor doctor : doctores) {
			if (doctor.getEspecialidad().equalsIgnoreCase(especialidad)) {
				doctoresFiltrados.add(doctor);
			}
		}

		return doctoresFiltrados;
	}

	public ArrayList<Doctor> listarDoctoresActivos() {
		ArrayList<Doctor> doctoresActivos = new ArrayList<>();

		for (Doctor doctor : doctores) {
			if (doctor.isActivo()) {
				doctoresActivos.add(doctor);
			}
		}

		return doctoresActivos;
	}

	// pacientes

	public boolean registrarPaciente(Paciente paciente) {
		if (paciente == null) {
			return false;
		}

		for (Paciente pacienteExistente : pacientes) {
			if (pacienteExistente.getCedula().equals(paciente.getCedula())) {
				return false;
			}
		}

		pacientes.add(paciente);
		return true;
	}

	public Paciente buscarPacientePorCedula(String cedula) {
		for (Paciente paciente : pacientes) {
			if (paciente.getCedula().equals(cedula)) {
				return paciente;
			}
		}
		return null;
	}

	public Paciente buscarPacientePorCodigo(String codigo) {
		for (Paciente paciente : pacientes) {
			if (paciente.getCodigoPaciente().equals(codigo)) {
				return paciente;
			}
		}
		return null;
	}

	public boolean verificarSiEsPaciente(String cedula) {
		for (Paciente paciente : pacientes) {
			if (paciente.getCedula().equals(cedula)) {
				return true;
			}
		}
		return false;
	}

	public boolean modificarPaciente(Paciente pacienteActualizado) {
		if (pacienteActualizado == null) {
			return false;
		}

		for (Paciente paciente : pacientes) {
			if (paciente.getCedula().equals(pacienteActualizado.getCedula())) {

				paciente.setNombre(pacienteActualizado.getNombre());
				paciente.setApellido(pacienteActualizado.getApellido());
				paciente.setTelefono(pacienteActualizado.getTelefono());
				paciente.setDireccion(pacienteActualizado.getDireccion());
				paciente.setFechaNacimiento(pacienteActualizado.getFechaNacimiento());
				paciente.setSexo(pacienteActualizado.getSexo());
				paciente.setTipoSangre(pacienteActualizado.getTipoSangre());
				paciente.setActivo(pacienteActualizado.isActivo());
				return true;
			}
		}

		return false;
	}

	public ArrayList<Paciente> listarPacientes() {
		return pacientes;
	}

	public ArrayList<Paciente> listarPacientesActivos() {
		ArrayList<Paciente> pacientesActivos = new ArrayList<>();

		for (Paciente paciente : pacientes) {
			if (paciente.isActivo()) {
				pacientesActivos.add(paciente);
			}
		}

		return pacientesActivos;
	}

	// enfermedades

	public boolean registrarEnfermedad(Enfermedad enfermedad) {
		if (enfermedad == null) {
			return false;
		}

		for (Enfermedad enfermedadExistente : enfermedades) {
			if (enfermedadExistente.getCodigoEnfermedad().equals(enfermedad.getCodigoEnfermedad())) {
				return false;
			}
		}

		enfermedades.add(enfermedad);
		return true;
	}

	public Enfermedad buscarEnfermedadPorCodigo(String codigo) {
		for (Enfermedad enfermedad : enfermedades) {
			if (enfermedad.getCodigoEnfermedad().equals(codigo)) {
				return enfermedad;
			}
		}
		return null;
	}

	public Enfermedad buscarEnfermedadPorNombre(String nombre) {
		for (Enfermedad enfermedad : enfermedades) {
			if (enfermedad.getNombre().equalsIgnoreCase(nombre)) {
				return enfermedad;
			}
		}
		return null;
	}

	public boolean activarVigilanciaEnfermedad(String codigoEnfermedad) {
		Enfermedad enfermedad = buscarEnfermedadPorCodigo(codigoEnfermedad);

		if (enfermedad != null) {
			enfermedad.activarVigilancia();
			return true;
		}

		return false;
	}

	public boolean desactivarVigilanciaEnfermedad(String codigoEnfermedad) {
		Enfermedad enfermedad = buscarEnfermedadPorCodigo(codigoEnfermedad);

		if (enfermedad != null) {
			enfermedad.desactivarVigilancia();
			return true;
		}

		return false;
	}

	public ArrayList<Enfermedad> listarEnfermedades() {
		return enfermedades;
	}

	public ArrayList<Enfermedad> listarEnfermedadesVigiladas() {
		ArrayList<Enfermedad> enfermedadesVigiladas = new ArrayList<>();

		for (Enfermedad enfermedad : enfermedades) {
			if (enfermedad.isBajoVigilancia()) {
				enfermedadesVigiladas.add(enfermedad);
			}
		}

		return enfermedadesVigiladas;
	}

	public boolean esEnfermedadVigilada(String nombreEnfermedad) {
		for (Enfermedad enfermedad : enfermedades) {

			if (enfermedad.getNombre().equalsIgnoreCase(nombreEnfermedad)) {
				return enfermedad.isBajoVigilancia();
			}
		}
		return false;
	}

	// vacunas

	public boolean agregarVacuna(Vacuna vacuna) {
		if (vacuna == null) {
			return false;
		}

		for (Vacuna vacunaExistente : vacunas) {
			if (vacunaExistente.getCodigoVacuna().equals(vacuna.getCodigoVacuna())) {
				return false;
			}
		}

		vacunas.add(vacuna);
		return true;
	}

	public Vacuna buscarVacunaPorCodigo(String codigo) {
		for (Vacuna vacuna : vacunas) {
			if (vacuna.getCodigoVacuna().equals(codigo)) {
				return vacuna;
			}
		}
		return null;
	}

	public boolean activarVacuna(String codigoVacuna) {
		Vacuna vacuna = buscarVacunaPorCodigo(codigoVacuna);

		if (vacuna != null) {
			vacuna.activar();
			return true;
		}

		return false;
	}

	public boolean desactivarVacuna(String codigoVacuna) {
		Vacuna vacuna = buscarVacunaPorCodigo(codigoVacuna);

		if (vacuna != null) {
			vacuna.desactivar();
			return true;
		}

		return false;
	}

	public ArrayList<Vacuna> listarVacunas() {
		return vacunas;
	}

	public ArrayList<Vacuna> listarVacunasActivas() {
		ArrayList<Vacuna> vacunasActivas = new ArrayList<>();

		for (Vacuna vacuna : vacunas) {
			if (vacuna.isActiva()) {
				vacunasActivas.add(vacuna);
			}
		}

		return vacunasActivas;
	}

	// tratamientos

	public boolean agregarTratamiento(Tratamiento tratamiento) {
		if (tratamiento == null) {
			return false;
		}

		for (Tratamiento tratamientoExistente : tratamientosPredefinidos) {
			if (tratamientoExistente.getCodigoTratamiento().equals(tratamiento.getCodigoTratamiento())) {
				return false;
			}
		}

		tratamientosPredefinidos.add(tratamiento);
		return true;
	}

	public Tratamiento buscarTratamientoPorCodigo(String codigo) {
		for (Tratamiento tratamiento : tratamientosPredefinidos) {
			if (tratamiento.getCodigoTratamiento().equals(codigo)) {
				return tratamiento;
			}
		}
		return null;
	}

	public ArrayList<Tratamiento> listarTratamientos() {
		return tratamientosPredefinidos;
	}

	public Tratamiento buscarTratamientoPorNombre(String nombre) {
		for (Tratamiento tratamiento : tratamientosPredefinidos) {
			if (tratamiento.getNombreTratamiento().equalsIgnoreCase(nombre)) {
				return tratamiento;
			}
		}
		return null;
	}

	// cistas

	public Cita agendarCita(String cedulaPaciente, String cedulaDoctor, LocalDate fecha, LocalTime hora,
			String motivo) {

		if (fecha.isBefore(LocalDate.now())) {

			return null;

		}

		Paciente paciente = buscarPacientePorCedula(cedulaPaciente);
		if (paciente == null) {
			return null; // Paciente no existe
		}

		Doctor doctor = buscarDoctorPorCedula(cedulaDoctor);
		if (doctor == null) {
			return null; // Doctor no existe
		}

		if (!verificarDisponibilidadDoctor(cedulaDoctor, fecha, hora)) {
			return null; // Doctor no disponible a esa hora
		}

		int citasDelDia = contarCitasDelDia(cedulaDoctor, fecha);
		if (citasDelDia >= doctor.getCitasPorDia()) {
			return null; // Doctor ya llegó al límite de citas
		}

		if (!doctor.puedeAtenderEnHorario(hora)) {
			return null; // Fuera del horario laboral
		}

		// Todo OK, crear la cita
		String codigoCita = generarCodigoCita();
		Cita nuevaCita = new Cita(codigoCita, paciente, doctor, fecha, hora, motivo);

		citas.add(nuevaCita);
		return nuevaCita;
	}

	public boolean verificarDisponibilidadDoctor(String cedulaDoctor, LocalDate fecha, LocalTime hora) {
		Doctor doctor = buscarDoctorPorCedula(cedulaDoctor);
		if (doctor == null) {
			return false;
		}

		// Verificar que la hora esté dentro del horario del doctor
		if (!doctor.puedeAtenderEnHorario(hora)) {
			return false;
		}

		// Calcular hora de fin de la nueva cita
		LocalTime horaFinNuevaCita = hora.plusMinutes(DURACION_CITA_MINUTOS);

		// Verificar que no haya conflictos con otras citas
		for (Cita cita : citas) {
			boolean mismoDoctorFecha = cita.getDoctor().getCedula().equals(cedulaDoctor)
					&& cita.getFechaCita().equals(fecha);
			boolean citaValida = !cita.getEstadoCita().equals("Cancelada");

			if (mismoDoctorFecha && citaValida) {
				LocalTime horaInicioCitaExistente = cita.getHoraCita();
				LocalTime horaFinCitaExistente = horaInicioCitaExistente.plusMinutes(DURACION_CITA_MINUTOS);

				// Verificar solapamiento de horarios
				boolean haySolapamiento = (hora.isBefore(horaFinCitaExistente)
						&& horaFinNuevaCita.isAfter(horaInicioCitaExistente));

				if (haySolapamiento) {
					return false;
				}
			}
		}

		return true;
	}

	// Obtiene una lista de horarios disponibles para un doctor en una fecha
	// específica

	public ArrayList<LocalTime> obtenerHorariosDisponibles(String cedulaDoctor, LocalDate fecha) {
		ArrayList<LocalTime> horariosDisponibles = new ArrayList<>();

		Doctor doctor = buscarDoctorPorCedula(cedulaDoctor);
		if (doctor == null || !doctor.isActivo()) {
			return horariosDisponibles;
		}

		// Verificar que el doctor no haya alcanzado el límite de citas
		int citasDelDia = contarCitasDelDia(cedulaDoctor, fecha);
		if (citasDelDia >= doctor.getCitasPorDia()) {
			return horariosDisponibles; // Ya no hay cupos
		}

		// Generar intervalos de tiempo desde horarioInicio hasta horarioFin
		LocalTime horaActual = doctor.getHorarioInicio();
		LocalTime horarioFin = doctor.getHorarioFin();

		while (horaActual.plusMinutes(DURACION_CITA_MINUTOS).isBefore(horarioFin)
				|| horaActual.plusMinutes(DURACION_CITA_MINUTOS).equals(horarioFin)) {

			if (verificarDisponibilidadDoctor(cedulaDoctor, fecha, horaActual)) {
				horariosDisponibles.add(horaActual);
			}

			// Avanzar al siguiente intervalo de 45 minutos
			horaActual = horaActual.plusMinutes(DURACION_CITA_MINUTOS);
		}

		return horariosDisponibles;
	}

	// Obtiene una representación en texto del doctor para mostrar en ComboBox

	public String obtenerInfoDoctorParaCombo(Doctor doctor) {
		return doctor.getNombre() + " " + doctor.getApellido() + " - " + doctor.getEspecialidad();
	}

	public int contarCitasDelDia(String cedulaDoctor, LocalDate fecha) {
		int contador = 0;

		for (Cita cita : citas) {
			boolean mismoDoctorFecha = cita.getDoctor().getCedula().equals(cedulaDoctor)
					&& cita.getFechaCita().equals(fecha);
			boolean citaValida = !cita.getEstadoCita().equals("Cancelada");

			if (mismoDoctorFecha && citaValida) {
				contador++;
			}
		}

		return contador;
	}

	// Obtiene cuántas citas le quedan disponibles a un doctor en una fecha

	public String obtenerCitasDisponiblesTexto(String cedulaDoctor, LocalDate fecha) {
		Doctor doctor = buscarDoctorPorCedula(cedulaDoctor);
		if (doctor == null) {
			return "Doctor no encontrado";
		}

		int citasUsadas = contarCitasDelDia(cedulaDoctor, fecha);
		int citasDisponibles = doctor.getCitasPorDia() - citasUsadas;

		return citasDisponibles + " de " + doctor.getCitasPorDia() + " citas disponibles";
	}

	public Cita buscarCita(String codigoCita) {
		for (Cita cita : citas) {
			if (cita.getCodigoCita().equals(codigoCita)) {
				return cita;
			}
		}
		return null;
	}

	public ArrayList<Cita> listarCitasPorDoctor(String cedulaDoctor, LocalDate fecha) {
		ArrayList<Cita> citasDoctor = new ArrayList<>();

		for (Cita cita : citas) {
			boolean mismoDoctorFecha = cita.getDoctor().getCedula().equals(cedulaDoctor)
					&& cita.getFechaCita().equals(fecha);

			if (mismoDoctorFecha) {
				citasDoctor.add(cita);
			}
		}

		return citasDoctor;
	}

	public ArrayList<Cita> listarCitasPorPaciente(String cedulaPaciente) {
		ArrayList<Cita> citasPaciente = new ArrayList<>();

		for (Cita cita : citas) {
			if (cita.getPaciente().getCedula().equals(cedulaPaciente)) {
				citasPaciente.add(cita);
			}
		}

		return citasPaciente;
	}

	public boolean cancelarCita(String codigoCita) {
		Cita cita = buscarCita(codigoCita);

		if (cita != null) {
			cita.cambiarEstado("Cancelada");
			return true;
		}

		return false;
	}

	public boolean completarCita(String codigoCita) {
		Cita cita = buscarCita(codigoCita);

		if (cita != null) {
			cita.cambiarEstado("Completada");
			return true;
		}

		return false;
	}

	public ArrayList<Cita> listarCitasPendientes() {
		ArrayList<Cita> citasPendientes = new ArrayList<>();

		for (Cita cita : citas) {
			if (cita.getEstadoCita().equals("Pendiente")) {
				citasPendientes.add(cita);
			}
		}

		return citasPendientes;
	}

	public ArrayList<Cita> listarCitasCompletadas() {
		ArrayList<Cita> citasCompletadas = new ArrayList<>();

		for (Cita cita : citas) {
			if (cita.getEstadoCita().equals("Completada")) {
				citasCompletadas.add(cita);
			}
		}

		return citasCompletadas;
	}

	public ArrayList<Cita> listarCitasPorFecha(LocalDate fecha) {
		ArrayList<Cita> citasPorFecha = new ArrayList<>();

		for (Cita cita : citas) {
			if (cita.getFechaCita().equals(fecha)) {
				citasPorFecha.add(cita);
			}
		}

		return citasPorFecha;
	}

	// consultas

	public Consulta registrarConsulta(String codigoCita, String sintomas, String diagnostico, String codigoTratamiento,
			String notas) {

		Cita cita = buscarCita(codigoCita);
		if (cita == null) {
			return null;
		}

		Tratamiento tratamiento = buscarTratamientoPorCodigo(codigoTratamiento);
		if (tratamiento == null) {
			return null;
		}

		String codigoConsulta = generarCodigoConsulta();
		Consulta nuevaConsulta = new Consulta(codigoConsulta, cita.getPaciente(), cita.getDoctor(), cita,
				LocalDate.now(), sintomas, diagnostico, tratamiento, notas);

		// Verificar si el diagnóstico es enfermedad vigilada
		if (esEnfermedadVigilada(diagnostico)) {
			nuevaConsulta.marcarComoVigilancia();
		}

		// Agregar la consulta a la historia clínica del paciente
		cita.getPaciente().getHistoriaClinica().agregarConsulta(nuevaConsulta);

		// Marcar la cita como completada
		cita.cambiarEstado("Completada");

		return nuevaConsulta;
	}

	public Consulta buscarConsulta(String codigoConsulta) {

		for (Paciente paciente : pacientes) {

			ArrayList<Consulta> consultas = paciente.getHistoriaClinica().getConsultas();

			for (Consulta consulta : consultas) {
				if (consulta.getCodigoConsulta().equals(codigoConsulta)) {
					return consulta;
				}
			}
		}

		return null;
	}

	public boolean marcarConsultaParaResumen(String codigoConsulta) {
		Consulta consulta = buscarConsulta(codigoConsulta);

		if (consulta != null) {
			consulta.marcarParaResumen();
			return true;
		}

		return false;
	}

	public boolean desmarcarConsultaDeResumen(String codigoConsulta) {
		Consulta consulta = buscarConsulta(codigoConsulta);

		if (consulta != null) {
			consulta.desmarcarParaResumen();
			return true;
		}

		return false;
	}

	public ArrayList<Consulta> listarConsultasPorPaciente(String cedulaPaciente) {
		Paciente paciente = buscarPacientePorCedula(cedulaPaciente);

		if (paciente != null) {
			return paciente.getHistoriaClinica().getConsultas();
		}

		return new ArrayList<>();
	}

	public ArrayList<Consulta> listarConsultasPorDoctor(String cedulaDoctor) {
		ArrayList<Consulta> consultasDoctor = new ArrayList<>();

		for (Paciente paciente : pacientes) {
			ArrayList<Consulta> consultas = paciente.getHistoriaClinica().getConsultas();

			for (Consulta consulta : consultas) {
				if (consulta.getDoctor().getCedula().equals(cedulaDoctor)) {
					consultasDoctor.add(consulta);
				}
			}
		}

		return consultasDoctor;
	}

	// Historia clinica

	public HistoriaClinica obtenerHistoriaClinica(String cedulaPaciente) {
		Paciente paciente = buscarPacientePorCedula(cedulaPaciente);

		if (paciente != null) {
			return paciente.getHistoriaClinica();
		}

		return null;
	}

	public String generarResumenAutomatico(String cedulaPaciente) {
		Paciente paciente = buscarPacientePorCedula(cedulaPaciente);

		if (paciente != null) {
			return paciente.getHistoriaClinica().generarResumen();
		}

		return "Paciente no encontrado";
	}

	public ArrayList<Consulta> obtenerConsultasParaResumen(String cedulaPaciente) {
		Paciente paciente = buscarPacientePorCedula(cedulaPaciente);

		if (paciente != null) {
			return paciente.getHistoriaClinica().obtenerConsultasParaResumen();
		}

		return new ArrayList<>();
	}

	// vacunacion

	public boolean registrarVacunaPaciente(String cedulaPaciente, String codigoVacuna, LocalDate fecha, String lote,
			String aplicadaPor, int numeroDosis) {

		Paciente paciente = buscarPacientePorCedula(cedulaPaciente);
		if (paciente == null) {
			return false;
		}

		Vacuna vacuna = buscarVacunaPorCodigo(codigoVacuna);
		if (vacuna == null) {
			return false;
		}

		// Registrar la vacuna al paciente
		paciente.registrarVacuna(vacuna, fecha, lote, aplicadaPor, numeroDosis);
		return true;
	}

	public boolean verificarVacunaAplicada(String cedulaPaciente, String codigoVacuna) {

		Paciente paciente = buscarPacientePorCedula(cedulaPaciente);
		if (paciente == null) {
			return false;
		}

		Vacuna vacuna = buscarVacunaPorCodigo(codigoVacuna);
		if (vacuna == null) {
			return false;
		}

		return paciente.tieneVacuna(vacuna);
	}

	public Vacuna buscarVacunaPorNombre(String nombre) {
		for (Vacuna vacuna : vacunas) {
			if (vacuna.getNombre().equalsIgnoreCase(nombre)) {
				return vacuna;
			}
		}
		return null;
	}

	public ArrayList<Vacuna> obtenerVacunasFaltantes(String cedulaPaciente) {

		Paciente paciente = buscarPacientePorCedula(cedulaPaciente);
		if (paciente == null) {
			return new ArrayList<>();
		}

		ArrayList<Vacuna> vacunasActivas = listarVacunasActivas();

		return paciente.obtenerVacunasFaltantes(vacunasActivas);
	}

	public ArrayList<RegistroVacuna> obtenerHistorialVacunacion(String cedulaPaciente) {

		Paciente paciente = buscarPacientePorCedula(cedulaPaciente);

		if (paciente != null) {
			return paciente.getRegistrosVacunas();
		}

		return new ArrayList<>();
	}

	public boolean isPrimerIngresp() {
		return primerIngresp;
	}

	public void setPrimerIngresp(boolean primerIngresp) {
		this.primerIngresp = primerIngresp;
	}

	// Alergias

	public ArrayList<Alergia> getAlergias() {
		return alergias;
	}

	public void setAlergias(ArrayList<Alergia> alergias) {
		this.alergias = alergias;
	}

	public void registrarAlergias(Alergia alergia) {
		alergias.add(alergia);
	}

	public boolean validarExistenciaAlergia(String alergiarev) {
		for (Alergia revisando : alergias) {
			if (revisando.getNombre().equalsIgnoreCase(alergiarev)) {
				return false;
			}
		}
		return true;
	}

}