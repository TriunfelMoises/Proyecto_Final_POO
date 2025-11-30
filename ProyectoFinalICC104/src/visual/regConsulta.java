package visual;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import logico.*;

import java.awt.*;
import java.util.ArrayList;

public class regConsulta extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();

    private JComboBox<String> cbCitas;
    private JComboBox<String> cbEnfermedades;

    private JTextArea txtSintomas;
    private JTextArea txtDiagnostico;
    private JTextArea txtNotas;
    private JTextArea txtAlergias;

    private JLabel lblTratamientoAsignado;

    private JButton btnAsignarTratamiento;
    private JButton btnModificarTratamiento;
    private JButton btnVerAlergias;

    // ⭐️ NUEVO BOTÓN
    private JButton btnRegistrarPaciente;

    private ArrayList<Cita> citasPendientes;
    private Tratamiento tratamientoAsignado = null;

    public regConsulta() {
        super((Frame) null, "Registrar consulta", true);
        inicializar();
    }

    private void inicializar() {

        setBounds(100, 100, 820, 590);
        setResizable(false);
        setLocationRelativeTo(null);

        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        contentPanel.setLayout(null);

        JLabel lblTitulo = new JLabel("Registrar consulta");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblTitulo.setBounds(20, 10, 300, 30);
        contentPanel.add(lblTitulo);

        // =============================
        //            CITA
        // =============================
        JLabel lblCita = new JLabel("Cita pendiente:");
        lblCita.setBounds(20, 60, 120, 25);
        contentPanel.add(lblCita);

        cbCitas = new JComboBox<>();
        cbCitas.setBounds(150, 60, 350, 25);
        contentPanel.add(cbCitas);

        cargarCitasPendientes();
        cbCitas.addActionListener(e -> actualizarDatosCita());

        // =============================
        //         ALERGIAS
        // =============================
        JLabel lblAler = new JLabel("Alergias:");
        lblAler.setBounds(20, 100, 120, 25);
        contentPanel.add(lblAler);

        txtAlergias = new JTextArea();
        txtAlergias.setEditable(false);
        JScrollPane spAler = new JScrollPane(txtAlergias);
        spAler.setBounds(150, 100, 350, 60);
        contentPanel.add(spAler);

        btnVerAlergias = new JButton("Ver detalles");
        btnVerAlergias.setBounds(510, 100, 120, 28);
        btnVerAlergias.addActionListener(e -> verDetallesAlergias());
        contentPanel.add(btnVerAlergias);

        // ⭐️ BOTÓN REGISTRAR PACIENTE
        btnRegistrarPaciente = new JButton("Registrar este paciente");
        btnRegistrarPaciente.setBounds(510, 135, 200, 28);
        btnRegistrarPaciente.setVisible(false);
        btnRegistrarPaciente.addActionListener(e -> registrarNuevoPaciente());
        contentPanel.add(btnRegistrarPaciente);

        // =============================
        //        SÍNTOMAS
        // =============================
        JLabel lblSint = new JLabel("Síntomas:");
        lblSint.setBounds(20, 180, 120, 25);
        contentPanel.add(lblSint);

        txtSintomas = new JTextArea();
        JScrollPane spSint = new JScrollPane(txtSintomas);
        spSint.setBounds(150, 180, 620, 70);
        contentPanel.add(spSint);

        // =============================
        //       DIAGNÓSTICO
        // =============================
        JLabel lblDiag = new JLabel("Diagnóstico:");
        lblDiag.setBounds(20, 265, 120, 25);
        contentPanel.add(lblDiag);

        txtDiagnostico = new JTextArea();
        JScrollPane spDiag = new JScrollPane(txtDiagnostico);
        spDiag.setBounds(150, 265, 620, 70);
        contentPanel.add(spDiag);

        // =============================
        //        ENFERMEDAD
        // =============================
        JLabel lblEnf = new JLabel("Enfermedad:");
        lblEnf.setBounds(20, 350, 120, 25);
        contentPanel.add(lblEnf);

        cbEnfermedades = new JComboBox<>();
        cbEnfermedades.setBounds(150, 350, 250, 25);
        contentPanel.add(cbEnfermedades);

        JButton btnAddEnf = new JButton("Agregar");
        btnAddEnf.setBounds(410, 350, 100, 28);
        btnAddEnf.addActionListener(e -> abrirRegEnfermedad());
        contentPanel.add(btnAddEnf);

        cargarEnfermedades();

        // =============================
        //       TRATAMIENTO
        // =============================
        JLabel lblTrat = new JLabel("Tratamiento:");
        lblTrat.setBounds(20, 390, 120, 25);
        contentPanel.add(lblTrat);

        lblTratamientoAsignado = new JLabel("Ninguno");
        lblTratamientoAsignado.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblTratamientoAsignado.setBounds(150, 390, 200, 25);
        contentPanel.add(lblTratamientoAsignado);

        btnAsignarTratamiento = new JButton("Asignar");
        btnAsignarTratamiento.setBounds(360, 388, 90, 28);
        btnAsignarTratamiento.addActionListener(e -> abrirRegTratamientos());
        contentPanel.add(btnAsignarTratamiento);

        btnModificarTratamiento = new JButton("Modificar");
        btnModificarTratamiento.setBounds(460, 388, 100, 28);
        btnModificarTratamiento.setEnabled(false);
        btnModificarTratamiento.addActionListener(e -> modificarTratamiento());
        contentPanel.add(btnModificarTratamiento);

        // =============================
        //           NOTAS
        // =============================
        JLabel lblNotas = new JLabel("Notas:");
        lblNotas.setBounds(20, 430, 120, 25);
        contentPanel.add(lblNotas);

        txtNotas = new JTextArea();
        JScrollPane spNotas = new JScrollPane(txtNotas);
        spNotas.setBounds(150, 430, 620, 70);
        contentPanel.add(spNotas);

        // =============================
        //        BOTONES FINALES
        // =============================
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(520, 510, 100, 30);
        btnCancelar.addActionListener(e -> dispose());
        contentPanel.add(btnCancelar);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(630, 510, 100, 30);
        btnRegistrar.addActionListener(e -> registrarConsulta());
        contentPanel.add(btnRegistrar);
    }

    // =============================
    //       MÉTODOS AUXILIARES
    // =============================
    private void cargarCitasPendientes() {
        cbCitas.removeAllItems();
        citasPendientes = Clinica.getInstance().listarCitasPendientes();

        for (Cita c : citasPendientes) {
            cbCitas.addItem(c.getCodigoCita());
        }
    }

    private Cita getCitaSeleccionada() {
        int i = cbCitas.getSelectedIndex();
        if (i < 0) return null;
        return citasPendientes.get(i);
    }

    private void actualizarDatosCita() {

        Cita c = getCitaSeleccionada();
        if (c == null) return;

        Paciente p = c.getPaciente();
        if (p == null) return;

        // ⭐ DETECCIÓN REAL DE PREPACIENTE
        boolean esPre = p.getCodigoPaciente().equals("XX");
        btnRegistrarPaciente.setVisible(esPre);

        // Alergias
        if (p.getAlergias() == null || p.getAlergias().isEmpty()) {
            txtAlergias.setText("No tiene alergias.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Alergia al : p.getAlergias()) {
                sb.append(al.getNombre()).append(", ");
            }
            txtAlergias.setText(sb.substring(0, sb.length() - 2));
        }
    }


    private void verDetallesAlergias() {
        Cita c = getCitaSeleccionada();
        if (c == null) return;

        Paciente p = c.getPaciente();
        if (p == null || p.getAlergias() == null || p.getAlergias().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No tiene alergias registradas.");
            return;
        }

        StringBuilder sb = new StringBuilder("Alergias del paciente:\n\n");
        for (Alergia a : p.getAlergias()) {
            sb.append("- ").append(a.getNombre())
              .append(" (Tipo: ").append(a.getTipo()).append(")\n");
        }

        JOptionPane.showMessageDialog(this, sb.toString(), "Detalles de alergias",
                                      JOptionPane.INFORMATION_MESSAGE);
    }

    private void cargarEnfermedades() {
        cbEnfermedades.removeAllItems();
        ArrayList<Enfermedad> lista = Clinica.getInstance().listarEnfermedades();
        for (Enfermedad e : lista) {
            cbEnfermedades.addItem(e.getNombre());
        }
    }

    private void abrirRegEnfermedad() {
        regEnfermedades win = new regEnfermedades();
        win.setVisible(true);
        cargarEnfermedades();
    }

    private void abrirRegTratamientos() {
        Cita c = getCitaSeleccionada();
        if (c == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una cita.");
            return;
        }

        regTratamiento rt = new regTratamiento(c);
        rt.setVisible(true);

        tratamientoAsignado = rt.getTratamientoTemporal();

        if (tratamientoAsignado != null) {
            Clinica.getInstance().agregarTratamiento(tratamientoAsignado);
            lblTratamientoAsignado.setText(tratamientoAsignado.getCodigoTratamiento());
            btnAsignarTratamiento.setEnabled(false);
            btnModificarTratamiento.setEnabled(true);
        }
    }

    private void modificarTratamiento() {
        if (tratamientoAsignado == null) return;

        regTratamiento rt = new regTratamiento(tratamientoAsignado);
        rt.setVisible(true);

        tratamientoAsignado = rt.getTratamientoTemporal();

        if (tratamientoAsignado != null) {
            lblTratamientoAsignado.setText(tratamientoAsignado.getCodigoTratamiento());
        }
    }

    private void registrarNuevoPaciente() {

        Cita c = getCitaSeleccionada();
        if (c == null) return;

        Paciente pre = c.getPaciente();

        regPaciente win = new regPaciente(pre);
        win.setModal(true);
        win.setVisible(true);

        // ➜ Buscar ya el paciente REAL
        Paciente real = Clinica.getInstance().buscarPacientePorCedula(pre.getCedula());

        if (real != null) {
            c.setPaciente(real);      // reemplazar prepaciente
        }

        actualizarDatosCita(); // refresca alergias y oculta el botón correctamente
    }


    private void registrarConsulta() {

        Cita c = getCitaSeleccionada();
        if (c == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una cita.");
            return;
        }

        // Prepaciente sin registrar → NO PERMITIR
        if (Clinica.getInstance().buscarPacientePorCedula(c.getPaciente().getCedula()) == null) {
            JOptionPane.showMessageDialog(this, 
                    "Debe registrar este paciente antes de registrar la consulta.");
            return;
        }

        if (tratamientoAsignado == null) {
            JOptionPane.showMessageDialog(this, "Debe asignar un tratamiento.");
            return;
        }

        String sintomas = txtSintomas.getText().trim();
        String diagnostico = txtDiagnostico.getText().trim();
        String notas = txtNotas.getText().trim();

        String enfNombre = (String) cbEnfermedades.getSelectedItem();
        Enfermedad enf = Clinica.getInstance().buscarEnfermedadPorNombre(enfNombre);

        Clinica.getInstance().registrarConsulta(
                c.getCodigoCita(),
                sintomas,
                diagnostico,
                tratamientoAsignado.getCodigoTratamiento(),
                notas,
                enf != null ? enf.getCodigoEnfermedad() : null
        );

        JOptionPane.showMessageDialog(this, "Consulta registrada exitosamente.");
    }

}
