package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.omg.CORBA.PUBLIC_MEMBER;

import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class selecAlergias extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtMasAlergias;
	private ArrayList<String> alergias = new ArrayList<>();
	private JRadioButton rdbtnPeni;
	private JRadioButton rdbtnAmoxicilina;
	private JRadioButton rdbtnCefalosporinas;
	private JRadioButton rdbtnSulfas;
	private JRadioButton rdbtnMacrlidos;
	private JRadioButton rdbtnQuinolonas;
	private JRadioButton rdbtnAines;
	private JRadioButton rdbtnAspirina;
	private JRadioButton rdbtnParacetamol;
	private JRadioButton rdbtnLidocana;
	private JRadioButton rdbtnAnestesia;
	private JRadioButton rdbtnInsulinas;
	private JRadioButton rdbtnAnticonvulsivos;
	private JRadioButton rdbtnOpioides;
	private JRadioButton rdbtnPrilocana;
	private JRadioButton rdbtnLtex;
	private JRadioButton rdbtnConservantes;
	private JRadioButton rdbtnDetergentes;
	private JRadioButton rdbtnMetales;
	private JRadioButton rdbtnMadera;
	private JRadioButton rdbtnPolietilenglicol;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			selecAlergias dialog = new selecAlergias();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public selecAlergias() {
		setTitle("Selecci\u00F3n de alergias");
		setBounds(100, 100, 732, 557);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("MEDICAMETOS");
			lblNewLabel.setBounds(15, 37, 111, 20);
			contentPanel.add(lblNewLabel);
		}
		{
			rdbtnPeni = new JRadioButton("Penicilina");
			rdbtnPeni.setBounds(11, 69, 155, 29);
			contentPanel.add(rdbtnPeni);
		}
		{
			rdbtnAmoxicilina = new JRadioButton("Amoxicilina");
			rdbtnAmoxicilina.setBounds(11, 106, 155, 29);
			contentPanel.add(rdbtnAmoxicilina);
		}
		{
			rdbtnCefalosporinas = new JRadioButton("Cefalosporinas");
			rdbtnCefalosporinas.setBounds(11, 143, 155, 29);
			contentPanel.add(rdbtnCefalosporinas);
		}
		{
			rdbtnSulfas = new JRadioButton("Sulfas");
			rdbtnSulfas.setBounds(11, 180, 155, 29);
			contentPanel.add(rdbtnSulfas);
		}
		{
			rdbtnMacrlidos = new JRadioButton("Macr\u00F3lidos");
			rdbtnMacrlidos.setBounds(11, 217, 155, 29);
			contentPanel.add(rdbtnMacrlidos);
		}
		{
			rdbtnQuinolonas = new JRadioButton("Quinolonas");
			rdbtnQuinolonas.setBounds(11, 254, 155, 29);
			contentPanel.add(rdbtnQuinolonas);
		}
		{
			rdbtnAines = new JRadioButton("AINEs");
			rdbtnAines.setBounds(11, 293, 155, 29);
			contentPanel.add(rdbtnAines);
		}
		{
			rdbtnAspirina = new JRadioButton("Aspirina");
			rdbtnAspirina.setBounds(11, 330, 155, 29);
			contentPanel.add(rdbtnAspirina);
		}
		{
			rdbtnParacetamol = new JRadioButton("Paracetamol");
			rdbtnParacetamol.setBounds(216, 217, 155, 29);
			contentPanel.add(rdbtnParacetamol);
		}
		{
			rdbtnLidocana = new JRadioButton("Lidoca\u00EDna");
			rdbtnLidocana.setBounds(216, 293, 155, 29);
			contentPanel.add(rdbtnLidocana);
		}
		{
			rdbtnAnestesia = new JRadioButton("Anestesia");
			rdbtnAnestesia.setBounds(216, 106, 155, 29);
			contentPanel.add(rdbtnAnestesia);
		}
		{
			rdbtnInsulinas = new JRadioButton("Insulinas");
			rdbtnInsulinas.setBounds(216, 143, 155, 29);
			contentPanel.add(rdbtnInsulinas);
		}
		{
			rdbtnAnticonvulsivos = new JRadioButton("Anticonvulsivos");
			rdbtnAnticonvulsivos.setBounds(216, 180, 155, 29);
			contentPanel.add(rdbtnAnticonvulsivos);
		}
		{
			rdbtnOpioides = new JRadioButton("Opioides");
			rdbtnOpioides.setBounds(216, 254, 183, 29);
			contentPanel.add(rdbtnOpioides);
		}
		{
			rdbtnPrilocana = new JRadioButton("Priloca\u00EDna");
			rdbtnPrilocana.setBounds(216, 69, 155, 29);
			contentPanel.add(rdbtnPrilocana);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("OTROS AL\u00C9RGENOS DE IMPORTANCIA");
			lblNewLabel_1.setBounds(406, 37, 289, 20);
			contentPanel.add(lblNewLabel_1);
		}
		{
			rdbtnLtex = new JRadioButton("L\u00E1tex");
			rdbtnLtex.setBounds(447, 69, 155, 29);
			contentPanel.add(rdbtnLtex);
		}
		{
			rdbtnConservantes = new JRadioButton("Conservantes");
			rdbtnConservantes.setBounds(447, 106, 155, 29);
			contentPanel.add(rdbtnConservantes);
		}
		{
			rdbtnDetergentes = new JRadioButton("Detergentes");
			rdbtnDetergentes.setBounds(447, 143, 155, 29);
			contentPanel.add(rdbtnDetergentes);
		}
		{
			rdbtnMetales = new JRadioButton("Metales");
			rdbtnMetales.setBounds(447, 180, 155, 29);
			contentPanel.add(rdbtnMetales);
		}
		{
			rdbtnMadera = new JRadioButton("Madera");
			rdbtnMadera.setBounds(447, 217, 155, 29);
			contentPanel.add(rdbtnMadera);
		}
		{
			rdbtnPolietilenglicol = new JRadioButton("Polietilenglicol");
			rdbtnPolietilenglicol.setBounds(447, 254, 155, 29);
			contentPanel.add(rdbtnPolietilenglicol);
		}
		{
			txtMasAlergias = new JTextField();
			txtMasAlergias.setBounds(15, 419, 644, 26);
			contentPanel.add(txtMasAlergias);
			txtMasAlergias.setColumns(10);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("En caso de poseer m\u00E1s u otras ingresar:");
			lblNewLabel_2.setBounds(15, 393, 301, 20);
			contentPanel.add(lblNewLabel_2);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Confirmar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
				        if (rdbtnPeni.isSelected()) {
				        	alergias.add("Penicilina");
				        }
				        if (rdbtnAmoxicilina.isSelected()) {
				        	alergias.add("Amoxicilina");
				        }
				        if (rdbtnCefalosporinas.isSelected()) {
				        	alergias.add("Cefalosporinas");
				        }
				        if (rdbtnSulfas.isSelected()) {
				        	alergias.add("Sulfas");
				        }
				        if (rdbtnMacrlidos.isSelected()) {
				        	alergias.add("Macrólidos");
				        }
				        if (rdbtnQuinolonas.isSelected()) {
				        	alergias.add("Quinolonas");
				        }
				        if (rdbtnAines.isSelected()) {
				        	alergias.add("AINEs");
				        }
				        if (rdbtnAspirina.isSelected()) {
				        	alergias.add("Aspirina");
				        }
				        if (rdbtnParacetamol.isSelected()) {
				        	alergias.add("Paracetamol");
				        }
				        if (rdbtnLidocana.isSelected()) {
				        	alergias.add("Lidocaína");
				        }
				        if (rdbtnAnestesia.isSelected()) {
				        	alergias.add("Anestesia");
				        }
				        if (rdbtnInsulinas.isSelected()) {
				        	alergias.add("Insulinas");
				        }
				        if (rdbtnAnticonvulsivos.isSelected()) {
				        	alergias.add("Anticonvulsivos");
				        }
				        if (rdbtnOpioides.isSelected()) {
				        	alergias.add("Opioides");
				        }
				        if (rdbtnPrilocana.isSelected()) {
				        	alergias.add("Prilocaína");
				        }
				        if (rdbtnLtex.isSelected()) {
				        	alergias.add("Látex");
				        }
				        if (rdbtnConservantes.isSelected()) {
				        	alergias.add("Conservantes");
				        }
				        if (rdbtnDetergentes.isSelected()) {
				        	alergias.add("Detergentes");
				        }
				        if (rdbtnMetales.isSelected()) {
				        	alergias.add("Metales");
				        }
				        if (rdbtnMadera.isSelected()) {
				        	alergias.add("Madera");
				        }
				        if (rdbtnPolietilenglicol.isSelected()) {
				        	alergias.add("Polietilenglicol");
				        }

				        if (!txtMasAlergias.getText().trim().isEmpty()) {
				            String texto = txtMasAlergias.getText().trim();
				            String[] extras = texto.split(",");
				            for (String item : extras) {
				                if (!item.trim().isEmpty()) {
				                    alergias.add(item.trim());
				                }
				            }
				        }

				        dispose();
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
	public ArrayList<String> mandarAlergias(){
		return alergias;
	}

}

