package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
	private JButton btnModificar;

	// Datos originales para detectar cambios
	private String telefonoOriginal = "";
	private String direccionOriginal = "";
	private float pesoOriginal = 0;
	private float estaturaOriginal = 0;

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

		if (esModificacion) {
			configurarDeteccionCambios();
		}
	}

	private void crearComponentes() {
		// Código
		JLabel lblCodigo = new JLabel("Código");
		lblCodigo.setBounds(15, 23, 69, 20);
		contentPanel.add(lblCodigo);

		txtCodigo = new JTextField();
		txtCodigo.setEnabled(false);
		txtCodigo.setBounds(111, 21, 120, 25);
		contentPanel.add(txtCodigo);
		txtCodigo.setColumns(10);
		txtCodigo.setText("PAC-" + Clinica.getInstance().contadorPacientes);

		// Nombre
		JLabel lblNombre = new JLabel("Nombre(s)");
		lblNombre.setBounds(15, 57, 80, 20);
		contentPanel.add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(111, 55, 296, 25);
		contentPanel.add(txtNombre);
		txtNombre.setColumns(10);

		// Apellido
		JLabel lblApellido = new JLabel("Apellido(s)");
		lblApellido.setBounds(15, 95, 80, 20);
		contentPanel.add(lblApellido);

		txtApellido = new JTextField();
		txtApellido.setBounds(111, 93, 296, 25);
		contentPanel.add(txtApellido);
		txtApellido.setColumns(10);

		// Cédula
		JLabel lblCedula = new JLabel("Cédula");
		lblCedula.setBounds(15, 133, 69, 20);
		contentPanel.add(lblCedula);

		try {
			MaskFormatter cedulaMask = new MaskFormatter("###-#######-#");
			cedulaMask.setPlaceholderCharacter('_');
			txtCedula = new JFormattedTextField(cedulaMask);
		} catch (Exception e) {
			txtCedula = new JFormattedTextField();
		}
		txtCedula.setBounds(111, 131, 196, 25);
		contentPanel.add(txtCedula);

		// Tipo de Sangre
		JLabel lblTipoSangre = new JLabel("Tipo de sangre");
		lblTipoSangre.setBounds(322, 133, 100, 20);
		contentPanel.add(lblTipoSangre);

		cbxTipoSangre = new JComboBox<String>();
		cbxTipoSangre.setModel(new DefaultComboBoxModel<String>(
				new String[] { "<Tipo>", "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-" }));
		cbxTipoSangre.setBounds(450, 128, 100, 25);
		contentPanel.add(cbxTipoSangre);

		// Estatura
		JLabel lblEstatura = new JLabel("Estatura(cm)");
		lblEstatura.setBounds(450, 57, 99, 20);
		contentPanel.add(lblEstatura);

		spnEstatura = new JSpinner();
		spnEstatura.setModel(new SpinnerNumberModel(150.0, 1.0, 300.0, 1.0));
		spnEstatura.setBounds(450, 79, 80, 25);
		contentPanel.add(spnEstatura);

		// Peso
		JLabel lblPeso = new JLabel("Peso(lb)");
		lblPeso.setBounds(15, 166, 69, 20);
		contentPanel.add(lblPeso);

		spnPeso = new JSpinner();
		spnPeso.setModel(new SpinnerNumberModel(150.0, 1.0, 500.0, 1.0));
		spnPeso.setBounds(111, 164, 94, 25);
		contentPanel.add(spnPeso);

		// Fecha de Nacimiento
		JLabel lblFechaNacimiento = new JLabel("Fecha de nacimiento");
		lblFechaNacimiento.setBounds(287, 169, 120, 20);
		contentPanel.add(lblFechaNacimiento);

		spnFechaNacimiento = new JSpinner();
		spnFechaNacimiento.setModel(new SpinnerDateModel(new Date(), null, new Date(), Calendar.DAY_OF_YEAR));
		JSpinner.DateEditor editor = new JSpinner.DateEditor(spnFechaNacimiento, "dd/MM/yyyy");
		spnFechaNacimiento.setEditor(editor);
		spnFechaNacimiento.setBounds(414, 166, 136, 25);
		contentPanel.add(spnFechaNacimiento);

		// Teléfono
		JLabel lblTelefono = new JLabel("Teléfono");
		lblTelefono.setBounds(15, 214, 69, 20);
		contentPanel.add(lblTelefono);

		try {
			MaskFormatter telefonoMask = new MaskFormatter("(###) ###-####");
			telefonoMask.setPlaceholderCharacter('_');
			txtTelefono = new JFormattedTextField(telefonoMask);
		} catch (Exception e) {
			txtTelefono = new JFormattedTextField();
		}
		txtTelefono.setBounds(111, 212, 196, 25);
		contentPanel.add(txtTelefono);

		// Sexo
		JLabel lblSexo = new JLabel("Sexo");
		lblSexo.setBounds(336, 214, 69, 20);
		contentPanel.add(lblSexo);

		rdbtnHombre = new JRadioButton("Masculino");
		rdbtnHombre.setSelected(true);
		rdbtnHombre.setBounds(388, 212, 94, 25);
		contentPanel.add(rdbtnHombre);

		rdbtnMujer = new JRadioButton("Femenino");
		rdbtnMujer.setBounds(483, 212, 91, 25);
		contentPanel.add(rdbtnMujer);

		ButtonGroup grupoSexo = new ButtonGroup();
		grupoSexo.add(rdbtnHombre);
		grupoSexo.add(rdbtnMujer);

		// Dirección
		JLabel lblDireccion = new JLabel("Dirección");
		lblDireccion.setBounds(15, 287, 69, 20);
		contentPanel.add(lblDireccion);

		JScrollPane scrollDireccion = new JScrollPane();
		scrollDireccion.setBounds(100, 258, 454, 80);
		contentPanel.add(scrollDireccion);
		
				txtdireccion = new JTextArea();
				scrollDireccion.setViewportView(txtdireccion);
				txtdireccion.setLineWrap(true);
				txtdireccion.setWrapStyleWord(true);

		// Alergias
		JLabel lblAlergias = new JLabel("¿Padece de alguna alergia?");
		lblAlergias.setBounds(15, 351, 200, 20);
		contentPanel.add(lblAlergias);

		chckbxAlergias = new JCheckBox("Sí, padezco de alergias");
		chckbxAlergias.setBounds(222, 349, 200, 25);
		contentPanel.add(chckbxAlergias);

		// Fecha de Registro
		JLabel lblFecha = new JLabel("Fecha Registro");
		lblFecha.setBounds(15, 400, 100, 20);
		contentPanel.add(lblFecha);

		spnFechaActual = new JSpinner();
		spnFechaActual.setEnabled(false);
		spnFechaActual.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_YEAR));
		JSpinner.DateEditor editorActual = new JSpinner.DateEditor(spnFechaActual, "dd/MM/yyyy");
		spnFechaActual.setEditor(editorActual);
		spnFechaActual.setBounds(111, 398, 120, 25);
		contentPanel.add(spnFechaActual);

		// Botones
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		btnModificar = new JButton(esModificacion ? "Modificar" : "Continuar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				procesarPaciente();
			}
		});
		btnModificar.setActionCommand("OK");
		buttonPane.add(btnModificar);
		getRootPane().setDefaultButton(btnModificar);

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
			txtCedula.setEnabled(false);
			txtNombre.setEnabled(false);
			txtApellido.setEnabled(false);
			spnFechaNacimiento.setEnabled(false);
			rdbtnHombre.setEnabled(false);
			rdbtnMujer.setEnabled(false);
			cbxTipoSangre.setEnabled(false);
			chckbxAlergias.setVisible(false);

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
			JOptionPane.showMessageDialog(this, "Complete los datos de este paciente", "Información",
					JOptionPane.INFORMATION_MESSAGE);
			txtCedula.setEnabled(false);
			txtNombre.setEnabled(false);
			txtApellido.setEnabled(false);
			txtTelefono.setEnabled(false);
		}
	}

	private void configurarDeteccionCambios() {
		btnModificar.setEnabled(false);

		DocumentListener cambioListener = new DocumentListener() {
			public void insertUpdate(DocumentEvent e) {
				verificarCambios();
			}

			public void removeUpdate(DocumentEvent e) {
				verificarCambios();
			}

			public void changedUpdate(DocumentEvent e) {
				verificarCambios();
			}
		};

		txtTelefono.getDocument().addDocumentListener(cambioListener);
		txtdireccion.getDocument().addDocumentListener(cambioListener);

		spnPeso.addChangeListener(e -> verificarCambios());
		spnEstatura.addChangeListener(e -> verificarCambios());
	}

	private void verificarCambios() {
		if (!esModificacion)
			return;

		String telefonoActual = txtTelefono.getText().replaceAll("[^0-9]", "");
		String direccionActual = txtdireccion.getText().trim();
		float pesoActual = ((Number) spnPeso.getValue()).floatValue();
		float estaturaActual = ((Number) spnEstatura.getValue()).floatValue();

		boolean hubocambios = !telefonoActual.equals(telefonoOriginal) || !direccionActual.equals(direccionOriginal)
				|| pesoActual != pesoOriginal || estaturaActual != estaturaOriginal;

		btnModificar.setEnabled(hubocambios);
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
			spnEstatura.setValue((double) pacienteCar.getEstatura());
		}

		if (pacienteCar.getPeso() > 0) {
			spnPeso.setValue((double) pacienteCar.getPeso());
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

		if (pacienteCar.getTipoSangre() != null) {
			for (int i = 0; i < cbxTipoSangre.getItemCount(); i++) {
				if (pacienteCar.getTipoSangre().equals(cbxTipoSangre.getItemAt(i))) {
					cbxTipoSangre.setSelectedIndex(i);
					break;
				}
			}
		}

		if (pacienteCar.getAlergias() != null && !pacienteCar.getAlergias().isEmpty()) {
			alegecitas = pacienteCar.getAlergias();
		}

		// Guardar datos originales
		telefonoOriginal = pacienteCar.getTelefono().replaceAll("[^0-9]", "");
		direccionOriginal = pacienteCar.getDireccion() != null ? pacienteCar.getDireccion() : "";
		pesoOriginal = pacienteCar.getPeso();
		estaturaOriginal = pacienteCar.getEstatura();
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

	private void procesarPaciente() {
		String cedulaLimpia = txtCedula.getText().replaceAll("[^0-9]", "");
		String telefonoLimpio = txtTelefono.getText().replaceAll("[^0-9]", "");

		// Validar campos vacíos
		if (txtNombre.getText().trim().isEmpty() || txtApellido.getText().trim().isEmpty()
				|| cedulaLimpia.length() != 11 || telefonoLimpio.length() != 10
				|| txtdireccion.getText().trim().isEmpty() || cbxTipoSangre.getSelectedIndex() == 0) {

			JOptionPane.showMessageDialog(this, "Complete todos los campos", "Campos incompletos",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		// Validar nombre y apellido
		if (!validarNombre(txtNombre.getText().trim(), "El nombre")) {
			txtNombre.requestFocus();
			return;
		}

		if (!validarNombre(txtApellido.getText().trim(), "El apellido")) {
			txtApellido.requestFocus();
			return;
		}

		// Validar cédula duplicada
		if (!esModificacion && Clinica.getInstance().isCedulaRegistrada(cedulaLimpia)) {
			JOptionPane.showMessageDialog(this, "Esta cédula ya está registrada", "Cédula duplicada",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Validar teléfono duplicado (solo si cambió)
		if (!telefonoLimpio.equals(telefonoOriginal)) {
			if (Clinica.getInstance().isTelefonoRegistrado(telefonoLimpio, cedulaLimpia)) {
				JOptionPane.showMessageDialog(this, "Este teléfono ya está registrado", "Teléfono duplicado",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

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

		char sexo = rdbtnHombre.isSelected() ? 'M' : 'F';

		if (!esModificacion && chckbxAlergias.isSelected()) {
			TomaAlergias alergenoS = new TomaAlergias();
			alergenoS.setModal(true);
			alergenoS.setVisible(true);
			alegecitas = alergenoS.AlergiasSeleccionadas();
			if (alegecitas == null || alegecitas.isEmpty()) {
				return;
			}
		}

		try {
			if (esModificacion) {
				pacienteCar.setDireccion(txtdireccion.getText().trim());
				pacienteCar.setTelefono(telefonoLimpio);
				pacienteCar.setEstatura(estatura);
				pacienteCar.setPeso(peso);

				if (Clinica.getInstance().modificarPaciente(pacienteCar)) {
					JOptionPane.showMessageDialog(this, "Paciente modificado exitosamente", "Éxito",
							JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
			} else {
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

				if (pacienteCar != null && pacienteCar.getCodigoPaciente().equals("XX")) {
					pacienteCar.setCodigoPaciente(txtCodigo.getText());
				}

				if (Clinica.getInstance().registrarPaciente(nuevoPaciente)) {
					// Abrir ventana de vacunas solo para registros nuevos
					selecVacunas viejitas = new selecVacunas(nuevoPaciente);
					viejitas.setModal(true);
					viejitas.setVisible(true);

					JOptionPane.showMessageDialog(this, "Registro exitoso", "Información",
							JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public Paciente getPaciente() {
		return pacienteCar;
	}
}