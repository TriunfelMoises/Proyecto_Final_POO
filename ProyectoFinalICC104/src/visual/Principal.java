package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import logico.Clinica;
import logico.Control;
import javax.swing.JToolBar;

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

		// ------------------ MENÚ PACIENTES ------------------'
		JMenu mnPacientes = new JMenu("Pacientes");
		menuBar.add(mnPacientes);

		JMenuItem mntmRegPac = new JMenuItem("Registrar");
		mntmRegPac.addActionListener(e -> {
			regPaciente dialog = new regPaciente();
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

		// ------------------ MENÚ DOCTORES ------------------
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

		// ------------------ MENÚ ENFERMEDADES ------------------
		JMenu mnEnfermedades = new JMenu("Enfermedades");
		menuBar.add(mnEnfermedades);

		// ------------------ MENÚ VACUNAS ------------------
		JMenu mnVacunas = new JMenu("Vacunas");
		menuBar.add(mnVacunas);

		// ------------------ MENÚ CITAS ------------------
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

		// ------------------ MENÚ ADMINISTRACIÓN ------------------
		JMenu mnAdministracion = new JMenu("Administración");
		menuBar.add(mnAdministracion);

		JMenuItem mntmRegistrarUsuarios = new JMenuItem("Registrar usuarios");
		mntmRegistrarUsuarios.addActionListener(e -> {
			// Como el menú solo es visible para admins, abrir directamente
			regUser dialog = new regUser();
			dialog.setModal(true);
			dialog.setVisible(true);
		});
		mnAdministracion.add(mntmRegistrarUsuarios);

		// Ocultar menú si no es admin
		if (Control.getLoginUser() != null && !"Administrador".equalsIgnoreCase(Control.getLoginUser().getTipo())) {
			mnAdministracion.setVisible(false);
		}

		// ------------------ PANEL PRINCIPAL ------------------
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());

		// ------------------ GUARDADO AUTOMÁTICO ------------------
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				guardarTodo();
				System.out.println("Datos guardados automáticamente al cerrar.");
			}
		});
	}

	// ------------------ MÉTODO DE GUARDADO AUTOMÁTICO ------------------
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
