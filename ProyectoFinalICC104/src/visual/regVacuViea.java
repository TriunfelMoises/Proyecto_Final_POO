package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.omg.CORBA.PUBLIC_MEMBER;

import logico.Vacuna;
import logico.VacunaVieja;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.Calendar;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.awt.event.ActionEvent;

public class regVacuViea extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtEnfermedad;
	private VacunaVieja vacu;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			regVacuViea dialog = new regVacuViea(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public regVacuViea(Vacuna vacunasAn) {
		setTitle("Ingreso de vacunas");
		setBounds(100, 100, 316, 210);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setLocationRelativeTo(null);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Enfermedad:");
		lblNewLabel.setBounds(15, 30, 112, 20);
		contentPanel.add(lblNewLabel);
		
		txtEnfermedad = new JTextField();
		txtEnfermedad.setBounds(108, 27, 170, 26);
		contentPanel.add(txtEnfermedad);
		txtEnfermedad.setColumns(10);
		
		if (vacunasAn!=null) {
			txtEnfermedad.setEnabled(false);
			txtEnfermedad.setText(vacunasAn.getEnfermedad());
		}
		
		JLabel lblNewLabel_1 = new JLabel("Fecha de aplicaci\u00F3n:");
		lblNewLabel_1.setBounds(15, 78, 142, 20);
		contentPanel.add(lblNewLabel_1);
		
		JSpinner spnFecha = new JSpinner();
		spnFecha.setModel(new SpinnerDateModel(new Date(), null, new Date(), Calendar.DAY_OF_YEAR));
		JSpinner.DateEditor editor = new JSpinner.DateEditor(spnFecha, "dd/MM/yyyy");
		spnFecha.setEditor(editor);		
		spnFecha.setBounds(158, 75, 120, 26);
		contentPanel.add(spnFecha);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("A\u00F1adir");
			    okButton.addActionListener(e -> {
			        if (txtEnfermedad.getText().isEmpty()) {
			            JOptionPane.showMessageDialog(regVacuViea.this, "Complete los datos", "Información", JOptionPane.INFORMATION_MESSAGE);
			            return;
			        }
			        Date fechaVacuna = (Date) spnFecha.getValue();
			        LocalDate fechapusir = fechaVacuna.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			        vacu = new VacunaVieja(txtEnfermedad.getText(), fechapusir);
			        dispose();
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
	public VacunaVieja mandarLaVacu() {
		return vacu;
	}

	
}
