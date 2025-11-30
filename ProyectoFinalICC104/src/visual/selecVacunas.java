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

import logico.Clinica;
import logico.Paciente;
import logico.Vacuna;
import logico.VacunaVieja;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class selecVacunas extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPanel panelChecks;
	Paciente paciente;

	public static void main(String[] args) {
		try {
			selecVacunas dialog = new selecVacunas(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public selecVacunas(Paciente elPaciente) {
		paciente = elPaciente;
		setTitle("Selección de vacunas");
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

		JButton btnNewButton = new JButton("Registrar vacuna");
		btnNewButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent arg0) {
		        regVacuViea registroR = new regVacuViea(null);
		        registroR.setModal(true);
		        registroR.setVisible(true);
		        VacunaVieja nueva = registroR.mandarLaVacu();
		        if (nueva != null) {
		            anadirVacunaAlPaciente(nueva);
		        }
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
			        for (Component comp : panelChecks.getComponents()) {
			            if (comp instanceof JCheckBox) {
			                JCheckBox chk = (JCheckBox) comp;
			                Object vacObj = chk.getClientProperty("vacuna"); 
			                if (vacObj != null) {
			                    if (chk.isSelected()) {
			                        Vacuna vac = (Vacuna) vacObj;
			                        regVacuViea dialog = new regVacuViea(vac);
			                        dialog.setModal(true);
			                        dialog.setLocationRelativeTo(selecVacunas.this);
			                        dialog.setVisible(true);
			                        VacunaVieja nueva = dialog.mandarLaVacu();
			                        if (nueva != null) {
			                            anadirVacunaAlPaciente(nueva);
			                            chk.setSelected(false);
			                            chk.setEnabled(false);
			                        }
			                    }
			                } 
			            }
			        }
			        cargarVacunas();
			        dispose();
			    }
			});

			buttonPane.add(okButton);
			cancelButton.setActionCommand("Cancelar");
			buttonPane.add(cancelButton);
		}

		JLabel lblNewLabel = new JLabel("Seleccione sus vacunas:");
		lblNewLabel.setBounds(15, 26, 166, 20);
		getContentPane().add(lblNewLabel);

		cargarVacunas();
	}

	private void cargarVacunas() {
		panelChecks.removeAll(); 
		for (VacunaVieja vacunita : paciente.getVacunasViejas()) {
			JCheckBox chk = new JCheckBox(vacunita.getEnfermedad());
			chk.putClientProperty("vacunaVieja", vacunita);
			panelChecks.add(chk);
			chk.setSelected(true);
			chk.setEnabled(false);
		}
		
		for (Vacuna vacunita : Clinica.getInstance().getVacunas()) {
			JCheckBox chk = new JCheckBox(vacunita.getNombre() + " (" + vacunita.getEnfermedad() + ")");
			chk.putClientProperty("vacuna", vacunita);
			panelChecks.add(chk);
		}

		panelChecks.revalidate();
		panelChecks.repaint();
	}
	public ArrayList<Vacuna> VacunasSeleccionadas() {
		ArrayList<Vacuna> seleccionadas = new ArrayList<>();

		for (Component comp : panelChecks.getComponents()) {
			if (comp instanceof JCheckBox) {
				JCheckBox chk = (JCheckBox) comp;    
				if (chk.isSelected()) { 
					Vacuna vacunita = (Vacuna) chk.getClientProperty("vacuna");
					seleccionadas.add(vacunita);
				}
			}
		}

		return seleccionadas;
	}
	
	private void anadirVacunaAlPaciente(VacunaVieja vv) {
	    if (vv == null) return;
	    if (paciente == null) {
	        JOptionPane.showMessageDialog(this, "No hay paciente para asignar la vacuna.", "Error", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    if (paciente.getVacunasViejas() == null) {
	        paciente.setVacunasViejas(new ArrayList<>());
	    }

	    paciente.getVacunasViejas().add(vv);
	    Clinica.getInstance().modificarPaciente(paciente);
	    cargarVacunas();

}
}
