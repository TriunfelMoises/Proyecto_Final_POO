package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logico.Clinica;
import logico.Control;
import logico.User;

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

	private FileInputStream empresa;
	private FileOutputStream empresa2;
	private ObjectInputStream empresaRead;
	private ObjectOutputStream empresaWrite;

	public static void main(String[] args) {
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
				String u = textField.getText();
				String p = new String(textField_1.getPassword());
				if (Control.getInstance().confirmLogin(u, p)) {
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
		btnSalir.addActionListener(e -> dispose());
		btnSalir.setBounds(165, 160, 135, 28);
		contentPane.add(btnSalir);

		cargarPersistenciaOSembrarAdmin();
	}

	private void cargarPersistenciaOSembrarAdmin() {
		try {
			// Intentar cargar datos guardados
			empresa = new FileInputStream("control.dat");
			empresaRead = new ObjectInputStream(empresa);
			Control temp = (Control) empresaRead.readObject();
			Control.setControl(temp);

			// AGREGAR ESTAS LÍNEAS para restaurar Clinica
			Clinica.setInstance(temp.getClinica());
			Clinica.getInstance().setarContadores();

			empresaRead.close();
			empresa.close();

			System.out.println("Datos cargados exitosamente");

		} catch (FileNotFoundException fnf) {
			// No existe el archivo, crear admin por defecto
			try {
				empresa2 = new FileOutputStream("control.dat");
				empresaWrite = new ObjectOutputStream(empresa2);
				User aux = new User("Administrador", "Admin", "Admin");
				Control.getInstance().regUser(aux);
				empresaWrite.writeObject(Control.getInstance());
				empresaWrite.close();
				empresa2.close();

				System.out.println("Archivo creado con admin por defecto");

			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
///