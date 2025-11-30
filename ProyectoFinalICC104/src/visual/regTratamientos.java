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
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.border.TitledBorder;

import logico.Clinica;
import logico.Tratamiento;

public class regTratamientos extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();

    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextArea txtDescripcion;
    private JTextArea txtIndicaciones;
    private JSpinner spnDuracion;
    
    private JTextField txtMedicamento;
    private JList<String> listMedicamentos;
    private DefaultListModel<String> modeloMedicamentos;
    
    private Tratamiento tratamientoAModificar = null;

    public regTratamientos() {
        setTitle("Registrar Tratamiento");
        setBounds(100, 100, 750, 520);
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
        panelMedicamentos.setBorder(new TitledBorder(null, "Medicamentos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelMedicamentos.setBounds(20, 175, 690, 150);
        panelMedicamentos.setLayout(null);
        contentPanel.add(panelMedicamentos);

        JLabel lblAgregarMed = new JLabel("Agregar medicamento:");
        lblAgregarMed.setFont(labelFont);
        lblAgregarMed.setBounds(10, 25, 150, 22);
        panelMedicamentos.add(lblAgregarMed);

        txtMedicamento = new JTextField();
        txtMedicamento.setBounds(160, 25, 350, 22);
        panelMedicamentos.add(txtMedicamento);

        JButton btnAgregarMed = new JButton("+");
        btnAgregarMed.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnAgregarMed.setBounds(520, 24, 50, 24);
        btnAgregarMed.addActionListener(e -> agregarMedicamento());
        panelMedicamentos.add(btnAgregarMed);

        JLabel lblListaMed = new JLabel("Medicamentos agregados:");
        lblListaMed.setFont(labelFont);
        lblListaMed.setBounds(10, 60, 180, 22);
        panelMedicamentos.add(lblListaMed);

        modeloMedicamentos = new DefaultListModel<>();
        listMedicamentos = new JList<>(modeloMedicamentos);
        JScrollPane spListaMedicamentos = new JScrollPane(listMedicamentos);
        spListaMedicamentos.setBounds(10, 85, 560, 55);
        panelMedicamentos.add(spListaMedicamentos);

        JButton btnEliminarMed = new JButton("Eliminar");
        btnEliminarMed.setBounds(580, 85, 100, 25);
        btnEliminarMed.addActionListener(e -> eliminarMedicamento());
        panelMedicamentos.add(btnEliminarMed);

        // INDICACIONES
        JLabel lblIndicaciones = new JLabel("Indicaciones:");
        lblIndicaciones.setFont(labelFont);
        lblIndicaciones.setBounds(20, 340, 100, 22);
        contentPanel.add(lblIndicaciones);

        txtIndicaciones = new JTextArea();
        txtIndicaciones.setLineWrap(true);
        txtIndicaciones.setWrapStyleWord(true);
        JScrollPane spIndicaciones = new JScrollPane(txtIndicaciones);
        spIndicaciones.setBounds(140, 340, 570, 70);
        contentPanel.add(spIndicaciones);

        // DURACION
        JLabel lblDuracion = new JLabel("Duracion (dias):");
        lblDuracion.setFont(labelFont);
        lblDuracion.setBounds(20, 425, 110, 22);
        contentPanel.add(lblDuracion);

        spnDuracion = new JSpinner();
        spnDuracion.setModel(new SpinnerNumberModel(1, 1, 365, 1));
        spnDuracion.setBounds(140, 425, 80, 22);
        contentPanel.add(spnDuracion);

        // BOTONES
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(e -> registrarTratamiento());
        buttonPane.add(btnRegistrar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> {
            Clinica.getInstance().recalcularContadorTratamientos();
            dispose();
        });
        buttonPane.add(btnCancelar);

        inicializarCodigo();
    }

    // CONSTRUCTOR PARA MODIFICAR
    public regTratamientos(Tratamiento tratamiento) {
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
            
            modeloMedicamentos.clear();
            for (String med : tratamiento.getMedicamentos()) {
                modeloMedicamentos.addElement(med);
            }
        }
    }

    private void inicializarCodigo() {
        String codigo = ("TRA-"+Clinica.getInstance().contadorTratamientos);
        txtCodigo.setText(codigo);
    }

    private void agregarMedicamento() {
        String medicamento = txtMedicamento.getText().trim();
        
        if (medicamento.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el nombre del medicamento", "Campo vacio", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Evitar duplicados
        for (int i = 0; i < modeloMedicamentos.getSize(); i++) {
            if (modeloMedicamentos.getElementAt(i).equalsIgnoreCase(medicamento)) {
                JOptionPane.showMessageDialog(this, "Este medicamento ya fue agregado", "Duplicado", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        modeloMedicamentos.addElement(medicamento);
        txtMedicamento.setText("");
        txtMedicamento.requestFocus();
    }

    private void eliminarMedicamento() {
        int indiceSeleccionado = listMedicamentos.getSelectedIndex();
        
        if (indiceSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un medicamento de la lista", "Sin seleccion", JOptionPane.WARNING_MESSAGE);
            return;
        }

        modeloMedicamentos.remove(indiceSeleccionado);
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

        if (modeloMedicamentos.isEmpty()) {
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
            for (int i = 0; i < modeloMedicamentos.getSize(); i++) {
                tratamientoAModificar.agregarMedicamentos(modeloMedicamentos.getElementAt(i));
            }
            
            JOptionPane.showMessageDialog(this, "Tratamiento modificado correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            return;
        }

        // MODO REGISTRO NUEVO
        Tratamiento nuevo = new Tratamiento(codigo, nombre, descripcion, indicaciones, duracion);

        for (int i = 0; i < modeloMedicamentos.getSize(); i++) {
            nuevo.agregarMedicamentos(modeloMedicamentos.getElementAt(i));
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
        txtMedicamento.setText("");
        modeloMedicamentos.clear();
        spnDuracion.setValue(1);
        txtNombre.requestFocus();
    }
}