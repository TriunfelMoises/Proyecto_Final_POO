package visualGraficos;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import logico.Clinica;
import logico.Paciente;

public class GraficaSexoPacientes {

    private ChartPanel chartPanel;

    public GraficaSexoPacientes() {

        DefaultPieDataset dataset = getDataset();

        JFreeChart chart = ChartFactory.createPieChart(
                "Distribución por sexo",
                dataset,
                true,
                true,
                false
        );

        chart.getTitle().setPaint(new java.awt.Color(28, 63, 117));
        chart.setBackgroundPaint(java.awt.Color.WHITE);

        chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);
    }

    private DefaultPieDataset getDataset() {

        int hombres = 0;
        int mujeres = 0;

        for (Paciente p : Clinica.getInstance().getPacientes()) {

            char sexo = p.getSexo();

            if (sexo == 'F' || sexo == 'f') mujeres++;
            else hombres++;
        }

        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Hombres", hombres);
        dataset.setValue("Mujeres", mujeres);

        return dataset;
    }

    public ChartPanel getPanel() {
        return chartPanel;
    }
}
