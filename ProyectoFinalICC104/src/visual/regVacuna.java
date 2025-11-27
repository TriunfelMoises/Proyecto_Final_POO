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
    private JTextField txtlaboratorio;
    private JTextField txtEnfermedad;

    public regVacuna() {
        setTitle("Registrar Vacuna");
        setBounds(100, 100, 520, 271);
        setLocationRelativeTo(null);
        setModal(true);

        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblCodigo = new JLabel("Código:");
        lblCodigo.setBounds(390, 16, 80, 20);
        contentPanel.add(lblCodigo);

        JLabel lblNumeroLote = new JLabel("Número de lote:");
        lblNumeroLote.setBounds(20, 68, 130, 20);
        contentPanel.add(lblNumeroLote);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 31, 80, 20);
        contentPanel.add(lblNombre);

        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setBounds(344, 78, 80, 20);
        contentPanel.add(lblCantidad);

        JLabel lblFechaCad = new JLabel("Fecha de caducidad:");
        lblFechaCad.setBounds(353, 114, 130, 20);
        contentPanel.add(lblFechaCad);

        // Campos
        txtCodigo = new JTextField();
        txtCodigo.setEditable(false);
        txtCodigo.setBounds(372, 40, 91, 22);
        txtCodigo.setText(Clinica.getInstance().generarCodigoVacuna());
        contentPanel.add(txtCodigo);

        txtNumeroLote = new JTextField();
        txtNumeroLote.setBounds(150, 67, 180, 22);
        contentPanel.add(txtNumeroLote);

        txtNombre = new JTextField();
        txtNombre.setBounds(110, 30, 220, 22);
        contentPanel.add(txtNombre);

        spnCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 100000, 1));
        spnCantidad.setBounds(421, 77, 62, 22);
        contentPanel.add(spnCantidad);

        spnFechaCaducidad = new JSpinner();
        spnFechaCaducidad.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_YEAR));
        JSpinner.DateEditor editorFecha = new JSpinner.DateEditor(spnFechaCaducidad, "dd/MM/yyyy");
        spnFechaCaducidad.setEditor(editorFecha);
        spnFechaCaducidad.setBounds(353, 137, 130, 26);
        contentPanel.add(spnFechaCaducidad);
        
        JLabel lblNewLabel = new JLabel("Laboratorio:");
        lblNewLabel.setBounds(20, 104, 99, 20);
        contentPanel.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Enfermedad:");
        lblNewLabel_1.setBounds(20, 140, 91, 20);
        contentPanel.add(lblNewLabel_1);
        
        txtlaboratorio = new JTextField();
        txtlaboratorio.setBounds(110, 101, 220, 22);
        contentPanel.add(txtlaboratorio);
        
        txtEnfermedad = new JTextField();
        txtEnfermedad.setBounds(110, 140, 220, 22);
        contentPanel.add(txtEnfermedad);

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
        String enfermedad = txtEnfermedad.getText();
        String laboratio = txtlaboratorio.getText();

        Vacuna nueva = new Vacuna();
        nueva.setCodigoVacuna(codigo);
        nueva.setNumeroLote(numeroLote);
        nueva.setNombre(nombre);
        nueva.setCantidad(cantidad);
        nueva.setFechaCaducidad(fechaCad); 
        nueva.setActiva(true);
        nueva.setLaboratio(laboratio);
        nueva.setEnfermedad(enfermedad);
        
        if (txtCodigo.getText().isEmpty()|| txtNumeroLote.getText().isEmpty()|| txtNombre.getText().isEmpty()||txtEnfermedad.getText().isEmpty()||txtlaboratorio.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Complete todos los campos correctamente", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean registrado = Clinica.getInstance().agregarVacuna(nueva);

        if (registrado) {
            JOptionPane.showMessageDialog(this, "Vacuna registrada exitosamente.");
            txtCodigo.setText(Clinica.getInstance().generarCodigoVacuna());
            txtNumeroLote.setText("");
            txtNombre.setText("");
            spnCantidad.setValue(1);
            txtEnfermedad.setText("");
            txtlaboratorio.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Error: ya existe una vacuna con este código.");
        }
    }
}
