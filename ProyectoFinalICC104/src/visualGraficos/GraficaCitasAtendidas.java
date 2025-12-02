package visualGraficos;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import logico.Cita;
import logico.Clinica;

public class GraficaCitasAtendidas {

    private ChartPanel chartPanel;

    public GraficaCitasAtendidas() {

        DefaultPieDataset dataset = getDataset();

        JFreeChart chart = ChartFactory.createPieChart(
                "Citas atendidas vs no atendidas",
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

        DefaultPieDataset dataset = new DefaultPieDataset();

        int atendidas = 0;
        int noAtendidas = 0;

        for (Cita c : Clinica.getInstance().getCitas()) {

            if (c == null || c.getEstadoCita() == null) continue;

            String estado = c.getEstadoCita().trim().toLowerCase();

            if (estado.equals("completada")) {
                atendidas++;
            } else {
                noAtendidas++;
            }
        }

        dataset.setValue("Atendidas", atendidas);
        dataset.setValue("No atendidas", noAtendidas);

        return dataset;
    }

    public ChartPanel getPanel() {
        return chartPanel;
    }
}
