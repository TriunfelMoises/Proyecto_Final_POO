package visualGraficos;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import logico.Clinica;
import logico.HistoriaClinica;
import logico.Paciente;

public class GraficaPacientesNuevosRecurrentes {

    private ChartPanel chartPanel;

    public GraficaPacientesNuevosRecurrentes() {

        DefaultPieDataset dataset = getDataset();

        JFreeChart chart = ChartFactory.createPieChart(
                "Pacientes nuevos vs recurrentes",
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

        int nuevos = 0;
        int recurrentes = 0;

        for (Paciente p : Clinica.getInstance().getPacientes()) {

            HistoriaClinica hc = p.getHistoriaClinica();
            if (hc == null) continue;

            int cant = hc.getConsultas().size();

            if (cant <= 1) nuevos++;
            else recurrentes++;
        }

        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Nuevos", nuevos);
        dataset.setValue("Recurrentes", recurrentes);

        return dataset;
    }

    public ChartPanel getPanel() {
        return chartPanel;
    }
}
