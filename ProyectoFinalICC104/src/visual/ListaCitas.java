package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import logico.Clinica;
import logico.Cita;
import logico.Doctor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.ArrayList;

public class ListaCitas extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable tableCitas;
	private DefaultTableModel modelo;
	private JComboBox<String> cbxEstado;
	private JComboBox<String> cbxDoctor;
	private JTextField txtBuscarPaciente;
	private JButton btnVerDetalles;
	private JButton btnRealizarConsulta;
	private JButton btnCancelarCita;

	public ListaCitas() {
		setTitle("Lista de Citas");
		setBounds(100, 100, 900, 600);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		// PANEL FILTROS
		JPanel panelFiltros = new JPanel();
		contentPanel.add(panelFiltros, BorderLayout.NORTH);
		panelFiltros.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel lblEstado = new JLabel("Estado:");
		panelFiltros.add(lblEstado);

		cbxEstado = new JComboBox<String>();
		cbxEstado.addItem("Todas");
		cbxEstado.addItem("Pendientes");
		cbxEstado.addItem("Completadas");
		cbxEstado.addItem("Canceladas");
		cbxEstado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarCitas();
			}
		});
		panelFiltros.add(cbxEstado);

		JLabel lblDoctor = new JLabel("  Doctor:");
		panelFiltros.add(lblDoctor);

		cbxDoctor = new JComboBox<String>();
		cbxDoctor.addItem("Todos");
		cbxDoctor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarCitas();
			}
		});
		panelFiltros.add(cbxDoctor);

		JLabel lblPaciente = new JLabel("  Paciente:");
		panelFiltros.add(lblPaciente);

		txtBuscarPaciente = new JTextField();
		txtBuscarPaciente.setColumns(15);
		panelFiltros.add(txtBuscarPaciente);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarCitas();
			}
		});
		panelFiltros.add(btnBuscar);

		// TABLA
		JScrollPane scrollPane = new JScrollPane();
		contentPanel.add(scrollPane, BorderLayout.CENTER);

		String[] columnas = { "Código", "Fecha", "Hora", "Paciente", "Doctor", "Motivo", "Estado" };
		modelo = new DefaultTableModel(columnas, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tableCitas = new JTable(modelo);
		scrollPane.setViewportView(tableCitas);

		// BOTONES
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		btnVerDetalles = new JButton("Ver Detalles");
		btnVerDetalles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				verDetalles();
			}
		});
		buttonPane.add(btnVerDetalles);

		btnRealizarConsulta = new JButton("Realizar Consulta");
		btnRealizarConsulta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				realizarConsulta();
			}
		});
		buttonPane.add(btnRealizarConsulta);

		btnCancelarCita = new JButton("Cancelar Cita");
		btnCancelarCita.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelarCita();
			}
		});
		buttonPane.add(btnCancelarCita);

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		buttonPane.add(btnCerrar);

		cargarDoctores();
		cargarCitas();
	}

	private void cargarDoctores() {
		cbxDoctor.removeAllItems();
		cbxDoctor.addItem("Todos");

		ArrayList<Doctor> doctores = Clinica.getInstance().listarDoctoresActivos();
		for (Doctor doctor : doctores) {
			cbxDoctor.addItem(doctor.getNombre() + " " + doctor.getApellido());
		}
	}

	private void cargarCitas() {
		modelo.setRowCount(0);
		ArrayList<Cita> citas = Clinica.getInstance().getCitas();

		// Verificar que los ComboBox no estén vacíos
		if (cbxEstado.getSelectedItem() == null || cbxDoctor.getSelectedItem() == null) {
			return; // Salir si no hay datos cargados
		}

		String estadoFiltro = (String) cbxEstado.getSelectedItem();
		String doctorFiltro = (String) cbxDoctor.getSelectedItem();
		String pacienteFiltro = txtBuscarPaciente.getText().trim().toLowerCase();

		for (Cita cita : citas) {
			boolean cumpleFiltros = true;

			// Filtro por estado
			if (!estadoFiltro.equals("Todas")) {
				String estadoBuscado = "";

				if (estadoFiltro.equals("Pendientes")) {
					estadoBuscado = "Pendiente";
				} else if (estadoFiltro.equals("Completadas")) {
					estadoBuscado = "Completada";
				} else if (estadoFiltro.equals("Canceladas")) {
					estadoBuscado = "Cancelada";
				}

				if (!cita.getEstadoCita().equals(estadoBuscado)) {
					cumpleFiltros = false;
				}
			}

			// Filtro por doctor
			if (!doctorFiltro.equals("Todos")) {
				String nombreCompletoDoctor = cita.getDoctor().getNombre() + " " + cita.getDoctor().getApellido();
				if (!nombreCompletoDoctor.equals(doctorFiltro)) {
					cumpleFiltros = false;
				}
			}

			// Filtro por paciente
			if (!pacienteFiltro.isEmpty()) {
				String nombreCompletoPaciente = cita.getPaciente().getNombre() + " " + cita.getPaciente().getApellido();
				if (!nombreCompletoPaciente.toLowerCase().contains(pacienteFiltro)) {
					cumpleFiltros = false;
				}
			}

			if (cumpleFiltros) {
				Object[] fila = { cita.getCodigoCita(), cita.getFechaCita().toString(), cita.getHoraCita().toString(),
						cita.getPaciente().getNombre() + " " + cita.getPaciente().getApellido(),
						cita.getDoctor().getNombre() + " " + cita.getDoctor().getApellido(), cita.getMotivoCita(),
						cita.getEstadoCita() };
				modelo.addRow(fila);
			}
		}
	}

	private void verDetalles() {
		int filaSeleccionada = tableCitas.getSelectedRow();
		if (filaSeleccionada == -1) {
			JOptionPane.showMessageDialog(this, "Seleccione una cita", "Advertencia", JOptionPane.WARNING_MESSAGE);
			return;
		}

		String codigoCita = (String) modelo.getValueAt(filaSeleccionada, 0);
		Cita cita = Clinica.getInstance().buscarCita(codigoCita);

		DetalleCita ventana = new DetalleCita(cita);
		ventana.setModal(true);
		ventana.setVisible(true);
	}

	private void realizarConsulta() {
		int filaSeleccionada = tableCitas.getSelectedRow();
		if (filaSeleccionada == -1) {
			JOptionPane.showMessageDialog(this, "Seleccione una cita", "Advertencia", JOptionPane.WARNING_MESSAGE);
			return;
		}

		String codigoCita = (String) modelo.getValueAt(filaSeleccionada, 0);
		Cita cita = Clinica.getInstance().buscarCita(codigoCita);

		if (!cita.estaPendiente()) {
			JOptionPane.showMessageDialog(this, "Solo se pueden realizar consultas a citas pendientes", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Aquí abrirías tu ventana RegistroConsulta
		JOptionPane.showMessageDialog(this, "Abrir ventana RegistroConsulta para la cita: " + codigoCita, "Info",
				JOptionPane.INFORMATION_MESSAGE);
		cargarCitas();
	}

	private void cancelarCita() {
		int filaSeleccionada = tableCitas.getSelectedRow();
		if (filaSeleccionada == -1) {
			JOptionPane.showMessageDialog(this, "Seleccione una cita", "Advertencia", JOptionPane.WARNING_MESSAGE);
			return;
		}

		String codigoCita = (String) modelo.getValueAt(filaSeleccionada, 0);

		int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de cancelar esta cita?", "Confirmar",
				JOptionPane.YES_NO_OPTION);

		if (confirmacion == JOptionPane.YES_OPTION) {
			boolean cancelada = Clinica.getInstance().cancelarCita(codigoCita);

			if (cancelada) {
				JOptionPane.showMessageDialog(this, "Cita cancelada exitosamente", "Éxito",
						JOptionPane.INFORMATION_MESSAGE);
				cargarCitas();
			} else {
				JOptionPane.showMessageDialog(this, "No se pudo cancelar la cita", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}