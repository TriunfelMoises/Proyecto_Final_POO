package visualGraficos;

import java.util.HashMap;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import logico.Alergia;
import logico.Clinica;
import logico.Paciente;

public class GraficaAlergiasComunes {

    private ChartPanel chartPanel;

    public GraficaAlergiasComunes() {

        DefaultCategoryDataset dataset = getDataset();

        JFreeChart chart = ChartFactory.createBarChart(
                "Alergias más comunes",
                "Alergia",
                "Pacientes",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false
        );

        chart.getTitle().setPaint(new java.awt.Color(28, 63, 117));
        chart.setBackgroundPaint(java.awt.Color.WHITE);

        chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);
    }

    private DefaultCategoryDataset getDataset() {

        HashMap<String, Integer> contador = new HashMap<>();

        for (Paciente p : Clinica.getInstance().getPacientes()) {

            for (Alergia a : p.getAlergias()) {

                if (a == null || a.getNombre() == null) continue;

                String nombre = a.getNombre();

                contador.put(nombre, contador.getOrDefault(nombre, 0) + 1);
            }
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Map.Entry<String, Integer> entry : contador.entrySet()) {
            dataset.addValue(entry.getValue(), "Pacientes", entry.getKey());
        }

        return dataset;
    }

    public ChartPanel getPanel() {
        return chartPanel;
    }
}
