package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import logico.Alergia;
import logico.Clinica;
import logico.Paciente;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.ButtonGroup;

public class regPaciente extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JFormattedTextField txtCedula;
	private JFormattedTextField txtTelefono;
	private JTextArea txtdireccion;
	private ArrayList<Alergia> alegecitas;
	private Paciente pacienteCar = null;
	private JRadioButton rdbtnHombre;
	private JRadioButton rdbtnMujer;
	private JSpinner spnFechaNacimiento;
	private JComboBox<String> cbxTipoSangre;
	private JSpinner spnFechaActual;
	private JSpinner spnEstatura;
	private JSpinner spnPeso;
	private JCheckBox chckbxAlergias;
	private boolean esModificacion = false;

	public static void main(String[] args) {
		try {
			regPaciente dialog = new regPaciente(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public regPaciente(Paciente elpaci) {
		pacienteCar = elpaci;
		esModificacion = (pacienteCar != null && !pacienteCar.getCodigoPaciente().equals("XX"));

		setTitle(esModificacion ? "Modificar Paciente" : "Registro de Pacientes");
		setBounds(100, 100, 600, 650);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		setLocationRelativeTo(null);

		crearComponentes();
		configurarModoEdicion();

		if (pacienteCar != null) {
			cargarPaciente();
		}
	}

	private void crearComponentes() {
		// Código
		JLabel lblCodigo = new JLabel("Código");
		lblCodigo.setBounds(451, 0, 69, 20);
		contentPanel.add(lblCodigo);

		txtCodigo = new JTextField();
		txtCodigo.setEnabled(false);
		txtCodigo.setBounds(451, 21, 120, 25);
		contentPanel.add(txtCodigo);
		txtCodigo.setColumns(10);
		txtCodigo.setText("PAC-" + Clinica.getInstance().contadorPacientes);

		// Nombre
		JLabel lblNombre = new JLabel("Nombre(s)");
		lblNombre.setBounds(15, 40, 80, 20);
		contentPanel.add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(111, 37, 296, 25);
		contentPanel.add(txtNombre);
		txtNombre.setColumns(10);

		// Apellido
		JLabel lblApellido = new JLabel("Apellido(s)");
		lblApellido.setBounds(15, 75, 80, 20);
		contentPanel.add(lblApellido);

		txtApellido = new JTextField();
		txtApellido.setBounds(111, 72, 296, 25);
		contentPanel.add(txtApellido);
		txtApellido.setColumns(10);

		// Cédula
		JLabel lblCedula = new JLabel("Cédula");
		lblCedula.setBounds(15, 110, 69, 20);
		contentPanel.add(lblCedula);

		try {
			MaskFormatter cedulaMask = new MaskFormatter("###-#######-#");
			cedulaMask.setPlaceholderCharacter('_');
			txtCedula = new JFormattedTextField(cedulaMask);
		} catch (Exception e) {
			txtCedula = new JFormattedTextField();
		}
		txtCedula.setBounds(111, 107, 196, 25);
		contentPanel.add(txtCedula);
		txtCedula.setColumns(10);

		// Tipo de Sangre
		JLabel lblTipoSangre = new JLabel("Tipo de sangre");
		lblTipoSangre.setBounds(322, 110, 100, 20);
		contentPanel.add(lblTipoSangre);

		cbxTipoSangre = new JComboBox<String>();
		cbxTipoSangre.setModel(new DefaultComboBoxModel<String>(
				new String[] { "<Tipo>", "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-" }));
		cbxTipoSangre.setBounds(445, 107, 100, 25);
		contentPanel.add(cbxTipoSangre);

		spnEstatura = new JSpinner();
		spnEstatura.setModel(
				new SpinnerNumberModel(Float.valueOf(150), Float.valueOf(1), Float.valueOf(300), Float.valueOf(1)));
		spnEstatura.setBounds(451, 73, 80, 25);
		contentPanel.add(spnEstatura);

		// Peso
		JLabel lblPeso = new JLabel("Peso(lb)");
		lblPeso.setBounds(15, 145, 69, 20);
		contentPanel.add(lblPeso);

		spnPeso = new JSpinner();
		spnPeso.setModel(
				new SpinnerNumberModel(Float.valueOf(150), Float.valueOf(1), Float.valueOf(500), Float.valueOf(1)));
		spnPeso.setBounds(111, 142, 94, 25);
		contentPanel.add(spnPeso);

		// Fecha de Nacimiento
		JLabel lblFechaNacimiento = new JLabel("Fecha de nacimiento");
		lblFechaNacimiento.setBounds(240, 145, 150, 20);
		contentPanel.add(lblFechaNacimiento);

		spnFechaNacimiento = new JSpinner();
		spnFechaNacimiento.setModel(new SpinnerDateModel(new Date(), null, new Date(), Calendar.DAY_OF_YEAR));
		JSpinner.DateEditor editor = new JSpinner.DateEditor(spnFechaNacimiento, "dd/MM/yyyy");
		spnFechaNacimiento.setEditor(editor);
		spnFechaNacimiento.setBounds(409, 142, 136, 25);
		contentPanel.add(spnFechaNacimiento);

		// Teléfono
		JLabel lblTelefono = new JLabel("Teléfono");
		lblTelefono.setBounds(15, 180, 69, 20);
		contentPanel.add(lblTelefono);

		try {
			MaskFormatter telefonoMask = new MaskFormatter("(###) ###-####");
			telefonoMask.setPlaceholderCharacter('_');
			txtTelefono = new JFormattedTextField(telefonoMask);
		} catch (Exception e) {
			txtTelefono = new JFormattedTextField();
		}
		txtTelefono.setBounds(111, 177, 196, 25);
		contentPanel.add(txtTelefono);
		txtTelefono.setColumns(10);

		// Sexo
		JLabel lblSexo = new JLabel("Sexo");
		lblSexo.setBounds(338, 180, 69, 20);
		contentPanel.add(lblSexo);

		rdbtnHombre = new JRadioButton("Masculino");
		rdbtnHombre.setSelected(true);
		rdbtnHombre.setBounds(374, 178, 106, 25);
		contentPanel.add(rdbtnHombre);

		rdbtnMujer = new JRadioButton("Femenino");
		rdbtnMujer.setBounds(484, 177, 120, 25);
		contentPanel.add(rdbtnMujer);

		ButtonGroup grupoSexo = new ButtonGroup();
		grupoSexo.add(rdbtnHombre);
		grupoSexo.add(rdbtnMujer);

		// Dirección
		JLabel lblDireccion = new JLabel("Dirección");
		lblDireccion.setBounds(15, 215, 69, 20);
		contentPanel.add(lblDireccion);

		JScrollPane scrollDireccion = new JScrollPane();
		scrollDireccion.setBounds(111, 215, 454, 80);
		contentPanel.add(scrollDireccion);

		txtdireccion = new JTextArea();
		txtdireccion.setLineWrap(true);
		txtdireccion.setWrapStyleWord(true);
		scrollDireccion.setViewportView(txtdireccion);

		// Alergias
		JLabel lblAlergias = new JLabel("¿Padece de alguna alergia?");
		lblAlergias.setBounds(15, 310, 200, 20);
		contentPanel.add(lblAlergias);

		chckbxAlergias = new JCheckBox("Sí, padezco de alergias");
		chckbxAlergias.setBounds(220, 308, 200, 25);
		contentPanel.add(chckbxAlergias);

		// Fecha de Registro
		JLabel lblFecha = new JLabel("Fecha Registro");
		lblFecha.setBounds(15, 350, 100, 20);
		contentPanel.add(lblFecha);

		spnFechaActual = new JSpinner();
		spnFechaActual.setEnabled(false);
		spnFechaActual.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_YEAR));
		JSpinner.DateEditor editorActual = new JSpinner.DateEditor(spnFechaActual, "dd/MM/yyyy");
		spnFechaActual.setEditor(editorActual);
		spnFechaActual.setBounds(111, 347, 120, 25);
		contentPanel.add(spnFechaActual);

		JLabel label = new JLabel("Estatura(cm)");
		label.setBounds(451, 52, 99, 20);
		contentPanel.add(label);

		// Botones
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton(esModificacion ? "Modificar" : "Continuar");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				procesarPaciente();
			}
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancelar");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
	}

	private void configurarModoEdicion() {
		if (esModificacion) {
			// En modificación, solo permitir editar campos modificables
			txtCedula.setEnabled(false);
			txtNombre.setEnabled(false);
			txtApellido.setEnabled(false);
			spnFechaNacimiento.setEnabled(false);
			rdbtnHombre.setEnabled(false);
			rdbtnMujer.setEnabled(false);
			cbxTipoSangre.setEnabled(false);
			chckbxAlergias.setVisible(false);

			// Buscar y ocultar la label de alergias
			for (int i = 0; i < contentPanel.getComponentCount(); i++) {
				if (contentPanel.getComponent(i) instanceof JLabel) {
					JLabel label = (JLabel) contentPanel.getComponent(i);
					if ("¿Padece de alguna alergia?".equals(label.getText())) {
						label.setVisible(false);
						break;
					}
				}
			}
		} else if (pacienteCar != null && pacienteCar.getCodigoPaciente().equals("XX")) {
			// Paciente interesado - completar datos
			JOptionPane.showMessageDialog(this, "Complete los datos de este paciente", "Información",
					JOptionPane.INFORMATION_MESSAGE);
			txtCedula.setEnabled(false);
			txtNombre.setEnabled(false);
			txtApellido.setEnabled(false);
			txtTelefono.setEnabled(false);
		}
	}

	private void cargarPaciente() {
		if (pacienteCar == null)
			return;

		txtdireccion.setText(pacienteCar.getDireccion() != null ? pacienteCar.getDireccion() : "");
		txtApellido.setText(pacienteCar.getApellido() != null ? pacienteCar.getApellido() : "");
		txtCedula.setText(pacienteCar.getCedula() != null ? pacienteCar.getCedula() : "");

		if (pacienteCar.getCodigoPaciente().equals("XX")) {
			txtCodigo.setText("PAC-" + Clinica.getInstance().contadorPacientes);
		} else {
			txtCodigo.setText(pacienteCar.getCodigoPaciente());
		}

		txtNombre.setText(pacienteCar.getNombre() != null ? pacienteCar.getNombre() : "");
		txtTelefono.setText(pacienteCar.getTelefono() != null ? pacienteCar.getTelefono() : "");

		if (pacienteCar.getEstatura() > 0) {
			spnEstatura.setValue(pacienteCar.getEstatura());
		}

		if (pacienteCar.getPeso() > 0) {
			spnPeso.setValue(pacienteCar.getPeso());
		}

		if (pacienteCar.getFechaNacimiento() != null) {
			Date fechaNacDate = Date
					.from(pacienteCar.getFechaNacimiento().atStartOfDay(ZoneId.systemDefault()).toInstant());
			spnFechaNacimiento.setValue(fechaNacDate);
		}

		if (pacienteCar.getFechaRegistro() != null) {
			Date fechaRegDate = Date
					.from(pacienteCar.getFechaRegistro().atStartOfDay(ZoneId.systemDefault()).toInstant());
			spnFechaActual.setValue(fechaRegDate);
		}

		if (pacienteCar.getSexo() == 'M') {
			rdbtnHombre.setSelected(true);
		} else {
			rdbtnMujer.setSelected(true);
		}

		// Cargar tipo de sangre
		if (pacienteCar.getTipoSangre() != null) {
			for (int i = 0; i < cbxTipoSangre.getItemCount(); i++) {
				if (pacienteCar.getTipoSangre().equals(cbxTipoSangre.getItemAt(i))) {
					cbxTipoSangre.setSelectedIndex(i);
					break;
				}
			}
		}

		// Configurar alergias
		if (pacienteCar.getAlergias() != null && !pacienteCar.getAlergias().isEmpty()) {
			alegecitas = pacienteCar.getAlergias();
		}
	}

	private void procesarPaciente() {
		// Limpiar datos
		String cedulaLimpia = txtCedula.getText().replaceAll("[^0-9]", "");
		String telefonoLimpio = txtTelefono.getText().replaceAll("[^0-9]", "");

		// Validar campos vacíos
		if (txtNombre.getText().trim().isEmpty() || txtApellido.getText().trim().isEmpty()
				|| cedulaLimpia.length() != 11 || telefonoLimpio.length() != 10
				|| txtdireccion.getText().trim().isEmpty() || cbxTipoSangre.getSelectedIndex() == 0) {

			JOptionPane.showMessageDialog(this,
					"Complete todos los campos correctamente.\n\n" + "Verifique:\n" + "• Cédula: 11 dígitos\n"
							+ "• Teléfono: 10 dígitos\n" + "• Tipo de sangre seleccionado",
					"Campos incompletos", JOptionPane.WARNING_MESSAGE);
			return;
		}

		// Validar cédula duplicada (solo para nuevos registros)
		if (!esModificacion && Clinica.getInstance().isCedulaRegistrada(cedulaLimpia)) {
			JOptionPane.showMessageDialog(this,
					"Esta cédula ya está registrada en el sistema.\n" + "Cédula: " + txtCedula.getText(),
					"Cédula duplicada", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Validar teléfono duplicado
		if (Clinica.getInstance().isTelefonoRegistrado(telefonoLimpio)) {
			JOptionPane.showMessageDialog(this,
					"Este teléfono ya está registrado en el sistema.\n" + "Teléfono: " + txtTelefono.getText() + "\n"
							+ "Por favor ingrese un teléfono diferente.",
					"Teléfono duplicado", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Validar peso y estatura
		float peso = ((Number) spnPeso.getValue()).floatValue();
		float estatura = ((Number) spnEstatura.getValue()).floatValue();

		if (peso < 1 || peso > 500) {
			JOptionPane.showMessageDialog(this, "El peso debe estar entre 1 y 500 libras", "Peso inválido",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (estatura < 1 || estatura > 300) {
			JOptionPane.showMessageDialog(this, "La estatura debe estar entre 1 y 300 cm", "Estatura inválida",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		// Determinar sexo
		char sexo = rdbtnHombre.isSelected() ? 'M' : 'F';

		// Procesar alergias (solo para nuevos registros o pacientes interesados)
		if (!esModificacion && chckbxAlergias.isSelected()) {
			TomaAlergias alergenoS = new TomaAlergias();
			alergenoS.setModal(true);
			alergenoS.setVisible(true);
			alegecitas = alergenoS.AlergiasSeleccionadas();
			if (alegecitas == null || alegecitas.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Seleccione alergias o desmarque la opción", "Alergias",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
		}

		try {
			if (esModificacion) {
				// Modificar paciente existente
				pacienteCar.setDireccion(txtdireccion.getText().trim());
				pacienteCar.setTelefono(telefonoLimpio);
				pacienteCar.setEstatura(estatura);
				pacienteCar.setPeso(peso);

				if (Clinica.getInstance().modificarPaciente(pacienteCar)) {
					JOptionPane.showMessageDialog(this, "Paciente modificado exitosamente", "Modificación exitosa",
							JOptionPane.INFORMATION_MESSAGE);
					dispose();
				} else {
					JOptionPane.showMessageDialog(this, "Error al modificar el paciente", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			} else {
				// Registrar nuevo paciente
				Date fechaNacDate = (Date) spnFechaNacimiento.getValue();
				LocalDate fechaNac = fechaNacDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

				if (fechaNac.isAfter(LocalDate.now())) {
					JOptionPane.showMessageDialog(this, "La fecha de nacimiento no puede ser futura", "Fecha inválida",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				Paciente nuevoPaciente = new Paciente(cedulaLimpia, txtNombre.getText().trim(),
						txtApellido.getText().trim(), telefonoLimpio, txtCodigo.getText());
				nuevoPaciente.setDireccion(txtdireccion.getText().trim());
				nuevoPaciente.setFechaNacimiento(fechaNac);
				nuevoPaciente.setSexo(sexo);
				nuevoPaciente.setTipoSangre(cbxTipoSangre.getSelectedItem().toString());
				nuevoPaciente.setPeso(peso);
				nuevoPaciente.setEstatura(estatura);
				nuevoPaciente.setAlergias(alegecitas);

				// Si es paciente interesado, actualizar código
				if (pacienteCar != null && pacienteCar.getCodigoPaciente().equals("XX")) {
					pacienteCar.setCodigoPaciente(txtCodigo.getText());
				}

				if (Clinica.getInstance().registrarPaciente(nuevoPaciente)) {
					JOptionPane.showMessageDialog(this,
							"Registro Satisfactorio\n\n" + "Código: " + nuevoPaciente.getCodigoPaciente() + "\n"
									+ "Nombre: " + nuevoPaciente.getNombre() + " " + nuevoPaciente.getApellido(),
							"Información", JOptionPane.INFORMATION_MESSAGE);
					dispose();
				} else {
					JOptionPane.showMessageDialog(this, "Error al registrar el paciente", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error al procesar: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public Paciente getPaciente() {
		return pacienteCar;
	}
}