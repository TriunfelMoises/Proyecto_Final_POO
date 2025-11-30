package visual;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import logico.Clinica;
import logico.Consulta;
import logico.Paciente;
import logico.Doctor;

public class listConsulta extends JDialog {

    private static final long serialVersionUID = 1L;

    private final JPanel contentPanel = new JPanel();
    private JTable tableConsultas;
    private DefaultTableModel modelo;

    public listConsulta() {
        setTitle("Listado de consultas");
        setBounds(100, 100, 900, 500);
        setLocationRelativeTo(null);
        setModal(true);

        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(contentPanel, BorderLayout.NORTH);
        contentPanel.setLayout(null);

        // ====== TABLA ======
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

        // ===== BOTONES =====
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        buttonPane.add(btnCerrar);

        // ===== CARGAR DATOS =====
        cargarConsultas();
    }

    private void cargarConsultas() {
        modelo.setRowCount(0); // limpiar tabla

        Clinica clinica = Clinica.getInstance();

        // Recorremos todos los pacientes
        for (Paciente p : clinica.getPacientes()) {
            if (p.getHistoriaClinica() == null) continue;

            for (Consulta c : p.getHistoriaClinica().getConsultas()) {
                if (c == null) continue;

                String codigo = c.getCodigoConsulta();

                String nombrePaciente = p.getNombre() + " " + p.getApellido();

                Doctor d = c.getDoctor();
                String nombreDoctor = (d != null)
                        ? d.getNombre() + " " + d.getApellido()
                        : "N/A";

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
}
