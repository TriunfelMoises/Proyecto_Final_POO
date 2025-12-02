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
import logico.Enfermedad;

public class GraficaEnfermedadesVigilancia {

    private ChartPanel chartPanel;

    public GraficaEnfermedadesVigilancia() {

        DefaultCategoryDataset dataset = getDataset();

        JFreeChart chart = ChartFactory.createBarChart(
                "Enfermedades bajo vigilancia",
                "Enfermedad",
                "Casos",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        chartPanel = new ChartPanel(chart);
    }

    private DefaultCategoryDataset getDataset() {

        HashMap<String, Integer> contador = new HashMap<>();

        for (Enfermedad e : Clinica.getInstance().listarEnfermedadesVigiladas()) {
            contador.put(e.getNombre(), 0);
        }

        for (Paciente p : Clinica.getInstance().getPacientes()) {

            HistoriaClinica hc = p.getHistoriaClinica();
            if (hc == null) continue;

            for (Consulta c : hc.getConsultas()) {

                if (c.isEsEnfermedadVigilancia()) {

                    String enfermedad = c.getDiagnostico();

                    contador.put(
                            enfermedad,
                            contador.getOrDefault(enfermedad, 0) + 1
                    );
                }
            }
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Map.Entry<String, Integer> entry : contador.entrySet()) {
            dataset.addValue(entry.getValue(), "Casos", entry.getKey());
        }

        return dataset;
    }

    public ChartPanel getPanel() {
        return chartPanel;
    }
}
