package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logico.Control;
import logico.User;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class regUser extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTextField txtUserName;
    private JPasswordField txtPass;
    private JComboBox<String> comboTipo;

    public regUser() {
        setTitle("Registrar Usuario");
        setBounds(100, 100, 380, 240);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblNombreUsuario = new JLabel("Nombre de usuario:");
        lblNombreUsuario.setBounds(20, 20, 150, 20);
        contentPanel.add(lblNombreUsuario);

        txtUserName = new JTextField();
        txtUserName.setBounds(20, 45, 150, 24);
        contentPanel.add(txtUserName);

        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setBounds(190, 20, 150, 20);
        contentPanel.add(lblPass);

        txtPass = new JPasswordField();
        txtPass.setBounds(190, 45, 150, 24);
        contentPanel.add(txtPass);

        JLabel lblTipo = new JLabel("Tipo de usuario:");
        lblTipo.setBounds(20, 85, 150, 20);
        contentPanel.add(lblTipo);

        comboTipo = new JComboBox<>();
        comboTipo.setModel(new DefaultComboBoxModel<>(new String[] {
            "<Seleccione>", "Administrador", "Doctor"
        }));
        comboTipo.setBounds(20, 110, 150, 24);
        contentPanel.add(comboTipo);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("Registrar");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registrarUsuario();
            }
        });
        buttonPane.add(okButton);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(e -> dispose());
        buttonPane.add(cancelButton);
    }

    private void registrarUsuario() {
        String tipo = (String) comboTipo.getSelectedItem();
        String user = txtUserName.getText();
        String pass = new String(txtPass.getPassword());

        if (tipo == null || "<Seleccione>".equals(tipo) || user.isEmpty() || pass.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Complete todos los campos y seleccione un tipo válido.",
                "Datos incompletos",
                javax.swing.JOptionPane.WARNING_MESSAGE);//
            return;
        }

        User nuevo = new User(tipo, user, pass);
        Control.getInstance().regUser(nuevo);
        javax.swing.JOptionPane.showMessageDialog(this, "Usuario registrado exitosamente.");
        dispose();
    }
}
//