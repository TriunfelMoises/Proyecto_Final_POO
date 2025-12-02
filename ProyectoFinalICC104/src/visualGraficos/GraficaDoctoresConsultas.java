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
import logico.Doctor;

public class GraficaDoctoresConsultas {

    private ChartPanel chartPanel;

    public GraficaDoctoresConsultas() {

        DefaultCategoryDataset dataset = getDataset();

        JFreeChart chart = ChartFactory.createBarChart(
                "Doctores con más consultas",
                "Doctor",
                "Consultas",
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
            if (hc == null) continue;

            for (Consulta c : hc.getConsultas()) {

                Doctor d = c.getDoctor();
                if (d == null) continue;

                String nombre = d.getNombre() + " " + d.getApellido();

                contador.put(nombre, contador.getOrDefault(nombre, 0) + 1);
            }
        }

        for (Map.Entry<String, Integer> entry : contador.entrySet()) {
            dataset.addValue(entry.getValue(), "Consultas", entry.getKey());
        }

        return dataset;
    }

    public ChartPanel getPanel() {
        return chartPanel;
    }
}
