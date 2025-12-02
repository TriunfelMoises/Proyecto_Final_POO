package visualGraficos;

import java.util.HashMap;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import logico.Clinica;
import logico.Consulta;
import logico.HistoriaClinica;
import logico.Paciente;

public class GraficaConsultasEspecialidad {

    private ChartPanel chartPanel;

    public GraficaConsultasEspecialidad() {

        DefaultPieDataset dataset = getDataset();

        JFreeChart chart = ChartFactory.createPieChart(
                "Consultas por especialidad",
                dataset,
                true,   // leyenda
                true,
                false
        );

        chart.getTitle().setPaint(new java.awt.Color(28, 63, 117));
        chart.setBackgroundPaint(java.awt.Color.WHITE);

        chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);
    }

    private DefaultPieDataset getDataset() {

        DefaultPieDataset dataset = new DefaultPieDataset();
        HashMap<String, Integer> contador = new HashMap<>();

        // RECORRER PACIENTES Y SUS CONSULTAS
        for (Paciente p : Clinica.getInstance().getPacientes()) {

            HistoriaClinica hc = p.getHistoriaClinica();
            if (hc == null) continue;

            for (Consulta c : hc.getConsultas()) {

                if (c == null || c.getDoctor() == null) continue;

                String esp = c.getDoctor().getEspecialidad();

                if (esp == null || esp.trim().isEmpty()) continue;

                contador.put(esp, contador.getOrDefault(esp, 0) + 1);
            }
        }

        // AGREGAR AL DATASET
        for (Map.Entry<String, Integer> entry : contador.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

        return dataset;
    }

    public ChartPanel getPanel() {
        return chartPanel;
    }
}
