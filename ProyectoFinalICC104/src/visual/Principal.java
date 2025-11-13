package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import logico.Control;

public class Principal extends JFrame {

    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Principal frame = new Principal();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Principal() {
        setTitle("Clínica - Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 820, 520);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnPacientes = new JMenu("Pacientes");
        menuBar.add(mnPacientes);

        JMenu mnDoctores = new JMenu("Doctores");
        menuBar.add(mnDoctores);
        
        JMenu mnEnfermedades = new JMenu("Enfermedades");
        menuBar.add(mnEnfermedades);

        JMenu mnVacunas = new JMenu("Vacunas");
        menuBar.add(mnVacunas);

        JMenu mnAdministracion = new JMenu("Administración");
        menuBar.add(mnAdministracion);

        JMenuItem mntmRegistrarUsuarios = new JMenuItem("Registrar Usuarios");
        mntmRegistrarUsuarios.addActionListener(e -> {
            regUser dialog = new regUser();
            dialog.setModal(true);
            dialog.setVisible(true);
        });
        mnAdministracion.add(mntmRegistrarUsuarios);

        if (Control.getLoginUser() != null
                && !"Administrador".equalsIgnoreCase(Control.getLoginUser().getTipo())) {
            mnAdministracion.setVisible(false); //
        }

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());
    }
}
//