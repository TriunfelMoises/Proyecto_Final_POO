package visual;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.Date;

import logico.Clinica;
import logico.Vacuna;

public class regVacuna extends JDialog {

    private JPanel contentPanel = new JPanel();
    private JTextField txtCodigo;
    private JTextField txtNumeroLote;
    private JTextField txtNombre;
    private JSpinner spnCantidad;
    private JSpinner spnFechaCaducidad;
    private JCheckBox chkActiva;

    public regVacuna() {
        setTitle("Registrar Vacuna");
        setBounds(100, 100, 520, 248);
        setLocationRelativeTo(null);
        setModal(true);

        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblCodigo = new JLabel("Código:");
        lblCodigo.setBounds(20, 20, 80, 20);
        contentPanel.add(lblCodigo);

        JLabel lblActiva = new JLabel("Activa:");
        lblActiva.setBounds(394, 20, 60, 20);
        contentPanel.add(lblActiva);

        JLabel lblNumeroLote = new JLabel("Número de lote:");
        lblNumeroLote.setBounds(20, 55, 110, 20);
        contentPanel.add(lblNumeroLote);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 90, 80, 20);
        contentPanel.add(lblNombre);

        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setBounds(20, 125, 80, 20);
        contentPanel.add(lblCantidad);

        JLabel lblFechaCad = new JLabel("Fecha de caducidad:");
        lblFechaCad.setBounds(233, 125, 130, 20);
        contentPanel.add(lblFechaCad);

        // Campos
        txtCodigo = new JTextField();
        txtCodigo.setEditable(false);
        txtCodigo.setBounds(150, 20, 180, 22);
        txtCodigo.setText(Clinica.getInstance().generarCodigoVacuna());
        contentPanel.add(txtCodigo);

        chkActiva = new JCheckBox();
        chkActiva.setBounds(454, 20, 20, 20);
        contentPanel.add(chkActiva);

        txtNumeroLote = new JTextField();
        txtNumeroLote.setBounds(150, 55, 180, 22);
        contentPanel.add(txtNumeroLote);

        txtNombre = new JTextField();
        txtNombre.setBounds(110, 90, 220, 22);
        contentPanel.add(txtNombre);

        spnCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 100000, 1));
        spnCantidad.setBounds(110, 124, 80, 22);
        contentPanel.add(spnCantidad);

        spnFechaCaducidad = new JSpinner();
        spnFechaCaducidad.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_YEAR));
        JSpinner.DateEditor editorFecha = new JSpinner.DateEditor(spnFechaCaducidad, "dd/MM/yyyy");
        spnFechaCaducidad.setEditor(editorFecha);
        spnFechaCaducidad.setBounds(360, 122, 130, 26);
        contentPanel.add(spnFechaCaducidad);

        // Botones
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(e -> registrarVacuna());
        buttonPane.add(btnRegistrar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        buttonPane.add(btnCancelar);
    }

    private void registrarVacuna() {
        String codigo = txtCodigo.getText();
        String numeroLote = txtNumeroLote.getText();
        String nombre = txtNombre.getText();
        int cantidad = (int) spnCantidad.getValue();
        Date fechaCad = (Date) spnFechaCaducidad.getValue();
        boolean activa = chkActiva.isSelected();

        Vacuna nueva = new Vacuna();
        nueva.setCodigoVacuna(codigo);
        nueva.setNumeroLote(numeroLote);
        nueva.setNombre(nombre);
        nueva.setCantidad(cantidad);
        nueva.setFechaCaducidad(fechaCad);  // <<< CORRECTO
        nueva.setActiva(activa);

        boolean registrado = Clinica.getInstance().agregarVacuna(nueva);

        if (registrado) {
            JOptionPane.showMessageDialog(this, "Vacuna registrada exitosamente.");
            txtCodigo.setText(Clinica.getInstance().generarCodigoVacuna());
            txtNumeroLote.setText("");
            txtNombre.setText("");
            spnCantidad.setValue(1);
            chkActiva.setSelected(false);
        } else {
            JOptionPane.showMessageDialog(this, "Error: ya existe una vacuna con este código.");
        }
    }
}
