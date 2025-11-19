package visual;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import logico.Clinica;
import logico.Enfermedad;

import java.awt.*;
import java.util.ArrayList;

public class listEnfermedad extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTable table;
    private DefaultTableModel model;

    public listEnfermedad() {
        setTitle("Listado de Enfermedades");
        setBounds(100, 100, 800, 400);
        setLocationRelativeTo(null);
        setModal(true);

        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));

        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Solo la última columna (Bajo vigilancia) editable
                return column == 3;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 3) {
                    return Boolean.class;
                }
                return String.class;
            }
        };

        String[] columnas = { "Código", "Nombre", "Descripción", "Bajo vigilancia" };
        model.setColumnIdentifiers(columnas);

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Listener para manejar cambios en "Bajo vigilancia"
        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int fila = e.getFirstRow();
                    int columna = e.getColumn();

                    if (fila < 0 || columna != 3) return;

                    String codigo = (String) model.getValueAt(fila, 0);
                    Object valor = model.getValueAt(fila, 3);

                    boolean bajoVigilancia = false;
                    if (valor instanceof Boolean) {
                        bajoVigilancia = (Boolean) valor;
                    } else if (valor != null) {
                        bajoVigilancia = Boolean.parseBoolean(valor.toString());
                    }

                    if (bajoVigilancia) {
                        Clinica.getInstance().activarVigilanciaEnfermedad(codigo);
                    } else {
                        Clinica.getInstance().desactivarVigilanciaEnfermedad(codigo);
                    }
                }
            }
        });

        // Panel botones
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        buttonPane.add(btnCerrar);

        cargarEnfermedades();
    }

    private void cargarEnfermedades() {
        model.setRowCount(0);

        ArrayList<Enfermedad> lista = Clinica.getInstance().listarEnfermedades();
        if (lista == null) return;

        for (Enfermedad enf : lista) {
            Object[] fila = new Object[4];
            fila[0] = enf.getCodigoEnfermedad();
            fila[1] = enf.getNombre();
            fila[2] = enf.getDescripcion();
            fila[3] = enf.isBajoVigilancia();
            model.addRow(fila);
        }
    }
}
