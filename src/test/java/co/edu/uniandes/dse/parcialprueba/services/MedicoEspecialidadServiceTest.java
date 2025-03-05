package co.edu.uniandes.dse.parcialprueba.services;

import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.repositories.MedicoRepository;
import co.edu.uniandes.dse.parcialprueba.repositories.EspecialidadRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest

@Import(MedicoEspecialidadService.class)
public class MedicoEspecialidadServiceTest {

    @Autowired
    private MedicoEspecialidadService medicoEspecialidadService;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private EspecialidadRepository especialidadRepository;

    private PodamFactory factory = new PodamFactoryImpl();

    /**
     * ✅ Test donde la especialidad se agrega correctamente al médico
     */
    @Test
    void testAddEspecialidadAMedico() throws EntityNotFoundException {
        MedicoEntity medico = factory.manufacturePojo(MedicoEntity.class);
        medicoRepository.save(medico);

        EspecialidadEntity especialidad = factory.manufacturePojo(EspecialidadEntity.class);
        especialidadRepository.save(especialidad);

        medicoEspecialidadService.addEspecialidad(medico.getId(), especialidad.getId());

        MedicoEntity medicoRecuperado = medicoRepository.findById(medico.getId()).get();
        assertTrue(medicoRecuperado.getEspecialidades().contains(especialidad));
    }

    /**
     * ❌ Test donde se lanza una excepción porque el médico no existe
     */
    @Test
    void testAddEspecialidadAMedicoNoExistente() {
        EspecialidadEntity especialidad = factory.manufacturePojo(EspecialidadEntity.class);
        especialidadRepository.save(especialidad);

        Long idMedicoInvalido = 999L; // ID que no existe

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            medicoEspecialidadService.addEspecialidad(idMedicoInvalido, especialidad.getId());
        });

        assertEquals("El médico con el ID especificado no existe.", exception.getMessage());
    }

    /**
     * ❌ Test donde se lanza una excepción porque la especialidad no existe
     */
    @Test
    void testAddEspecialidadAEspecialidadNoExistente() {
        MedicoEntity medico = factory.manufacturePojo(MedicoEntity.class);
        medicoRepository.save(medico);

        Long idEspecialidadInvalido = 999L; // ID que no existe

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            medicoEspecialidadService.addEspecialidad(medico.getId(), idEspecialidadInvalido);
        });

        assertEquals("La especialidad con el ID especificado no existe.", exception.getMessage());
    }
}
