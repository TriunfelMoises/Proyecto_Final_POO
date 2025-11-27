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
import logico.Vacuna;

public class listVacuna extends JDialog {
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private static DefaultTableModel model;
	private static Object[] row;
	private JTextField txtBuscar;
	private JButton btnModificar;
	private Vacuna vacunaVer = null;

	public static void main(String[] args) {
		try {
			listVacuna dialog = new listVacuna();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public listVacuna() {
		setTitle("Listado de vacunas");
		setBounds(100, 100, 833, 489);
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
		
		JLabel lblBuscar = new JLabel("Buscar por nombre, lote o laboratorio:");
		panelBusqueda.add(lblBuscar);
		
		txtBuscar = new JTextField();
		txtBuscar.setColumns(30);
		panelBusqueda.add(txtBuscar);
		
		// Búsqueda en tiempo real
		txtBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				buscarVacunas(txtBuscar.getText());
			}
		});
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarVacunas(txtBuscar.getText());
			}
		});
		panelBusqueda.add(btnBuscar);
		
		JButton btnMostrarTodos = new JButton("Mostrar Todos");
		btnMostrarTodos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtBuscar.setText("");
				cargarVacunas();
			}
		});
		panelBusqueda.add(btnMostrarTodos);
		
		// ========== PANEL DE TABLA ==========
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "Vacunas Registradas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
					String[] headers = {"Código", "Lote", "Nombre", "Cantidad", "Enfermedad", "Laboratorio"};
					model.setColumnIdentifiers(headers);
					table.setModel(model);
					scrollPane.setViewportView(table);
					
					// Ajustar anchos de columnas
					table.getColumnModel().getColumn(0).setPreferredWidth(50);  
					table.getColumnModel().getColumn(1).setPreferredWidth(110); 
					table.getColumnModel().getColumn(2).setPreferredWidth(200); 
					table.getColumnModel().getColumn(3).setPreferredWidth(10); 
					table.getColumnModel().getColumn(4).setPreferredWidth(87);  
					table.getColumnModel().getColumn(5).setPreferredWidth(87); 
					
					// Listener para selección
					table.getSelectionModel().addListSelectionListener(e -> {
						if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
							int index = table.getSelectedRow();
							String codigo = model.getValueAt(index, 0).toString();
							vacunaVer = Clinica.getInstance().buscarVacunaPorCodigo(codigo);
							
							
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
		
		cargarVacunas();
	}
	
	public static void cargarVacunas() {
		model.setRowCount(0);
		row = new Object[model.getColumnCount()];
		for (Vacuna laDolorosa : Clinica.getInstance().getVacunas()) {
			row[0] = laDolorosa.getCodigoVacuna();
			row[1] = laDolorosa.getNumeroLote();
			row[2] = laDolorosa.getNombre();
			row[3] = laDolorosa.getCantidad();
			row[4] = laDolorosa.getEnfermedad();
			row[5] = laDolorosa.getLaboratio();
			model.addRow(row);
		}
	}
	
	// ========== MÉTODO PARA BUSCAR DOCTORES ==========
	private void buscarVacunas(String criterio) {
		model.setRowCount(0);
		row = new Object[model.getColumnCount()];
		
		if (criterio.trim().isEmpty()) {
			cargarVacunas();
			return;
		}
		
		String criterioLower = criterio.toLowerCase().trim();
		
		for (Vacuna laDolorosa : Clinica.getInstance().getVacunas()) {
			String nombreCompleto = laDolorosa.getNombre();
			String lote = laDolorosa.getNumeroLote().toLowerCase();
			String labora = laDolorosa.getLaboratio().toLowerCase();
			
			if (nombreCompleto.contains(criterioLower) || lote.contains(criterioLower) || labora.contains(criterioLower)) {
				row[0] = laDolorosa.getCodigoVacuna();
				row[1] = laDolorosa.getNumeroLote();
				row[2] = laDolorosa.getNombre();
				row[3] = laDolorosa.getCantidad();
				row[4] = laDolorosa.getEnfermedad();
				row[5] = laDolorosa.getLaboratio();
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