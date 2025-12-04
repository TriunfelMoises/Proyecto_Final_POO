package visual;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
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
import java.awt.Color;

public class Principal extends JFrame {

	private JPanel contentPane;
	static Socket sfd;
	static DataInputStream entradaSocket;
	static DataOutputStream salidaSocket;

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
		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/javax/swing/plaf/metal/icons/ocean/computer.gif")));

		setTitle("Clínica - Principal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screen);
		setLocationRelativeTo(null);
		setResizable(true);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(255, 250, 205));
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

		// ITEM REPORTES
		JMenuItem mntmReportes = new JMenuItem("Reportes");
		mntmReportes.addActionListener(e -> {
			Reportes ventana = new Reportes();
			ventana.setVisible(true);
		});
		mnAdministracion.add(mntmReportes);

		JMenuItem mntmRegistrarUsuarios = new JMenuItem("Registrar usuarios");
		mntmRegistrarUsuarios.addActionListener(e -> {
			regUser dialog = new regUser();
			dialog.setModal(true);
			dialog.setVisible(true);
		});
		mnAdministracion.add(mntmRegistrarUsuarios);

		JMenu mnNewMenu = new JMenu("Respaldo");
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("Respaldar datos");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					sfd = new Socket("127.0.0.1", 7000);
					DataInputStream aux = new DataInputStream(new FileInputStream(new File("control.dat")));
					salidaSocket = new DataOutputStream(sfd.getOutputStream());
					int unByte;
					try {
						while ((unByte = aux.read()) != -1) {
							salidaSocket.write(unByte);
							salidaSocket.flush();
						}
					} catch (IOException ioe) {
						System.out.println("Error: " + ioe);
					}
				} catch (UnknownHostException uhe) {
					System.out.println("No se puede acceder al servidor");
					System.exit(1);
				} catch (IOException ioe) {
					System.out.println("Comunicaci�n rechazada");
					System.exit(1);
				}
			}
		});
		mnNewMenu.add(mntmNewMenuItem);

		if (Control.getLoginUser() != null && !"Administrador".equalsIgnoreCase(Control.getLoginUser().getTipo())) {
			mnAdministracion.setVisible(false);
			mnDoctores.setVisible(false);
		} else {
			mnPacientes.setVisible(false);
			mnCitas.setVisible(false);
			mnConsultas.setVisible(false);
			mnEnfermedades.setVisible(false);
			mnVacunas.setVisible(false);
		}

		contentPane = new PanelFondo();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);
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
