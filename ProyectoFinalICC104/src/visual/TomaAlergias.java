package visual;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

import logico.Alergia;
import logico.Clinica;
import javax.swing.JLabel;

public class TomaAlergias extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPanel panelChecks;

	public static void main(String[] args) {
		try {
			TomaAlergias dialog = new TomaAlergias();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public TomaAlergias() {
		setTitle("Selección de alergias");
		setBounds(100, 100, 295, 372);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 71, 273, 201);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		setLocationRelativeTo(null);


		{
			panelChecks = new JPanel();
			panelChecks.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelChecks.setLayout(new javax.swing.BoxLayout(panelChecks, javax.swing.BoxLayout.Y_AXIS));
			JScrollPane scrollPane = new JScrollPane(panelChecks);
			scrollPane.setBounds(5, 0, 264, 159);
			contentPanel.add(scrollPane);
		}

		JButton btnNewButton = new JButton("Registrar alergia");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				regAlergia registoR = new regAlergia();
				registoR.setModal(true);
				registoR.setVisible(true);
				cargarAlergias();
			}
		});
		btnNewButton.setBounds(59, 172, 165, 29);
		contentPanel.add(btnNewButton);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 272, 273, 44);
			buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);

			JButton cancelButton = new JButton("Cancelar");
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});

			JButton okButton = new JButton("Seleccionar");
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			buttonPane.add(okButton);
			cancelButton.setActionCommand("Cancelar");
			buttonPane.add(cancelButton);
		}

		JLabel lblNewLabel = new JLabel("Seleccione sus alergias:");
		lblNewLabel.setBounds(15, 26, 166, 20);
		getContentPane().add(lblNewLabel);

		cargarAlergias();
	}

	private void cargarAlergias() {
		panelChecks.removeAll(); 

		for (Alergia alergia : Clinica.getInstance().getAlergias()) {
			JCheckBox chk = new JCheckBox(alergia.getNombre() + " (" + alergia.getTipo() + ")");
			chk.putClientProperty("alergia", alergia);
			panelChecks.add(chk);
		}

		panelChecks.revalidate();
		panelChecks.repaint();
	}
	public ArrayList<Alergia> AlergiasSeleccionadas() {
		ArrayList<Alergia> seleccionadas = new ArrayList<>();

		for (Component comp : panelChecks.getComponents()) {
			if (comp instanceof JCheckBox) {
				JCheckBox chk = (JCheckBox) comp;    
				if (chk.isSelected()) { 
					Alergia alergia = (Alergia) chk.getClientProperty("alergia");
					seleccionadas.add(alergia);
				}
			}
		}

		return seleccionadas;
	}

}
