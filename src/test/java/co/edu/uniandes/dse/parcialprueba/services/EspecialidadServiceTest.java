package co.edu.uniandes.dse.parcialprueba.services;


import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(EspecialidadService.class)
public class EspecialidadServiceTest {

    @Autowired
    private EspecialidadService especialidadService;

    private PodamFactory factory = new PodamFactoryImpl();

    @Test
    void testCreateEspecialidadValida() throws IllegalOperationException {
        EspecialidadEntity especialidad = factory.manufacturePojo(EspecialidadEntity.class);
        especialidad.setDescripcion("Cardiología Avanzada"); // Descripción válida

        EspecialidadEntity result = especialidadService.createEspecialidad(especialidad);
        assertNotNull(result);
        assertTrue(result.getDescripcion().length() >= 10);
    }

    @Test
    void testCreateEspecialidadDescripcionCorta() {
        EspecialidadEntity especialidad = factory.manufacturePojo(EspecialidadEntity.class);
        especialidad.setDescripcion("Corta"); // Descripción demasiado corta

        assertThrows(IllegalOperationException.class, () -> especialidadService.createEspecialidad(especialidad));
    }
}
