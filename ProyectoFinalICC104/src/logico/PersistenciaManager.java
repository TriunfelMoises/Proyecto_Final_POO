package logico;

import java.io.*;
import logico.Control;
import logico.User;

public class PersistenciaManager {

	public static void guardarDatos() {
		try {
			FileOutputStream fos = new FileOutputStream("control.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(Control.getInstance());
			oos.close();
			fos.close();
			System.out.println("Datos guardados exitosamente");
		} catch (Exception e) {
			System.err.println("Error guardando datos: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static boolean cargarDatos() {
		File archivo = new File("control.dat");

		if (!archivo.exists()) {
			System.out.println("Archivo de datos no existe");
			return false;
		}

		try (FileInputStream fis = new FileInputStream(archivo); ObjectInputStream ois = new ObjectInputStream(fis)) {

			// Cargar Control desde archivo
			Control temp = (Control) ois.readObject();
			Control.setControl(temp);

			System.out.println("Datos cargados exitosamente. Usuarios: " + Control.getInstance().getMisUsers().size());
			return true;

		} catch (IOException | ClassNotFoundException ex) {
			System.err.println("Error cargando datos: " + ex.getMessage());
			ex.printStackTrace();
			return false;
		}
	}

	public static void crearAdminPorDefecto() {
		try {
			// Crear admin por defecto
			User admin = new User("Administrador", "Admin", "Admin");
			Control.getInstance().regUser(admin);

			// Guardar
			guardarDatos();
			System.out.println("Admin por defecto creado y guardado");

		} catch (Exception e) {
			System.err.println("Error creando admin: " + e.getMessage());
			e.printStackTrace();
		}
	}
}