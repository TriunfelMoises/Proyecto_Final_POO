package logico;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class HistoriaClinica implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Paciente paciente;
    private ArrayList<Consulta> consultas;
    private LocalDate fechaCreacion;
    
    public HistoriaClinica(Paciente paciente, LocalDate fechaCreacion) {
        super();
        this.paciente = paciente;
        this.consultas = new ArrayList<>();
        this.fechaCreacion = fechaCreacion;
    }
    
    public Paciente getPaciente() {
        return paciente;
    }
    
    public ArrayList<Consulta> getConsultas() {
        return consultas;
    }
    
    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void agregarConsulta(Consulta consulta) {
        consultas.add(consulta);
    }
    
    public ArrayList<Consulta> obtenerTodasConsultas() {
        return consultas;
    }
    
    public ArrayList<Consulta> obtenerConsultasParaResumen() {
        ArrayList<Consulta> resumen = new ArrayList<>();
        for (Consulta consulta : consultas) {
            if (consulta.vaAlResumen()) {
                resumen.add(consulta);
            }
        }
        return resumen;
    }
    
    public ArrayList<Consulta> obtenerConsultasEnfermedadVigilancia() {
        ArrayList<Consulta> vigilancia = new ArrayList<>();
        for (Consulta consulta : consultas) {
            if (consulta.isEsEnfermedadVigilancia()) {
                vigilancia.add(consulta);
            }
        }
        return vigilancia;
    }
    
    public ArrayList<Consulta> obtenerConsultasPorFecha(LocalDate inicio, LocalDate fin) {
        ArrayList<Consulta> porFechas = new ArrayList<>();
        for (Consulta consulta : consultas) {
            LocalDate fecha = consulta.getFechaConsulta();
            if ((fecha.isEqual(inicio) || fecha.isAfter(inicio)) && 
                (fecha.isEqual(fin) || fecha.isBefore(fin))) {
                porFechas.add(consulta);
            }
        }
        return porFechas;
    }
    
    public ArrayList<Consulta> obtenerUltimasConsultas(int cantidad) {
        ArrayList<Consulta> ultimas = new ArrayList<>();
        int total = consultas.size();
        
        if (cantidad > total) {
            cantidad = total;
        }
        
        for (int i = total - cantidad; i < total; i++) {
            ultimas.add(consultas.get(i));
        }
        
        return ultimas;
    }
    
    public int cantidadConsultas() {
        return consultas.size();
    }
    
    public String generarResumen() {
        StringBuilder resumen = new StringBuilder();
        				
        resumen.append("===============================================================\n");
        resumen.append("           RESUMEN DE HISTORIA CLÍNICA\n");
        resumen.append("===============================================================\n\n");

        // Información del paciente
        resumen.append("PACIENTE: ").append(paciente.getNombre()).append(" ");
        resumen.append(paciente.getApellido()).append("\n");
        resumen.append("CÉDULA: ").append(paciente.getCedula()).append("\n");
        resumen.append("CÓDIGO: ").append(paciente.getCodigoPaciente()).append("\n");
        resumen.append("TIPO DE SANGRE: ").append(paciente.getTipoSangre()).append("\n");
        resumen.append("FECHA DE REGISTRO: ").append(fechaCreacion).append("\n\n");

        // Alergias
        ArrayList<Alergia> alergias = paciente.getAlergias();
        if (alergias != null && !alergias.isEmpty()) {
            resumen.append("ALERGIAS:\n");
            for (Alergia a : alergias) {
                resumen.append("  ").append(a.getNombre());
                if (a.getTipo() != null && !a.getTipo().isEmpty()) {
                    resumen.append(" (").append(a.getTipo()).append(")");
                }
                resumen.append("\n");
            }
            resumen.append("\n");
        } else {
            resumen.append("ALERGIAS: No presenta\n\n");
        }

        resumen.append("===============================================================\n");
        resumen.append("CONSULTAS INCLUIDAS EN EL RESUMEN:\n");
        resumen.append("(Incluye consultas marcadas + enfermedades bajo vigilancia)\n");
        resumen.append("===============================================================\n\n");

        // Obtener consultas para resumen
        ArrayList<Consulta> consultasResumen = obtenerConsultasParaResumen();

        if (consultasResumen.isEmpty()) {
            resumen.append("No hay consultas marcadas para resumen.\n");
        } else {
            int contador = 1;
            for (Consulta c : consultasResumen) {
                resumen.append("[").append(contador++).append("] CONSULTA: ");
                resumen.append(c.getCodigoConsulta()).append("\n");
                resumen.append("    Fecha: ").append(c.getFechaConsulta()).append("\n");
                resumen.append("    Doctor: ").append(c.getDoctor().getNombre()).append(" ");
                resumen.append(c.getDoctor().getApellido()).append("\n");
                resumen.append("    Especialidad: ").append(c.getDoctor().getEspecialidad()).append("\n");
                resumen.append("    Síntomas: ").append(c.getSintomas()).append("\n");
                resumen.append("    Diagnóstico: ").append(c.getDiagnostico()).append("\n");
                resumen.append("    Tratamiento: ").append(c.getTratamiento().getNombreTratamiento()).append("\n");
                
                if (c.isEsEnfermedadVigilancia()) {
                    resumen.append("    ENFERMEDAD BAJO VIGILANCIA EPIDEMIOLÓGICA\n");
                }
                
                if (c.getNotasMedicas() != null && !c.getNotasMedicas().isEmpty()) {
                    resumen.append("    Notas: ").append(c.getNotasMedicas()).append("\n");
                }
                
                resumen.append("\n");
            }
        }

        resumen.append("===============================================================\n");
        resumen.append("ESTADÍSTICAS:\n");
        resumen.append("===============================================================\n");
        resumen.append("Total de consultas: ").append(consultas.size()).append("\n");
        resumen.append("Consultas en resumen: ").append(consultasResumen.size()).append("\n");
        resumen.append("Enfermedades bajo vigilancia: ");
        resumen.append(obtenerConsultasEnfermedadVigilancia().size()).append("\n\n");

        resumen.append("===============================================================\n");
        resumen.append("Fecha de generación: ").append(LocalDate.now()).append("\n");
        resumen.append("===============================================================\n");

        return resumen.toString();
    }
}