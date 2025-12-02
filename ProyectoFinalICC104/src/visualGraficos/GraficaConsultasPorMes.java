package visualGraficos;

import java.time.Month;
import java.util.HashMap;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import logico.Clinica;
import logico.Consulta;
import logico.HistoriaClinica;
import logico.Paciente;

public class GraficaConsultasPorMes {

    private ChartPanel chartPanel;

    public GraficaConsultasPorMes() {

        DefaultCategoryDataset dataset = getDataset();

        JFreeChart chart = ChartFactory.createBarChart(
                "Consultas atendidas por mes",
                "Mes",
                "Cantidad",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        chartPanel = new ChartPanel(chart);
    }

    private DefaultCategoryDataset getDataset() {

        HashMap<Month, Integer> contador = new HashMap<>();

        for (Month m : Month.values()) {
            contador.put(m, 0);
        }

        for (Paciente p : Clinica.getInstance().getPacientes()) {

            HistoriaClinica hc = p.getHistoriaClinica();
            if (hc == null) continue;

            for (Consulta c : hc.getConsultas()) {

                Month mes = c.getFechaConsulta().getMonth();
                contador.put(mes, contador.get(mes) + 1);
            }
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Month m : Month.values()) {
            dataset.addValue(contador.get(m), "Consultas", m.toString());
        }

        return dataset;
    }

    public ChartPanel getPanel() {
        return chartPanel;
    }
}
