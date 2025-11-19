package visual;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import logico.Clinica;
import logico.Enfermedad;

public class regEnfermedades extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextArea txtDescripcion;
    private JCheckBox chkBajoVigilancia;

    public regEnfermedades() {
        setTitle("Registrar Enfermedad");
        setBounds(100, 100, 550, 320);
        setLocationRelativeTo(null);
        setModal(true);

        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        // Etiquetas
        JLabel lblCodigo = new JLabel("Código:");
        lblCodigo.setBounds(20, 20, 80, 20);
        contentPanel.add(lblCodigo);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 55, 80, 20);
        contentPanel.add(lblNombre);

        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setBounds(20, 90, 80, 20);
        contentPanel.add(lblDescripcion);

        JLabel lblBajoVigilancia = new JLabel("Bajo vigilancia:");
        lblBajoVigilancia.setBounds(20, 210, 100, 20);
        contentPanel.add(lblBajoVigilancia);

        // Campos
        txtCodigo = new JTextField();
        txtCodigo.setEditable(false);
        txtCodigo.setBounds(130, 20, 140, 22);
        txtCodigo.setText(Clinica.getInstance().generarCodigoEnfermedad());
        contentPanel.add(txtCodigo);

        txtNombre = new JTextField();
        txtNombre.setBounds(130, 55, 220, 22);
        contentPanel.add(txtNombre);

        // Descripción como JTextArea grande (para párrafos)
        txtDescripcion = new JTextArea();
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);

        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
        scrollDescripcion.setBounds(130, 90, 360, 100);
        contentPanel.add(scrollDescripcion);

        chkBajoVigilancia = new JCheckBox();
        chkBajoVigilancia.setBounds(130, 210, 20, 20);
        contentPanel.add(chkBajoVigilancia);

        // Botones
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(e -> registrarEnfermedad());
        buttonPane.add(btnRegistrar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        buttonPane.add(btnCancelar);
    }

    private void registrarEnfermedad() {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        boolean bajoVigilancia = chkBajoVigilancia.isSelected();

        if (codigo.isEmpty() || nombre.isEmpty() || descripcion.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Complete todos los campos obligatorios.",
                    "Datos incompletos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Enfermedad nueva = new Enfermedad(codigo, nombre, descripcion, bajoVigilancia);
        boolean agregado = Clinica.getInstance().registrarEnfermedad(nueva);

        if (agregado) {
            JOptionPane.showMessageDialog(this,
                    "Enfermedad registrada correctamente.",
                    "Registro exitoso",
                    JOptionPane.INFORMATION_MESSAGE);

            // No cerramos: dejamos registrar varias seguidas
            txtNombre.setText("");
            txtDescripcion.setText("");
            chkBajoVigilancia.setSelected(false);

            txtCodigo.setText(Clinica.getInstance().generarCodigoEnfermedad());
            txtNombre.requestFocus();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Ya existe una enfermedad con ese código.",
                    "Error al registrar",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
