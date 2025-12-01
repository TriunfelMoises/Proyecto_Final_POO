package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;

import logico.Clinica;
import logico.Doctor;
import logico.Paciente;
import logico.Cita;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.awt.Color;
import java.awt.Font;

public class AgendarCita extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtCedula;
	private JComboBox<String> cbxDoctor;
	private JSpinner spnFecha;
	private JComboBox<String> cbxHora;
	private JTextArea txtMotivo;
	private JLabel lblCitasDisponibles;
	private JLabel lblMensajeDisponibilidad;
	private JLabel lbltelefono;
	private JTextField txtTelefonio;
	private JTextField txtPaciente;
	private JTextField txtApellido;
	private Paciente nuevoPax;
	private Paciente pacienteSeleccionado = null;
	private ArrayList<Doctor> doctoresActivos = new ArrayList<>();

	public AgendarCita() {
		setTitle("Agendar Cita");
		setBounds(100, 100, 600, 600);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		// ========== PANEL PACIENTE ==========
		JPanel panelPaciente = new JPanel();
		panelPaciente.setBorder(
				new TitledBorder(null, "Datos del Paciente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelPaciente.setBounds(10, 11, 564, 100);
		contentPanel.add(panelPaciente);
		panelPaciente.setLayout(null);

		JLabel lblCedula = new JLabel("Cédula:");
		lblCedula.setBounds(10, 25, 80, 20);
		panelPaciente.add(lblCedula);

		txtCedula = new JTextField();
		try {
			MaskFormatter cedulaMask = new MaskFormatter("###-#######-#");
			cedulaMask.setPlaceholderCharacter('_');
			txtCedula = new JFormattedTextField(cedulaMask);
		} catch (Exception e) {
			txtCedula = new JFormattedTextField();
		}
		txtCedula.setBounds(100, 25, 150, 20);
		panelPaciente.add(txtCedula);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(e -> buscarPaciente());
		btnBuscar.setBounds(260, 24, 90, 23);
		panelPaciente.add(btnBuscar);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(10, 60, 80, 20);
		panelPaciente.add(lblNombre);

		lbltelefono = new JLabel("Teléfono");
		lbltelefono.setBounds(365, 25, 69, 20);
		panelPaciente.add(lbltelefono);

		txtTelefonio = new JTextField();
		try {
			MaskFormatter telefonoMask = new MaskFormatter("(###) ###-####");
			telefonoMask.setPlaceholderCharacter('_');
			txtTelefonio = new JFormattedTextField(telefonoMask);
		} catch (Exception e) {
			txtTelefonio = new JFormattedTextField();
		}
		txtTelefonio.setBounds(432, 22, 122, 26);
		panelPaciente.add(txtTelefonio);
		txtTelefonio.setColumns(10);
		txtTelefonio.setText("");
		txtTelefonio.setEnabled(false);

		txtPaciente = new JTextField();
		txtPaciente.setEnabled(false);
		txtPaciente.setBounds(100, 57, 163, 26);
		panelPaciente.add(txtPaciente);
		txtPaciente.setColumns(10);
		txtPaciente.setText("");

		JLabel lblNewLabel = new JLabel("Apellido");
		lblNewLabel.setBounds(270, 60, 69, 20);
		panelPaciente.add(lblNewLabel);

		txtApellido = new JTextField();
		txtApellido.setEnabled(false);
		txtApellido.setBounds(337, 57, 217, 26);
		panelPaciente.add(txtApellido);
		txtApellido.setColumns(10);

		// ========== PANEL CITA ==========
		JPanel panelCita = new JPanel();
		panelCita.setBorder(
				new TitledBorder(null, "Datos de la Cita", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelCita.setBounds(10, 122, 564, 160);
		contentPanel.add(panelCita);
		panelCita.setLayout(null);

		JLabel lblDoctor = new JLabel("Doctor:");
		lblDoctor.setBounds(10, 25, 80, 20);
		panelCita.add(lblDoctor);

		cbxDoctor = new JComboBox<String>();
		cbxDoctor.addActionListener(e -> actualizarHorariosDisponibles());
		cbxDoctor.setBounds(100, 25, 454, 20);
		panelCita.add(cbxDoctor);

		JLabel lblFecha = new JLabel("Fecha:");
		lblFecha.setBounds(10, 56, 80, 20);
		panelCita.add(lblFecha);

		spnFecha = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spnFecha, "dd/MM/yyyy");
		spnFecha.setEditor(dateEditor);
		spnFecha.setValue(new Date());
		spnFecha.addChangeListener(e -> actualizarHorariosDisponibles());
		spnFecha.setBounds(100, 56, 150, 20);
		panelCita.add(spnFecha);

		JLabel lblHora = new JLabel("Hora:");
		lblHora.setBounds(10, 87, 80, 20);
		panelCita.add(lblHora);

		cbxHora = new JComboBox<String>();
		cbxHora.setBounds(100, 87, 150, 20);
		panelCita.add(cbxHora);

		lblCitasDisponibles = new JLabel("");
		lblCitasDisponibles.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCitasDisponibles.setBounds(10, 118, 544, 20);
		panelCita.add(lblCitasDisponibles);

		// ========== PANEL MOTIVO ==========
		JPanel panelMotivo = new JPanel();
		panelMotivo.setBorder(
				new TitledBorder(null, "Motivo de la Cita", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelMotivo.setBounds(10, 293, 564, 120);
		contentPanel.add(panelMotivo);
		panelMotivo.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panelMotivo.add(scrollPane, BorderLayout.CENTER);

		txtMotivo = new JTextArea();
		txtMotivo.setLineWrap(true);
		txtMotivo.setWrapStyleWord(true);
		scrollPane.setViewportView(txtMotivo);

		// ========== MENSAJE DISPONIBILIDAD ==========
		lblMensajeDisponibilidad = new JLabel("");
		lblMensajeDisponibilidad.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMensajeDisponibilidad.setBounds(10, 424, 564, 40);
		contentPanel.add(lblMensajeDisponibilidad);

		// ========== BOTONES ==========
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnAgendar = new JButton("Agendar");
		btnAgendar.addActionListener(e -> agendarCita());
		buttonPane.add(btnAgendar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(e -> dispose());
		buttonPane.add(btnCancelar);

		cargarDoctores();
	}

	private void cargarDoctores() {
		cbxDoctor.removeAllItems();
		doctoresActivos = Clinica.getInstance().listarDoctoresActivos();

		for (Doctor doctor : doctoresActivos) {
			String info = doctor.getNombre() + " " + doctor.getApellido() + " - " + doctor.getEspecialidad();
			cbxDoctor.addItem(info);
		}
	}

	private void buscarPaciente() {
		String cedula = txtCedula.getText().trim();

		if (cedula.isEmpty() || cedula.replaceAll("[^0-9]", "").length() != 11) {
			JOptionPane.showMessageDialog(this, "Ingrese una cédula válida de 11 dígitos", "Advertencia",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		// Primero verificar si la cédula pertenece a un doctor
		String cedulaLimpia = cedula.replaceAll("[^0-9]", "");
		Doctor doctorConEstaCedula = Clinica.getInstance().buscarDoctorPorCedula(cedulaLimpia);
		if (doctorConEstaCedula != null) {
			JOptionPane.showMessageDialog(this,
					"Esta cédula pertenece a un doctor del sistema.\n" + "Doctor: " + doctorConEstaCedula.getNombre()
							+ " " + doctorConEstaCedula.getApellido() + "\n"
							+ "No puede ser utilizada para agendar citas como paciente.",
					"Cédula de Doctor", JOptionPane.WARNING_MESSAGE);
			limpiarCamposPaciente();
			return;
		}

		// Buscar paciente normal
		pacienteSeleccionado = Clinica.getInstance().buscarPacientePorCedula(cedula);

		if (pacienteSeleccionado != null) {
			txtPaciente.setText(pacienteSeleccionado.getNombre());
			txtApellido.setText(pacienteSeleccionado.getApellido());
			txtTelefonio.setText(pacienteSeleccionado.getTelefono());
			// Deshabilitar campos ya que es un paciente existente
			txtPaciente.setEnabled(false);
			txtApellido.setEnabled(false);
			txtTelefonio.setEnabled(false);
		} else {
			// Habilitar campos para nuevo paciente
			txtPaciente.setEnabled(true);
			txtApellido.setEnabled(true);
			txtTelefonio.setEnabled(true);
			txtPaciente.setText("");
			txtApellido.setText("");
			txtTelefonio.setText("");
			txtPaciente.requestFocus();
		}
	}

	private void limpiarCamposPaciente() {
		txtCedula.setText("");
		txtPaciente.setText("");
		txtApellido.setText("");
		txtTelefonio.setText("");
		txtPaciente.setEnabled(false);
		txtApellido.setEnabled(false);
		txtTelefonio.setEnabled(false);
		pacienteSeleccionado = null;
	}

	private void actualizarHorariosDisponibles() {
		cbxHora.removeAllItems();
		lblCitasDisponibles.setText("");
		lblMensajeDisponibilidad.setText("");

		if (cbxDoctor.getSelectedIndex() == -1) {
			return;
		}

		Doctor doctor = doctoresActivos.get(cbxDoctor.getSelectedIndex());
		Date fechaSeleccionada = (Date) spnFecha.getValue();
		LocalDate fecha = fechaSeleccionada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		// Obtener horarios disponibles
		ArrayList<LocalTime> horariosDisponibles = Clinica.getInstance().obtenerHorariosDisponibles(doctor.getCedula(),
				fecha);

		// Mostrar información de citas disponibles
		String textoCitas = Clinica.getInstance().obtenerCitasDisponiblesTexto(doctor.getCedula(), fecha);
		lblCitasDisponibles.setText(textoCitas);

		// Verificar si hay horarios disponibles
		if (horariosDisponibles.isEmpty()) {
			lblMensajeDisponibilidad.setText("No hay horarios disponibles para este día");
			lblMensajeDisponibilidad.setForeground(Color.RED);
			return;
		}

		// Cargar horarios en el ComboBox
		for (LocalTime hora : horariosDisponibles) {
			cbxHora.addItem(hora.toString());
		}

		lblMensajeDisponibilidad.setText("Horarios disponibles cargados correctamente");
		lblMensajeDisponibilidad.setForeground(new Color(0, 128, 0));
	}

	private void agendarCita() {
		// Validar paciente
		if (pacienteSeleccionado == null && (txtPaciente.getText().isEmpty() || txtTelefonio.getText().isEmpty()
				|| txtApellido.getText().isEmpty())) {
			JOptionPane.showMessageDialog(this, "Debe completar todos los datos del paciente", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Validar teléfono (solo para nuevos pacientes)
		if (pacienteSeleccionado == null) {
			String telefonoLimpio = txtTelefonio.getText().replaceAll("[^0-9]", "");
			if (telefonoLimpio.length() != 10) {
				JOptionPane.showMessageDialog(this, "El teléfono debe tener 10 dígitos", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Validar que el teléfono no esté registrado
			if (Clinica.getInstance().isTelefonoRegistrado(telefonoLimpio)) {
				JOptionPane.showMessageDialog(this,
						"Este teléfono ya está registrado en el sistema.\n"
								+ "Por favor ingrese un teléfono diferente.",
						"Teléfono Duplicado", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

		// Validar doctor
		if (cbxDoctor.getSelectedIndex() == -1) {
			JOptionPane.showMessageDialog(this, "Debe seleccionar un doctor", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Validar hora
		if (cbxHora.getSelectedIndex() == -1) {
			JOptionPane.showMessageDialog(this, "No hay horarios disponibles", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Validar motivo
		String motivo = txtMotivo.getText().trim();
		if (motivo.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Debe ingresar el motivo de la cita", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Obtener datos
		Doctor doctor = doctoresActivos.get(cbxDoctor.getSelectedIndex());
		Date fechaSeleccionada = (Date) spnFecha.getValue();
		LocalDate fecha = fechaSeleccionada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		String horaTexto = (String) cbxHora.getSelectedItem();
		LocalTime hora = LocalTime.parse(horaTexto);
		Cita nuevaCita;

		if (pacienteSeleccionado == null) {
			// Validación final para asegurar que no sea un doctor
			String cedulaLimpia = txtCedula.getText().replaceAll("[^0-9]", "");
			Doctor doctorVerificacion = Clinica.getInstance().buscarDoctorPorCedula(cedulaLimpia);
			if (doctorVerificacion != null) {
				JOptionPane.showMessageDialog(this,
						"No puede registrar un doctor como paciente.\n" + "La cédula pertenece al doctor: "
								+ doctorVerificacion.getNombre() + " " + doctorVerificacion.getApellido(),
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Crear nuevo paciente como interesado
			nuevoPax = new Paciente(txtCedula.getText().replaceAll("[^0-9]", ""), txtPaciente.getText().trim(),
					txtApellido.getText().trim(), txtTelefonio.getText().replaceAll("[^0-9]", ""), "XX");
			Clinica.getInstance().registarInteresados(nuevoPax);
			nuevaCita = Clinica.getInstance().agendarCita(nuevoPax, doctor.getCedula(), fecha, hora, motivo);
		} else {
			// Usar paciente existente
			nuevaCita = Clinica.getInstance().agendarCita(pacienteSeleccionado, doctor.getCedula(), fecha, hora,
					motivo);
		}

		// Agendar cita
		if (nuevaCita != null) {
			JOptionPane.showMessageDialog(this,
					"Cita agendada exitosamente\n" + "Código: " + nuevaCita.getCodigoCita() + "\n" + "Fecha: "
							+ nuevaCita.getFechaCita() + "\n" + "Hora: " + nuevaCita.getHoraCita(),
					"Éxito", JOptionPane.INFORMATION_MESSAGE);
			dispose();
		} else {
			JOptionPane.showMessageDialog(this, "No se pudo agendar la cita. Verifique la disponibilidad.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}