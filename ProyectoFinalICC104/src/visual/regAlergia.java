package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logico.Alergia;
import logico.Clinica;
import logico.Doctor;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class regAlergia extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtNombre;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			regAlergia dialog = new regAlergia();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public regAlergia() {
		setTitle("Registro de alergia");
		setBounds(100, 100, 262, 278);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JComboBox<String> cbxTipo = new JComboBox();
		cbxTipo.setModel(new DefaultComboBoxModel(new String[] {"<Seleccione>", "Alimento", "Medicamento", "Ambiental", "Animal", "Contacto"}));
		cbxTipo.setBounds(15, 123, 196, 26);
		contentPanel.add(cbxTipo);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(15, 53, 196, 26);
		contentPanel.add(txtNombre);
		txtNombre.setColumns(10);
		
		
		JLabel lblNewLabel = new JLabel("Al\u00E9rgeno");
		lblNewLabel.setBounds(15, 29, 69, 20);
		contentPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Tipo");
		lblNewLabel_1.setBounds(15, 96, 69, 20);
		contentPanel.add(lblNewLabel_1);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Registrar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (txtNombre.getText()==""|| cbxTipo.getSelectedIndex()==0) {
				            JOptionPane.showMessageDialog(null, "Complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
				            return;
						}
						else {
						     Alergia aregistrar = new Alergia(txtNombre.getText(), cbxTipo.getSelectedItem().toString());
						     Clinica.getInstance().registrarAlergias(aregistrar);
					          JOptionPane.showMessageDialog(null, "Registro satisfactorio", "Información", JOptionPane.INFORMATION_MESSAGE);
					          txtNombre.setText("");
					          cbxTipo.setSelectedIndex(0);
					          dispose();

						}
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
