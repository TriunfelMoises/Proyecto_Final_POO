package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

import logico.Clinica;
import logico.Tratamiento;

public class GestionTratamientos extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTable tablaTratamientos;
    private DefaultTableModel modeloTabla;

    public GestionTratamientos() {
        setTitle("Gestion de Tratamientos");
        setBounds(100, 100, 900, 600);
        setLocationRelativeTo(null);
        setModal(true);

        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));

        // TABLA
        String[] columnas = {"Codigo", "Nombre", "Descripcion", "Medicamentos", "Duracion"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaTratamientos = new JTable(modeloTabla);
        tablaTratamientos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaTratamientos.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tablaTratamientos.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
        tablaTratamientos.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(tablaTratamientos);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // BOTONES
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(e -> agregarTratamiento());
        buttonPane.add(btnAgregar);

        JButton btnModificar = new JButton("Modificar");
        btnModificar.addActionListener(e -> modificarTratamiento());
        buttonPane.add(btnModificar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> eliminarTratamiento());
        buttonPane.add(btnEliminar);

        JButton btnVerDetalles = new JButton("Ver Detalles");
        btnVerDetalles.addActionListener(e -> verDetalles());
        buttonPane.add(btnVerDetalles);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        buttonPane.add(btnCerrar);

        cargarTratamientos();
    }

    private void cargarTratamientos() {
        modeloTabla.setRowCount(0);

        for (Tratamiento t : Clinica.getInstance().getTratamientosPredefinidos()) {
            String medicamentos = String.join(", ", t.getMedicamentos());
            if (medicamentos.length() > 50) {
                medicamentos = medicamentos.substring(0, 47) + "...";
            }

            String descripcion = t.getDescripcion();
            if (descripcion.length() > 40) {
                descripcion = descripcion.substring(0, 37) + "...";
            }

            Object[] fila = {
                t.getCodigoTratamiento(),
                t.getNombreTratamiento(),
                descripcion,
                medicamentos,
                t.getDuracion()
            };
            modeloTabla.addRow(fila);
        }
    }

    private void agregarTratamiento() {
        regTratamientos dialogo = new regTratamientos();
        dialogo.setVisible(true);
        cargarTratamientos();
    }

    private void modificarTratamiento() {
        int filaSeleccionada = tablaTratamientos.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un tratamiento de la tabla", "Sin seleccion", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String codigo = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
        Tratamiento tratamiento = Clinica.getInstance().buscarTratamientoPorCodigo(codigo);

        if (tratamiento != null) {
            modificarTratamientoDialogo(tratamiento);
            cargarTratamientos();
        }
    }

    private void modificarTratamientoDialogo(Tratamiento tratamiento) {
        regTratamientos dialogo = new regTratamientos(tratamiento);
        dialogo.setTitle("Modificar Tratamiento");
        dialogo.setVisible(true);
    }

    private void eliminarTratamiento() {
        int filaSeleccionada = tablaTratamientos.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un tratamiento de la tabla", "Sin seleccion", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "Esta seguro que desea eliminar este tratamiento?", 
            "Confirmar eliminacion", 
            JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            String codigo = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
            Tratamiento tratamiento = Clinica.getInstance().buscarTratamientoPorCodigo(codigo);

            if (tratamiento != null) {
                Clinica.getInstance().eliminarTratamiento(tratamiento);
                JOptionPane.showMessageDialog(this, "Tratamiento eliminado correctamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
                cargarTratamientos();
            }
        }
    }

    private void verDetalles() {
        int filaSeleccionada = tablaTratamientos.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un tratamiento de la tabla", "Sin seleccion", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String codigo = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
        Tratamiento t = Clinica.getInstance().buscarTratamientoPorCodigo(codigo);

        if (t != null) {
            String detalles = "CODIGO: " + t.getCodigoTratamiento() + "\n\n" +
                            "NOMBRE: " + t.getNombreTratamiento() + "\n\n" +
                            "DESCRIPCION:\n" + t.getDescripcion() + "\n\n" +
                            "MEDICAMENTOS:\n";

            for (String med : t.getMedicamentos()) {
                detalles = detalles + "- " + med + "\n";
            }

            detalles = detalles + "\nINDICACIONES:\n" + t.getIndicaciones() + "\n\n" +
                      "DURACION: " + t.getDuracion();

            JOptionPane.showMessageDialog(this, detalles, "Detalles del Tratamiento", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}