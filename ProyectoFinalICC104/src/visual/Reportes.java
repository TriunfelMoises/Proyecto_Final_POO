package visual;

import javax.swing.*;

import javax.swing.border.EmptyBorder;
import java.awt.*;
import org.jfree.chart.ChartPanel;

// IMPORTAR TODAS LAS GRÁFICAS
import visualGraficos.GraficaConsultasPorMes;
import visualGraficos.GraficaConsultasEspecialidad;
import visualGraficos.GraficaEnfermedadesDiagnosticas;
import visualGraficos.GraficaEnfermedadesVigilancia;
import visualGraficos.GraficaCitasAtendidas;
import visualGraficos.GraficaPacientesNuevosRecurrentes;
import visualGraficos.GraficaDoctoresConsultas;
import visualGraficos.GraficaSexoPacientes;
import visualGraficos.GraficaAlergiasComunes;

public class Reportes extends JDialog {

    private static final long serialVersionUID = 1L;

    private JPanel contentPanel;
    private JPanel panelGrafica;
    private JComboBox<String> cbReportes;

    public Reportes() {
    	setIconImage(Toolkit.getDefaultToolkit().getImage(Reportes.class.getResource("/recursos/adm.jpg")));
        setTitle("Reportes de la Clínica");
        setBounds(100, 100, 1000, 650);
        setModal(true);
        setLocationRelativeTo(null);

        getContentPane().setLayout(new BorderLayout());

        contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPanel.setLayout(null);
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        // -------------------------
        //       TÍTULO
        // -------------------------
        JLabel lblTitulo = new JLabel("Reportes");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(new Color(28, 63, 117));
        lblTitulo.setBounds(20, 10, 300, 40);
        contentPanel.add(lblTitulo);

        // -------------------------
        //  ComboBox de reportes
        // -------------------------
        cbReportes = new JComboBox<>();
        cbReportes.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        cbReportes.setBounds(20, 70, 350, 35);

        cbReportes.addItem("Consultas atendidas por mes");
        cbReportes.addItem("Consultas por especialidad");
        cbReportes.addItem("Enfermedades más diagnosticadas");
        cbReportes.addItem("Enfermedades bajo vigilancia");
        cbReportes.addItem("Citas atendidas vs no atendidas");
        cbReportes.addItem("Pacientes nuevos vs recurrentes");
        cbReportes.addItem("Doctores con más consultas");
        cbReportes.addItem("Distribución por sexo");
        cbReportes.addItem("Alergias más comunes");

        contentPanel.add(cbReportes);

        // -------------------------
        //       Botón GENERAR
        // -------------------------
        JButton btnGenerar = new JButton("Generar");
        btnGenerar.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnGenerar.setBounds(400, 70, 120, 35);
        btnGenerar.addActionListener(e -> generarReporte());
        contentPanel.add(btnGenerar);

        // -------------------------
        //     Botón Exportar PDF
        // -------------------------
        JButton btnPdf = new JButton("Exportar PDF");
        btnPdf.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnPdf.setBounds(540, 70, 150, 35);
        contentPanel.add(btnPdf);

        // -------------------------
        //   Panel donde va la gráfica
        // -------------------------
        panelGrafica = new JPanel();
        panelGrafica.setBounds(20, 130, 940, 460);
        panelGrafica.setLayout(new BorderLayout());
        panelGrafica.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        contentPanel.add(panelGrafica);
    }

    // ============================================================
    //              GENERAR EL REPORTE SELECCIONADO
    // ============================================================
    private void generarReporte() {

        panelGrafica.removeAll();  
        ChartPanel panel = null;

        int opcion = cbReportes.getSelectedIndex();

        switch (opcion) {

            case 0: // Consultas atendidas por mes
                panel = new GraficaConsultasPorMes().getPanel();
                break;

            case 1: // Consultas por especialidad
                panel = new GraficaConsultasEspecialidad().getPanel();
                break;

            case 2: // Enfermedades más diagnosticadas
                panel = new GraficaEnfermedadesDiagnosticas().getPanel();
                break;

            case 3: // Enfermedades bajo vigilancia
                panel = new GraficaEnfermedadesVigilancia().getPanel();
                break;

            case 4: // Citas atendidas vs no atendidas
                panel = new GraficaCitasAtendidas().getPanel();
                break;

            case 5: // Pacientes nuevos vs recurrentes
                panel = new GraficaPacientesNuevosRecurrentes().getPanel();
                break;

            case 6: // Doctores con más consultas
                panel = new GraficaDoctoresConsultas().getPanel();
                break;

            case 7: // Distribución por sexo
                panel = new GraficaSexoPacientes().getPanel();
                break;

            case 8: // Alergias más comunes
                panel = new GraficaAlergiasComunes().getPanel();
                break;
        }

        if (panel != null) {
            panelGrafica.add(panel, BorderLayout.CENTER);
        }

        panelGrafica.revalidate();
        panelGrafica.repaint();
    }
}
