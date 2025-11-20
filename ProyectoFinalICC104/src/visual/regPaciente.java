package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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
import javax.swing.SpinnerNumberModel;

public class regPaciente extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtCedula;
	private JTextField txtTelefono;
	private ArrayList<Alergia> alegecitas;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			regPaciente dialog = new regPaciente();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public regPaciente() {
		setTitle("Registro de pacientes");
		setBounds(100, 100, 582, 591);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		setLocationRelativeTo(null);
		{
			JLabel lblNewLabel = new JLabel("C\u00F3digo");
			lblNewLabel.setBounds(466, 0, 69, 34);
			contentPanel.add(lblNewLabel);
		}
		
		txtCodigo = new JTextField();
		txtCodigo.setEditable(false);
		txtCodigo.setBounds(451, 28, 94, 26);
		contentPanel.add(txtCodigo);
		txtCodigo.setColumns(10);
		txtCodigo.setText(Clinica.getInstance().generarCodigoPaciente());
		
		JLabel lblNewLabel_1 = new JLabel("Nombre(s)");
		lblNewLabel_1.setBounds(15, 43, 94, 20);
		contentPanel.add(lblNewLabel_1);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(111, 40, 296, 26);
		contentPanel.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Apellido(s)");
		lblNewLabel_2.setBounds(15, 96, 94, 20);
		contentPanel.add(lblNewLabel_2);
		
		txtApellido = new JTextField();
		txtApellido.setBounds(111, 93, 296, 26);
		contentPanel.add(txtApellido);
		txtApellido.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("C\u00E9dula");
		lblNewLabel_3.setBounds(15, 139, 69, 20);
		contentPanel.add(lblNewLabel_3);
		
		txtCedula = new JTextField();
		txtCedula.setBounds(111, 136, 196, 26);
		contentPanel.add(txtCedula);
		txtCedula.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Sexo");
		lblNewLabel_4.setBounds(338, 239, 69, 20);
		contentPanel.add(lblNewLabel_4);
		
		JRadioButton rdbtnHombre = new JRadioButton("M");
		rdbtnHombre.setSelected(true);
		rdbtnHombre.setBounds(394, 235, 51, 29);
		contentPanel.add(rdbtnHombre);
		
		JRadioButton rdbtnMujer = new JRadioButton("F");
		rdbtnMujer.setBounds(466, 235, 51, 29);
		contentPanel.add(rdbtnMujer);
		
		rdbtnHombre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rdbtnMujer.setSelected(false);
			}
		});
		rdbtnMujer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rdbtnHombre.setSelected(false);
			}
		});
		
		JLabel lblNewLabel_5 = new JLabel("Tel\u00E9fono");
		lblNewLabel_5.setBounds(15, 239, 69, 20);
		contentPanel.add(lblNewLabel_5);
		
		txtTelefono = new JTextField();
		txtTelefono.setBounds(111, 236, 196, 26);
		contentPanel.add(txtTelefono);
		txtTelefono.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("Fecha de nacimiento");
		lblNewLabel_6.setBounds(240, 188, 154, 20);
		contentPanel.add(lblNewLabel_6);
		
		JSpinner spnFechaNacimiento = new JSpinner();
		spnFechaNacimiento.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_YEAR));
		JSpinner.DateEditor editor = new JSpinner.DateEditor(spnFechaNacimiento, "dd/MM/yyyy");
		spnFechaNacimiento.setEditor(editor);
		spnFechaNacimiento.setBounds(409, 185, 108, 26);
		contentPanel.add(spnFechaNacimiento);
		
		JLabel lblNewLabel_7 = new JLabel("Direcci\u00F3n");
		lblNewLabel_7.setBounds(15, 285, 69, 20);
		contentPanel.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("Tipo de sangre");
		lblNewLabel_8.setBounds(322, 142, 108, 20);
		contentPanel.add(lblNewLabel_8);
		
		JComboBox<String> cbxTipoSangre = new JComboBox<String>();
		cbxTipoSangre.setModel(new DefaultComboBoxModel(new String[] {"<Tipo>", "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"}));
		cbxTipoSangre.setBounds(445, 143, 72, 26);
		contentPanel.add(cbxTipoSangre);
		
		JLabel lblNewLabel_9 = new JLabel("Fecha");
		lblNewLabel_9.setBounds(15, 460, 69, 20);
		contentPanel.add(lblNewLabel_9);
		
		JSpinner spnFechaActual = new JSpinner();
		spnFechaActual.setEnabled(false);
		spnFechaActual.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_YEAR));
		JSpinner.DateEditor editorActual = new JSpinner.DateEditor(spnFechaActual, "dd/MM/yyyy");
		spnFechaActual.setEditor(editorActual);
		spnFechaActual.setBounds(111, 457, 94, 26);
		contentPanel.add(spnFechaActual);
		
		JLabel lblNewLabel_10 = new JLabel("\u00BFUsted padece de alguna alergia?");
		lblNewLabel_10.setBounds(15, 406, 263, 20);
		contentPanel.add(lblNewLabel_10);
		
		JCheckBox chckbxAlergias = new JCheckBox("Si, padezco de alergias");
		chckbxAlergias.setBounds(297, 402, 202, 29);
		contentPanel.add(chckbxAlergias);
		
		JTextArea txtdireccion = new JTextArea();
		txtdireccion.setBounds(112, 295, 405, 84);
		contentPanel.add(txtdireccion);
		
		JLabel lblNewLabel_11 = new JLabel("Peso(lb)");
		lblNewLabel_11.setBounds(15, 188, 69, 20);
		contentPanel.add(lblNewLabel_11);
		
		JSpinner spnPeso = new JSpinner();
		spnPeso.setModel(new SpinnerNumberModel(new Float(1), new Float(1), null, new Float(1)));
		spnPeso.setBounds(111, 185, 94, 26);
		contentPanel.add(spnPeso);
		
		JLabel lblNewLabel_12 = new JLabel("Estatura(cm)");
		lblNewLabel_12.setBounds(451, 72, 99, 20);
		contentPanel.add(lblNewLabel_12);
		
		JSpinner spnEstatura = new JSpinner();
		spnEstatura.setModel(new SpinnerNumberModel(new Float(1), new Float(1), null, new Float(1)));
		spnEstatura.setBounds(451, 93, 94, 26);
		contentPanel.add(spnEstatura);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Continuar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						char sexo;
						
						if (txtApellido.getText()=="" || txtCedula.getText()=="" || txtdireccion.getText()==""|| txtNombre.getText()==""|| txtTelefono.getText()==""|| cbxTipoSangre.getSelectedIndex()==0) {
				            JOptionPane.showMessageDialog(null, "Complete todos los campos correctamente", "Error", JOptionPane.ERROR_MESSAGE);
				            return;
						}
						if (!txtTelefono.getText().matches("\\d+")|| !txtCedula.getText().matches("\\d+")) {
				            JOptionPane.showMessageDialog(null, "El telefono y la cédula solo pueden contener valores numéricos", "Error", JOptionPane.ERROR_MESSAGE);
						    return;
						}
						if (Clinica.getInstance().verificarSiEsPaciente(txtCedula.getText())) {
				            JOptionPane.showMessageDialog(null, "Esta cédula ya se encuentra registrada en un paciente", "Error", JOptionPane.ERROR_MESSAGE);
						}
						if (rdbtnHombre.isSelected()) {
							sexo = 'M';
						}
						else {
							sexo = 'F';
						}
						if (chckbxAlergias.isSelected()) {
							TomaAlergias alergenoS = new TomaAlergias();
							alergenoS.setModal(true);
							alergenoS.setVisible(true);
							alegecitas=alergenoS.AlergiasSeleccionadas();
							if (alegecitas.isEmpty()) {
								return;
							}
						}
						Date fechaActDate = (Date) spnFechaActual.getValue();
						LocalDate fechaActual = fechaActDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						Date fechaNacDate = (Date) spnFechaNacimiento.getValue();
						LocalDate fechaNac = fechaNacDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						Date hoyDate = new Date();
						Paciente elqueva = new Paciente(txtCedula.getText(), txtNombre.getText(), txtApellido.getText(), txtTelefono.getText(), txtdireccion.getText(), fechaNac, sexo, txtCodigo.getText(), cbxTipoSangre.getSelectedItem().toString(), fechaActual, ((Number) spnPeso.getValue()).floatValue(), ((Number) spnEstatura.getValue()).floatValue());
						elqueva.setAlergias(alegecitas);
						Clinica.getInstance().registrarPaciente(elqueva);
			            JOptionPane.showMessageDialog(null, "Registro Satisfactorio", "Información", JOptionPane.INFORMATION_MESSAGE);
			            txtNombre.setText("");
			            txtApellido.setText("");
			            txtCedula.setText("");
			            txtCodigo.setText(Clinica.getInstance().generarCodigoPaciente());
			            txtdireccion.setText("");
			            txtTelefono.setText("");
			            rdbtnHombre.setSelected(true);
			            rdbtnMujer.setSelected(false);
			            chckbxAlergias.setSelected(false);
			            spnFechaActual.setValue(hoyDate);
			            spnFechaNacimiento.setValue(hoyDate);
			            cbxTipoSangre.setSelectedIndex(0);
			            spnEstatura.setValue(1);
			            spnPeso.setValue(1);
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
}
