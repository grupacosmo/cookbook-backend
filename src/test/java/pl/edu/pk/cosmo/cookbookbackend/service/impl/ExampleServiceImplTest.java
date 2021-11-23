package pl.edu.pk.cosmo.cookbookbackend.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.edu.pk.cosmo.cookbookbackend.converter.ExampleConverter;
import pl.edu.pk.cosmo.cookbookbackend.repository.entity.ExampleEntity;
import pl.edu.pk.cosmo.cookbookbackend.repository.interfaces.ExampleRepository;
import pl.edu.pk.cosmo.cookbookbackend.service.dto.ExampleDTO;
import pl.edu.pk.cosmo.cookbookbackend.service.exception.AlreadyExistsException;

import static org.mockito.Mockito.*;

/**
 * @author Patryk Borchowiec
 */
class ExampleServiceImplTest {
    @InjectMocks
    private ExampleServiceImpl exampleService;

    @Mock
    private ExampleRepository exampleRepository;

    @Mock
    private ExampleConverter exampleConverter;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    class save {
        @Test
        void exampleWithGivenNameAlreadyExists_shouldThrowAlreadyExistsException() {
            // given
            final String exampleName = "name";
            final ExampleDTO exampleDTO = new ExampleDTO();
            exampleDTO.setName(exampleName);

            // when
            when(exampleRepository.existsByName(exampleName)).thenReturn(true);

            // then
            Assertions.assertThrows(AlreadyExistsException.class, () -> exampleService.save(exampleDTO));
            verify(exampleRepository, times(0)).save(any(ExampleEntity.class));
        }

        @Test
        void properData_shouldSaveInDatabase() throws AlreadyExistsException {
            // given
            final String exampleName = "name";
            final ExampleDTO exampleDTO = new ExampleDTO();
            exampleDTO.setName(exampleName);
            final ExampleEntity exampleEntity = new ExampleEntity();
            exampleEntity.setName(exampleName);

            // when
            when(exampleRepository.existsByName(exampleName)).thenReturn(false);
            when(exampleConverter.toEntity(exampleDTO)).thenReturn(exampleEntity);
            exampleService.save(exampleDTO);

            // then
            verify(exampleRepository, times(1)).save(exampleEntity);
        }
    }
}