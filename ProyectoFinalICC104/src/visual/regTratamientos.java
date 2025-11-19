package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;

import logico.Clinica;
import logico.Tratamiento;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Diálogo para registrar tratamientos.
 */
public class regTratamientos extends JDialog {

    private final JPanel contentPanel = new JPanel();

    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtDescripcion;
    private JTextField txtIndicaciones;
    private JSpinner spnDuracion;   // <-- CAMBIO

    /**
     * Constructor del diálogo regTratamiento.
     */
    public regTratamientos() {
        setTitle("Registrar Tratamiento");
        setBounds(100, 100, 500, 280);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblCodigo = new JLabel("Código:");
        lblCodigo.setBounds(20, 20, 80, 20);
        contentPanel.add(lblCodigo);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 55, 80, 20);
        contentPanel.add(lblNombre);

        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setBounds(20, 90, 80, 20);
        contentPanel.add(lblDescripcion);

        JLabel lblIndicaciones = new JLabel("Indicaciones:");
        lblIndicaciones.setBounds(20, 125, 80, 20);
        contentPanel.add(lblIndicaciones);

        JLabel lblDuracion = new JLabel("Duración (días):");
        lblDuracion.setBounds(20, 160, 100, 20);
        contentPanel.add(lblDuracion);

        txtCodigo = new JTextField();
        txtCodigo.setBounds(110, 20, 140, 22);
        contentPanel.add(txtCodigo);

        txtNombre = new JTextField();
        txtNombre.setBounds(110, 55, 140, 22);
        contentPanel.add(txtNombre);

        txtDescripcion = new JTextField();
        txtDescripcion.setBounds(110, 90, 340, 22);
        contentPanel.add(txtDescripcion);

        txtIndicaciones = new JTextField();
        txtIndicaciones.setBounds(110, 125, 340, 22);
        contentPanel.add(txtIndicaciones);//

        // === Spinner para duración en días ===
        spnDuracion = new JSpinner(new SpinnerNumberModel(1, 1, 365, 1));
        spnDuracion.setBounds(130, 160, 80, 22);
        contentPanel.add(spnDuracion);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("Registrar");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registrarTratamiento();
            }
        });
        buttonPane.add(okButton);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonPane.add(cancelButton);
    }

    /**
     * Lógica del botón Registrar.
     */
    private void registrarTratamiento() {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String indicaciones = txtIndicaciones.getText().trim();
        int duracionDias = (Integer) spnDuracion.getValue(); // Spinner en días

        // Validación de campos
        if (codigo.isEmpty() || nombre.isEmpty() || descripcion.isEmpty()
                || indicaciones.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Complete todos los campos.",
                    "Datos incompletos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Si tu clase Tratamiento usa String para duración:
        String duracionStr = String.valueOf(duracionDias);

        Tratamiento nuevo = new Tratamiento(codigo, nombre, descripcion, indicaciones, duracionStr);
        boolean agregado = Clinica.getInstance().agregarTratamiento(nuevo);

        if (agregado) {
            JOptionPane.showMessageDialog(this,
                    "Tratamiento registrado correctamente.",
                    "Registro exitoso",
                    JOptionPane.INFORMATION_MESSAGE);

            // 👇 NO cerramos la ventana, limpiamos para registrar otro
            txtCodigo.setText("");
            txtNombre.setText("");
            txtDescripcion.setText("");
            txtIndicaciones.setText("");
            spnDuracion.setValue(1); // vuelve a 1 día

            txtCodigo.requestFocus();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Ya existe un tratamiento con ese código.",
                    "Error al registrar",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


}
