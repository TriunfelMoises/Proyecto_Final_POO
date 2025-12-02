package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import java.util.Date;
import java.util.Calendar;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.MaskFormatter;
import logico.Clinica;
import logico.Control;
import logico.Doctor;
import logico.User;

import javax.swing.ButtonGroup;
import javax.swing.SpinnerListModel;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class regDoctor extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JFormattedTextField txtCedula;
	private JFormattedTextField txtTelefono;
	private JTextArea txtDireccion;
	private JTextField txtNumeroLicencia;
	private JSpinner spnHorarioInicio;
	private JSpinner spnHorarioFin;
	private JRadioButton rdbtnHombre;
	private JRadioButton rdbtnMujer;
	private JComboBox<String> cbxEspecialidad;
	private JSpinner spnCitasPorDia;
	private JSpinner spnFechaNacimiento;
	private JCheckBox chckbxActivo;
	private JTextField txtUsuario;
	private JTextField txtContrasena;

	public static void main(String[] args) {
		try {
			regDoctor dialog = new regDoctor();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public regDoctor() {
		setTitle("Registro de Doctores");
		setModal(true);
		setBounds(100, 100, 680, 650);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		// ========== CÓDIGO (esquina superior derecha) ==========
		JLabel lblCodigo = new JLabel("Código:");
		lblCodigo.setBounds(540, 15, 60, 20);
		contentPanel.add(lblCodigo);

		txtCodigo = new JTextField();
		txtCodigo.setEditable(false);
		txtCodigo.setBounds(540, 38, 100, 26);
		contentPanel.add(txtCodigo);
		txtCodigo.setColumns(10);
		txtCodigo.setText("DOC-" + Clinica.getInstance().contadorDoctores);

		// ========== FILA 1: NOMBRE Y APELLIDO ==========
		JLabel lblNombre = new JLabel("Nombre(s):");
		lblNombre.setBounds(15, 15, 100, 20);
		contentPanel.add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(15, 38, 250, 26);
		contentPanel.add(txtNombre);
		txtNombre.setColumns(10);

		JLabel lblApellido = new JLabel("Apellido(s):");
		lblApellido.setBounds(280, 15, 100, 20);
		contentPanel.add(lblApellido);

		txtApellido = new JTextField();
		txtApellido.setBounds(280, 38, 245, 26);
		contentPanel.add(txtApellido);
		txtApellido.setColumns(10);

		// ========== FILA 2: CÉDULA Y TELÉFONO CON MÁSCARAS ==========
		JLabel lblCedula = new JLabel("Cédula:");
		lblCedula.setBounds(15, 80, 100, 20);
		contentPanel.add(lblCedula);

		try {
			MaskFormatter cedulaMask = new MaskFormatter("###-#######-#");
			cedulaMask.setPlaceholderCharacter('_');
			txtCedula = new JFormattedTextField(cedulaMask);
		} catch (Exception e) {
			txtCedula = new JFormattedTextField();
		}
		txtCedula.setBounds(15, 103, 180, 26);
		contentPanel.add(txtCedula);

		JLabel lblTelefono = new JLabel("Teléfono:");
		lblTelefono.setBounds(210, 80, 100, 20);
		contentPanel.add(lblTelefono);

		try {
			MaskFormatter telefonoMask = new MaskFormatter("(###) ###-####");
			telefonoMask.setPlaceholderCharacter('_');
			txtTelefono = new JFormattedTextField(telefonoMask);
		} catch (Exception e) {
			txtTelefono = new JFormattedTextField();
		}
		txtTelefono.setBounds(210, 103, 180, 26);
		contentPanel.add(txtTelefono);

		// ========== FILA 3: SEXO Y FECHA DE NACIMIENTO ==========
		JLabel lblSexo = new JLabel("Sexo:");
		lblSexo.setBounds(405, 80, 50, 20);
		contentPanel.add(lblSexo);

		rdbtnHombre = new JRadioButton("M");
		rdbtnHombre.setSelected(true);
		rdbtnHombre.setBounds(405, 100, 60, 29);
		contentPanel.add(rdbtnHombre);

		rdbtnMujer = new JRadioButton("F");
		rdbtnMujer.setBounds(470, 100, 55, 29);
		contentPanel.add(rdbtnMujer);

		// ButtonGroup para que solo se seleccione uno
		ButtonGroup grupoSexo = new ButtonGroup();
		grupoSexo.add(rdbtnHombre);
		grupoSexo.add(rdbtnMujer);

		JLabel lblFechaNacimiento = new JLabel("Fecha de nacimiento:");
		lblFechaNacimiento.setBounds(15, 145, 150, 20);
		contentPanel.add(lblFechaNacimiento);

		spnFechaNacimiento = new JSpinner();
		spnFechaNacimiento.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_YEAR));
		JSpinner.DateEditor editor = new JSpinner.DateEditor(spnFechaNacimiento, "dd/MM/yyyy");
		spnFechaNacimiento.setEditor(editor);
		spnFechaNacimiento.setBounds(15, 168, 150, 26);
		contentPanel.add(spnFechaNacimiento);

		// ========== DIRECCIÓN (TEXTAREA) ==========
		JLabel lblDireccion = new JLabel("Dirección:");
		lblDireccion.setBounds(180, 145, 100, 20);
		contentPanel.add(lblDireccion);

		txtDireccion = new JTextArea();
		txtDireccion.setLineWrap(true);
		txtDireccion.setWrapStyleWord(true);
		JScrollPane scrollDireccion = new JScrollPane(txtDireccion);
		scrollDireccion.setBounds(180, 168, 460, 60);
		contentPanel.add(scrollDireccion);

		// ========== FILA 4: ESPECIALIDAD Y CITAS POR DÍA ==========
		JLabel lblEspecialidad = new JLabel("Especialidad:");
		lblEspecialidad.setBounds(15, 245, 100, 20);
		contentPanel.add(lblEspecialidad);

		cbxEspecialidad = new JComboBox<String>();
		cbxEspecialidad.setModel(new DefaultComboBoxModel<>(new String[] { "<Seleccione>", "Cardiología", "Pediatría",
				"Dermatología", "Neurología", "Ginecología", "Medicina General", "Traumatología", "Oftalmología",
				"Otorrinolaringología", "Psiquiatría", "Urología", "Endocrinología" }));
		cbxEspecialidad.setBounds(15, 268, 280, 26);
		contentPanel.add(cbxEspecialidad);

		JLabel lblCitasPorDia = new JLabel("Citas por día:");
		lblCitasPorDia.setBounds(310, 245, 100, 20);
		contentPanel.add(lblCitasPorDia);

		spnCitasPorDia = new JSpinner();
		spnCitasPorDia.setModel(new SpinnerNumberModel(5, 1, 20, 1));
		spnCitasPorDia.setBounds(310, 268, 80, 26);
		contentPanel.add(spnCitasPorDia);

		JLabel lblInfoCitas = new JLabel("(Máximo 20)");
		lblInfoCitas.setBounds(400, 245, 100, 20);
		contentPanel.add(lblInfoCitas);

		// ========== FILA 5: NÚMERO DE LICENCIA ==========
		JLabel lblNumeroLicencia = new JLabel("Número de Licencia Médica:");
		lblNumeroLicencia.setBounds(15, 310, 200, 20);
		contentPanel.add(lblNumeroLicencia);

		try {
			// Ejemplo de máscara básica con 4 letras + guion + 5 dígitos
			MaskFormatter licenciaMask = new MaskFormatter("UUUU-#####");
			licenciaMask.setPlaceholderCharacter('_');
			txtNumeroLicencia = new JFormattedTextField(licenciaMask);
		} catch (Exception e) {
			txtNumeroLicencia = new JFormattedTextField();
		}

		txtNumeroLicencia.setBounds(15, 333, 250, 26);
		contentPanel.add(txtNumeroLicencia);
		txtNumeroLicencia.setColumns(10);

		JLabel lblInfoLicencia = new JLabel("(Ej: EXQM-12345 o similar)");
		lblInfoLicencia.setBounds(270, 336, 200, 20);
		contentPanel.add(lblInfoLicencia);

		// ========== FILA 6: HORARIOS CON SPINNERS ==========
		JLabel lblHorarioInicio = new JLabel("Horario Inicio:");
		lblHorarioInicio.setBounds(15, 375, 120, 20);
		contentPanel.add(lblHorarioInicio);

		// Crear modelo para horas (de 6:00 AM a 10:00 PM en intervalos de 30 minutos)
		String[] horasDisponibles = generarHorasDisponibles();
		spnHorarioInicio = new JSpinner(new SpinnerListModel(horasDisponibles));
		spnHorarioInicio.setValue("08:00"); // Valor por defecto
		spnHorarioInicio.setBounds(15, 398, 100, 26);
		contentPanel.add(spnHorarioInicio);

		JLabel lblFormatoInicio = new JLabel("(HH:MM)");
		lblFormatoInicio.setBounds(125, 401, 60, 20);
		contentPanel.add(lblFormatoInicio);

		JLabel lblHorarioFin = new JLabel("Horario Fin:");
		lblHorarioFin.setBounds(200, 375, 100, 20);
		contentPanel.add(lblHorarioFin);

		spnHorarioFin = new JSpinner(new SpinnerListModel(horasDisponibles));
		spnHorarioFin.setValue("17:00"); // Valor por defecto
		spnHorarioFin.setBounds(200, 398, 100, 26);
		contentPanel.add(spnHorarioFin);

		JLabel lblFormatoFin = new JLabel("(HH:MM)");
		lblFormatoFin.setBounds(310, 401, 60, 20);
		contentPanel.add(lblFormatoFin);

		// ========== ACTIVO ==========
		chckbxActivo = new JCheckBox("Doctor Activo");
		chckbxActivo.setSelected(true);
		chckbxActivo.setBounds(15, 440, 150, 29);
		contentPanel.add(chckbxActivo);

		// ========== INFORMACIÓN ADICIONAL ==========
		JLabel lblInfo = new JLabel("Nota: Todos los campos son obligatorios");
		lblInfo.setBounds(15, 480, 400, 20);
		contentPanel.add(lblInfo);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.GRAY, 1, true));
		panel.setBounds(385, 386, 255, 153);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Credenciales de acceso:");
		lblNewLabel.setBounds(15, 0, 180, 20);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Usuario:");
		lblNewLabel_1.setBounds(15, 41, 69, 20);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Contrase\u00F1a:");
		lblNewLabel_2.setBounds(15, 78, 85, 20);
		panel.add(lblNewLabel_2);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(106, 38, 139, 26);
		panel.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		txtContrasena = new JTextField();
		txtContrasena.setBounds(106, 75, 139, 26);
		panel.add(txtContrasena);
		txtContrasena.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Nota: El usuario y la contrase\u00F1a ");
		lblNewLabel_3.setBounds(15, 114, 230, 20);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("no podr\u00E1n ser modificados.");
		lblNewLabel_4.setBounds(15, 133, 225, 20);
		panel.add(lblNewLabel_4);

		// ========== PANEL DE BOTONES ==========
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Registrar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						registrarDoctor();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton btnLimpiar = new JButton("Limpiar");
				btnLimpiar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						limpiarCampos();
					}
				});
				buttonPane.add(btnLimpiar);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	/**
	 * Genera un array de horas disponibles en formato HH:MM desde las 6:00 AM hasta
	 * las 10:00 PM en intervalos de 30 minutos
	 */
	private String[] generarHorasDisponibles() {
		java.util.List<String> horas = new java.util.ArrayList<>();

		for (int hora = 6; hora <= 22; hora++) {
			for (int minuto = 0; minuto < 60; minuto += 30) {
				String horaStr = String.format("%02d:%02d", hora, minuto);
				horas.add(horaStr);
			}
		}

		return horas.toArray(new String[0]);
	}

	private boolean validarNombre(String texto, String campo) {
		// Solo letras, espacios y tildes
		if (!texto.matches("[a-záéíóúñüA-ZÁÉÍÓÚÑÜ ]+")) {
			JOptionPane.showMessageDialog(this,
					campo + " solo puede contener letras y espacios.\nNo se permiten números ni caracteres especiales.",
					"Nombre inválido", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	private void registrarDoctor() {
		// ========== 1. LIMPIAR DATOS ==========
		String cedulaLimpia = txtCedula.getText().replaceAll("[^0-9]", "");
		String telefonoLimpio = txtTelefono.getText().replaceAll("[^0-9]", "");
		String licenciaTexto = txtNumeroLicencia.getText().trim().replaceAll("_", "");

		// ========== 2. VALIDAR CAMPOS VACÍOS ==========
		if (txtNombre.getText().trim().isEmpty() || txtApellido.getText().trim().isEmpty()
				|| cedulaLimpia.length() != 11 || telefonoLimpio.length() != 10
				|| txtDireccion.getText().trim().isEmpty() || licenciaTexto.isEmpty() || licenciaTexto.length() < 4
				|| cbxEspecialidad.getSelectedIndex() == 0 || txtUsuario.getText().isEmpty() || txtContrasena.getText().isEmpty()) {

			JOptionPane.showMessageDialog(this,
					"Complete todos los campos correctamente.\n\n" + "Verifique:\n" + "• Cédula: 11 dígitos\n"
							+ "• Teléfono: 10 dígitos\n" + "• Licencia: al menos 4 caracteres",
					"Campos incompletos", JOptionPane.WARNING_MESSAGE);
			return;
		}

		// ========== 2.5. VALIDAR NOMBRE Y APELLIDO ==========
		if (!validarNombre(txtNombre.getText().trim(), "El nombre")) {
			txtNombre.requestFocus();
			return;
		}

		if (!validarNombre(txtApellido.getText().trim(), "El apellido")) {
			txtApellido.requestFocus();
			return;
		}

		// ========== 3. VALIDAR CÉDULA DUPLICADA ==========
		if (Clinica.getInstance().isCedulaRegistrada(cedulaLimpia)) {
			JOptionPane.showMessageDialog(this,
					"Esta cédula ya está registrada en el sistema.\n" + "Cédula: " + txtCedula.getText(),
					"Cédula duplicada", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// ========== 4. VALIDAR TELÉFONO DUPLICADO ==========
		if (Clinica.getInstance().isTelefonoRegistrado(telefonoLimpio)) {
			JOptionPane.showMessageDialog(this,
					"Este teléfono ya está registrado en el sistema.\n" + "Teléfono: " + txtTelefono.getText() + "\n"
							+ "Por favor ingrese un teléfono diferente.",
					"Teléfono duplicado", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// ========== 5. VALIDAR LICENCIA DUPLICADA ==========
		String licenciaCompleta = txtNumeroLicencia.getText().trim().toUpperCase();
		if (Clinica.getInstance().isLicenciaRegistrada(licenciaCompleta)) {
			JOptionPane.showMessageDialog(this,
					"Esta licencia médica ya está registrada.\n" + "Licencia: " + licenciaCompleta,
					"Licencia duplicada", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// ========== 6. DETERMINAR SEXO ==========
		char sexo = rdbtnHombre.isSelected() ? 'M' : 'F';

		try {
			// ========== 7. FECHA DE NACIMIENTO ==========
			Date fechaNacDate = (Date) spnFechaNacimiento.getValue();
			LocalDate fechaNac = fechaNacDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			if (fechaNac.isAfter(LocalDate.now())) {
				JOptionPane.showMessageDialog(this, "La fecha de nacimiento no puede ser futura", "Fecha inválida",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// ========== 8. HORARIOS ==========
			String horarioInicioStr = (String) spnHorarioInicio.getValue();
			String horarioFinStr = (String) spnHorarioFin.getValue();

			LocalTime horarioInicio = LocalTime.parse(horarioInicioStr);
			LocalTime horarioFin = LocalTime.parse(horarioFinStr);

			if (!horarioFin.isAfter(horarioInicio)) {
				JOptionPane.showMessageDialog(this, "El horario de fin debe ser posterior al de inicio",
						"Horario inválido", JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Validar que haya al menos 1 hora de diferencia
			if (horarioInicio.plusHours(1).isAfter(horarioFin)) {
				JOptionPane.showMessageDialog(
						this, "El horario laboral debe ser de al menos 1 hora.\n" + "Horario actual: "
								+ horarioInicioStr + " - " + horarioFinStr,
						"Horario muy corto", JOptionPane.WARNING_MESSAGE);
				return;
			}

			// ========== 9. CREAR DOCTOR ==========
			String especialidad = cbxEspecialidad.getSelectedItem().toString();
			int citasPorDia = (Integer) spnCitasPorDia.getValue();
			boolean activo = chckbxActivo.isSelected();

			Doctor nuevoDoctor = new Doctor(cedulaLimpia, txtNombre.getText().trim(), txtApellido.getText().trim(),
					telefonoLimpio, txtDireccion.getText().trim(), fechaNac, sexo, txtCodigo.getText(), especialidad,
					licenciaCompleta, citasPorDia, horarioInicio, horarioFin, activo, txtUsuario.getText(), txtContrasena.getText());
			User doc = new User("Doctor", txtUsuario.getText(), txtContrasena.getText());

			// ========== 10. REGISTRAR ==========
			if (Clinica.getInstance().registrarDoctor(nuevoDoctor)) {
				Control.getInstance().regUser(doc);
				JOptionPane.showMessageDialog(this,
						"Doctor registrado exitosamente\n\n" + "Código: " + txtCodigo.getText() + "\n" + "Nombre: "
								+ nuevoDoctor.getNombre() + " " + nuevoDoctor.getApellido() + "\n" + "Licencia: "
								+ nuevoDoctor.getNumeroLicencia() + "\n" + "Horario: " + horarioInicioStr + " - "
								+ horarioFinStr,
						"Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
				limpiarCampos();
			} else {
				JOptionPane.showMessageDialog(this,
						"Error: No se pudo registrar el doctor.\n" + "Verifique que no existan duplicados.", "Error",
						JOptionPane.ERROR_MESSAGE);
				Clinica.getInstance().recalcularContadorDoctores();
			}

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error al registrar:\n" + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			Clinica.getInstance().recalcularContadorDoctores();
		}
	}

	// ========== MÉTODO PARA LIMPIAR CAMPOS ==========
	private void limpiarCampos() {
		txtNombre.setText("");
		txtApellido.setText("");
		txtCedula.setText("");
		txtTelefono.setText("");
		txtDireccion.setText("");
		txtNumeroLicencia.setText("");
		spnHorarioInicio.setValue("08:00");
		spnHorarioFin.setValue("17:00");
		txtCodigo.setText("DOC-" + Clinica.getInstance().contadorDoctores);
		rdbtnHombre.setSelected(true);
		chckbxActivo.setSelected(true);
		cbxEspecialidad.setSelectedIndex(0);
		spnCitasPorDia.setValue(5);
		spnFechaNacimiento.setValue(new Date());
		txtUsuario.setText("");
		txtContrasena.setText("");
	}
}