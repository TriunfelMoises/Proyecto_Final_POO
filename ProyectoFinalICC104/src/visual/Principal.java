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

import logico.Control;
import logico.PersistenciaManager;
import Server.Servidor;

import javax.swing.JOptionPane;
import java.awt.Color;
import javax.swing.JButton;

public class Principal extends JFrame {

	private JPanel contentPane;
	static Socket sfd;
	static DataInputStream entradaSocket;
	static DataOutputStream salidaSocket;

	public static void main(String[] args) {
		// Iniciar servidor en un thread separado
		Thread servidorThread = new Thread(() -> {
			Servidor.main(new String[] {});
		}, "ServidorAutoStarter");
		servidorThread.setDaemon(true);
		servidorThread.start();

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
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(Principal.class.getResource("/javax/swing/plaf/metal/icons/ocean/computer.gif")));

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

		JMenuItem mntmListarConsultas = new JMenuItem("Listar consultas");
		mntmListarConsultas.addActionListener(e -> {
			listConsulta dialog = new listConsulta();
			dialog.setLocationRelativeTo(Principal.this);
			dialog.setVisible(true);
		});
		mnConsultas.add(mntmListarConsultas);

		// ------------------ MENU ADMIN ------------------
		JMenu mnAdministracion = new JMenu("Administración");
		menuBar.add(mnAdministracion);

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

		// CONFIGURAR VISIBILIDAD DE MENÚS SEGÚN TIPO DE USUARIO
		if (Control.getLoginUser() != null) {
			String tipoUsuario = Control.getLoginUser().getTipo();

			if ("Administrador".equals(tipoUsuario)) {
				// ADMIN ve todo el sistema (todos los menús visibles)
			} else if ("Doctor".equals(tipoUsuario)) {
				// DOCTOR solo ve lo necesario para su trabajo
				mnAdministracion.setVisible(false);
				mnDoctores.setVisible(false);
				// Los demás menús (Pacientes, Citas, Consultas) quedan visibles
			} else {
				// Otro tipo de usuario (si hay)
				JOptionPane.showMessageDialog(this, "Tipo de usuario no reconocido: " + tipoUsuario, "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}

		// Panel de fondo
		contentPane = new PanelFondo();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
				// Botón de cerrar sesión
				JButton btnCerrarSesion = new JButton("Cerrar sesión");
				btnCerrarSesion.setBounds(1718, 937, 139, 43);
				contentPane.add(btnCerrarSesion);
				btnCerrarSesion.setBackground(new Color(240, 230, 140));
				btnCerrarSesion.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						// Guardar datos antes de cerrar
						PersistenciaManager.guardarDatos();

						// Limpiar usuario logeado
						Control.clearLoginUser();

						// Cerrar ventana
						dispose();

						// Volver a login
						login inicio = new login();
						inicio.setVisible(true);
					}
				});

		// Guardado y respaldo automático al cerrar
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				// Guardar datos del sistema
				PersistenciaManager.guardarDatos();

				// Hacer respaldo en servidor
				realizarRespaldo();

				// Detener servidor
				Servidor.detenerServidor();
			}
		});
	}

	private void realizarRespaldo() {
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
				System.out.println("Error en respaldo: " + ioe);
			} finally {
				try {
					aux.close();
					if (salidaSocket != null)
						salidaSocket.close();
					if (sfd != null)
						sfd.close();
				} catch (IOException ex) {
					System.out.println("Error cerrando conexión: " + ex);
				}
			}
		} catch (UnknownHostException uhe) {
			System.out.println("No se puede acceder al servidor: " + uhe);
		} catch (IOException ioe) {
			System.out.println("Comunicación rechazada: " + ioe);
		}
	}
}