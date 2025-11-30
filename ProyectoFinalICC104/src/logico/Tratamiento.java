package logico;

import java.io.Serializable;
import java.util.ArrayList;

public class Tratamiento implements Serializable {

    private static final long serialVersionUID = 1L;

    private static int contador = 1;

    private String codigoTratamiento;

    // Información del paciente
    private String nombrePaciente;
    private int edadPaciente;
    private boolean alergico;
    private String detalleAlergia;

    // Información de la consulta
    private String codigoConsulta;
    private String especialidad;

    // Medicamentos asociados
    private ArrayList<Medicamento> medicamentos;

    // ============================================================
    //                 GENERACIÓN DE CÓDIGO
    // ============================================================
    public static String generarCodigo() {
        return String.format("TRA-%05d", contador++);
    }

    // ============================================================
    //              CONSTRUCTOR OFICIAL (EL QUE USA REGTRATAMIENTO)
    // ============================================================
    public Tratamiento(String codigoTratamiento,
                       String nombrePaciente,
                       int edadPaciente,
                       boolean alergico,
                       String detalleAlergia,
                       String codigoConsulta,
                       String especialidad) {

        this.codigoTratamiento = codigoTratamiento;
        this.nombrePaciente = nombrePaciente;
        this.edadPaciente = edadPaciente;
        this.alergico = alergico;
        this.detalleAlergia = detalleAlergia;
        this.codigoConsulta = codigoConsulta;
        this.especialidad = especialidad;
        this.medicamentos = new ArrayList<>();
    }

    // ============================================================
    //                       GETTERS / SETTERS
    // ============================================================
    public String getCodigoTratamiento() {
        return codigoTratamiento;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public int getEdadPaciente() {
        return edadPaciente;
    }

    public boolean isAlergico() {
        return alergico;
    }

    public String getDetalleAlergia() {
        return detalleAlergia;
    }

    public String getCodigoConsulta() {
        return codigoConsulta;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public ArrayList<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(ArrayList<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }

    // ============================================================
    //                       MÉTODOS ÚTILES
    // ============================================================
    public void agregarMedicamento(Medicamento m) {
        medicamentos.add(m);
    }

    public void eliminarMedicamento(int index) {
        if (index >= 0 && index < medicamentos.size()) {
            medicamentos.remove(index);
        }
    }
}
