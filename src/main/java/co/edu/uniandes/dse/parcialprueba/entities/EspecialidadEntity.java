package co.edu.uniandes.dse.parcialprueba.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
public class EspecialidadEntity extends BaseEntity {
    
    private String nombre;
    private String descripcion;

    @ManyToMany(mappedBy = "especialidades")
    private List<MedicoEntity> medicos;
}
