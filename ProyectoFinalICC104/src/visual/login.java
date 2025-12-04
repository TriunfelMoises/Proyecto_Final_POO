package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import logico.Control;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class login extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField textField_1;

	public static void main(String[] args) {
		// Iniciar servidor de respaldo
		new Thread(() -> {
			Server.Servidor.main(new String[] {});
		}, "ServidorAutoStarter").start();

		EventQueue.invokeLater(() -> {
			try {
				login frame = new login();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public login() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(login.class.getResource("/recursos/doc.jpg")));
		setTitle("Login - Clínica");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 340, 260);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(20, 30, 100, 20);
		contentPane.add(lblUsuario);

		textField = new JTextField();
		textField.setBounds(20, 55, 280, 24);
		contentPane.add(textField);

		JLabel lblPass = new JLabel("Contraseña:");
		lblPass.setBounds(20, 90, 100, 20);
		contentPane.add(lblPass);

		textField_1 = new JPasswordField();
		textField_1.setBounds(20, 115, 280, 24);
		contentPane.add(textField_1);

		JButton btnLogin = new JButton("Iniciar sesión");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String usuario = textField.getText().trim();
				String password = new String(textField_1.getPassword()).trim();

				if (usuario.isEmpty() || password.isEmpty()) {
					JOptionPane.showMessageDialog(login.this, "Ingrese usuario y contraseña", "Campos vacíos",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				if (Control.getInstance().confirmLogin(usuario, password)) {
					Principal frame = new Principal();
					dispose();
					frame.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(login.this, "Usuario o contraseña incorrectos",
							"Error de autenticación", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnLogin.setBounds(20, 160, 135, 28);
		contentPane.add(btnLogin);

		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(e -> System.exit(0));
		btnSalir.setBounds(165, 160, 135, 28);
		contentPane.add(btnSalir);

		cargarPersistenciaOSembrarAdmin();
	}

	private void cargarPersistenciaOSembrarAdmin() {
		// Intentar cargar datos existentes
		if (logico.PersistenciaManager.cargarDatos()) {
			System.out.println("Sistema cargado desde archivo");
		} else {
			// No hay archivo o error, crear admin por defecto
			logico.PersistenciaManager.crearAdminPorDefecto();
		}
	}
}