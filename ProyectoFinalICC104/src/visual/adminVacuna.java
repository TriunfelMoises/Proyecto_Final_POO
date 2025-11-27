package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logico.Clinica;
import logico.Paciente;
import logico.RegistroVacuna;
import logico.Vacuna;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.Calendar;
import javax.swing.SpinnerNumberModel;
import javax.swing.DefaultComboBoxModel;

public class adminVacuna extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtEnfermera;
	private JButton btnVerAlergias = new JButton("Ver alergias");;
	private JTextField txtCedula;
	private JTextField txtSangre;
	private JTextField txtLote;
	private JTextField txtEnfermedad;
	private Vacuna laQuePuncha = new Vacuna();
	private Paciente elVacunado = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			adminVacuna dialog = new adminVacuna();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public adminVacuna() {
		setTitle("Administrar vacuna");
		setBounds(100, 100, 648, 499);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		btnVerAlergias.setEnabled(false);
		setLocationRelativeTo(null);

		JComboBox<String> cbxPaciente = new JComboBox<String>();
		cbxPaciente.setModel(new DefaultComboBoxModel(new String[] {"<Seleccione>"}));
		for (Paciente recorriendo : Clinica.getInstance().getPacientes()) {
			cbxPaciente.addItem(recorriendo.getCodigoPaciente()+": "+recorriendo.getNombre()+" "+ recorriendo.getApellido());
		}
		
		cbxPaciente.setBounds(15, 53, 219, 26);
		contentPanel.add(cbxPaciente);
		
		JLabel lblNewLabel = new JLabel("Seleccione paciente");
		lblNewLabel.setBounds(15, 27, 155, 20);
		contentPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Seleccione vacuna");
		lblNewLabel_1.setBounds(15, 168, 206, 20);
		contentPanel.add(lblNewLabel_1);
		
		JComboBox<String> cbxVacuna = new JComboBox<String>();
		cbxVacuna.setModel(new DefaultComboBoxModel(new String[] {"<Seleccione>"}));
	    for (Vacuna recorriendo : Clinica.getInstance().getVacunas()) {
	    	cbxVacuna.addItem(recorriendo.getCodigoVacuna() + ": " +recorriendo.getNombre());
	    }
		cbxVacuna.setBounds(15, 192, 219, 26);
		contentPanel.add(cbxVacuna);
		
		JLabel lblNewLabel_2 = new JLabel("Indique quien est\u00E1 administrando esta dosis");
		lblNewLabel_2.setBounds(15, 316, 317, 20);
		contentPanel.add(lblNewLabel_2);
		
		txtEnfermera = new JTextField();
		txtEnfermera.setBounds(15, 339, 252, 26);
		contentPanel.add(txtEnfermera);
		txtEnfermera.setColumns(10);
		
		JButton btnNewButton = new JButton("Registrar paciente");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				regPaciente anadiPaciente = new regPaciente(null);
				anadiPaciente.setModal(true);
				anadiPaciente.setVisible(true);
				refrescarComboPacientes(cbxPaciente);
			}
		});
		btnNewButton.setBounds(430, 52, 181, 29);
		contentPanel.add(btnNewButton);
		
		btnVerAlergias.setBounds(245, 52, 181, 29);
		contentPanel.add(btnVerAlergias);
		
		JLabel lblNewLabel_3 = new JLabel("Cedula");
		lblNewLabel_3.setBounds(15, 95, 69, 20);
		contentPanel.add(lblNewLabel_3);
		
		txtCedula = new JTextField();
		txtCedula.setEnabled(false);
		txtCedula.setBounds(15, 116, 188, 26);
		contentPanel.add(txtCedula);
		txtCedula.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Sangre");
		lblNewLabel_4.setBounds(236, 95, 69, 20);
		contentPanel.add(lblNewLabel_4);
		
		txtSangre = new JTextField();
		txtSangre.setEnabled(false);
		txtSangre.setBounds(236, 116, 51, 26);
		contentPanel.add(txtSangre);
		txtSangre.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("Peso(lb)");
		lblNewLabel_5.setBounds(333, 95, 69, 20);
		contentPanel.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Estatura(cm)");
		lblNewLabel_6.setBounds(451, 97, 90, 20);
		contentPanel.add(lblNewLabel_6);
		
		JButton btnNewButton_1 = new JButton("Registrar vacuna");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				regVacuna vacun = new regVacuna();
				vacun.setModal(true);
				vacun.setVisible(true);
				refrescarComboVacunas(cbxVacuna);
			}
		});
		btnNewButton_1.setBounds(249, 191, 177, 29);
		contentPanel.add(btnNewButton_1);
		
		JLabel lblNewLabel_7 = new JLabel("Lote");
		lblNewLabel_7.setBounds(15, 237, 69, 20);
		contentPanel.add(lblNewLabel_7);
		
		txtLote = new JTextField();
		txtLote.setEnabled(false);
		txtLote.setColumns(10);
		txtLote.setBounds(15, 258, 188, 26);
		contentPanel.add(txtLote);
		
		txtEnfermedad = new JTextField();
		txtEnfermedad.setEnabled(false);
		txtEnfermedad.setColumns(10);
		txtEnfermedad.setBounds(316, 258, 95, 26);
		contentPanel.add(txtEnfermedad);
		
		JLabel lblCantidad = new JLabel("Cantidad");
		lblCantidad.setBounds(236, 237, 69, 20);
		contentPanel.add(lblCantidad);
		
		JLabel lblEnfermedad = new JLabel("Enfermedad");
		lblEnfermedad.setBounds(316, 237, 93, 20);
		contentPanel.add(lblEnfermedad);
		
		JLabel lblVencimiento = new JLabel("Vencimiento");
		lblVencimiento.setBounds(451, 237, 90, 20);
		contentPanel.add(lblVencimiento);
		
		JLabel lblNewLabel_8 = new JLabel("Fecha");
		lblNewLabel_8.setBounds(494, 316, 69, 20);
		contentPanel.add(lblNewLabel_8);
		
		JSpinner spnFecha = new JSpinner();
		spnFecha.setEnabled(false);
		spnFecha.setModel(new SpinnerDateModel(new Date(), null, new Date(), Calendar.DAY_OF_YEAR));
		JSpinner.DateEditor editor = new JSpinner.DateEditor(spnFecha, "dd/MM/yyyy");
		spnFecha.setEditor(editor);		
		spnFecha.setBounds(451, 339, 112, 26);
		contentPanel.add(spnFecha);
		
		JSpinner spnPeso = new JSpinner();
		spnPeso.setModel(new SpinnerNumberModel(new Float(0), null, null, new Float(1)));
		spnPeso.setEnabled(false);
		spnPeso.setBounds(316, 116, 86, 26);
		contentPanel.add(spnPeso);
		
		JSpinner spnEstatura = new JSpinner();
		spnEstatura.setModel(new SpinnerNumberModel(new Float(0), null, null, new Float(1)));
		spnEstatura.setEnabled(false);
		spnEstatura.setBounds(430, 116, 121, 26);
		contentPanel.add(spnEstatura);
		
		JSpinner spnVencimiento = new JSpinner();
		spnVencimiento.setEnabled(false);
		spnVencimiento.setModel(new SpinnerDateModel(new Date(), null, new Date(), Calendar.DAY_OF_YEAR));
		JSpinner.DateEditor editora = new JSpinner.DateEditor(spnVencimiento, "dd/MM/yyyy");
		spnVencimiento.setEditor(editora);			
		spnVencimiento.setBounds(430, 258, 121, 26);
		contentPanel.add(spnVencimiento);
		
		JSpinner spnCantidad = new JSpinner();
		spnCantidad.setEnabled(false);
		spnCantidad.setBounds(236, 258, 51, 26);
		contentPanel.add(spnCantidad);
		cbxPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        Object seleccionado = cbxPaciente.getSelectedItem();
		        if (seleccionado == null) {
		            return; 
		        }
				String codPaciente = cbxPaciente.getSelectedItem().toString();
				if (cbxPaciente.getSelectedIndex()!=0) {
					String[] buscandoelcodigo = codPaciente.split(":");
					codPaciente = buscandoelcodigo[0].trim();
					elVacunado = Clinica.getInstance().buscarPacientePorCodigo(codPaciente);
					txtCedula.setText(elVacunado.getCedula());
					spnEstatura.setValue(elVacunado.getEstatura());
					spnPeso.setValue(elVacunado.getPeso());
					txtSangre.setText(elVacunado.getTipoSangre());
					btnVerAlergias.setEnabled(true);
				}
			}
		});
		cbxVacuna.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		        Object seleccionado2 = cbxVacuna.getSelectedItem();
		        if (seleccionado2 == null) {
		            return; 
		        }
				String codVacuna = cbxVacuna.getSelectedItem().toString();
				if (cbxVacuna.getSelectedIndex()!=0) {
					String[] buscandoelcodigo = codVacuna.split(":");
					codVacuna = buscandoelcodigo[0].trim();
					laQuePuncha = Clinica.getInstance().buscarVacunaPorCodigo(codVacuna);
					txtEnfermedad.setText(laQuePuncha.getEnfermedad());
					txtLote.setText(laQuePuncha.getNumeroLote());
					spnCantidad.setValue(laQuePuncha.getCantidad());
					spnVencimiento.setValue(laQuePuncha.getFechaCaducidad());
				}
			}
		});
		btnVerAlergias.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				verAlergias viendo = new verAlergias(elVacunado);
				viendo.setModal(true);
				viendo.setVisible(true);
			}
		});
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Administrar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (cbxPaciente.getSelectedIndex()==0||cbxVacuna.getSelectedIndex()==0||txtEnfermera.getText().isEmpty()) {
				            JOptionPane.showMessageDialog(null, "Complete todos los campos correctamente", "Error", JOptionPane.ERROR_MESSAGE);
				            return;
						}
						Date fechaSeleccionada = (Date) spnFecha.getValue();
						LocalDate fecha = fechaSeleccionada.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();						
						RegistroVacuna termin = new RegistroVacuna(laQuePuncha, fecha, txtLote.getText(), txtEnfermera.getText());
						Clinica.getInstance().registrarVacunaPaciente(elVacunado, termin);
			            JOptionPane.showMessageDialog(null, "Registro Satisfactorio", "Información", JOptionPane.INFORMATION_MESSAGE);
			            spnCantidad.setValue(0);
			            spnEstatura.setValue(0);
			            spnPeso.setValue(0);
			            cbxPaciente.setSelectedIndex(0);
			            cbxVacuna.setSelectedIndex(0);
			            txtCedula.setText("");
			            txtEnfermedad.setText("");
			            txtEnfermera.setText("");
			            txtLote.setText("");
			            txtSangre.setText("");
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
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
	private void refrescarComboPacientes(JComboBox<String> cbxPaciente) {
	    cbxPaciente.removeAllItems();
	    cbxPaciente.addItem("<Seleccione>");
	    for (Paciente recorriendo : Clinica.getInstance().getPacientes()) {
	        cbxPaciente.addItem(
	            recorriendo.getCodigoPaciente() + ": " +recorriendo.getNombre() + " " +recorriendo.getApellido());
	    }
	}
	
	private void refrescarComboVacunas(JComboBox<String> cbxVacuna) {
		cbxVacuna.removeAllItems(); 
		cbxVacuna.addItem("<Seleccione>");
	    for (Vacuna recorriendo : Clinica.getInstance().getVacunas()) {
	    	cbxVacuna.addItem(recorriendo.getCodigoVacuna() + ": " +recorriendo.getNombre());
	    }
	}
}


