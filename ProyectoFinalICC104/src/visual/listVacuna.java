package visual;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logico.Clinica;
import logico.Vacuna;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

public class listVacuna extends JDialog {

    private JPanel contentPanel = new JPanel();
    private JTable table;
    private DefaultTableModel model;

    public listVacuna() {
        setTitle("Listado de Vacunas");
        setBounds(100, 100, 700, 500);
        setLocationRelativeTo(null);
        setModal(true);

        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout());

        String[] headers = {
                "Código", "Nombre", "Lote", "Cantidad",
                "Fecha Caducidad", "Activa"
        };

        model = new DefaultTableModel();
        model.setColumnIdentifiers(headers);

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        cargarVacunas();

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = table.getSelectedRow();
                if (fila >= 0) {
                    editarVacuna(fila);
                }
            }
        });
    }

    private void cargarVacunas() {
        model.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        for (Vacuna v : Clinica.getInstance().listarVacunas()) {
            Object[] fila = {
                    v.getCodigoVacuna(),
                    v.getNombre(),
                    v.getNumeroLote(),
                    v.getCantidad(),
                    sdf.format(v.getFechaCaducidad()),
                    v.isActiva() ? "Sí" : "No"
            };
            model.addRow(fila);
        }
    }

    private void editarVacuna(int fila) {
        String codigo = (String) model.getValueAt(fila, 0);
        Vacuna v = Clinica.getInstance().buscarVacunaPorCodigo(codigo);

        if (v == null) return;

        String nuevaCantidadStr = JOptionPane.showInputDialog(
                this,
                "Nueva cantidad:",
                v.getCantidad()
        );

        if (nuevaCantidadStr != null) {
            int nuevaCantidad = Integer.parseInt(nuevaCantidadStr);
            v.setCantidad(nuevaCantidad);
        }

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿La vacuna está activa?",
                "Estado",
                JOptionPane.YES_NO_OPTION);

        v.setActiva(opcion == JOptionPane.YES_OPTION);

        cargarVacunas();
    }
}
