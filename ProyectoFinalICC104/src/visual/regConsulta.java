package visual;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

import logico.Cita;
import logico.Clinica;
import logico.Doctor;
import logico.Tratamiento;
import logico.Enfermedad;

public class regConsulta extends JDialog {

    private static final long serialVersionUID = 1L;

    private final JPanel contentPanel = new JPanel();

    private JComboBox<String> cbCitas;
    private JComboBox<String> cbTratamientos;
    private JComboBox<String> cbEnfermedades;

    private JTextField txtNumLicenciaDoctor;
    private JTextArea txtSintomas;
    private JTextArea txtDiagnostico;
    private JTextArea txtNotas;

    private JButton btnAgendarCita;
    private JButton btnAsignarTratamiento;

    private ArrayList<Cita> citasPendientes;
    private ArrayList<Tratamiento> tratamientos;
    private ArrayList<Enfermedad> enfermedades;

    // Número de licencia con el que se entró (para filtrar citas)
    private String numLicenciaActual;

    public regConsulta() {
        setTitle("Registrar consulta");
        setBounds(100, 100, 700, 537);
        setLocationRelativeTo(null);
        setModal(true);

        numLicenciaActual = null;

        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        // ===== Etiquetas =====
        JLabel lblCita = new JLabel("Cita pendiente:");
        lblCita.setBounds(20, 20, 120, 20);
        contentPanel.add(lblCita);

        JLabel lblLicencia = new JLabel("No. licencia doctor:");
        lblLicencia.setBounds(20, 55, 140, 20);
        contentPanel.add(lblLicencia);

        JLabel lblSintomas = new JLabel("Síntomas:");
        lblSintomas.setBounds(20, 90, 100, 20);
        contentPanel.add(lblSintomas);

        JLabel lblDiagnostico = new JLabel("Diagnóstico:");
        lblDiagnostico.setBounds(20, 190, 100, 20);
        contentPanel.add(lblDiagnostico);

        JLabel lblEnfermedad = new JLabel("Enfermedad:");
        lblEnfermedad.setBounds(20, 280, 100, 20);
        contentPanel.add(lblEnfermedad);

        JLabel lblTratamiento = new JLabel("Tratamiento:");
        lblTratamiento.setBounds(20, 315, 100, 20);
        contentPanel.add(lblTratamiento);

        JLabel lblNotas = new JLabel("Notas:");
        lblNotas.setBounds(20, 360, 100, 20);
        contentPanel.add(lblNotas);

        // ===== Combos y campos =====

        cbCitas = new JComboBox<>();
        cbCitas.setBounds(160, 20, 360, 24);
        contentPanel.add(cbCitas);

        btnAgendarCita = new JButton("Agendar cita");
        btnAgendarCita.setBounds(530, 20, 130, 24);
        btnAgendarCita.setVisible(false);
        btnAgendarCita.addActionListener(e -> abrirRegCitasYRefrescar());
        contentPanel.add(btnAgendarCita);

        txtNumLicenciaDoctor = new JTextField();
        txtNumLicenciaDoctor.setBounds(160, 55, 200, 24);
        contentPanel.add(txtNumLicenciaDoctor);

        txtSintomas = new JTextArea();
        txtSintomas.setLineWrap(true);
        txtSintomas.setWrapStyleWord(true);
        JScrollPane spSintomas = new JScrollPane(txtSintomas);
        spSintomas.setBounds(160, 90, 500, 80);
        contentPanel.add(spSintomas);

        txtDiagnostico = new JTextArea();
        txtDiagnostico.setLineWrap(true);
        txtDiagnostico.setWrapStyleWord(true);
        JScrollPane spDiagnostico = new JScrollPane(txtDiagnostico);
        spDiagnostico.setBounds(160, 190, 500, 80);
        contentPanel.add(spDiagnostico);

        cbEnfermedades = new JComboBox<>();
        cbEnfermedades.setBounds(160, 280, 260, 24);
        contentPanel.add(cbEnfermedades);

        cbTratamientos = new JComboBox<>();
        cbTratamientos.setBounds(160, 315, 260, 24);
        contentPanel.add(cbTratamientos);

        btnAsignarTratamiento = new JButton("Asignar tratamiento");
        btnAsignarTratamiento.setBounds(430, 315, 160, 24);
        btnAsignarTratamiento.addActionListener(e -> abrirVentanaAsignarTratamiento());
        contentPanel.add(btnAsignarTratamiento);

        txtNotas = new JTextArea();
        txtNotas.setLineWrap(true);
        txtNotas.setWrapStyleWord(true);
        JScrollPane spNotas = new JScrollPane(txtNotas);
        spNotas.setBounds(160, 360, 500, 80);
        contentPanel.add(spNotas);

        // ===== Botones inferiores =====
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(e -> registrarConsulta());
        buttonPane.add(btnRegistrar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        buttonPane.add(btnCancelar);

        // Cargar datos iniciales (sin filtrar por doctor)
        cargarCitasPendientes();
        cargarEnfermedades();
        inicializarComboTratamientosVacio();
    }

    /**
     * Constructor que recibe el número de licencia del doctor.
     * Aquí SÍ filtramos las citas solo para ese doctor.
     */
    public regConsulta(String numLicenciaDoctor) {
        this();
        this.numLicenciaActual = numLicenciaDoctor;
        txtNumLicenciaDoctor.setText(numLicenciaDoctor);
        txtNumLicenciaDoctor.setEditable(false);

        // Volvemos a cargar las citas, pero ahora filtradas por este doctor
        cargarCitasPendientesPorDoctor();
    }

    /**
     * Carga TODAS las citas pendientes (se usa cuando no se entra con licencia).
     */
    private void cargarCitasPendientes() {
        Clinica clinica = Clinica.getInstance();
        citasPendientes = clinica.listarCitasPendientes();
        cbCitas.removeAllItems();

        cbCitas.addItem("<Seleccione>");

        if (citasPendientes == null || citasPendientes.isEmpty()) {
            cbCitas.addItem("No hay citas pendientes");
            cbCitas.setEnabled(false);

            if (btnAgendarCita != null) {
                btnAgendarCita.setVisible(true);
            }
        } else {
            if (btnAgendarCita != null) {
                btnAgendarCita.setVisible(false);
            }

            for (Cita c : citasPendientes) {
                String texto = c.getCodigoCita() + " - " +
                        c.getPaciente().getNombre() + " " +
                        c.getPaciente().getApellido() + " / Dr. " +
                        c.getDoctor().getNombre() + " " + c.getDoctor().getApellido() +
                        " (" + c.getFechaCita() + " " + c.getHoraCita() + ")";
                cbCitas.addItem(texto);
            }
            cbCitas.setEnabled(true);
            cbCitas.setSelectedIndex(0);
        }
    }

    /**
     * Carga solo las citas pendientes del doctor indicado por numLicenciaActual.
     */
    private void cargarCitasPendientesPorDoctor() {
        Clinica clinica = Clinica.getInstance();

        ArrayList<Cita> todas = clinica.listarCitasPendientes();
        citasPendientes = new ArrayList<>();

        // Filtrar por número de licencia del doctor
        if (todas != null) {
            for (Cita c : todas) {
                if (c.getDoctor() != null &&
                        c.getDoctor().getNumeroLicencia().equalsIgnoreCase(numLicenciaActual)) {
                    citasPendientes.add(c);
                }
            }
        }

        cbCitas.removeAllItems();
        cbCitas.addItem("<Seleccione>");

        if (citasPendientes.isEmpty()) {
            cbCitas.addItem("No hay citas pendientes para este doctor");
            cbCitas.setEnabled(false);

            if (btnAgendarCita != null) {
                btnAgendarCita.setVisible(true);
            }
        } else {
            if (btnAgendarCita != null) {
                btnAgendarCita.setVisible(false);
            }

            for (Cita c : citasPendientes) {
                String texto = c.getCodigoCita() + " - " +
                        c.getPaciente().getNombre() + " " +
                        c.getPaciente().getApellido() +
                        " / Fecha: " + c.getFechaCita() + " " + c.getHoraCita();
                cbCitas.addItem(texto);
            }
            cbCitas.setEnabled(true);
            cbCitas.setSelectedIndex(0);
        }
    }

    /**
     * Abre la ventana de agendar cita y recarga las citas pendientes.
     * Si hay doctor logueado, recarga solo sus citas.
     */
    private void abrirRegCitasYRefrescar() {
        AgendarCita dialog = new AgendarCita();
        dialog.setModal(true);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        if (numLicenciaActual != null && !numLicenciaActual.isEmpty()) {
            cargarCitasPendientesPorDoctor();
        } else {
            cargarCitasPendientes();
        }
    }

    /**
     * Carga las enfermedades registradas en la clínica.
     * Primer ítem: "No especificar".
     */
    private void cargarEnfermedades() {
        cbEnfermedades.removeAllItems();
        enfermedades = Clinica.getInstance().listarEnfermedades(); // ajusta el nombre si es distinto

        cbEnfermedades.addItem("No especificar");
        if (enfermedades != null && !enfermedades.isEmpty()) {
            for (Enfermedad e : enfermedades) {
                String texto = e.getCodigoEnfermedad() + " - " + e.getNombre();
                cbEnfermedades.addItem(texto);
            }
            cbEnfermedades.setSelectedIndex(0);
            cbEnfermedades.setEnabled(true);
        } else {
            cbEnfermedades.setEnabled(false);
        }
    }

    /**
     * Deja el combo de tratamientos "vacío" como si no existieran
     * tratamientos registrados aún para esta consulta.
     */
    private void inicializarComboTratamientosVacio() {
        tratamientos = new ArrayList<>();
        cbTratamientos.removeAllItems();
        cbTratamientos.addItem("No hay tratamientos registrados");
        cbTratamientos.setEnabled(false);
    }

    /**
     * Carga los tratamientos disponibles desde la clínica.
     * Se usa principalmente luego de cerrar regTratamientos.
     */
    private void cargarTratamientos() {
        tratamientos = Clinica.getInstance().listarTratamientos();
        cbTratamientos.removeAllItems();

        if (tratamientos == null || tratamientos.isEmpty()) {
            cbTratamientos.addItem("No hay tratamientos registrados");
            cbTratamientos.setEnabled(false);
        } else {
            for (Tratamiento t : tratamientos) {
                String texto = t.getCodigoTratamiento() + " - " + t.getNombreTratamiento();
                cbTratamientos.addItem(texto);
            }
            cbTratamientos.setSelectedIndex(0);
            cbTratamientos.setEnabled(true);
        }
    }

    /**
     * Abre la ventana de registrar tratamiento y refresca la lista
     * cuando se guarde uno nuevo. Pasa el paciente de la cita seleccionada
     * para mostrar sus alergias en regTratamientos.
     */
    private void abrirVentanaAsignarTratamiento() {
        if (citasPendientes == null || citasPendientes.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe existir al menos una cita pendiente para asignar un tratamiento.",
                    "Sin citas",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int indiceComboCita = cbCitas.getSelectedIndex();
        if (indiceComboCita <= 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar una cita para asignar un tratamiento.",
                    "Cita requerida",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int indiceCita = indiceComboCita - 1;
        if (indiceCita < 0 || indiceCita >= citasPendientes.size()) {
            JOptionPane.showMessageDialog(
                    this,
                    "La cita seleccionada no es válida.",
                    "Cita inválida",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        Cita citaSeleccionada = citasPendientes.get(indiceCita);

        regTratamientos dialog = new regTratamientos(citaSeleccionada.getPaciente());
        dialog.setModal(true);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        // Al volver, recargamos los tratamientos
        cargarTratamientos();
    }

    /**
     * Lógica para registrar la consulta.
     */
    private void registrarConsulta() {
        // Validar cita seleccionada
        if (citasPendientes == null || citasPendientes.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "No hay citas disponibles para registrar una consulta.",
                    "Sin citas",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int indiceComboCita = cbCitas.getSelectedIndex();
        if (indiceComboCita <= 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar una cita pendiente.",
                    "Cita requerida",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int indiceCita = indiceComboCita - 1;
        if (indiceCita < 0 || indiceCita >= citasPendientes.size()) {
            JOptionPane.showMessageDialog(
                    this,
                    "La cita seleccionada no es válida.",
                    "Cita inválida",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        Cita citaSeleccionada = citasPendientes.get(indiceCita);

        // Validar número de licencia (aunque ya esté fijo, validamos por seguridad)
        String numLicencia = txtNumLicenciaDoctor.getText().trim();
        if (numLicencia.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe indicar el número de licencia del doctor que atiende la consulta.",
                    "Licencia requerida",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Clinica clinica = Clinica.getInstance();

        Doctor doctorAtiende = clinica.buscarDoctorPorNumeroLicencia(numLicencia);
        if (doctorAtiende == null) {
            JOptionPane.showMessageDialog(this,
                    "No se encontró un doctor con ese número de licencia.",
                    "Doctor no encontrado",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!doctorAtiende.isActivo()) {
            JOptionPane.showMessageDialog(this,
                    "El doctor con ese número de licencia se encuentra INACTIVO y no puede atender consultas.",
                    "Doctor inactivo",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Doctor doctorDeLaCita = citaSeleccionada.getDoctor();
        boolean puedeAtender = false;

        if (doctorAtiende.getCodigoDoctor().equals(doctorDeLaCita.getCodigoDoctor())) {
            puedeAtender = true;
        } else if (doctorAtiende.getEspecialidad().equalsIgnoreCase(doctorDeLaCita.getEspecialidad())) {
            puedeAtender = true;
        }

        if (!puedeAtender) {
            JOptionPane.showMessageDialog(this,
                    "El doctor indicado no puede atender esta consulta.\n" +
                            "Solo puede hacerlo el doctor de la cita o un doctor de la misma especialidad.",
                    "Doctor no autorizado",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sintomas = txtSintomas.getText().trim();
        String diagnostico = txtDiagnostico.getText().trim();
        String notas = txtNotas.getText().trim();

        if (sintomas.isEmpty() || diagnostico.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Los campos de síntomas y diagnóstico son obligatorios.",
                    "Datos incompletos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validar tratamientos
        if (tratamientos == null || tratamientos.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "No hay tratamientos registrados. Debe asignar un tratamiento a la consulta.",
                    "Tratamiento requerido",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int indiceTrat = cbTratamientos.getSelectedIndex();
        if (indiceTrat < 0 || indiceTrat >= tratamientos.size()) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un tratamiento válido.",
                    "Tratamiento inválido",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        Tratamiento tratSeleccionado = tratamientos.get(indiceTrat);

        // Enfermedad seleccionada (opcional)
        String codigoEnfermedad = null;
        if (enfermedades != null && !enfermedades.isEmpty()
                && cbEnfermedades.isEnabled()
                && cbEnfermedades.getSelectedIndex() > 0) {
            int idxEnf = cbEnfermedades.getSelectedIndex() - 1; // 0 es "No especificar"
            if (idxEnf >= 0 && idxEnf < enfermedades.size()) {
                Enfermedad enf = enfermedades.get(idxEnf);
                codigoEnfermedad = enf.getCodigoEnfermedad();
            }
        }

        String codigoCita = citaSeleccionada.getCodigoCita();
        String codigoTratamiento = tratSeleccionado.getCodigoTratamiento();

        if (clinica.registrarConsulta(codigoCita, sintomas, diagnostico,
                codigoTratamiento, notas, codigoEnfermedad) != null) {

            JOptionPane.showMessageDialog(this,
                    "Consulta registrada correctamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

            txtSintomas.setText("");
            txtDiagnostico.setText("");
            txtNotas.setText("");

            // Volver a cargar citas: filtradas si hay licencia fija
            if (numLicenciaActual != null && !numLicenciaActual.isEmpty()) {
                cargarCitasPendientesPorDoctor();
            } else {
                cargarCitasPendientes();
            }

            // Dejar combo de tratamiento vacío obligando a crear uno nuevo
            inicializarComboTratamientosVacio();
        } else {
            JOptionPane.showMessageDialog(this,
                    "No se pudo registrar la consulta. Verifique los datos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
