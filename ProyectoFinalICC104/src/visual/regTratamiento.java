package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import logico.Clinica;
import logico.Tratamiento;
import logico.Medicamento;
import java.awt.Toolkit;
import java.awt.Color;

public class regTratamiento extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();

    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextArea txtDescripcion;
    private JTextArea txtIndicaciones;
    private JSpinner spnDuracion;
    
    private JTable tableMedicamentos;
    private DefaultTableModel modeloMedicamentos;
    
    private Tratamiento tratamientoAModificar = null;

    public regTratamiento() {
    	setIconImage(Toolkit.getDefaultToolkit().getImage(regTratamiento.class.getResource("/recursos/agu.jpg")));
        setTitle("Registrar Tratamiento");
        setBounds(100, 100, 750, 600);
        setLocationRelativeTo(null);
        setModal(true);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                Clinica.getInstance().recalcularContadorTratamientos();
            }
        });

        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(220, 220, 220));
        contentPanel.setBorder(new EmptyBorder(12, 12, 12, 12));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        Font labelFont = new Font("Tahoma", Font.PLAIN, 12);

        // CODIGO
        JLabel lblCodigo = new JLabel("Codigo:");
        lblCodigo.setFont(labelFont);
        lblCodigo.setBounds(20, 20, 80, 22);
        contentPanel.add(lblCodigo);

        txtCodigo = new JTextField();
        txtCodigo.setBackground(new Color(224, 255, 255));
        txtCodigo.setBounds(140, 20, 180, 22);
        txtCodigo.setEditable(false);
        contentPanel.add(txtCodigo);

        // NOMBRE
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(labelFont);
        lblNombre.setBounds(20, 55, 80, 22);
        contentPanel.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(140, 55, 570, 22);
        contentPanel.add(txtNombre);

        // DESCRIPCION
        JLabel lblDescripcion = new JLabel("Descripcion:");
        lblDescripcion.setFont(labelFont);
        lblDescripcion.setBounds(20, 90, 100, 22);
        contentPanel.add(lblDescripcion);

        txtDescripcion = new JTextArea();
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        JScrollPane spDescripcion = new JScrollPane(txtDescripcion);
        spDescripcion.setBounds(140, 90, 570, 70);
        contentPanel.add(spDescripcion);

        // PANEL MEDICAMENTOS DINAMICO
        JPanel panelMedicamentos = new JPanel();
        panelMedicamentos.setBackground(new Color(220, 220, 220));
        panelMedicamentos.setBorder(new TitledBorder(null, "Medicamentos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelMedicamentos.setBounds(20, 175, 690, 180);
        panelMedicamentos.setLayout(null);
        contentPanel.add(panelMedicamentos);

        String[] columnas = {"Nombre", "Dosis (mg)", "Cada (hrs)", "Duracion (dias)", "Via"};
        modeloMedicamentos = new DefaultTableModel(columnas, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableMedicamentos = new JTable(modeloMedicamentos);
        JScrollPane spMedicamentos = new JScrollPane(tableMedicamentos);
        spMedicamentos.setBounds(10, 25, 560, 110);
        panelMedicamentos.add(spMedicamentos);

        JButton btnAgregarMed = new JButton("Agregar");
        btnAgregarMed.setBounds(580, 25, 100, 25);
        btnAgregarMed.addActionListener(e -> agregarMedicamento());
        panelMedicamentos.add(btnAgregarMed);

        JButton btnEditarMed = new JButton("Editar");
        btnEditarMed.setBounds(580, 60, 100, 25);
        btnEditarMed.addActionListener(e -> editarMedicamento());
        panelMedicamentos.add(btnEditarMed);

        JButton btnEliminarMed = new JButton("Eliminar");
        btnEliminarMed.setBounds(580, 95, 100, 25);
        btnEliminarMed.addActionListener(e -> eliminarMedicamento());
        panelMedicamentos.add(btnEliminarMed);

        // INDICACIONES
        JLabel lblIndicaciones = new JLabel("Indicaciones:");
        lblIndicaciones.setFont(labelFont);
        lblIndicaciones.setBounds(20, 370, 100, 22);
        contentPanel.add(lblIndicaciones);

        txtIndicaciones = new JTextArea();
        txtIndicaciones.setLineWrap(true);
        txtIndicaciones.setWrapStyleWord(true);
        JScrollPane spIndicaciones = new JScrollPane(txtIndicaciones);
        spIndicaciones.setBounds(140, 370, 570, 70);
        contentPanel.add(spIndicaciones);

        // DURACION
        JLabel lblDuracion = new JLabel("Duracion (dias):");
        lblDuracion.setFont(labelFont);
        lblDuracion.setBounds(20, 455, 110, 22);
        contentPanel.add(lblDuracion);

        spnDuracion = new JSpinner();
        spnDuracion.setModel(new SpinnerNumberModel(1, 1, 365, 1));
        spnDuracion.setBounds(140, 455, 80, 22);
        contentPanel.add(spnDuracion);

        // BOTONES
        JPanel buttonPane = new JPanel();
        buttonPane.setBackground(new Color(224, 255, 255));
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(e -> registrarTratamiento());
        buttonPane.add(btnRegistrar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(255, 239, 213));
        btnCancelar.addActionListener(e -> {
            Clinica.getInstance().recalcularContadorTratamientos();
            dispose();
        });
        buttonPane.add(btnCancelar);

        inicializarCodigo();
    }

    // CONSTRUCTOR PARA MODIFICAR
    public regTratamiento(Tratamiento tratamiento) {
        this();
        this.tratamientoAModificar = tratamiento;
        
        if (tratamiento != null) {
            txtCodigo.setText(tratamiento.getCodigoTratamiento());
            txtNombre.setText(tratamiento.getNombreTratamiento());
            txtDescripcion.setText(tratamiento.getDescripcion());
            txtIndicaciones.setText(tratamiento.getIndicaciones());
            
            String duracionTexto = tratamiento.getDuracion();
            try {
                int dias = Integer.parseInt(duracionTexto.replaceAll("[^0-9]", ""));
                spnDuracion.setValue(dias);
            } catch (Exception e) {
                spnDuracion.setValue(1);
            }
            
            modeloMedicamentos.setRowCount(0);
            for (Medicamento med : tratamiento.getMedicamentos()) {
                Object[] fila = {
                    med.getNombre(),
                    med.getDosisMg(),
                    med.getFrecuenciaHoras(),
                    med.getDuracionDias(),
                    med.getVia()
                };
                modeloMedicamentos.addRow(fila);
            }
        }
    }

    private void inicializarCodigo() {
        String codigo = Tratamiento.generarCodigo();
        txtCodigo.setText(codigo);
    }

    private void agregarMedicamento() {
        regMedicamento dialogo = new regMedicamento();
        dialogo.setModal(true);
        dialogo.setLocationRelativeTo(this);
        dialogo.setVisible(true);

        Medicamento med = dialogo.getMedicamento();
        if (med != null) {
            Object[] fila = {
                med.getNombre(),
                med.getDosisMg(),
                med.getFrecuenciaHoras(),
                med.getDuracionDias(),
                med.getVia()
            };
            modeloMedicamentos.addRow(fila);
        }
    }

    private void editarMedicamento() {
        int filaSeleccionada = tableMedicamentos.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un medicamento de la tabla", "Sin seleccion", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Medicamento medOriginal = new Medicamento(
            modeloMedicamentos.getValueAt(filaSeleccionada, 0).toString(),
            Double.parseDouble(modeloMedicamentos.getValueAt(filaSeleccionada, 1).toString()),
            Integer.parseInt(modeloMedicamentos.getValueAt(filaSeleccionada, 2).toString()),
            Integer.parseInt(modeloMedicamentos.getValueAt(filaSeleccionada, 3).toString()),
            modeloMedicamentos.getValueAt(filaSeleccionada, 4).toString(),
            ""
        );

        regMedicamento dialogo = new regMedicamento(medOriginal);
        dialogo.setModal(true);
        dialogo.setLocationRelativeTo(this);
        dialogo.setVisible(true);

        Medicamento medEditado = dialogo.getMedicamento();
        if (medEditado != null) {
            modeloMedicamentos.setValueAt(medEditado.getNombre(), filaSeleccionada, 0);
            modeloMedicamentos.setValueAt(medEditado.getDosisMg(), filaSeleccionada, 1);
            modeloMedicamentos.setValueAt(medEditado.getFrecuenciaHoras(), filaSeleccionada, 2);
            modeloMedicamentos.setValueAt(medEditado.getDuracionDias(), filaSeleccionada, 3);
            modeloMedicamentos.setValueAt(medEditado.getVia(), filaSeleccionada, 4);
        }
    }

    private void eliminarMedicamento() {
        int filaSeleccionada = tableMedicamentos.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un medicamento de la tabla", "Sin seleccion", JOptionPane.WARNING_MESSAGE);
            return;
        }

        modeloMedicamentos.removeRow(filaSeleccionada);
    }

    private void registrarTratamiento() {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String indicaciones = txtIndicaciones.getText().trim();
        int duracionDias = (int) spnDuracion.getValue();
        String duracion = duracionDias + " dias";

        // Validaciones
        if (nombre.isEmpty() || descripcion.isEmpty() || indicaciones.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombre, descripcion e indicaciones son campos obligatorios.", "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (modeloMedicamentos.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Debe agregar al menos un medicamento.", "Sin medicamentos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // MODO MODIFICACION
        if (tratamientoAModificar != null) {
            tratamientoAModificar.setNombreTratamiento(nombre);
            tratamientoAModificar.setDescripcion(descripcion);
            tratamientoAModificar.setIndicaciones(indicaciones);
            tratamientoAModificar.setDuracion(duracion);
            
            tratamientoAModificar.getMedicamentos().clear();
            for (int i = 0; i < modeloMedicamentos.getRowCount(); i++) {
                Medicamento med = new Medicamento(
                    modeloMedicamentos.getValueAt(i, 0).toString(),
                    Double.parseDouble(modeloMedicamentos.getValueAt(i, 1).toString()),
                    Integer.parseInt(modeloMedicamentos.getValueAt(i, 2).toString()),
                    Integer.parseInt(modeloMedicamentos.getValueAt(i, 3).toString()),
                    modeloMedicamentos.getValueAt(i, 4).toString(),
                    ""
                );
                tratamientoAModificar.agregarMedicamento(med);
            }
            
            JOptionPane.showMessageDialog(this, "Tratamiento modificado correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            return;
        }

        // MODO REGISTRO NUEVO
        Tratamiento nuevo = new Tratamiento(codigo, nombre, descripcion, indicaciones, duracion);

        for (int i = 0; i < modeloMedicamentos.getRowCount(); i++) {
            Medicamento med = new Medicamento(
                modeloMedicamentos.getValueAt(i, 0).toString(),
                Double.parseDouble(modeloMedicamentos.getValueAt(i, 1).toString()),
                Integer.parseInt(modeloMedicamentos.getValueAt(i, 2).toString()),
                Integer.parseInt(modeloMedicamentos.getValueAt(i, 3).toString()),
                modeloMedicamentos.getValueAt(i, 4).toString(),
                ""
            );
            nuevo.agregarMedicamento(med);
        }

        boolean registrado = Clinica.getInstance().agregarTratamiento(nuevo);

        if (registrado) {
            JOptionPane.showMessageDialog(this, "Tratamiento registrado correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Ya existe un tratamiento con ese codigo.", "Error al registrar", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        inicializarCodigo();
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtIndicaciones.setText("");
        modeloMedicamentos.setRowCount(0);
        spnDuracion.setValue(1);
        txtNombre.requestFocus();
    }
}