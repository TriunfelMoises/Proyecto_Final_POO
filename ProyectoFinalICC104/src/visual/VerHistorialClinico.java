package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.TitledBorder;
import logico.Clinica;
import logico.Control;
import logico.Paciente;
import logico.Consulta;
import logico.Vacuna;
import logico.RegistroVacuna;
import logico.Alergia;
import logico.Doctor;
import java.util.ArrayList;
import java.awt.Color;

public class VerHistorialClinico extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTextField txtBusqueda;
    private JLabel lblNombrePaciente;
    private JLabel lblCedula;
    private JLabel lblTipoSangre;
    private JLabel lblFechaRegistro;
    private JTextArea txtAlergias;
    
    private JTable tableConsultas;
    private DefaultTableModel modeloConsultas;
    
    private JTextArea txtResumen;
    
    private JTable tableVacunasAplicadas;
    private DefaultTableModel modeloVacunasAplicadas;
    
    private JTable tableVacunasFaltantes;
    private DefaultTableModel modeloVacunasFaltantes;
    
    private Paciente pacienteActual = null;
    private String numeroLicenciaDoctor = null; // Para filtrar consultas si es doctor
    private boolean esAdministrador = false;

    public VerHistorialClinico() {
        setTitle("Ver Historial Clínico");
        setBounds(100, 100, 1000, 700);
        setLocationRelativeTo(null);
        setModal(true);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        // Verificar tipo de usuario
        verificarTipoUsuario();

        Font labelFont = new Font("Tahoma", Font.PLAIN, 12);
        Font boldFont = new Font("Tahoma", Font.BOLD, 12);

        // ========== PANEL BÚSQUEDA ==========
        JPanel panelBusqueda = new JPanel();
        panelBusqueda.setBorder(new TitledBorder(null, "Buscar Paciente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelBusqueda.setBounds(10, 11, 964, 80);
        panelBusqueda.setLayout(null);
        contentPanel.add(panelBusqueda);

        JLabel lblBuscar = new JLabel("Cédula o Código:");
        lblBuscar.setFont(labelFont);
        lblBuscar.setBounds(10, 30, 120, 22);
        panelBusqueda.add(lblBuscar);

        txtBusqueda = new JTextField();
        txtBusqueda.setBounds(140, 30, 200, 22);
        panelBusqueda.add(txtBusqueda);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(350, 29, 100, 24);
        btnBuscar.addActionListener(e -> buscarPaciente());
        panelBusqueda.add(btnBuscar);

        // ========== PANEL INFORMACIÓN PACIENTE ==========
        JPanel panelInfo = new JPanel();
        panelInfo.setBorder(new TitledBorder(null, "Información del Paciente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelInfo.setBounds(10, 102, 964, 150);
        panelInfo.setLayout(null);
        contentPanel.add(panelInfo);

        JLabel lblNombreLbl = new JLabel("Nombre:");
        lblNombreLbl.setFont(labelFont);
        lblNombreLbl.setBounds(10, 25, 80, 22);
        panelInfo.add(lblNombreLbl);

        lblNombrePaciente = new JLabel("---");
        lblNombrePaciente.setFont(boldFont);
        lblNombrePaciente.setBounds(100, 25, 400, 22);
        panelInfo.add(lblNombrePaciente);

        JLabel lblCedulaLbl = new JLabel("Cédula:");
        lblCedulaLbl.setFont(labelFont);
        lblCedulaLbl.setBounds(520, 25, 80, 22);
        panelInfo.add(lblCedulaLbl);

        lblCedula = new JLabel("---");
        lblCedula.setFont(boldFont);
        lblCedula.setBounds(610, 25, 344, 22);
        panelInfo.add(lblCedula);

        JLabel lblTipoSangreLbl = new JLabel("Tipo Sangre:");
        lblTipoSangreLbl.setFont(labelFont);
        lblTipoSangreLbl.setBounds(10, 55, 80, 22);
        panelInfo.add(lblTipoSangreLbl);

        lblTipoSangre = new JLabel("---");
        lblTipoSangre.setFont(boldFont);
        lblTipoSangre.setBounds(100, 55, 100, 22);
        panelInfo.add(lblTipoSangre);

        JLabel lblFechaRegLbl = new JLabel("Registro:");
        lblFechaRegLbl.setFont(labelFont);
        lblFechaRegLbl.setBounds(220, 55, 80, 22);
        panelInfo.add(lblFechaRegLbl);

        lblFechaRegistro = new JLabel("---");
        lblFechaRegistro.setFont(boldFont);
        lblFechaRegistro.setBounds(310, 55, 200, 22);
        panelInfo.add(lblFechaRegistro);

        JLabel lblAlergiasLbl = new JLabel("Alergias:");
        lblAlergiasLbl.setFont(labelFont);
        lblAlergiasLbl.setBounds(10, 85, 80, 22);
        panelInfo.add(lblAlergiasLbl);

        JScrollPane spAlergias = new JScrollPane();
        spAlergias.setBounds(100, 85, 854, 55);
        panelInfo.add(spAlergias);

        txtAlergias = new JTextArea();
        txtAlergias.setEditable(false);
        txtAlergias.setLineWrap(true);
        txtAlergias.setWrapStyleWord(true);
        spAlergias.setViewportView(txtAlergias);

        // ========== TABS ==========
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(10, 263, 964, 340);
        contentPanel.add(tabbedPane);

        // TAB 1: CONSULTAS
        JPanel tabConsultas = new JPanel();
        tabbedPane.addTab("Consultas", null, tabConsultas, null);
        tabConsultas.setLayout(new BorderLayout(0, 0));

        JScrollPane spConsultas = new JScrollPane();
        tabConsultas.add(spConsultas, BorderLayout.CENTER);

        String[] columnasConsultas = {"Código", "Fecha", "Doctor", "Diagnóstico", "Tratamiento", "En Resumen", "Vigilancia"};
        modeloConsultas = new DefaultTableModel(columnasConsultas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableConsultas = new JTable(modeloConsultas);
        tableConsultas.getColumnModel().getColumn(3).setPreferredWidth(150);
        spConsultas.setViewportView(tableConsultas);

        JPanel panelBotonesConsultas = new JPanel();
        panelBotonesConsultas.setLayout(new FlowLayout(FlowLayout.RIGHT));
        tabConsultas.add(panelBotonesConsultas, BorderLayout.SOUTH);

        JButton btnVerDetalleConsulta = new JButton("Ver Detalles");
        btnVerDetalleConsulta.addActionListener(e -> verDetalleConsulta());
        panelBotonesConsultas.add(btnVerDetalleConsulta);

        JButton btnMarcarResumen = new JButton("Marcar para Resumen");
        btnMarcarResumen.addActionListener(e -> marcarParaResumen());
        panelBotonesConsultas.add(btnMarcarResumen);

        JButton btnDesmarcarResumen = new JButton("Desmarcar de Resumen");
        btnDesmarcarResumen.addActionListener(e -> desmarcarDeResumen());
        panelBotonesConsultas.add(btnDesmarcarResumen);

        // TAB 2: RESUMEN AUTOMÁTICO
        JPanel tabResumen = new JPanel();
        tabbedPane.addTab("Resumen Automático", null, tabResumen, null);
        tabResumen.setLayout(new BorderLayout(0, 0));

        JScrollPane spResumen = new JScrollPane();
        tabResumen.add(spResumen, BorderLayout.CENTER);

        txtResumen = new JTextArea();
        txtResumen.setEditable(false);
        txtResumen.setLineWrap(true);
        txtResumen.setWrapStyleWord(true);
        txtResumen.setFont(new Font("Monospaced", Font.PLAIN, 12));
        spResumen.setViewportView(txtResumen);

        JPanel panelBotonesResumen = new JPanel();
        panelBotonesResumen.setLayout(new FlowLayout(FlowLayout.RIGHT));
        tabResumen.add(panelBotonesResumen, BorderLayout.SOUTH);

        JButton btnGenerarResumen = new JButton("Generar Resumen");
        btnGenerarResumen.addActionListener(e -> generarResumen());
        panelBotonesResumen.add(btnGenerarResumen);

        // TAB 3: VACUNACIÓN
        JPanel tabVacunacion = new JPanel();
        tabbedPane.addTab("Vacunación", null, tabVacunacion, null);
        tabVacunacion.setLayout(null);

        // Vacunas Aplicadas
        JLabel lblVacunasAplicadas = new JLabel("Vacunas Aplicadas:");
        lblVacunasAplicadas.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblVacunasAplicadas.setBounds(10, 10, 200, 22);
        tabVacunacion.add(lblVacunasAplicadas);

        JScrollPane spVacunasAplicadas = new JScrollPane();
        spVacunasAplicadas.setBounds(10, 35, 929, 110);
        tabVacunacion.add(spVacunasAplicadas);

        String[] columnasVacunasAplicadas = {"Vacuna", "Fecha Aplicación", "Lote", "Aplicada Por"};
        modeloVacunasAplicadas = new DefaultTableModel(columnasVacunasAplicadas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableVacunasAplicadas = new JTable(modeloVacunasAplicadas);
        spVacunasAplicadas.setViewportView(tableVacunasAplicadas);

        // Vacunas Faltantes
        JLabel lblVacunasFaltantes = new JLabel("Vacunas Pendientes:");
        lblVacunasFaltantes.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblVacunasFaltantes.setBounds(10, 160, 200, 22);
        tabVacunacion.add(lblVacunasFaltantes);

        JScrollPane spVacunasFaltantes = new JScrollPane();
        spVacunasFaltantes.setBounds(10, 185, 929, 110);
        tabVacunacion.add(spVacunasFaltantes);

        String[] columnasVacunasFaltantes = {"Código", "Nombre", "Enfermedad", "Laboratorio"};
        modeloVacunasFaltantes = new DefaultTableModel(columnasVacunasFaltantes, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableVacunasFaltantes = new JTable(modeloVacunasFaltantes);
        spVacunasFaltantes.setViewportView(tableVacunasFaltantes);

        // BOTÓN CERRAR
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        buttonPane.add(btnCerrar);
    }

    private void verificarTipoUsuario() {
        if (Control.getLoginUser() != null) {
            String tipoUsuario = Control.getLoginUser().getTipo();
            
            if ("Administrador".equalsIgnoreCase(tipoUsuario)) {
                esAdministrador = true;
                numeroLicenciaDoctor = null; // Admin ve todo
            } else if ("Doctor".equalsIgnoreCase(tipoUsuario)) {
                // Cuando implementes el login de doctores, aquí obtendrás su número de licencia
                // Por ahora, como no está implementado, le damos acceso total
                esAdministrador = false;
                numeroLicenciaDoctor = null; // Temporal: acceso total hasta que se implemente
            }
        }
    }

    private void buscarPaciente() {
        String busqueda = txtBusqueda.getText().trim();

        if (busqueda.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese una cédula o código de paciente", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Intentar buscar por cédula primero
        Paciente paciente = Clinica.getInstance().buscarPacientePorCedula(busqueda);

        // Si no se encuentra, buscar por código
        if (paciente == null) {
            paciente = Clinica.getInstance().buscarPacientePorCodigo(busqueda);
        }

        if (paciente == null) {
            JOptionPane.showMessageDialog(this, "Paciente no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            limpiarInformacion();
            return;
        }

        pacienteActual = paciente;
        mostrarInformacionPaciente();
        cargarConsultas();
        cargarVacunacion();
    }

    private void mostrarInformacionPaciente() {
        if (pacienteActual == null) return;

        lblNombrePaciente.setText(pacienteActual.getNombre() + " " + pacienteActual.getApellido());
        lblCedula.setText(pacienteActual.getCedula());
        lblTipoSangre.setText(pacienteActual.getTipoSangre());
        lblFechaRegistro.setText(pacienteActual.getFechaRegistro().toString());

        // Mostrar alergias
        ArrayList<Alergia> alergias = pacienteActual.getAlergias();
        if (alergias == null || alergias.isEmpty()) {
            txtAlergias.setText("No presenta alergias registradas");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Alergia a : alergias) {
                sb.append(" ").append(a.getNombre());
                if (a.getTipo() != null && !a.getTipo().isEmpty()) {
                    sb.append(" (").append(a.getTipo()).append(")");
                }
                sb.append("\n");
            }
            txtAlergias.setText(sb.toString());
        }
    }

    private void cargarConsultas() {
        modeloConsultas.setRowCount(0);

        if (pacienteActual == null) return;

        ArrayList<Consulta> consultas;

        // SISTEMA DE PERMISOS
        if (esAdministrador) {
            // Administrador ve TODAS las consultas
            consultas = pacienteActual.getHistoriaClinica().getConsultas();
        } else if (numeroLicenciaDoctor != null) {
            // Doctor ve consultas según su licencia
            consultas = Clinica.getInstance().listarConsultasVisiblesParaDoctor(numeroLicenciaDoctor);
            
            // Filtrar solo las del paciente actual
            ArrayList<Consulta> consultasFiltradas = new ArrayList<>();
            for (Consulta c : consultas) {
                if (c.getPaciente().getCedula().equals(pacienteActual.getCedula())) {
                    consultasFiltradas.add(c);
                }
            }
            consultas = consultasFiltradas;
        } else {
            // Usuario temporal o sin permisos específicos: ve todo (por ahora)
            consultas = pacienteActual.getHistoriaClinica().getConsultas();
        }

        for (Consulta c : consultas) {
            Object[] fila = {
                c.getCodigoConsulta(),
                c.getFechaConsulta().toString(),
                c.getDoctor().getNombre() + " " + c.getDoctor().getApellido(),
                c.getDiagnostico(),
                c.getTratamiento().getNombreTratamiento(),
                c.isIncluidaEnResumen() ? "Sí" : "No",
                c.isEsEnfermedadVigilancia() ? "Sí" : "No"
            };
            modeloConsultas.addRow(fila);
        }
    }

    private void cargarVacunacion() {
        modeloVacunasAplicadas.setRowCount(0);
        modeloVacunasFaltantes.setRowCount(0);

        if (pacienteActual == null) return;

        // Vacunas Aplicadas
        ArrayList<RegistroVacuna> registros = pacienteActual.getRegistrosVacunas();
        if (registros != null) {
            for (RegistroVacuna r : registros) {
                Object[] fila = {
                    r.getVacuna().getNombre(),
                    r.getFechaAplicacion().toString(),
                    r.getVacuna().getNumeroLote(),
                    r.getAplicadaPor()
                };
                modeloVacunasAplicadas.addRow(fila);
            }
        }

        // Vacunas Faltantes
        ArrayList<Vacuna> faltantes = Clinica.getInstance().obtenerVacunasFaltantes(pacienteActual.getCedula());
        if (faltantes != null) {
            for (Vacuna v : faltantes) {
                Object[] fila = {
                    v.getCodigoVacuna(),
                    v.getNombre(),
                    v.getEnfermedad(),
                    v.getLaboratio()
                };
                modeloVacunasFaltantes.addRow(fila);
            }
        }
    }

    private void verDetalleConsulta() {
        int filaSeleccionada = tableConsultas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una consulta", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String codigoConsulta = (String) modeloConsultas.getValueAt(filaSeleccionada, 0);
        Consulta consulta = Clinica.getInstance().buscarConsulta(codigoConsulta);

        if (consulta == null) {
            JOptionPane.showMessageDialog(this, "Consulta no encontrada", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Aquí abrirías tu ventana DetalleConsulta (cuando la tengas)
        JOptionPane.showMessageDialog(this, "Ver detalles de la consulta: " + codigoConsulta, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void marcarParaResumen() {
        int filaSeleccionada = tableConsultas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una consulta", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String codigoConsulta = (String) modeloConsultas.getValueAt(filaSeleccionada, 0);
        
        if (Clinica.getInstance().marcarConsultaParaResumen(codigoConsulta)) {
            JOptionPane.showMessageDialog(this, "Consulta marcada para resumen", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarConsultas();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo marcar la consulta", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void desmarcarDeResumen() {
        int filaSeleccionada = tableConsultas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una consulta", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String codigoConsulta = (String) modeloConsultas.getValueAt(filaSeleccionada, 0);
        
        if (Clinica.getInstance().desmarcarConsultaDeResumen(codigoConsulta)) {
            JOptionPane.showMessageDialog(this, "Consulta desmarcada del resumen", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarConsultas();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo desmarcar la consulta", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generarResumen() {
        if (pacienteActual == null) {
            JOptionPane.showMessageDialog(this, "Debe buscar un paciente primero", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // SOLO LLAMAR AL MÉTODO DE LA CLASE HistoriaClinica
        String resumen = pacienteActual.getHistoriaClinica().generarResumen();
        
        txtResumen.setText(resumen);
        txtResumen.setCaretPosition(0); // Scroll al inicio
    }

    private void limpiarInformacion() {
        pacienteActual = null;
        lblNombrePaciente.setText("---");
        lblCedula.setText("---");
        lblTipoSangre.setText("---");
        lblFechaRegistro.setText("---");
        txtAlergias.setText("");
        modeloConsultas.setRowCount(0);
        txtResumen.setText("");
        modeloVacunasAplicadas.setRowCount(0);
        modeloVacunasFaltantes.setRowCount(0);
    }
}