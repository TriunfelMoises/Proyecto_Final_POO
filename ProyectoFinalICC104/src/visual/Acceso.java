package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import com.sun.org.glassfish.external.statistics.AverageRangeStatistic;

import logico.Clinica;
import logico.Control;
import logico.User;

import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Acceso extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private final JTextField txtUsuario = new JTextField();
	private JPasswordField txtContra;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Acceso dialog = new Acceso();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Acceso() {
		setTitle("Acceso usuarios");
		setBounds(100, 100, 267, 294);
        setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		txtUsuario.setBounds(15, 107, 203, 20);
		contentPanel.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Usuario");
		lblNewLabel.setBounds(15, 81, 69, 20);
		contentPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Contrase\u00F1a");
		lblNewLabel_1.setBounds(15, 143, 123, 20);
		contentPanel.add(lblNewLabel_1);
		
		txtContra = new JPasswordField();
		txtContra.setColumns(10);
		txtContra.setBounds(15, 168, 203, 20);
		contentPanel.add(txtContra);
		
		JLabel lblNewLabel_2 = new JLabel("Para acceder debe ingresar un ");
		lblNewLabel_2.setBounds(15, 16, 348, 20);
		contentPanel.add(lblNewLabel_2);
		
		JLabel lblAdministrador = new JLabel("ADMINISTRADOR");
		lblAdministrador.setBounds(15, 45, 348, 20);
		contentPanel.add(lblAdministrador);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Ingresar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (Control.getInstance().confirmLoginAdmin(txtUsuario.getText(), txtContra.getText())==true) {
							dispose();
							regUser aver = new regUser();
							aver.setModal(true);
							aver.setVisible(true);
						}
						
						else {
					        javax.swing.JOptionPane.showMessageDialog(null, "Usuario inválido");
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
