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

public class modDoctor extends JDialog {

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
	private Doctor doctorAModificar;

	public static void main(String[] args) {
		try {
			// Para pruebas, necesitas pasar un doctor
			modDoctor dialog = new modDoctor(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public modDoctor(Doctor doctor) {
		this.doctorAModificar = doctor;

		if (doctor == null) {
			JOptionPane.showMessageDialog(this, "No se ha seleccionado ningún doctor", "Error",
					JOptionPane.ERROR_MESSAGE);
			dispose();
			return;
		}

		setTitle("Modificar Doctor - " + doctor.getNombre() + " " + doctor.getApellido());
		setModal(true);
		setBounds(100, 100, 680, 650);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		// ========== CÓDIGO (NO EDITABLE) ==========
		JLabel lblCodigo = new JLabel("Código:");
		lblCodigo.setBounds(540, 15, 60, 20);
		contentPanel.add(lblCodigo);

		txtCodigo = new JTextField();
		txtCodigo.setEditable(false);
		txtCodigo.setEnabled(false);
		txtCodigo.setBounds(540, 38, 100, 26);
		contentPanel.add(txtCodigo);
		txtCodigo.setText(doctor.getCodigoDoctor());

		// ========== FILA 1: NOMBRE Y APELLIDO ==========
		JLabel lblNombre = new JLabel("Nombre(s):");
		lblNombre.setBounds(15, 15, 100, 20);
		contentPanel.add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(15, 38, 250, 26);
		contentPanel.add(txtNombre);
		txtNombre.setText(doctor.getNombre());

		JLabel lblApellido = new JLabel("Apellido(s):");
		lblApellido.setBounds(280, 15, 100, 20);
		contentPanel.add(lblApellido);

		txtApellido = new JTextField();
		txtApellido.setBounds(280, 38, 245, 26);
		contentPanel.add(txtApellido);
		txtApellido.setText(doctor.getApellido());

		// ========== FILA 2: CÉDULA (NO EDITABLE) Y TELÉFONO ==========
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
		txtCedula.setEditable(false);
		txtCedula.setEnabled(false);
		txtCedula.setBounds(15, 103, 180, 26);
		contentPanel.add(txtCedula);
		txtCedula.setText(formatearCedula(doctor.getCedula()));

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
		txtTelefono.setText(formatearTelefono(doctor.getTelefono()));

		// ========== FILA 3: SEXO Y FECHA DE NACIMIENTO ==========
		JLabel lblSexo = new JLabel("Sexo:");
		lblSexo.setBounds(405, 80, 50, 20);
		contentPanel.add(lblSexo);

		rdbtnHombre = new JRadioButton("M");
		rdbtnHombre.setBounds(405, 100, 60, 29);
		contentPanel.add(rdbtnHombre);

		rdbtnMujer = new JRadioButton("F");
		rdbtnMujer.setBounds(470, 100, 55, 29);
		contentPanel.add(rdbtnMujer);

		ButtonGroup grupoSexo = new ButtonGroup();
		grupoSexo.add(rdbtnHombre);
		grupoSexo.add(rdbtnMujer);

		if (doctor.getSexo() == 'M') {
			rdbtnHombre.setSelected(true);
		} else {
			rdbtnMujer.setSelected(true);
		}

		JLabel lblFechaNacimiento = new JLabel("Fecha de nacimiento:");
		lblFechaNacimiento.setBounds(15, 145, 150, 20);
		contentPanel.add(lblFechaNacimiento);

		spnFechaNacimiento = new JSpinner();
		Date fechaNacDate = Date.from(doctor.getFechaNacimiento().atStartOfDay(ZoneId.systemDefault()).toInstant());
		spnFechaNacimiento.setModel(new SpinnerDateModel(fechaNacDate, null, null, Calendar.DAY_OF_YEAR));
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
		txtDireccion.setText(doctor.getDireccion());
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
		cbxEspecialidad.setSelectedItem(doctor.getEspecialidad());

		JLabel lblCitasPorDia = new JLabel("Citas por día:");
		lblCitasPorDia.setBounds(310, 245, 100, 20);
		contentPanel.add(lblCitasPorDia);

		spnCitasPorDia = new JSpinner();
		spnCitasPorDia.setModel(new SpinnerNumberModel(doctor.getCitasPorDia(), 1, 20, 1));
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
			MaskFormatter licenciaMask = new MaskFormatter("UUUU-#####");
			licenciaMask.setPlaceholderCharacter('_');
			txtNumeroLicencia = new JFormattedTextField(licenciaMask);
			txtNumeroLicencia.setEnabled(false);
			txtNumeroLicencia.setEditable(false);
		} catch (Exception e) {
			txtNumeroLicencia = new JFormattedTextField();
		}

		txtNumeroLicencia.setBounds(15, 333, 250, 26);
		txtNumeroLicencia.setText(doctor.getNumeroLicencia());
		contentPanel.add(txtNumeroLicencia);

		JLabel lblInfoLicencia = new JLabel("(Ej: EXQM-12345)");
		lblInfoLicencia.setBounds(270, 336, 200, 20);
		contentPanel.add(lblInfoLicencia);

		// ========== FILA 6: HORARIOS ==========
		JLabel lblHorarioInicio = new JLabel("Horario Inicio:");
		lblHorarioInicio.setBounds(15, 375, 120, 20);
		contentPanel.add(lblHorarioInicio);

		txtHorarioInicio = new JTextField();
		txtHorarioInicio.setText(doctor.getHorarioInicio().toString());
		txtHorarioInicio.setBounds(15, 398, 100, 26);
		contentPanel.add(txtHorarioInicio);

		JLabel lblFormatoInicio = new JLabel("(HH:MM)");
		lblFormatoInicio.setBounds(125, 401, 60, 20);
		contentPanel.add(lblFormatoInicio);

		JLabel lblHorarioFin = new JLabel("Horario Fin:");
		lblHorarioFin.setBounds(200, 375, 100, 20);
		contentPanel.add(lblHorarioFin);

		txtHorarioFin = new JTextField();
		txtHorarioFin.setText(doctor.getHorarioFin().toString());
		txtHorarioFin.setBounds(200, 398, 100, 26);
		contentPanel.add(txtHorarioFin);

		JLabel lblFormatoFin = new JLabel("(HH:MM)");
		lblFormatoFin.setBounds(310, 401, 60, 20);
		contentPanel.add(lblFormatoFin);

		// ========== ACTIVO ==========
		chckbxActivo = new JCheckBox("Doctor Activo");
		chckbxActivo.setSelected(doctor.isActivo());
		chckbxActivo.setBounds(15, 440, 150, 29);
		contentPanel.add(chckbxActivo);

		// ========== PANEL DE BOTONES ==========
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnGuardar = new JButton("Guardar Cambios");
				btnGuardar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						modificarDoctor();
					}
				});
				btnGuardar.setActionCommand("OK");
				buttonPane.add(btnGuardar);
				getRootPane().setDefaultButton(btnGuardar);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	// ========== MÉTODO PARA MODIFICAR DOCTOR ==========
	private void modificarDoctor() {
		// Validar campos vacíos
		if (txtApellido.getText().trim().isEmpty() || txtDireccion.getText().trim().isEmpty()
				|| txtNombre.getText().trim().isEmpty()
				|| txtTelefono.getText().trim().replace("_", "").replace("(", "").replace(")", "").replace("-", "")
						.replace(" ", "").isEmpty()
				|| txtNumeroLicencia.getText().trim().isEmpty() || txtHorarioInicio.getText().trim().isEmpty()
				|| txtHorarioFin.getText().trim().isEmpty() || cbxEspecialidad.getSelectedIndex() == 0) {

			JOptionPane.showMessageDialog(this, "Complete todos los campos correctamente", "Campos incompletos",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		// Validar formato de teléfono
		String telefonoLimpio = txtTelefono.getText().replaceAll("[^0-9]", "");
		if (telefonoLimpio.length() != 10) {
			JOptionPane.showMessageDialog(this, "El teléfono debe tener 10 dígitos", "Teléfono inválido",
					JOptionPane.ERROR_MESSAGE);
			return;
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

			// Convertir horarios
			LocalTime horarioInicio = LocalTime.parse(txtHorarioInicio.getText().trim());
			LocalTime horarioFin = LocalTime.parse(txtHorarioFin.getText().trim());

			// Validar horarios
			if (!horarioFin.isAfter(horarioInicio)) {
				JOptionPane.showMessageDialog(this, "El horario de fin debe ser posterior al horario de inicio",
						"Horario inválido", JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Actualizar datos del doctor
			doctorAModificar.setNombre(txtNombre.getText().trim());
			doctorAModificar.setApellido(txtApellido.getText().trim());
			doctorAModificar.setTelefono(telefonoLimpio);
			doctorAModificar.setDireccion(txtDireccion.getText().trim());
			doctorAModificar.setFechaNacimiento(fechaNac);
			doctorAModificar.setSexo(rdbtnHombre.isSelected() ? 'M' : 'F');
			doctorAModificar.setEspecialidad(cbxEspecialidad.getSelectedItem().toString());
			doctorAModificar.setCitasPorDia((Integer) spnCitasPorDia.getValue());
			doctorAModificar.setHorarioInicio(horarioInicio);
			doctorAModificar.setHorarioFin(horarioFin);
			doctorAModificar.setActivo(chckbxActivo.isSelected());

			// Modificar en la clínica
			if (Clinica.getInstance().modificarDoctor(doctorAModificar)) {
				JOptionPane.showMessageDialog(this,
						"Doctor modificado exitosamente\n" + "Código: " + doctorAModificar.getCodigoDoctor() + "\n"
								+ "Nombre: " + doctorAModificar.getNombre() + " " + doctorAModificar.getApellido(),
						"Modificación Exitosa", JOptionPane.INFORMATION_MESSAGE);
				dispose();
			} else {
				JOptionPane.showMessageDialog(this, "Error al modificar el doctor", "Error", JOptionPane.ERROR_MESSAGE);
			}

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error al modificar doctor:\n" + ex.getMessage()
					+ "\n\nVerifique el formato de los horarios (HH:MM)", "Error", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}

	// ========== MÉTODOS AUXILIARES PARA FORMATEAR ==========
	private String formatearCedula(String cedula) {
		// Formato: 000-0000000-0
		if (cedula.length() == 11) {
			return cedula.substring(0, 3) + "-" + cedula.substring(3, 10) + "-" + cedula.substring(10);
		}
		return cedula;
	}

	private String formatearTelefono(String telefono) {
		// Formato: (000) 000-0000
		if (telefono.length() == 10) {
			return "(" + telefono.substring(0, 3) + ") " + telefono.substring(3, 6) + "-" + telefono.substring(6);
		}
		return telefono;
	}
}