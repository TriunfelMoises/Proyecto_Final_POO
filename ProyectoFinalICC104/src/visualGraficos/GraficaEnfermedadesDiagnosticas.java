package visualGraficos;

import java.util.HashMap;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import logico.Clinica;
import logico.Consulta;
import logico.HistoriaClinica;
import logico.Paciente;

public class GraficaEnfermedadesDiagnosticas {

    private ChartPanel chartPanel;

    public GraficaEnfermedadesDiagnosticas() {

        DefaultCategoryDataset dataset = getDataset();

        JFreeChart chart = ChartFactory.createBarChart(
                "Enfermedades más diagnosticadas",
                "Enfermedad",
                "Frecuencia",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false
        );

        chart.setBackgroundPaint(java.awt.Color.WHITE);
        chart.getTitle().setPaint(new java.awt.Color(28, 63, 117));

        chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);
    }

    private DefaultCategoryDataset getDataset() {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        HashMap<String, Integer> contador = new HashMap<>();

        for (Paciente p : Clinica.getInstance().getPacientes()) {

            HistoriaClinica hc = p.getHistoriaClinica();

            for (Consulta c : hc.getConsultas()) {

                String diag = c.getDiagnostico();

                if (diag == null || diag.trim().isEmpty()) {
                    continue; // ignorar diagnósticos vacíos
                }

                contador.put(diag, contador.getOrDefault(diag, 0) + 1);
            }
        }

        for (Map.Entry<String, Integer> entry : contador.entrySet()) {
            dataset.addValue(entry.getValue(), "Frecuencia", entry.getKey());
        }

        return dataset;
    }

    public ChartPanel getPanel() {
        return chartPanel;
    }
}
