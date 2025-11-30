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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
    private JButton regPacButton;

    private ArrayList<Cita> citasPendientes;
    private ArrayList<Tratamiento> tratamientos;
    private ArrayList<Enfermedad> enfermedades;

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

        JLabel lblSintomas = new JLabel("Sintomas:");
        lblSintomas.setBounds(20, 90, 100, 20);
        contentPanel.add(lblSintomas);

        JLabel lblDiagnostico = new JLabel("Diagnostico:");
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
        cbEnfermedades.setBounds(160, 280, 500, 24);
        contentPanel.add(cbEnfermedades);

        cbTratamientos = new JComboBox<>();
        cbTratamientos.setBounds(160, 315, 500, 24);
        contentPanel.add(cbTratamientos);

        txtNotas = new JTextArea();
        txtNotas.setLineWrap(true);
        txtNotas.setWrapStyleWord(true);
        JScrollPane spNotas = new JScrollPane(txtNotas);
        spNotas.setBounds(160, 360, 500, 80);
        contentPanel.add(spNotas);
        
        regPacButton = new JButton("Registrar este paciente");
        regPacButton.setBounds(413, 51, 247, 29);
        contentPanel.add(regPacButton);
        regPacButton.setVisible(false);
        regPacButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cita c = getCitaSeleccionadaActual(); 
                if (c != null && c.getPaciente() != null) {
                    regPaciente dialog = new regPaciente(c.getPaciente());
                    dialog.setModal(true);
                    dialog.setLocationRelativeTo(regConsulta.this);
                    dialog.setVisible(true);

                    if (numLicenciaActual != null && !numLicenciaActual.isEmpty()) {
                        cargarCitasPendientesPorDoctor();
                    } else {
                        cargarCitasPendientes();
                    }
                    actualizarRegPacButton();
                } else {
                    JOptionPane.showMessageDialog(regConsulta.this, "No hay paciente válido seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
 
        cbCitas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarRegPacButton(); 
            }
        });
  
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(e -> registrarConsulta());
        buttonPane.add(btnRegistrar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        buttonPane.add(btnCancelar);

        cargarCitasPendientes();
        cargarEnfermedades();
        cargarTratamientos();
    }

    public regConsulta(String numLicenciaDoctor) {
        this();
        this.numLicenciaActual = numLicenciaDoctor;
        txtNumLicenciaDoctor.setText(numLicenciaDoctor);
        txtNumLicenciaDoctor.setEditable(false);
        cargarCitasPendientesPorDoctor();
    }

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
                if (c.getPaciente() != null) {
                    String texto = c.getCodigoCita() + " - " +
                            c.getPaciente().getNombre() + " " +
                            c.getPaciente().getApellido() + " / Dr. " +
                            c.getDoctor().getNombre() + " " + c.getDoctor().getApellido() +
                            " (" + c.getFechaCita() + " " + c.getHoraCita() + ")";
                    cbCitas.addItem(texto);
                }
            }
            cbCitas.setEnabled(true);
            cbCitas.setSelectedIndex(0);
        }

        actualizarRegPacButton();
    }

    private void cargarCitasPendientesPorDoctor() {
        Clinica clinica = Clinica.getInstance();

        ArrayList<Cita> todas = clinica.listarCitasPendientes();
        citasPendientes = new ArrayList<>();

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
                if (c.getPaciente() != null) {
                    String texto = c.getCodigoCita() + " - " +
                            c.getPaciente().getNombre() + " " +
                            c.getPaciente().getApellido() +
                            " / Fecha: " + c.getFechaCita() + " " + c.getHoraCita();
                    cbCitas.addItem(texto);
                }
            }
            cbCitas.setEnabled(true);
            cbCitas.setSelectedIndex(0);
        }

        actualizarRegPacButton();
    }

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

    private void cargarEnfermedades() {
        cbEnfermedades.removeAllItems();
        enfermedades = Clinica.getInstance().listarEnfermedades();

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

    private void cargarTratamientos() {
        tratamientos = Clinica.getInstance().listarTratamientos();
        cbTratamientos.removeAllItems();

        if (tratamientos == null || tratamientos.isEmpty()) {
            cbTratamientos.addItem("No hay tratamientos registrados - Registre uno desde el menu Tratamientos");
            cbTratamientos.setEnabled(false);
        } else {
            cbTratamientos.addItem("<Seleccione>");
            for (Tratamiento t : tratamientos) {
                String texto = t.getCodigoTratamiento() + " - " + t.getNombreTratamiento();
                cbTratamientos.addItem(texto);
            }
            cbTratamientos.setSelectedIndex(0);
            cbTratamientos.setEnabled(true);
        }
    }

    private void registrarConsulta() {
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
                    "La cita seleccionada no es valida.",
                    "Cita invalida",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        Cita citaSeleccionada = citasPendientes.get(indiceCita);
        


        String numLicencia = txtNumLicenciaDoctor.getText().trim();
        if (numLicencia.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe indicar el numero de licencia del doctor que atiende la consulta.",
                    "Licencia requerida",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Clinica clinica = Clinica.getInstance();

        Doctor doctorAtiende = clinica.buscarDoctorPorNumeroLicencia(numLicencia);
        if (doctorAtiende == null) {
            JOptionPane.showMessageDialog(this,
                    "No se encontro un doctor con ese numero de licencia.",
                    "Doctor no encontrado",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!doctorAtiende.isActivo()) {
            JOptionPane.showMessageDialog(this,
                    "El doctor con ese numero de licencia se encuentra INACTIVO y no puede atender consultas.",
                    "Doctor inactivo",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (regPacButton.isVisible()) {
            JOptionPane.showMessageDialog(this,
                    "Complete los datos de este paciente",
                    "Error",
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
                    "Los campos de sintomas y diagnostico son obligatorios.",
                    "Datos incompletos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (tratamientos == null || tratamientos.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "No hay tratamientos registrados en el sistema.\n" +
                    "Por favor, registre al menos un tratamiento desde:\n" +
                    "Menu > Tratamientos > Registrar Tratamiento",
                    "Tratamiento requerido",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int indiceTrat = cbTratamientos.getSelectedIndex();
        if (indiceTrat <= 0) {
            JOptionPane.showMessageDialog(this,
                    "Debe seleccionar un tratamiento.",
                    "Tratamiento requerido",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int indiceRealTrat = indiceTrat - 1;
        if (indiceRealTrat < 0 || indiceRealTrat >= tratamientos.size()) {
            JOptionPane.showMessageDialog(this,
                    "El tratamiento seleccionado no es valido.",
                    "Tratamiento invalido",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        Tratamiento tratSeleccionado = tratamientos.get(indiceRealTrat);

        String codigoEnfermedad = null;
        if (enfermedades != null && !enfermedades.isEmpty()
                && cbEnfermedades.isEnabled()
                && cbEnfermedades.getSelectedIndex() > 0) {
            int idxEnf = cbEnfermedades.getSelectedIndex() - 1;
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
                    "Exito",
                    JOptionPane.INFORMATION_MESSAGE);

            txtSintomas.setText("");
            txtDiagnostico.setText("");
            txtNotas.setText("");

            if (numLicenciaActual != null && !numLicenciaActual.isEmpty()) {
                cargarCitasPendientesPorDoctor();
            } else {
                cargarCitasPendientes();
            }

            cargarTratamientos();
        } else {
            JOptionPane.showMessageDialog(this,
                    "No se pudo registrar la consulta. Verifique los datos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private Cita getCitaSeleccionadaActual() {
        int sel = cbCitas.getSelectedIndex();
        if (sel <= 0) return null;
        int idx = sel - 1;
        if (citasPendientes == null || idx < 0 || idx >= citasPendientes.size()) return null;
        return citasPendientes.get(idx);
    }
   
    private void actualizarRegPacButton() {
        Cita actual = getCitaSeleccionadaActual();
        boolean mostrar = false;
        if (actual != null && actual.getPaciente() != null) {
            String codigo = actual.getPaciente().getCodigoPaciente();
            if (codigo != null && codigo.equals("XX")) {
                mostrar = true;
            }
        }
        regPacButton.setVisible(mostrar);
    }
}
