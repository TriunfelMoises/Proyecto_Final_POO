package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JFormattedTextField;

import logico.Clinica;
import logico.Tratamiento;
import logico.Paciente;
import logico.Alergia;

public class regTratamientos extends JDialog {

	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();

	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JTextArea txtDescripcion;
	private JTextArea txtIndicaciones;
	private JSpinner spnDuracion;

	private JLabel lblPacienteInfo;
	private JTextArea txtAlergiasPaciente;
	private JButton btnVerAlergias;

	private Paciente pacienteActual;

	public regTratamientos() {
		setTitle("Registrar tratamiento");
		// un poco más alta para que todo se vea cómodo
		setBounds(100, 100, 730, 580);
		setLocationRelativeTo(null);
		setModal(true);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				Clinica.getInstance().recalcularContadorTratamientos();
			}
		});

		pacienteActual = null;

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(12, 12, 12, 12));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		Font labelFont = new Font("Tahoma", Font.PLAIN, 12);

		JLabel lblCodigo = new JLabel("Código:");
		lblCodigo.setFont(labelFont);
		lblCodigo.setBounds(20, 20, 80, 22);
		contentPanel.add(lblCodigo);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(labelFont);
		lblNombre.setBounds(20, 55, 80, 22);
		contentPanel.add(lblNombre);

		JLabel lblDescripcion = new JLabel("Descripción:");
		lblDescripcion.setFont(labelFont);
		lblDescripcion.setBounds(20, 90, 100, 22);
		contentPanel.add(lblDescripcion);

		JLabel lblIndicaciones = new JLabel("Indicaciones:");
		lblIndicaciones.setFont(labelFont);
		lblIndicaciones.setBounds(20, 190, 100, 22);
		contentPanel.add(lblIndicaciones);

		JLabel lblDuracion = new JLabel("Duración (días):");
		lblDuracion.setFont(labelFont);
		lblDuracion.setBounds(20, 290, 110, 22);
		contentPanel.add(lblDuracion);

		JLabel lblPaciente = new JLabel("Paciente:");
		lblPaciente.setFont(labelFont);
		lblPaciente.setBounds(20, 325, 100, 22);
		contentPanel.add(lblPaciente);

		JLabel lblAlergiasPac = new JLabel("Alergias del paciente:");
		lblAlergiasPac.setFont(labelFont);
		lblAlergiasPac.setBounds(20, 371, 150, 22);
		contentPanel.add(lblAlergiasPac);

		// Botón ver detalle de alergias al lado del label
		btnVerAlergias = new JButton("Ver detalle de alergias");
		btnVerAlergias.setBounds(140, 450, 161, 25);
		btnVerAlergias.addActionListener(e -> mostrarAlergiasEnDialogo());
		contentPanel.add(btnVerAlergias);

		txtCodigo = new JTextField();
		txtCodigo.setBounds(140, 20, 180, 22);
		txtCodigo.setEditable(false);
		contentPanel.add(txtCodigo);

		txtNombre = new JTextField();
		txtNombre.setBounds(140, 55, 520, 22);
		contentPanel.add(txtNombre);

		txtDescripcion = new JTextArea();
		txtDescripcion.setLineWrap(true);
		txtDescripcion.setWrapStyleWord(true);
		JScrollPane spDescripcion = new JScrollPane(txtDescripcion);
		spDescripcion.setBounds(140, 90, 520, 80);
		contentPanel.add(spDescripcion);

		txtIndicaciones = new JTextArea();
		txtIndicaciones.setLineWrap(true);
		txtIndicaciones.setWrapStyleWord(true);
		JScrollPane spIndicaciones = new JScrollPane(txtIndicaciones);
		spIndicaciones.setBounds(140, 190, 520, 80);
		contentPanel.add(spIndicaciones);

		spnDuracion = new JSpinner();
		spnDuracion.setModel(new SpinnerNumberModel(1, 1, 365, 1));
		spnDuracion.setBounds(140, 290, 80, 22);
		contentPanel.add(spnDuracion);

		lblPacienteInfo = new JLabel();
		lblPacienteInfo.setFont(labelFont);
		lblPacienteInfo.setBounds(140, 325, 520, 22);
		contentPanel.add(lblPacienteInfo);
		JScrollPane spAlergiasPaciente = new JScrollPane();
		spAlergiasPaciente.setBounds(140, 370, 520, 80);
		contentPanel.add(spAlergiasPaciente);
		
				txtAlergiasPaciente = new JTextArea();
				spAlergiasPaciente.setColumnHeaderView(txtAlergiasPaciente);
				txtAlergiasPaciente.setLineWrap(true);
				txtAlergiasPaciente.setWrapStyleWord(true);
				txtAlergiasPaciente.setEditable(false);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(e -> registrarTratamiento());
		buttonPane.add(btnRegistrar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(e -> {
			Clinica.getInstance().recalcularContadorTratamientos();
			dispose();
		});
		buttonPane.add(btnCancelar);

		inicializarCodigo();
		actualizarInfoPaciente();
	}

	public regTratamientos(Paciente paciente) {
		this();
		this.pacienteActual = paciente;
		actualizarInfoPaciente();
	}

	private void inicializarCodigo() {
		String codigo = Clinica.getInstance().generarCodigoTratamiento();
		txtCodigo.setText(codigo);
		txtCodigo.setEditable(false);
	}

	private void actualizarInfoPaciente() {
		if (pacienteActual == null) {
			lblPacienteInfo.setText("No hay paciente seleccionado.");
			txtAlergiasPaciente.setText("No hay paciente seleccionado.");
			btnVerAlergias.setEnabled(false);
			return;
		}

		btnVerAlergias.setEnabled(true);

		String info = pacienteActual.getNombre() + " " +
				pacienteActual.getApellido() +
				" - Cédula: " + pacienteActual.getCedula();
		lblPacienteInfo.setText(info);

		ArrayList<Alergia> alergias = pacienteActual.getAlergias();
		if (alergias == null || alergias.isEmpty()) {
			txtAlergiasPaciente.setText("El paciente no registra alergias.");
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append("El paciente presenta las siguientes alergias:\n");
			for (Alergia a : alergias) {
				sb.append("• ").append(a.getNombre());
				if (a.getTipo() != null && !a.getTipo().isEmpty()) {
					sb.append(" (tipo: ").append(a.getTipo()).append(")");
				}
				sb.append("\n");
			}
			txtAlergiasPaciente.setText(sb.toString());
		}
	}

	private void mostrarAlergiasEnDialogo() {
		if (pacienteActual == null) {
			JOptionPane.showMessageDialog(
					this,
					"No hay paciente seleccionado.",
					"Paciente no disponible",
					JOptionPane.WARNING_MESSAGE
			);
			return;
		}

		ArrayList<Alergia> alergiasSistema = Clinica.getInstance().getAlergias();
		if (alergiasSistema == null || alergiasSistema.isEmpty()) {
			JOptionPane.showMessageDialog(
					this,
					"Aún no hay alergias registradas en el sistema.",
					"Sin alergias",
					JOptionPane.INFORMATION_MESSAGE
			);
			return;
		}

		ArrayList<Alergia> alergiasPaciente = pacienteActual.getAlergias();

		StringBuilder sb = new StringBuilder();
		sb.append("Detalle de alergias registradas en el sistema:\n\n");

		for (Alergia a : alergiasSistema) {
			boolean tiene = (alergiasPaciente != null && alergiasPaciente.contains(a));

			sb.append(tiene ? "-> " : "   ");
			sb.append(a.getNombre());
			if (a.getTipo() != null && !a.getTipo().isEmpty()) {
				sb.append(" (").append(a.getTipo()).append(")");
			}
			sb.append("\n");
		}

		JTextArea area = new JTextArea(sb.toString());
		area.setEditable(false);
		area.setFont(new Font("Monospaced", Font.PLAIN, 13));
		area.setLineWrap(true);
		area.setWrapStyleWord(true);

		JScrollPane scroll = new JScrollPane(area);
		scroll.setPreferredSize(new java.awt.Dimension(460, 280));

		// <<< ESTA ES LA NOTA QUE SE HABÍA PERDIDO >>>
		Object[] contenido = {
				scroll,
				"\nNota: El símbolo \"->\" indica la(s) alergia(s) que presenta el paciente.\n" +
				"Si una alergia no posee este símbolo, significa que el paciente no la presenta."
		};

		JOptionPane.showMessageDialog(
				this,
				contenido,
				"Detalle de alergias",
				JOptionPane.INFORMATION_MESSAGE
		);
	}

	private void registrarTratamiento() {
		String codigo = txtCodigo.getText().trim();
		String nombre = txtNombre.getText().trim();
		String descripcion = txtDescripcion.getText().trim();
		String indicaciones = txtIndicaciones.getText().trim();
		int duracionDias = (int) spnDuracion.getValue();
		String duracion = duracionDias + " días";

		if (nombre.isEmpty() || descripcion.isEmpty() || indicaciones.isEmpty()) {
			JOptionPane.showMessageDialog(
					this,
					"Nombre, descripción e indicaciones son campos obligatorios.",
					"Datos incompletos",
					JOptionPane.WARNING_MESSAGE
			);
			return;
		}

		Tratamiento nuevo = new Tratamiento(
				codigo,
				nombre,
				descripcion,
				indicaciones,
				duracion
		);

		boolean registrado = Clinica.getInstance().agregarTratamiento(nuevo);

		if (registrado) {
			JOptionPane.showMessageDialog(
					this,
					"Tratamiento registrado correctamente.",
					"Éxito",
					JOptionPane.INFORMATION_MESSAGE
			);

			inicializarCodigo();
			txtNombre.setText("");
			txtDescripcion.setText("");
			txtIndicaciones.setText("");
			spnDuracion.setValue(1);
			txtNombre.requestFocus();
		} else {
			JOptionPane.showMessageDialog(
					this,
					"Ya existe un tratamiento con ese código.",
					"Error al registrar",
					JOptionPane.ERROR_MESSAGE
			);
		}
	}
}
