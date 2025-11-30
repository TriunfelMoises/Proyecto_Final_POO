package visual;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logico.*;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class regTratamiento extends JDialog {

    private static final long serialVersionUID = 1L;

    private JPanel contentPanel;

    // CAMPOS
    private JTextField txtCodigo;
    private JTextField txtPaciente;
    private JTextField txtEdad;
    private JTextField txtConsulta;
    private JTextField txtEspecialidad;

    private JTextArea txtAlergias;

    private JTable tableMedicamentos;
    private DefaultTableModel modelMedicamentos;

    private JButton btnAddMed;
    private JButton btnEditMed;
    private JButton btnDeleteMed;

    // REFERENCIAS
    private Cita citaActual;
    private Tratamiento tratamientoEditar;
    private Tratamiento tratamientoTemporal;

 
    /**
     * @wbp.parser.constructor
     */
    public regTratamiento(Cita cita) {

        this.citaActual = cita;
        this.tratamientoEditar = null;

        initUI();
        cargarDatosDesdeCita(cita);
    }

    public regTratamiento(Tratamiento existente) {

        this.citaActual = null;
        this.tratamientoEditar = existente;

        initUI();
        cargarDatosExistente(existente);
    }


    private void initUI() {

        setTitle("Registrar Tratamiento");
        setModal(true);
        setResizable(false);
        setSize(780, 650);
        setLocationRelativeTo(null);

        getContentPane().setLayout(new BorderLayout());

        contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(12, 12, 12, 12));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        construirCabecera();
        construirTablaMedicamentos();
        construirBotonera();
    }

    // ============================================================
    //                     SECCIÓN DE DATOS BÁSICOS
    // ============================================================
    private void construirCabecera() {

        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new GridLayout(5, 1, 0, 8));
        contentPanel.add(panelInfo);

        // Fila Código
        JPanel fila1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fila1.add(new JLabel("Código:"));
        txtCodigo = new JTextField(15);
        txtCodigo.setEditable(false);
        fila1.add(txtCodigo);
        panelInfo.add(fila1);

        // Fila Paciente
        JPanel fila2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fila2.add(new JLabel("Paciente:"));
        txtPaciente = new JTextField(25);
        txtPaciente.setEditable(false);
        fila2.add(txtPaciente);

        fila2.add(new JLabel("Edad:"));
        txtEdad = new JTextField(4);
        txtEdad.setEditable(false);
        fila2.add(txtEdad);

        panelInfo.add(fila2);

        // Fila Especialidad + Consulta
        JPanel fila3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fila3.add(new JLabel("Consulta:"));
        txtConsulta = new JTextField(10);
        txtConsulta.setEditable(false);
        fila3.add(txtConsulta);

        fila3.add(new JLabel("Especialidad:"));
        txtEspecialidad = new JTextField(15);
        txtEspecialidad.setEditable(false);
        fila3.add(txtEspecialidad);

        panelInfo.add(fila3);

        // Fila alergias
        JPanel fila4 = new JPanel(new BorderLayout());
        fila4.add(new JLabel("Alergias:"), BorderLayout.NORTH);

        txtAlergias = new JTextArea(3, 40);
        txtAlergias.setEditable(false);
        JScrollPane spAler = new JScrollPane(txtAlergias);
        fila4.add(spAler, BorderLayout.CENTER);

        panelInfo.add(fila4);
    }

    // ============================================================
    //                 TABLA DE MEDICAMENTOS
    // ============================================================
    private void construirTablaMedicamentos() {

        JPanel panelTabla = new JPanel();
        panelTabla.setLayout(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createTitledBorder("Medicamentos asignados"));
        contentPanel.add(panelTabla);

        modelMedicamentos = new DefaultTableModel(
                new String[]{"Nombre", "Dosis (mg)", "Cada horas", "Días", "Vía"}, 0
        ) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        tableMedicamentos = new JTable(modelMedicamentos);
        panelTabla.add(new JScrollPane(tableMedicamentos), BorderLayout.CENTER);
    }

    // ============================================================
    //                  BOTONES DE MEDICAMENTOS
    // ============================================================
    private void construirBotonera() {

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        contentPanel.add(panelBotones);

        btnAddMed = new JButton("Agregar medicamento");
        btnAddMed.addActionListener(e -> agregarMedicamento());
        panelBotones.add(btnAddMed);

        btnEditMed = new JButton("Editar");
        btnEditMed.setEnabled(false);
        btnEditMed.addActionListener(e -> editarMedicamento());
        panelBotones.add(btnEditMed);

        btnDeleteMed = new JButton("Eliminar");
        btnDeleteMed.setEnabled(false);
        btnDeleteMed.addActionListener(e -> eliminarMedicamento());
        panelBotones.add(btnDeleteMed);

        tableMedicamentos.getSelectionModel().addListSelectionListener(e -> {
            boolean sel = tableMedicamentos.getSelectedRow() >= 0;
            btnEditMed.setEnabled(sel);
            btnDeleteMed.setEnabled(sel);
        });

        // Botones inferiores
        JPanel inferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(inferior, BorderLayout.SOUTH);

        JButton btnGuardar = new JButton("Guardar y cerrar");
        btnGuardar.addActionListener(e -> guardarTratamiento());
        inferior.add(btnGuardar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        inferior.add(btnCancelar);
    }

    // ============================================================
    //                 CARGAR DATOS DESDE CITA
    // ============================================================
    private void cargarDatosDesdeCita(Cita c) {

        Paciente p = c.getPaciente();

        txtCodigo.setText(Tratamiento.generarCodigo());
        txtPaciente.setText(p.getNombre() + " " + p.getApellido());
        txtEdad.setText(String.valueOf(LocalDate.now().getYear() - p.getFechaNacimiento().getYear()));

        StringBuilder sb = new StringBuilder();
        if (p.getAlergias().isEmpty()) {
            sb.append("No presenta alergias.");
        } else {
            for (Alergia a : p.getAlergias()) {
                sb.append("- ").append(a.getNombre()).append("\n");
            }
        }
        txtAlergias.setText(sb.toString());

        txtConsulta.setText(c.getCodigoCita());
        txtEspecialidad.setText(c.getDoctor().getEspecialidad());
    }

    // ============================================================
    //                CARGAR DATOS PARA EDICIÓN
    // ============================================================
    private void cargarDatosExistente(Tratamiento t) {

        txtCodigo.setText(t.getCodigoTratamiento());
        txtPaciente.setText(t.getNombrePaciente());
        txtEdad.setText(String.valueOf(t.getEdadPaciente()));
        txtConsulta.setText(t.getCodigoConsulta());
        txtEspecialidad.setText(t.getEspecialidad());
        txtAlergias.setText(t.getDetalleAlergia());

        for (Medicamento m : t.getMedicamentos()) {
            modelMedicamentos.addRow(new Object[]{
                    m.getNombre(),
                    m.getDosisMg(),
                    m.getFrecuenciaHoras(),
                    m.getDuracionDias(),
                    m.getVia()
            });
        }
    }

    // ============================================================
    //                      ACCIONES
    // ============================================================
    private void agregarMedicamento() {

        regMedicamento dialog = new regMedicamento();
        dialog.setModal(true);
        dialog.setVisible(true);

        Medicamento med = dialog.getMedicamento();

        if (med != null) {
            modelMedicamentos.addRow(new Object[]{
                    med.getNombre(),
                    med.getDosisMg(),
                    med.getFrecuenciaHoras(),
                    med.getDuracionDias(),
                    med.getVia()
            });
        }
    }

    private void editarMedicamento() {
        int row = tableMedicamentos.getSelectedRow();
        if (row < 0) return;

        Medicamento original = new Medicamento(
                modelMedicamentos.getValueAt(row, 0).toString(),
                Double.parseDouble(modelMedicamentos.getValueAt(row, 1).toString()),
                Integer.parseInt(modelMedicamentos.getValueAt(row, 2).toString()),
                Integer.parseInt(modelMedicamentos.getValueAt(row, 3).toString()),
                modelMedicamentos.getValueAt(row, 4).toString(),
                ""
        );

        regMedicamento dialog = new regMedicamento(original);
        dialog.setModal(true);
        dialog.setVisible(true);

        Medicamento editado = dialog.getMedicamento();
        if (editado != null) {
            modelMedicamentos.setValueAt(editado.getNombre(), row, 0);
            modelMedicamentos.setValueAt(editado.getDosisMg(), row, 1);
            modelMedicamentos.setValueAt(editado.getFrecuenciaHoras(), row, 2);
            modelMedicamentos.setValueAt(editado.getDuracionDias(), row, 3);
            modelMedicamentos.setValueAt(editado.getVia(), row, 4);
        }
    }

    private void eliminarMedicamento() {
        int row = tableMedicamentos.getSelectedRow();
        if (row >= 0) {
            modelMedicamentos.removeRow(row);
        }
    }

    // ============================================================
    //                     GUARDAR TRATAMIENTO
    // ============================================================
    private void guardarTratamiento() {

        String codigo = txtCodigo.getText();
        String paciente = txtPaciente.getText();
        int edad = Integer.parseInt(txtEdad.getText());
        String consulta = txtConsulta.getText();
        String especialidad = txtEspecialidad.getText();
        String alergias = txtAlergias.getText();

        Tratamiento t = new Tratamiento(
                codigo,
                paciente,
                edad,
                false,
                alergias,
                consulta,
                especialidad
        );

        // Cargar medicamentos
        for (int i = 0; i < modelMedicamentos.getRowCount(); i++) {
            t.agregarMedicamento(new Medicamento(
                    modelMedicamentos.getValueAt(i, 0).toString(),
                    Double.parseDouble(modelMedicamentos.getValueAt(i, 1).toString()),
                    Integer.parseInt(modelMedicamentos.getValueAt(i, 2).toString()),
                    Integer.parseInt(modelMedicamentos.getValueAt(i, 3).toString()),
                    modelMedicamentos.getValueAt(i, 4).toString(),
                    ""
            ));
        }

        tratamientoTemporal = t;
        dispose();
    }

    public Tratamiento getTratamientoTemporal() {
        return tratamientoTemporal;
    }
}
