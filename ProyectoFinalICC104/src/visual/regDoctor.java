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
import logico.Doctor;
import javax.swing.ButtonGroup;

public class regDoctor extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JFormattedTextField txtCedula;
	private JFormattedTextField txtTelefono;
	private JTextArea txtDireccion;
	private JTextField txtNumeroLicencia;
	private JTextField txtHorarioInicio;
	private JTextField txtHorarioFin;
	private JRadioButton rdbtnHombre;
	private JRadioButton rdbtnMujer;
	private JComboBox<String> cbxEspecialidad;
	private JSpinner spnCitasPorDia;
	private JSpinner spnFechaNacimiento;
	private JCheckBox chckbxActivo;

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

		// ========== FILA 6: HORARIOS ==========
		JLabel lblHorarioInicio = new JLabel("Horario Inicio:");
		lblHorarioInicio.setBounds(15, 375, 120, 20);
		contentPanel.add(lblHorarioInicio);

		txtHorarioInicio = new JTextField();
		txtHorarioInicio.setText("08:00");
		txtHorarioInicio.setBounds(15, 398, 100, 26);
		contentPanel.add(txtHorarioInicio);
		txtHorarioInicio.setColumns(10);

		JLabel lblFormatoInicio = new JLabel("(HH:MM)");
		lblFormatoInicio.setBounds(125, 401, 60, 20);
		contentPanel.add(lblFormatoInicio);

		JLabel lblHorarioFin = new JLabel("Horario Fin:");
		lblHorarioFin.setBounds(200, 375, 100, 20);
		contentPanel.add(lblHorarioFin);

		txtHorarioFin = new JTextField();
		txtHorarioFin.setText("17:00");
		txtHorarioFin.setBounds(200, 398, 100, 26);
		contentPanel.add(txtHorarioFin);
		txtHorarioFin.setColumns(10);

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

	// ========== MÉTODO PARA REGISTRAR DOCTOR ==========
	private void registrarDoctor() {
		char sexo;

		// Validar campos vacíos
		if (txtApellido.getText().trim().isEmpty()
				|| txtCedula.getText().trim().replace("_", "").replace("-", "").isEmpty()
				|| txtDireccion.getText().trim().isEmpty() || txtNombre.getText().trim().isEmpty()
				|| txtTelefono.getText().trim().replace("_", "").replace("(", "").replace(")", "").replace("-", "")
						.replace(" ", "").isEmpty()
				|| txtNumeroLicencia.getText().trim().isEmpty() || txtHorarioInicio.getText().trim().isEmpty()
				|| txtHorarioFin.getText().trim().isEmpty() || cbxEspecialidad.getSelectedIndex() == 0) {

			JOptionPane.showMessageDialog(this, "Complete todos los campos correctamente", "Campos incompletos",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		// Validar formato de cédula (debe tener 11 dígitos)
		String cedulaLimpia = txtCedula.getText().replaceAll("[^0-9]", "");
		if (cedulaLimpia.length() != 11) {
			JOptionPane.showMessageDialog(this, "La cédula debe tener 11 dígitos", "Cédula inválida",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Validar formato de teléfono (debe tener 10 dígitos)
		String telefonoLimpio = txtTelefono.getText().replaceAll("[^0-9]", "");
		if (telefonoLimpio.length() != 10) {
			JOptionPane.showMessageDialog(this, "El teléfono debe tener 10 dígitos", "Teléfono inválido",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Determinar sexo
		if (rdbtnHombre.isSelected()) {
			sexo = 'M';
		} else {
			sexo = 'F';
		}

		try {
			// Convertir fecha de nacimiento
			Date fechaNacDate = (Date) spnFechaNacimiento.getValue();
			LocalDate fechaNac = fechaNacDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			// Validar que no sea fecha futura
			if (fechaNac.isAfter(LocalDate.now())) {
				JOptionPane.showMessageDialog(this, "La fecha de nacimiento no puede ser futura", "Fecha inválida",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Obtener especialidad
			String especialidad = cbxEspecialidad.getSelectedItem().toString();

			// Obtener citas por día
			int citasPorDia = (Integer) spnCitasPorDia.getValue();

			// Convertir horarios
			LocalTime horarioInicio = LocalTime.parse(txtHorarioInicio.getText().trim());
			LocalTime horarioFin = LocalTime.parse(txtHorarioFin.getText().trim());

			// Validar que horario fin sea después de horario inicio
			if (!horarioFin.isAfter(horarioInicio)) {
				JOptionPane.showMessageDialog(this, "El horario de fin debe ser posterior al horario de inicio",
						"Horario inválido", JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Obtener estado activo
			boolean activo = chckbxActivo.isSelected();

			// Crear el doctor
			Doctor nuevoDoctor = new Doctor(cedulaLimpia, txtNombre.getText().trim(), txtApellido.getText().trim(),
					telefonoLimpio, txtDireccion.getText().trim(), fechaNac, sexo, txtCodigo.getText(), especialidad,
					txtNumeroLicencia.getText().trim().toUpperCase(), citasPorDia, horarioInicio, horarioFin, activo);

			// Registrar en la clínica
			if (Clinica.getInstance().registrarDoctor(nuevoDoctor)) {
				JOptionPane.showMessageDialog(this,
						"Doctor registrado exitosamente\n" + "Código: " + txtCodigo.getText() + "\n" + "Nombre: "
								+ nuevoDoctor.getNombre() + " " + nuevoDoctor.getApellido(),
						"Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);

				// Limpiar campos
				limpiarCampos();
			} else {
				JOptionPane.showMessageDialog(this, "Ya existe un doctor registrado con esa cédula", "Doctor duplicado",
						JOptionPane.ERROR_MESSAGE);
			}

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error al registrar doctor:\n" + ex.getMessage()
					+ "\n\nVerifique el formato de los horarios (HH:MM)", "Error", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
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
		txtHorarioInicio.setText("08:00");
		txtHorarioFin.setText("17:00");
		txtCodigo.setText("DOC-" + Clinica.getInstance().contadorDoctores);
		rdbtnHombre.setSelected(true);
		chckbxActivo.setSelected(true);
		cbxEspecialidad.setSelectedIndex(0);
		spnCitasPorDia.setValue(5);
		spnFechaNacimiento.setValue(new Date());
	}
}