package visual;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.awt.event.ActionListener;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import logico.Clinica;
import logico.Control;
import logico.Doctor;

import javax.swing.JOptionPane;

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

        setTitle("Clinica - Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screen);
        setLocationRelativeTo(null);
        setResizable(true);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // ------------------ MENU PACIENTES ------------------
        JMenu mnPacientes = new JMenu("Pacientes");
        menuBar.add(mnPacientes);

        JMenuItem mntmRegPac = new JMenuItem("Registrar");
        mntmRegPac.addActionListener(e -> {
            regPaciente dialog = new regPaciente(null);
            dialog.setModal(true);
            dialog.setVisible(true);
        });
        mnPacientes.add(mntmRegPac);

        JMenuItem mntmListPac = new JMenuItem("Listar");
        mntmListPac.addActionListener(e -> {
            listPacientes dialog = new listPacientes();
            dialog.setModal(true);
            dialog.setVisible(true);
        });
        mnPacientes.add(mntmListPac);

        JMenuItem mntmVerHistorial = new JMenuItem("Ver Historial Clinico");
        mntmVerHistorial.addActionListener(e -> {
            VerHistorialClinico dialog = new VerHistorialClinico();
            dialog.setModal(true);
            dialog.setLocationRelativeTo(Principal.this);
            dialog.setVisible(true);
        });
        mnPacientes.add(mntmVerHistorial);

        // ------------------ MENU DOCTORES ------------------
        JMenu mnDoctores = new JMenu("Doctores");
        menuBar.add(mnDoctores);

        JMenuItem mntmRegDoc = new JMenuItem("Registrar");
        mntmRegDoc.addActionListener(e -> {
            regDoctor dialog = new regDoctor();
            dialog.setModal(true);
            dialog.setVisible(true);
        });
        mnDoctores.add(mntmRegDoc);

        JMenuItem mntmListDoc = new JMenuItem("Listar");
        mntmListDoc.addActionListener(e -> {
            listDoctor dialog = new listDoctor();
            dialog.setModal(true);
            dialog.setVisible(true);
        });
        mnDoctores.add(mntmListDoc);

        // ------------------ MENU ENFERMEDADES ------------------
        JMenu mnEnfermedades = new JMenu("Enfermedades");
        menuBar.add(mnEnfermedades);

        JMenuItem mntmRegEnf = new JMenuItem("Registrar");
        mntmRegEnf.addActionListener(e -> {
            regEnfermedades dialog = new regEnfermedades();
            dialog.setModal(true);
            dialog.setLocationRelativeTo(Principal.this);
            dialog.setVisible(true);
        });
        mnEnfermedades.add(mntmRegEnf);

        JMenuItem mntmListEnf = new JMenuItem("Listar");
        mntmListEnf.addActionListener(e -> {
            listEnfermedad dialog = new listEnfermedad();
            dialog.setModal(true);
            dialog.setLocationRelativeTo(Principal.this);
            dialog.setVisible(true);
        });
        mnEnfermedades.add(mntmListEnf);

        // ------------------ MENU VACUNAS ------------------
        JMenu mnVacunas = new JMenu("Vacunas");
        menuBar.add(mnVacunas);

        JMenuItem mntmRegVac = new JMenuItem("Registrar");
        mntmRegVac.addActionListener(e -> {
            regVacuna vacunacion = new regVacuna();
            vacunacion.setModal(true);
            vacunacion.setVisible(true);
        });
        mnVacunas.add(mntmRegVac);

        JMenuItem mntmListVac = new JMenuItem("Listar");
        mntmListVac.addActionListener(e -> {
            listVacuna verVacuna = new listVacuna();
            verVacuna.setModal(true);
            verVacuna.setVisible(true);
        });
        mnVacunas.add(mntmListVac);

        JMenuItem mntmAdminVac = new JMenuItem("Administrar a paciente");
        mntmAdminVac.addActionListener(e -> {
            adminVacuna vacu = new adminVacuna();
            vacu.setModal(true);
            vacu.setVisible(true);
        });
        mnVacunas.add(mntmAdminVac);

        // ------------------ MENU CITAS ------------------
        JMenu mnCitas = new JMenu("Citas");
        menuBar.add(mnCitas);

        JMenuItem mntmAgendarCita = new JMenuItem("Agendar Cita");
        mntmAgendarCita.addActionListener(e -> {
            AgendarCita dialog = new AgendarCita();
            dialog.setModal(true);
            dialog.setVisible(true);
        });
        mnCitas.add(mntmAgendarCita);

        JMenuItem mntmListaCitas = new JMenuItem("Lista de Citas");
        mntmListaCitas.addActionListener(e -> {
            ListaCitas dialog = new ListaCitas();
            dialog.setModal(true);
            dialog.setVisible(true);
        });
        mnCitas.add(mntmListaCitas);
     // ------------------ MENU CONSULTAS ------------------
        JMenu mnConsultas = new JMenu("Consultas");
        menuBar.add(mnConsultas);

        JMenuItem mntmRegConsulta = new JMenuItem("Registrar consulta");
        mntmRegConsulta.addActionListener(e -> {

            // ← SIN FILTRO, SIN LICENCIA, SIN VALIDACIONES
        	regConsulta dialog = new regConsulta();
            dialog.setLocationRelativeTo(Principal.this);
            dialog.setVisible(true);

        });
        mnConsultas.add(mntmRegConsulta);

        // ===== LISTAR CONSULTAS (YA SIN FILTROS) =====
        JMenuItem mntmListarConsultas = new JMenuItem("Listar consultas");
        mntmListarConsultas.addActionListener(e -> {
            listConsulta dialog = new listConsulta();
            dialog.setLocationRelativeTo(Principal.this);
            dialog.setVisible(true);
        });
        mnConsultas.add(mntmListarConsultas);

        // ------------------ MENU TRATAMIENTOS ------------------

        // ------------------ MENU ADMIN ------------------
        JMenu mnAdministracion = new JMenu("Administracion");
        menuBar.add(mnAdministracion);

        JMenuItem mntmRegistrarUsuarios = new JMenuItem("Registrar usuarios");
        mntmRegistrarUsuarios.addActionListener(e -> {
            regUser dialog = new regUser();
            dialog.setModal(true);
            dialog.setVisible(true);
        });
        mnAdministracion.add(mntmRegistrarUsuarios);

        if (Control.getLoginUser() != null &&
                !"Administrador".equalsIgnoreCase(Control.getLoginUser().getTipo())) {
            mnAdministracion.setVisible(false);
        }

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        // Guardado automático
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                guardarTodo();
            }
        });
    }

    private void guardarTodo() {
        try {
            FileOutputStream fileOut = new FileOutputStream("control.dat");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(Control.getInstance());
            out.close();
            fileOut.close();
            System.out.println("Datos guardados exitosamente");
        } catch (Exception e) {
            System.out.println("Error guardando datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
