package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import logico.Clinica;
import logico.Doctor;
import logico.Paciente;

public class listPacientes extends JDialog {
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private static DefaultTableModel model;
	private static Object[] row;
	private JTextField txtBuscar;
	private JButton btnModificar;
	private Paciente pacienteSeleccionado = null;

	public static void main(String[] args) {
		try {
			listPacientes dialog = new listPacientes();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public listPacientes() {
		setTitle("Listado de pacientes");
		setBounds(100, 100, 748, 489);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		// ========== PANEL DE BÚSQUEDA ==========
		JPanel panelBusqueda = new JPanel();
		panelBusqueda.setBorder(new TitledBorder(null, "Búsqueda", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPanel.add(panelBusqueda, BorderLayout.NORTH);
		panelBusqueda.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JLabel lblBuscar = new JLabel("Buscar por nombre o cédula:");
		panelBusqueda.add(lblBuscar);
		
		txtBuscar = new JTextField();
		txtBuscar.setColumns(30);
		panelBusqueda.add(txtBuscar);
		
		// Búsqueda en tiempo real
		txtBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				buscarPacientes(txtBuscar.getText());
			}
		});
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarPacientes(txtBuscar.getText());
			}
		});
		panelBusqueda.add(btnBuscar);
		
		JButton btnMostrarTodos = new JButton("Mostrar Todos");
		btnMostrarTodos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtBuscar.setText("");
				cargarPacientes();
			}
		});
		panelBusqueda.add(btnMostrarTodos);
		
		// ========== PANEL DE TABLA ==========
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "Pacientes Registrados", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(new BorderLayout(0, 0));
			{
				JScrollPane scrollPane = new JScrollPane();
				panel.add(scrollPane, BorderLayout.CENTER);
				{
					model = new DefaultTableModel() {
						// Hacer que la tabla no sea editable
						@Override
						public boolean isCellEditable(int row, int column) {
							return false;
						}
					};
					table = new JTable();
					table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					String[] headers = {"Código", "Cédula", "Nombre Completo", "Sexo", "Sangre", "Teléfono"};
					model.setColumnIdentifiers(headers);
					table.setModel(model);
					scrollPane.setViewportView(table);
					
					// Ajustar anchos de columnas
					table.getColumnModel().getColumn(0).setPreferredWidth(80);  
					table.getColumnModel().getColumn(1).setPreferredWidth(110); 
					table.getColumnModel().getColumn(2).setPreferredWidth(200); 
					table.getColumnModel().getColumn(3).setPreferredWidth(10); 
					table.getColumnModel().getColumn(4).setPreferredWidth(50);  
					table.getColumnModel().getColumn(5).setPreferredWidth(70); 
					
					// Listener para selección
					table.getSelectionModel().addListSelectionListener(e -> {
						if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
							int index = table.getSelectedRow();
							String codigo = model.getValueAt(index, 0).toString();
							pacienteSeleccionado = Clinica.getInstance().buscarPacientePorCodigo(codigo);
							
							// Habilitar botones
							btnModificar.setEnabled(true);
							
						}
					});
				}
			}
		}
		
		// ========== PANEL DE BOTONES ==========
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
			{
				btnModificar = new JButton("Modificar");
				btnModificar.setEnabled(false);
				btnModificar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (pacienteSeleccionado != null) {
							regPaciente dialog = new regPaciente(pacienteSeleccionado);
							dialog.setModal(true);
							dialog.setVisible(true);
							cargarPacientes(); // Recargar tabla después de modificar
							pacienteSeleccionado = null;
							btnModificar.setEnabled(false);
						} else {
							JOptionPane.showMessageDialog(listPacientes.this, 
								"Seleccione un paciente de la lista", 
								"Aviso", 
								JOptionPane.WARNING_MESSAGE);
						}
					}
				});
				buttonPane.add(btnModificar);
			}
			
			{
				JButton btnCerrar = new JButton("Cerrar");
				btnCerrar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btnCerrar.setActionCommand("Cancel");
				buttonPane.add(btnCerrar);
			}
		}
		
		cargarPacientes();
	}
	
	// ========== MÉTODO PARA CARGAR TODOS LOS DOCTORES ==========
	public static void cargarPacientes() {
		model.setRowCount(0);
		row = new Object[model.getColumnCount()];
		for (Paciente elpac : Clinica.getInstance().getPacientes()) {
			row[0] = elpac.getCodigoPaciente();
			row[1] = elpac.getCedula();
			row[2] = elpac.getNombre() + " " + elpac.getApellido();
			row[3] = elpac.getSexo();
			row[4] = elpac.getTipoSangre();
			row[5] = elpac.getTelefono();
			model.addRow(row);
		}
	}
	
	// ========== MÉTODO PARA BUSCAR DOCTORES ==========
	private void buscarPacientes(String criterio) {
		model.setRowCount(0);
		row = new Object[model.getColumnCount()];
		
		if (criterio.trim().isEmpty()) {
			cargarPacientes();
			return;
		}
		
		String criterioLower = criterio.toLowerCase().trim();
		
		for (Paciente elEnfermo : Clinica.getInstance().getPacientes()) {
			String nombreCompleto = (elEnfermo.getNombre() + " " + elEnfermo.getApellido()).toLowerCase();
			String cedula = elEnfermo.getCedula().toLowerCase();
			
			if (nombreCompleto.contains(criterioLower) || cedula.contains(criterioLower)) {
				row[0] = elEnfermo.getCodigoPaciente();
				row[1] = elEnfermo.getCedula();
				row[2] = elEnfermo.getNombre() + " " + elEnfermo.getApellido();
				row[3] = elEnfermo.getSexo();
				row[4] = elEnfermo.getTipoSangre();
				row[5] = elEnfermo.getTelefono();
				model.addRow(row);
			}
		}
		
		if (model.getRowCount() == 0) {
			JOptionPane.showMessageDialog(this, 
				"No se encontraron pacientes con ese criterio de búsqueda", 
				"Sin resultados", 
				JOptionPane.INFORMATION_MESSAGE);
		}
	}
}