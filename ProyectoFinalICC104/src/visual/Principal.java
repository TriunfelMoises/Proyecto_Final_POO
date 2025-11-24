package visual;

import java.awt.BorderLayout;

import java.awt.EventQueue;
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
import javax.swing.JOptionPane;

import logico.Clinica;
import logico.Control;
import logico.Doctor;

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

		// ------------------ MENÚ PACIENTES ------------------
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
		
		// ------------------ MENÚ CONSULTAS ------------------
		JMenu mnConsultas = new JMenu("Consultas");
		menuBar.add(mnConsultas);

		JMenuItem mntmRegConsulta = new JMenuItem("Registrar consulta");
		mntmRegConsulta.addActionListener(new ActionListener() {
			
		    @Override
		    public void actionPerformed(ActionEvent e) {

		        Clinica clinica = Clinica.getInstance();

		        // Bucle para que la ventana de licencia "no se cierre" hasta que todo esté bien
		        while (true) {
		            // ====== CAMPO FORMATEADO COMO EN regDoctor (UUUU-##### con '-') ======
		            JFormattedTextField txtLicencia;
		            try {
		                MaskFormatter licenciaMask = new MaskFormatter("UUUU-#####");
		                licenciaMask.setPlaceholderCharacter('_');
		                txtLicencia = new JFormattedTextField(licenciaMask);
		            } catch (Exception ex) {
		                txtLicencia = new JFormattedTextField();
		            }

		            JPanel panel = new JPanel(new BorderLayout(5, 5));
		            panel.add(new JLabel("Ingrese el número de licencia del doctor:"), BorderLayout.NORTH);
		            panel.add(txtLicencia, BorderLayout.CENTER);

		            int opcion = JOptionPane.showConfirmDialog(
		                    Principal.this,
		                    panel,
		                    "Registrar consulta",
		                    JOptionPane.OK_CANCEL_OPTION,
		                    JOptionPane.PLAIN_MESSAGE   // sin signo de interrogación
		            );

		            // Si cancela o cierra el cuadro, salimos del flujo
		            if (opcion != JOptionPane.OK_OPTION) {
		                return;
		            }

		            String numLicencia = txtLicencia.getText().trim();

		            // Validar que rellenó bien la máscara (sin '_')
		            if (numLicencia.isEmpty() || numLicencia.contains("_")) {
		                JOptionPane.showMessageDialog(
		                        Principal.this,
		                        "Debe ingresar un número de licencia válido (formato: AAAA-12345).",
		                        "Dato inválido",
		                        JOptionPane.WARNING_MESSAGE
		                );
		                // Volvemos arriba del while y mostramos de nuevo la ventanita
		                continue;
		            }

		            // Buscar doctor por NÚMERO DE LICENCIA real
		            Doctor doctor = clinica.buscarDoctorPorNumeroLicencia(numLicencia);

		            if (doctor == null) {
		                // ====== DOCTOR NO EXISTE – PREGUNTAR SI DESEA REGISTRAR UNO NUEVO ======
		                int resp = JOptionPane.showConfirmDialog(
		                        Principal.this,
		                        "No se encontró un doctor con el número de licencia:\n"
		                                + numLicencia + "\n\n¿Desea registrar un nuevo doctor?",
		                        "Doctor no encontrado",
		                        JOptionPane.YES_NO_OPTION,
		                        JOptionPane.QUESTION_MESSAGE
		                );

		                if (resp == JOptionPane.YES_OPTION) {
		                    int cantAntes = clinica.getDoctores().size();

		                    regDoctor ventana = new regDoctor();
		                    ventana.setModal(true);
		                    ventana.setLocationRelativeTo(Principal.this);
		                    ventana.setVisible(true);

		                    // Ajustamos el contador de códigos según la cantidad real de doctores
		                    clinica.recalcularContadorDoctores();

		                    int cantDespues = clinica.getDoctores().size();
		                    if (cantDespues > cantAntes) {
		                        JOptionPane.showMessageDialog(
		                                Principal.this,
		                                "Doctor registrado correctamente.\n" +
		                                "Ahora ingrese nuevamente su número de licencia para continuar.",
		                                "Doctor registrado",
		                                JOptionPane.INFORMATION_MESSAGE
		                        );
		                    }
		                }

		                // En cualquier caso (Sí o No), volvemos al while para pedir licencia de nuevo
		                continue;
		            }

		            // Validar que el doctor esté ACTIVO
		            if (!doctor.isActivo()) {
		                JOptionPane.showMessageDialog(
		                        Principal.this,
		                        "El doctor con ese número de licencia se encuentra INACTIVO y no puede atender consultas.",
		                        "Doctor inactivo",
		                        JOptionPane.ERROR_MESSAGE
		                );
		                // Volvemos a pedir otra licencia
		                continue;
		            }

		            // Si llegó aquí, el doctor existe y está activo – ahora sí abrimos regConsulta
		            regConsulta dialog = new regConsulta(numLicencia);
		            dialog.setLocationRelativeTo(Principal.this);
		            dialog.setVisible(true);

		            // Salimos del bucle; ya se abrió regConsulta correctamente
		            break;
		        }
		    }
		});
		mnConsultas.add(mntmRegConsulta);
		
		JMenuItem mntmListarConsultas = new JMenuItem("Listar consultas");
		mntmListarConsultas.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {

		        Clinica clinica = Clinica.getInstance();

		        while (true) {

		            // ====== MISMA MÁSCARA QUE EN regDoctor Y EN Registrar Consulta ======
		            JFormattedTextField txtLicencia;
		            try {
		                MaskFormatter licenciaMask = new MaskFormatter("UUUU-#####");
		                licenciaMask.setPlaceholderCharacter('_');
		                txtLicencia = new JFormattedTextField(licenciaMask);
		            } catch (Exception ex) {
		                txtLicencia = new JFormattedTextField();
		            }

		            JPanel panel = new JPanel(new BorderLayout(5, 5));
		            panel.add(new JLabel("Ingrese el número de licencia del doctor:"), BorderLayout.NORTH);
		            panel.add(txtLicencia, BorderLayout.CENTER);

		            int opcion = JOptionPane.showConfirmDialog(
		                    Principal.this,
		                    panel,
		                    "Listar consultas",
		                    JOptionPane.OK_CANCEL_OPTION,
		                    JOptionPane.PLAIN_MESSAGE
		            );

		            // Canceló
		            if (opcion != JOptionPane.OK_OPTION) {
		                return;
		            }

		            String numLicencia = txtLicencia.getText().trim();

		            // ===== Validación exacta: no permitir '_' ======
		            if (numLicencia.isEmpty() || numLicencia.contains("_")) {
		                JOptionPane.showMessageDialog(
		                        Principal.this,
		                        "Debe ingresar un número de licencia válido (formato: AAAA-12345).",
		                        "Dato inválido",
		                        JOptionPane.WARNING_MESSAGE
		                );
		                continue; // volver a pedirlo
		            }

		            // Buscar doctor
		            Doctor doctor = clinica.buscarDoctorPorNumeroLicencia(numLicencia);

		            if (doctor == null) {
		                JOptionPane.showMessageDialog(
		                        Principal.this,
		                        "No existe un doctor con ese número de licencia.",
		                        "Doctor no encontrado",
		                        JOptionPane.ERROR_MESSAGE
		                );
		                continue; // volver a pedir licencia
		            }

		            // Validar que esté ACTIVO
		            if (!doctor.isActivo()) {
		                JOptionPane.showMessageDialog(
		                        Principal.this,
		                        "El doctor con ese número de licencia está INACTIVO y no puede visualizar consultas.",
		                        "Doctor inactivo",
		                        JOptionPane.ERROR_MESSAGE
		                );
		                continue;
		            }

		            // Si todo está bien => abrir listConsulta
		            listConsulta dialog = new listConsulta(numLicencia);
		            dialog.setLocationRelativeTo(Principal.this);
		            dialog.setVisible(true);

		            break; // salir del bucle
		        }
		    }
		});
		mnConsultas.add(mntmListarConsultas);



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