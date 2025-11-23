package visual;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;

import logico.Clinica;
import logico.Consulta;
import logico.Paciente;
import logico.Doctor;

public class listConsulta extends JDialog {

    private static final long serialVersionUID = 1L;

    private final JPanel contentPanel = new JPanel();
    private JTable tableConsultas;
    private DefaultTableModel modelo;
    private JTextField txtNumLicencia;

    private ArrayList<Consulta> consultasVisibles;
    private String numLicenciaDoctor;

    public listConsulta(String numLicenciaDoctor) {
        setTitle("Listado de consultas");
        setBounds(100, 100, 900, 500);
        setLocationRelativeTo(null);
        setModal(true);

        this.numLicenciaDoctor = numLicenciaDoctor;

        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(contentPanel, BorderLayout.NORTH);
        contentPanel.setLayout(null);

        Font labelFont = new Font("Tahoma", Font.PLAIN, 12);

        JLabel lblLicencia = new JLabel("No. licencia doctor:");
        lblLicencia.setFont(labelFont);
        lblLicencia.setBounds(10, 10, 130, 22);
        contentPanel.add(lblLicencia);

        txtNumLicencia = new JTextField();
        txtNumLicencia.setBounds(150, 10, 180, 22);
        txtNumLicencia.setEditable(false);
        txtNumLicencia.setText(numLicenciaDoctor);
        contentPanel.add(txtNumLicencia);

        // ===== Tabla de consultas =====
        String[] columnas = {
                "Código", "Paciente", "Doctor",
                "Fecha", "Diagnóstico", "Vigilancia"
        };

        modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(columnas);

        tableConsultas = new JTable();
        tableConsultas.setModel(modelo);
        tableConsultas.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(tableConsultas);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // ===== Botones abajo =====
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        buttonPane.add(btnCerrar);

        // Cargar datos
        cargarConsultasVisibles();
    }

    /**
     * Carga las consultas que este doctor puede ver:
     *  - Sus propias consultas
     *  - Más las consultas cuya enfermedad está marcada como de vigilancia
     */
    private void cargarConsultasVisibles() {
        modelo.setRowCount(0); // limpiar tabla

        Clinica clinica = Clinica.getInstance();
        consultasVisibles = clinica.listarConsultasPorDoctor(numLicenciaDoctor);

        if (consultasVisibles == null || consultasVisibles.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "No hay consultas disponibles para este doctor.",
                    "Sin consultas",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        for (Consulta c : consultasVisibles) {
            if (c == null) {
                continue;
            }

            String codigo = c.getCodigoConsulta();
            String nombrePaciente = "";
            Paciente p = c.getPaciente();
            if (p != null) {
                nombrePaciente = p.getNombre() + " " + p.getApellido();
            }

            String nombreDoctor = "";
            Doctor d = c.getDoctor();
            if (d != null) {
                nombreDoctor = d.getNombre() + " " + d.getApellido();
            }

            String fecha = (c.getFechaConsulta() != null)
                    ? c.getFechaConsulta().toString()
                    : "";

            String diagnostico = c.getDiagnostico();
            String vigilancia = c.isEsEnfermedadVigilancia() ? "Sí" : "No";

            Object[] fila = {
                    codigo,
                    nombrePaciente,
                    nombreDoctor,
                    fecha,
                    diagnostico,
                    vigilancia
            };

            modelo.addRow(fila);
        }
    }
}
