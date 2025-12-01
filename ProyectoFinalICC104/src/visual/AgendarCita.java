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
	private JButton btnBuscar;
	private Paciente pacienteSeleccionado = null;
	private ArrayList<Doctor> doctoresActivos = new ArrayList<>();

	public AgendarCita() {
		setTitle("Agendar Cita");
		setBounds(100, 100, 600, 600);
		setLocationRelativeTo(null);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		// ========== PANEL PACIENTE ==========
		JPanel panelPaciente = new JPanel();
		panelPaciente.setBorder(
				new TitledBorder(null, "Datos del Paciente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelPaciente.setBounds(10, 11, 564, 150);
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
		txtCedula.setBounds(100, 25, 150, 25);
		panelPaciente.add(txtCedula);

		// Enter para buscar automáticamente
		txtCedula.addActionListener(e -> buscarPaciente());

		btnBuscar = new JButton("Buscar Paciente");
		btnBuscar.addActionListener(e -> buscarPaciente());
		btnBuscar.setBounds(260, 24, 150, 27);
		panelPaciente.add(btnBuscar);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(10, 65, 80, 20);
		panelPaciente.add(lblNombre);

		lbltelefono = new JLabel("Teléfono:");
		lbltelefono.setBounds(10, 105, 80, 20);
		panelPaciente.add(lbltelefono);

		txtTelefonio = new JTextField();
		try {
			MaskFormatter telefonoMask = new MaskFormatter("(###) ###-####");
			telefonoMask.setPlaceholderCharacter('_');
			txtTelefonio = new JFormattedTextField(telefonoMask);
		} catch (Exception e) {
			txtTelefonio = new JFormattedTextField();
		}
		txtTelefonio.setBounds(100, 105, 150, 25);
		panelPaciente.add(txtTelefonio);
		txtTelefonio.setColumns(10);
		txtTelefonio.setText("");
		txtTelefonio.setEnabled(false);

		txtPaciente = new JTextField();
		txtPaciente.setEnabled(false);
		txtPaciente.setBounds(100, 65, 250, 25);
		panelPaciente.add(txtPaciente);
		txtPaciente.setColumns(10);
		txtPaciente.setText("");

		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(360, 65, 80, 20);
		panelPaciente.add(lblApellido);

		txtApellido = new JTextField();
		txtApellido.setEnabled(false);
		txtApellido.setBounds(430, 65, 124, 25);
		panelPaciente.add(txtApellido);
		txtApellido.setColumns(10);

		// Mensaje de estado
		JLabel lblEstadoPaciente = new JLabel("");
		lblEstadoPaciente.setBounds(260, 105, 294, 20);
		lblEstadoPaciente.setForeground(Color.BLUE);
		lblEstadoPaciente.setFont(lblEstadoPaciente.getFont().deriveFont(Font.ITALIC));
		panelPaciente.add(lblEstadoPaciente);

		// ========== PANEL CITA ==========
		JPanel panelCita = new JPanel();
		panelCita.setBorder(
				new TitledBorder(null, "Datos de la Cita", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelCita.setBounds(10, 172, 564, 160);
		contentPanel.add(panelCita);
		panelCita.setLayout(null);

		JLabel lblDoctor = new JLabel("Doctor:");
		lblDoctor.setBounds(10, 25, 80, 20);
		panelCita.add(lblDoctor);

		cbxDoctor = new JComboBox<String>();
		cbxDoctor.addActionListener(e -> actualizarHorariosDisponibles());
		cbxDoctor.setBounds(100, 25, 454, 25);
		panelCita.add(cbxDoctor);

		JLabel lblFecha = new JLabel("Fecha:");
		lblFecha.setBounds(10, 56, 80, 20);
		panelCita.add(lblFecha);

		spnFecha = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spnFecha, "dd/MM/yyyy");
		spnFecha.setEditor(dateEditor);
		spnFecha.setValue(new Date());
		spnFecha.addChangeListener(e -> actualizarHorariosDisponibles());
		spnFecha.setBounds(100, 56, 150, 25);
		panelCita.add(spnFecha);

		JLabel lblHora = new JLabel("Hora:");
		lblHora.setBounds(10, 87, 80, 20);
		panelCita.add(lblHora);

		cbxHora = new JComboBox<String>();
		cbxHora.setBounds(100, 87, 150, 25);
		panelCita.add(cbxHora);

		lblCitasDisponibles = new JLabel("");
		lblCitasDisponibles.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCitasDisponibles.setBounds(10, 118, 544, 20);
		panelCita.add(lblCitasDisponibles);

		// ========== PANEL MOTIVO ==========
		JPanel panelMotivo = new JPanel();
		panelMotivo.setBorder(
				new TitledBorder(null, "Motivo de la Cita", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelMotivo.setBounds(10, 343, 564, 120);
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
		lblMensajeDisponibilidad.setBounds(10, 474, 564, 40);
		contentPanel.add(lblMensajeDisponibilidad);

		// ========== BOTONES ==========
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnAgendar = new JButton("Agendar Cita");
		btnAgendar.addActionListener(e -> agendarCita());
		buttonPane.add(btnAgendar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(e -> dispose());
		buttonPane.add(btnCancelar);

		cargarDoctores();
		limpiarCamposPaciente();
	}

	private void cargarDoctores() {
		cbxDoctor.removeAllItems();
		doctoresActivos = Clinica.getInstance().listarDoctoresActivos();

		if (doctoresActivos.isEmpty()) {
			cbxDoctor.addItem("No hay doctores disponibles");
			cbxDoctor.setEnabled(false);
		} else {
			for (Doctor doctor : doctoresActivos) {
				String info = doctor.getNombre() + " " + doctor.getApellido() + " - " + doctor.getEspecialidad();
				cbxDoctor.addItem(info);
			}
			cbxDoctor.setEnabled(true);
		}
	}

	private void buscarPaciente() {
		String cedula = txtCedula.getText().trim();
		String cedulaLimpia = cedula.replaceAll("[^0-9]", "");

		if (cedulaLimpia.length() != 11) {
			JOptionPane.showMessageDialog(this, "Ingrese una cédula válida de 11 dígitos", "Advertencia",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		// Primero verificar si la cédula pertenece a un doctor
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

		// Buscar paciente
		pacienteSeleccionado = Clinica.getInstance().buscarPacientePorCedula(cedulaLimpia);

		if (pacienteSeleccionado != null) {
			// PACIENTE EXISTENTE - Cargar datos y NO permitir edición
			cargarDatosPacienteExistente();
		} else {
			// NUEVO PACIENTE (interesado) - Habilitar campos para llenar
			prepararParaNuevoPaciente();
		}
	}

	private void cargarDatosPacienteExistente() {
		// Cargar datos del paciente existente
		txtPaciente.setText(pacienteSeleccionado.getNombre());
		txtApellido.setText(pacienteSeleccionado.getApellido());
		txtTelefonio.setText(pacienteSeleccionado.getTelefono());

		// DESHABILITAR campos - NO se pueden modificar
		txtPaciente.setEnabled(false);
		txtApellido.setEnabled(false);
		txtTelefonio.setEnabled(false);

		// Cambiar color de fondo para indicar que no es editable
		txtPaciente.setBackground(new Color(240, 240, 240));
		txtApellido.setBackground(new Color(240, 240, 240));
		txtTelefonio.setBackground(new Color(240, 240, 240));

		// Actualizar mensaje
		actualizarMensajeEstado(" Paciente registrado encontrado. Los datos están bloqueados para edición.");
	}

	private void prepararParaNuevoPaciente() {
		// Limpiar campos
		txtPaciente.setText("");
		txtApellido.setText("");
		txtTelefonio.setText("");

		// HABILITAR campos - Se pueden llenar
		txtPaciente.setEnabled(true);
		txtApellido.setEnabled(true);
		txtTelefonio.setEnabled(true);

		// Restaurar color de fondo
		txtPaciente.setBackground(Color.WHITE);
		txtApellido.setBackground(Color.WHITE);
		txtTelefonio.setBackground(Color.WHITE);

		// Poner foco en el primer campo
		txtPaciente.requestFocus();

		// Actualizar mensaje
		actualizarMensajeEstado(" Paciente no encontrado. Complete los datos para crear un nuevo interesado.");
	}

	private void actualizarMensajeEstado(String mensaje) {
		// Buscar el label de estado en el panel
		for (int i = 0; i < contentPanel.getComponentCount(); i++) {
			if (contentPanel.getComponent(i) instanceof JPanel) {
				JPanel panel = (JPanel) contentPanel.getComponent(i);
				if (panel.getBorder() instanceof TitledBorder) {
					TitledBorder border = (TitledBorder) panel.getBorder();
					if (border.getTitle().equals("Datos del Paciente")) {
						// Buscar el label de estado dentro de este panel
						for (int j = 0; j < panel.getComponentCount(); j++) {
							if (panel.getComponent(j) instanceof JLabel) {
								JLabel label = (JLabel) panel.getComponent(j);
								if (label.getText().isEmpty() || label.getForeground().equals(Color.BLUE)) {
									label.setText(mensaje);
									break;
								}
							}
						}
						break;
					}
				}
			}
		}
	}

	private void limpiarCamposPaciente() {
		txtCedula.setText("");
		txtPaciente.setText("");
		txtApellido.setText("");
		txtTelefonio.setText("");

		// Deshabilitar campos inicialmente
		txtPaciente.setEnabled(false);
		txtApellido.setEnabled(false);
		txtTelefonio.setEnabled(false);

		// Restaurar colores
		txtPaciente.setBackground(Color.WHITE);
		txtApellido.setBackground(Color.WHITE);
		txtTelefonio.setBackground(Color.WHITE);

		pacienteSeleccionado = null;

		// Actualizar mensaje
		actualizarMensajeEstado("Ingrese una cédula y presione Buscar");
	}

	private void actualizarHorariosDisponibles() {
		cbxHora.removeAllItems();
		lblCitasDisponibles.setText("");
		lblMensajeDisponibilidad.setText("");

		if (cbxDoctor.getSelectedIndex() == -1 || cbxDoctor.getSelectedIndex() >= doctoresActivos.size()) {
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
		// Validar que se haya buscado un paciente primero
		String cedulaLimpia = txtCedula.getText().replaceAll("[^0-9]", "");
		if (cedulaLimpia.length() != 11) {
			JOptionPane.showMessageDialog(this, "Debe ingresar una cédula válida de 11 dígitos", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Validar datos del paciente (dependiendo de si es existente o nuevo)
		if (pacienteSeleccionado == null) {
			// Validar campos para nuevo paciente
			if (txtPaciente.getText().trim().isEmpty() || txtApellido.getText().trim().isEmpty()
					|| txtTelefonio.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Debe completar todos los datos del paciente", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Validar teléfono para nuevo paciente
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

			// Validar nombre y apellido (sin números)
			if (!txtPaciente.getText().trim().matches("[a-záéíóúñüÁÉÍÓÚÑÜ ]+")
					|| !txtApellido.getText().trim().matches("[a-záéíóúñüÁÉÍÓÚÑÜ ]+")) {
				JOptionPane.showMessageDialog(this, "Nombre y apellido solo pueden contener letras y espacios", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

		// Validar doctor
		if (cbxDoctor.getSelectedIndex() == -1 || doctoresActivos.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Debe seleccionar un doctor", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Validar hora
		if (cbxHora.getSelectedIndex() == -1 || cbxHora.getItemCount() == 0) {
			JOptionPane.showMessageDialog(this, "Debe seleccionar una hora disponible", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Validar motivo
		String motivo = txtMotivo.getText().trim();
		if (motivo.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Debe ingresar el motivo de la cita", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			// Obtener datos
			Doctor doctor = doctoresActivos.get(cbxDoctor.getSelectedIndex());
			Date fechaSeleccionada = (Date) spnFecha.getValue();
			LocalDate fecha = fechaSeleccionada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			String horaTexto = (String) cbxHora.getSelectedItem();
			LocalTime hora = LocalTime.parse(horaTexto);
			Cita nuevaCita = null;

			if (pacienteSeleccionado != null) {
				// PACIENTE EXISTENTE - Agendar directamente
				nuevaCita = Clinica.getInstance().agendarCita(pacienteSeleccionado, doctor.getCedula(), fecha, hora,
						motivo);
			} else {
				// NUEVO PACIENTE (interesado) - Crear primero como interesado
				String telefonoLimpio = txtTelefonio.getText().replaceAll("[^0-9]", "");
				Paciente nuevoInteresado = new Paciente(cedulaLimpia, txtPaciente.getText().trim(),
						txtApellido.getText().trim(), telefonoLimpio, "XX");

				// Registrar como interesado
				Clinica.getInstance().registarInteresados(nuevoInteresado);

				// Agendar cita con el interesado
				nuevaCita = Clinica.getInstance().agendarCita(nuevoInteresado, doctor.getCedula(), fecha, hora, motivo);
			}

			if (nuevaCita != null) {
				String tipoPaciente = (pacienteSeleccionado != null) ? "paciente registrado"
						: "interesado (será registrado en consulta)";
				JOptionPane.showMessageDialog(this,
						"CITA AGENDADA EXITOSAMENTE\n\n" + "Código: " + nuevaCita.getCodigoCita() + "\n" + "Fecha: "
								+ nuevaCita.getFechaCita() + "\n" + "Hora: " + nuevaCita.getHoraCita() + "\n"
								+ "Doctor: " + doctor.getNombre() + " " + doctor.getApellido() + "\n" + "Paciente: "
								+ txtPaciente.getText() + " " + txtApellido.getText() + "\n" + "Tipo: " + tipoPaciente,
						"Éxito", JOptionPane.INFORMATION_MESSAGE);
				dispose();
			} else {
				JOptionPane.showMessageDialog(this, "No se pudo agendar la cita. Verifique la disponibilidad.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error al agendar la cita:\n" + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}
}