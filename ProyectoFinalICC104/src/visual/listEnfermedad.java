package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingConstants;

import logico.Clinica;
import logico.Enfermedad;

public class listEnfermedad extends JDialog {

    private static final long serialVersionUID = 1L;

    private JTable tableEnfermedad;
    private DefaultTableModel modelo;

    private JTextField txtBuscarNombre;
    private JComboBox<String> cbTipoFiltro;

    private ArrayList<Enfermedad> listaEnfermedades;
    private ArrayList<Enfermedad> listaFiltrada;

    public listEnfermedad() {
        setTitle("Listado de enfermedades");
        setBounds(100, 100, 800, 450);
        setLocationRelativeTo(null);
        setModal(true);
        setResizable(false);

        getContentPane().setLayout(new BorderLayout());

        JPanel panelFiltros = new JPanel();
        panelFiltros.setBorder(new EmptyBorder(10, 10, 5, 10));
        panelFiltros.setLayout(new GridBagLayout());
        getContentPane().add(panelFiltros, BorderLayout.NORTH);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 5, 3, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFiltros.add(new JLabel("Nombre:"), gbc);

        txtBuscarNombre = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        panelFiltros.add(txtBuscarNombre, gbc);
        gbc.weightx = 0;

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFiltros.add(new JLabel("Tipo:"), gbc);

        cbTipoFiltro = new JComboBox<>();
        cbTipoFiltro.addItem("Todos");
        cbTipoFiltro.addItem("Autoinmune");
        cbTipoFiltro.addItem("Cardiovascular");
        cbTipoFiltro.addItem("Congénita");
        cbTipoFiltro.addItem("Dermatológica");
        cbTipoFiltro.addItem("Endocrina");
        cbTipoFiltro.addItem("Gastrointestinal");
        cbTipoFiltro.addItem("Genética");
        cbTipoFiltro.addItem("Hematológica");
        cbTipoFiltro.addItem("Iatrogénica");
        cbTipoFiltro.addItem("Inmunológica");
        cbTipoFiltro.addItem("Metabólica");
        cbTipoFiltro.addItem("Musculoesquelética");
        cbTipoFiltro.addItem("Neoplasia o cáncer");
        cbTipoFiltro.addItem("Neurológica");
        cbTipoFiltro.addItem("Nutricional");
        cbTipoFiltro.addItem("Oftalmológica");
        cbTipoFiltro.addItem("Otorrinolaringológica");
        cbTipoFiltro.addItem("Profesional");
        cbTipoFiltro.addItem("Psiquiátrica");
        cbTipoFiltro.addItem("Rara");
        cbTipoFiltro.addItem("Renal o urinaria");
        cbTipoFiltro.addItem("Respiratoria");
        cbTipoFiltro.addItem("Reumatológica");
        cbTipoFiltro.addItem("Toxicológica");
        cbTipoFiltro.addItem("Traumática");
        cbTipoFiltro.addItem("Infecciosa");

        gbc.gridx = 1;
        gbc.gridy = 1;
        panelFiltros.add(cbTipoFiltro, gbc);

        JButton btnLimpiar = new JButton("Limpiar filtros");
        btnLimpiar.addActionListener(e -> limpiarFiltros());
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panelFiltros.add(btnLimpiar, gbc);
        gbc.gridwidth = 1;

        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 10, 10, 10));
        contentPanel.setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        String[] columnas = {"Código", "Nombre", "Bajo vigilancia", "Tipo"};

        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false; 
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 2) {
                    return Boolean.class; 
                }
                return String.class;
            }
        };
        modelo.setColumnIdentifiers(columnas);

        tableEnfermedad = new JTable(modelo);
        tableEnfermedad.setFillsViewportHeight(true);

        tableEnfermedad.getTableHeader().setResizingAllowed(false);
        tableEnfermedad.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        tableEnfermedad.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableEnfermedad.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableEnfermedad.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

        tableEnfermedad.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            private final JCheckBox check = new JCheckBox();

            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {

                boolean marcado = false;
                if (value instanceof Boolean) {
                    marcado = (Boolean) value;
                }

                check.setSelected(marcado);
                check.setHorizontalAlignment(SwingConstants.CENTER);
                check.setEnabled(false); 

                if (isSelected) {
                    check.setBackground(table.getSelectionBackground());
                } else {
                    check.setBackground(table.getBackground());
                }

                return check;
            }
        });

        tableEnfermedad.getColumnModel().getColumn(0).setPreferredWidth(90);
        tableEnfermedad.getColumnModel().getColumn(1).setPreferredWidth(260);
        tableEnfermedad.getColumnModel().getColumn(2).setPreferredWidth(120);
        tableEnfermedad.getColumnModel().getColumn(3).setPreferredWidth(250);

        tableEnfermedad.setRowHeight(28);

        tableEnfermedad.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    verDetallesEnfermedad();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tableEnfermedad);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnModificar = new JButton("Modificar");
        btnModificar.addActionListener(e -> modificarEnfermedad());
        buttonPane.add(btnModificar);

        JButton btnDetalles = new JButton("Ver detalles");
        btnDetalles.addActionListener(e -> verDetallesEnfermedad());
        buttonPane.add(btnDetalles);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        buttonPane.add(btnCerrar);

        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        Clinica cli = Clinica.getInstance();
        listaEnfermedades = cli.listarEnfermedades();
        if (listaEnfermedades == null) {
            listaEnfermedades = new ArrayList<>();
        }

        listaFiltrada = new ArrayList<>();

        txtBuscarNombre.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { aplicarFiltros(); }
            public void removeUpdate(DocumentEvent e) { aplicarFiltros(); }
            public void changedUpdate(DocumentEvent e) { aplicarFiltros(); }
        });

        cbTipoFiltro.addActionListener(e -> aplicarFiltros());

        aplicarFiltros();
    }

    private void aplicarFiltros() {
        modelo.setRowCount(0);
        listaFiltrada.clear();

        if (listaEnfermedades == null || listaEnfermedades.isEmpty()) {
            return;
        }

        String nombreFiltro = txtBuscarNombre.getText().trim().toLowerCase();
        String tipoFiltro = (String) cbTipoFiltro.getSelectedItem();

        for (Enfermedad e : listaEnfermedades) {
            if (e == null) continue;

            if (!nombreFiltro.isEmpty()) {
                String nombreEnf = e.getNombre() != null ? e.getNombre().toLowerCase() : "";
                if (!nombreEnf.contains(nombreFiltro)) {
                    continue;
                }
            }

            if (!"Todos".equalsIgnoreCase(tipoFiltro)) {
                if (e.getTipo() == null || !e.getTipo().equalsIgnoreCase(tipoFiltro)) {
                    continue;
                }
            }

            Object[] fila = {
                e.getCodigoEnfermedad(),
                e.getNombre(),
                e.isBajoVigilancia(),
                e.getTipo()
            };

            modelo.addRow(fila);
            listaFiltrada.add(e);
        }
    }

    private void limpiarFiltros() {
        txtBuscarNombre.setText("");
        cbTipoFiltro.setSelectedIndex(0);
        aplicarFiltros();
    }

    private void verDetallesEnfermedad() {
        int filaSeleccionada = tableEnfermedad.getSelectedRow();
        if (filaSeleccionada < 0 || filaSeleccionada >= listaFiltrada.size()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar una enfermedad de la tabla.",
                    "Selección requerida",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Enfermedad e = listaFiltrada.get(filaSeleccionada);
        mostrarVentanaDetalles(e);
    }

    private void mostrarVentanaDetalles(Enfermedad e) {
        JDialog dlg = new JDialog(this, "Detalles de la enfermedad", true);
        dlg.setSize(600, 420);
        dlg.setLocationRelativeTo(this);
        dlg.setResizable(false);
        dlg.setLayout(new BorderLayout(10, 10));

        JPanel panelInfo = new JPanel(new GridBagLayout());
        panelInfo.setBorder(new EmptyBorder(10, 10, 0, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0;

        Font labelBold = new Font("Tahoma", Font.BOLD, 12);
        Font labelNormal = new Font("Tahoma", Font.PLAIN, 12);

        int fila = 0;

        gbc.gridx = 0; gbc.gridy = fila;
        JLabel lblCodigo = new JLabel("Código:");
        lblCodigo.setFont(labelBold);
        panelInfo.add(lblCodigo, gbc);

        gbc.gridx = 1; 
        gbc.weightx = 1;
        JLabel valCodigo = new JLabel(e.getCodigoEnfermedad());
        valCodigo.setFont(labelNormal);
        panelInfo.add(valCodigo, gbc);
        gbc.weightx = 0;
        fila++;

        gbc.gridx = 0; gbc.gridy = fila;
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(labelBold);
        panelInfo.add(lblNombre, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        JLabel valNombre = new JLabel(e.getNombre());
        valNombre.setFont(labelNormal);
        panelInfo.add(valNombre, gbc);
        gbc.weightx = 0;
        fila++;

        gbc.gridx = 0; gbc.gridy = fila;
        JLabel lblVig = new JLabel("Bajo vigilancia:");
        lblVig.setFont(labelBold);
        panelInfo.add(lblVig, gbc);

        gbc.gridx = 1;
        JCheckBox chk = new JCheckBox(e.isBajoVigilancia() ? "Sí" : "No");
        chk.setSelected(e.isBajoVigilancia());
        chk.setEnabled(false);
        panelInfo.add(chk, gbc);
        fila++;

        gbc.gridx = 0; gbc.gridy = fila;
        JLabel lblGrav = new JLabel("Nivel de gravedad:");
        lblGrav.setFont(labelBold);
        panelInfo.add(lblGrav, gbc);

        gbc.gridx = 1;
        JLabel valGrav = new JLabel(String.valueOf(e.getNivelGravedad()));
        valGrav.setFont(labelNormal);
        panelInfo.add(valGrav, gbc);
        fila++;

        gbc.gridx = 0; gbc.gridy = fila;
        JLabel lblPot = new JLabel("Potencial de propagación:");
        lblPot.setFont(labelBold);
        panelInfo.add(lblPot, gbc);

        gbc.gridx = 1;
        JLabel valPot = new JLabel(e.getPotencialPropagacion());
        valPot.setFont(labelNormal);
        panelInfo.add(valPot, gbc);
        fila++;

        gbc.gridx = 0; gbc.gridy = fila;
        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setFont(labelBold);
        panelInfo.add(lblTipo, gbc);

        gbc.gridx = 1;
        JLabel valTipo = new JLabel(e.getTipo());
        valTipo.setFont(labelNormal);
        panelInfo.add(valTipo, gbc);
        fila++;

        dlg.add(panelInfo, BorderLayout.NORTH);

        JPanel panelSintomas = new JPanel(new BorderLayout());
        panelSintomas.setBorder(new EmptyBorder(5, 10, 10, 10));

        JLabel lblSintomas = new JLabel("Síntomas y signos:");
        lblSintomas.setFont(labelBold);
        panelSintomas.add(lblSintomas, BorderLayout.NORTH);

        JTextArea txtArea = new JTextArea();
        txtArea.setEditable(false);
        txtArea.setLineWrap(true);
        txtArea.setWrapStyleWord(true);
        txtArea.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txtArea.setText(e.getSintomasYSignos() != null ? e.getSintomasYSignos() : "No especificado");

        JScrollPane sp = new JScrollPane(txtArea);
        panelSintomas.add(sp, BorderLayout.CENTER);

        dlg.add(panelSintomas, BorderLayout.CENTER);

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(ev -> dlg.dispose());
        panelBoton.add(btnCerrar);

        dlg.add(panelBoton, BorderLayout.SOUTH);

        dlg.setVisible(true);
    }

    private void modificarEnfermedad() {
        int filaSeleccionada = tableEnfermedad.getSelectedRow();
        if (filaSeleccionada < 0 || filaSeleccionada >= listaFiltrada.size()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar una enfermedad de la tabla.",
                    "Selección requerida",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Enfermedad e = listaFiltrada.get(filaSeleccionada);

        regEnfermedades dialog = new regEnfermedades(e, true);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        Clinica cli = Clinica.getInstance();
        listaEnfermedades = cli.listarEnfermedades();
        if (listaEnfermedades == null) {
            listaEnfermedades = new ArrayList<>();
        }
        aplicarFiltros();
    }
}
