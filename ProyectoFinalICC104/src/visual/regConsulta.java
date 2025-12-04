package visual;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import logico.*;

import java.awt.*;
import java.util.ArrayList;

public class regConsulta extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	private JComboBox<String> cbCitas;
	private JComboBox<String> cbTratamientos;
	private JComboBox<String> cbEnfermedades;

	private JTextArea txtSintomas;
	private JTextArea txtDiagnostico;
	private JTextArea txtNotas;
	private JTextArea txtAlergias;

	private JButton btnVerAlergias;
	private JButton btnRegistrarPaciente;
	private JButton btnGestionarTratamientos;

	private ArrayList<Cita> citasPendientes;
	private ArrayList<Tratamiento> tratamientos;
	private ArrayList<Enfermedad> enfermedades;

	// Constructor sin parámetros
	public regConsulta() {
		super((Frame) null, "Registrar consulta", true);
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(regConsulta.class.getResource("/javax/swing/plaf/metal/icons/ocean/file.gif")));
		inicializar();
	}

	// Constructor con parámetro
	public regConsulta(Cita citaSeleccionada) {
		super((Frame) null, "Registrar consulta", true);
		inicializar();

		if (citaSeleccionada != null) {
			seleccionarCitaPorCodigo(citaSeleccionada.getCodigoCita());
		}
	}

	private void seleccionarCitaPorCodigo(String codigoCita) {
		for (int i = 0; i < cbCitas.getItemCount(); i++) {
			String item = cbCitas.getItemAt(i);
			if (item.startsWith(codigoCita + " - ")) {
				cbCitas.setSelectedIndex(i);
				break;
			}
		}
	}

	private void inicializar() {

		setBounds(100, 100, 820, 590);
		setResizable(false);
		setLocationRelativeTo(null);

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		contentPanel.setLayout(null);

		JLabel lblTitulo = new JLabel("Registrar consulta");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTitulo.setBounds(20, 10, 300, 30);
		contentPanel.add(lblTitulo);

		// CITA
		JLabel lblCita = new JLabel("Cita pendiente:");
		lblCita.setBounds(20, 60, 120, 25);
		contentPanel.add(lblCita);

		cbCitas = new JComboBox<>();
		cbCitas.setBounds(150, 60, 350, 25);
		contentPanel.add(cbCitas);

		cargarCitasPendientes();
		cbCitas.addActionListener(e -> actualizarDatosCita());

		// ALERGIAS
		JLabel lblAler = new JLabel("Alergias:");
		lblAler.setBounds(20, 100, 120, 25);
		contentPanel.add(lblAler);

		txtAlergias = new JTextArea();
		txtAlergias.setEditable(false);
		JScrollPane spAler = new JScrollPane(txtAlergias);
		spAler.setBounds(150, 100, 350, 60);
		contentPanel.add(spAler);

		btnVerAlergias = new JButton("Ver detalles");
		btnVerAlergias.setBounds(510, 100, 120, 28);
		btnVerAlergias.addActionListener(e -> verDetallesAlergias());
		contentPanel.add(btnVerAlergias);

		btnRegistrarPaciente = new JButton("Registrar este paciente");
		btnRegistrarPaciente.setBounds(510, 135, 200, 28);
		btnRegistrarPaciente.setVisible(false);
		btnRegistrarPaciente.addActionListener(e -> registrarNuevoPaciente());
		contentPanel.add(btnRegistrarPaciente);

		// SINTOMAS
		JLabel lblSint = new JLabel("Sintomas:");
		lblSint.setBounds(20, 180, 120, 25);
		contentPanel.add(lblSint);

		txtSintomas = new JTextArea();
		JScrollPane spSint = new JScrollPane(txtSintomas);
		spSint.setBounds(150, 180, 620, 70);
		contentPanel.add(spSint);

		// DIAGNOSTICO
		JLabel lblDiag = new JLabel("Diagnostico:");
		lblDiag.setBounds(20, 265, 120, 25);
		contentPanel.add(lblDiag);

		txtDiagnostico = new JTextArea();
		JScrollPane spDiag = new JScrollPane(txtDiagnostico);
		spDiag.setBounds(150, 265, 620, 70);
		contentPanel.add(spDiag);

		// ENFERMEDAD
		JLabel lblEnf = new JLabel("Enfermedad:");
		lblEnf.setBounds(20, 350, 120, 25);
		contentPanel.add(lblEnf);

		cbEnfermedades = new JComboBox<>();
		cbEnfermedades.setBounds(150, 350, 250, 25);
		contentPanel.add(cbEnfermedades);

		JButton btnAddEnf = new JButton("Agregar");
		btnAddEnf.setBounds(410, 350, 100, 28);
		btnAddEnf.addActionListener(e -> abrirRegEnfermedad());
		contentPanel.add(btnAddEnf);

		cargarEnfermedades();

		// TRATAMIENTO
		JLabel lblTrat = new JLabel("Tratamiento:");
		lblTrat.setBounds(20, 390, 120, 25);
		contentPanel.add(lblTrat);

		cbTratamientos = new JComboBox<>();
		cbTratamientos.setBounds(150, 390, 400, 25);
		contentPanel.add(cbTratamientos);

		btnGestionarTratamientos = new JButton("Gestionar");
		btnGestionarTratamientos.setBounds(560, 388, 110, 28);
		btnGestionarTratamientos.addActionListener(e -> abrirGestionTratamientos());
		contentPanel.add(btnGestionarTratamientos);

		cargarTratamientos();

		// NOTAS
		JLabel lblNotas = new JLabel("Notas:");
		lblNotas.setBounds(20, 430, 120, 25);
		contentPanel.add(lblNotas);

		txtNotas = new JTextArea();
		JScrollPane spNotas = new JScrollPane(txtNotas);
		spNotas.setBounds(150, 430, 620, 70);
		contentPanel.add(spNotas);

		// BOTONES FINALES
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(520, 510, 100, 30);
		btnCancelar.addActionListener(e -> dispose());
		contentPanel.add(btnCancelar);

		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.setBounds(630, 510, 100, 30);
		btnRegistrar.addActionListener(e -> registrarConsulta());
		contentPanel.add(btnRegistrar);
	}

	private void cargarCitasPendientes() {
		cbCitas.removeAllItems();
		citasPendientes = Clinica.getInstance().listarCitasPendientes();

		// Obtener doctor logeado
		Doctor doctorLogeado = Control.getDoctorLogeado();

		cbCitas.addItem("<Seleccione>");

		if (doctorLogeado == null) {
			JOptionPane.showMessageDialog(this, "No hay doctor logeado en el sistema.", "Error de sesión",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		String licenciaDoctorLogeado = doctorLogeado.getNumeroLicencia();
		boolean tieneCitas = false;

		for (Cita c : citasPendientes) {
			// Verificar que el doctor de la cita sea el mismo que está logeado
			if (c.getDoctor() != null && c.getDoctor().getNumeroLicencia().equals(licenciaDoctorLogeado)) {

				cbCitas.addItem(c.getCodigoCita() + " - " + c.getPaciente().getNombre());
				tieneCitas = true;
			}
		}

		// Si no hay citas para este doctor
		if (!tieneCitas) {
			cbCitas.addItem("No tiene citas pendientes");
			cbCitas.setEnabled(false);
			JOptionPane.showMessageDialog(this, "No tiene citas pendientes asignadas.", "Sin citas",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private Cita getCitaSeleccionada() {
		int i = cbCitas.getSelectedIndex();
		if (i <= 0)
			return null;
		return citasPendientes.get(i - 1);
	}

	private void actualizarDatosCita() {
		Cita c = getCitaSeleccionada();
		if (c == null)
			return;

		Paciente p = c.getPaciente();
		if (p == null)
			return;

		boolean esPre = p.getCodigoPaciente().equals("XX");
		btnRegistrarPaciente.setVisible(esPre);

		if (p.getAlergias() == null || p.getAlergias().isEmpty()) {
			txtAlergias.setText("No tiene alergias.");
		} else {
			StringBuilder sb = new StringBuilder();
			for (Alergia al : p.getAlergias()) {
				sb.append(al.getNombre()).append(", ");
			}
			txtAlergias.setText(sb.substring(0, sb.length() - 2));
		}
	}

	private void verDetallesAlergias() {
		Cita c = getCitaSeleccionada();
		if (c == null)
			return;

		Paciente p = c.getPaciente();
		if (p == null || p.getAlergias() == null || p.getAlergias().isEmpty()) {
			JOptionPane.showMessageDialog(this, "No tiene alergias registradas.");
			return;
		}

		StringBuilder sb = new StringBuilder("Alergias del paciente:\n\n");
		for (Alergia a : p.getAlergias()) {
			sb.append("- ").append(a.getNombre()).append(" (Tipo: ").append(a.getTipo()).append(")\n");
		}

		JOptionPane.showMessageDialog(this, sb.toString(), "Detalles de alergias", JOptionPane.INFORMATION_MESSAGE);
	}

	private void cargarEnfermedades() {
		cbEnfermedades.removeAllItems();
		cbEnfermedades.addItem("No especificar");
		enfermedades = Clinica.getInstance().listarEnfermedades();
		for (Enfermedad e : enfermedades) {
			cbEnfermedades.addItem(e.getNombre());
		}
	}

	private void cargarTratamientos() {
		cbTratamientos.removeAllItems();
		tratamientos = Clinica.getInstance().listarTratamientos();

		if (tratamientos == null || tratamientos.isEmpty()) {
			cbTratamientos.addItem("No hay tratamientos - Gestione uno primero");
			cbTratamientos.setEnabled(false);
		} else {
			cbTratamientos.addItem("<Seleccione>");
			for (Tratamiento t : tratamientos) {
				cbTratamientos.addItem(t.getCodigoTratamiento() + " - " + t.getNombreTratamiento());
			}
			cbTratamientos.setEnabled(true);
		}
	}

	private void abrirRegEnfermedad() {
		regEnfermedades win = new regEnfermedades();
		win.setVisible(true);
		cargarEnfermedades();
	}

	private void abrirGestionTratamientos() {
		GestionTratamientos dialogo = new GestionTratamientos();
		dialogo.setModal(true);
		dialogo.setLocationRelativeTo(this);
		dialogo.setVisible(true);
		cargarTratamientos();
	}

	private void registrarNuevoPaciente() {
		Cita c = getCitaSeleccionada();
		if (c == null)
			return;

		Paciente pre = c.getPaciente();

		regPaciente win = new regPaciente(pre);
		win.setModal(true);
		win.setVisible(true);

		Paciente real = Clinica.getInstance().buscarPacientePorCedula(pre.getCedula());

		if (real != null) {
			c.setPaciente(real);
		}

		actualizarDatosCita();
	}

	private void registrarConsulta() {
		Cita c = getCitaSeleccionada();
		if (c == null) {
			JOptionPane.showMessageDialog(this, "Debe seleccionar una cita.");
			return;
		}

		if (Clinica.getInstance().buscarPacientePorCedula(c.getPaciente().getCedula()) == null) {
			JOptionPane.showMessageDialog(this, "Debe registrar este paciente antes de registrar la consulta.");
			return;
		}

		if (tratamientos == null || tratamientos.isEmpty()) {
			JOptionPane.showMessageDialog(this,
					"No hay tratamientos disponibles.\nPor favor, registre uno primero desde el boton Gestionar.");
			return;
		}

		int indiceTrat = cbTratamientos.getSelectedIndex();
		if (indiceTrat <= 0) {
			JOptionPane.showMessageDialog(this, "Debe seleccionar un tratamiento.");
			return;
		}

		String sintomas = txtSintomas.getText().trim();
		String diagnostico = txtDiagnostico.getText().trim();
		String notas = txtNotas.getText().trim();

		if (sintomas.isEmpty() || diagnostico.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Sintomas y diagnostico son obligatorios.");
			return;
		}

		Tratamiento tratSeleccionado = tratamientos.get(indiceTrat - 1);

		String codigoEnfermedad = null;
		if (cbEnfermedades.getSelectedIndex() > 0) {
			String enfNombre = (String) cbEnfermedades.getSelectedItem();
			Enfermedad enf = Clinica.getInstance().buscarEnfermedadPorNombre(enfNombre);
			if (enf != null) {
				codigoEnfermedad = enf.getCodigoEnfermedad();
			}
		}

		Clinica.getInstance().registrarConsulta(c.getCodigoCita(), sintomas, diagnostico,
				tratSeleccionado.getCodigoTratamiento(), notas, codigoEnfermedad);

		JOptionPane.showMessageDialog(this, "Consulta registrada exitosamente.");
		dispose();
	}
}