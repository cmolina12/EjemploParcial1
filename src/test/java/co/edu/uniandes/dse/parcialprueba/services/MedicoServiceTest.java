package co.edu.uniandes.dse.parcialprueba.services;

import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(MedicoService.class)
public class MedicoServiceTest {

    @Autowired
    private MedicoService medicoService;

    private PodamFactory factory = new PodamFactoryImpl();

    @Test
    void testCreateMedicoValido() throws IllegalOperationException {
        MedicoEntity medico = factory.manufacturePojo(MedicoEntity.class);
        medico.setRegistroMedico("RM1234"); // Registro válido
        MedicoEntity result = medicoService.createMedico(medico);

        assertNotNull(result);
        assertEquals("RM1234", result.getRegistroMedico());
    }

    @Test
    void testCreateMedicoRegistroInvalido() {
        MedicoEntity medico = factory.manufacturePojo(MedicoEntity.class);
        medico.setRegistroMedico("1234RM"); // Registro inválido

        assertThrows(IllegalOperationException.class, () -> medicoService.createMedico(medico));
    }
}

