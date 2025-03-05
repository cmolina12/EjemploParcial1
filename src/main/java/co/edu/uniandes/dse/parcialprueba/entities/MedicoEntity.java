package co.edu.uniandes.dse.parcialprueba.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
public class MedicoEntity extends BaseEntity {
    
    private String nombre;
    private String apellido;
    private String registroMedico;

    @ManyToMany
    @JoinTable(
        name = "medico_especialidad",
        joinColumns = @JoinColumn(name = "medico_id"),
        inverseJoinColumns = @JoinColumn(name = "especialidad_id")
    )
    private List<EspecialidadEntity> especialidades;
}
