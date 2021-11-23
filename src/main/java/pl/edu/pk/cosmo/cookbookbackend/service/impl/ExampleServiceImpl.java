package pl.edu.pk.cosmo.cookbookbackend.service.impl;

import org.springframework.stereotype.Service;
import pl.edu.pk.cosmo.cookbookbackend.converter.ExampleConverter;
import pl.edu.pk.cosmo.cookbookbackend.repository.interfaces.ExampleRepository;
import pl.edu.pk.cosmo.cookbookbackend.service.dto.ExampleDTO;
import pl.edu.pk.cosmo.cookbookbackend.service.exception.AlreadyExistsException;
import pl.edu.pk.cosmo.cookbookbackend.service.interfaces.ExampleService;

import javax.validation.constraints.NotNull;

/**
 * @author Patryk Borchowiec
 */
@Service
public class ExampleServiceImpl implements ExampleService {
    private final ExampleRepository exampleRepository;
    private final ExampleConverter exampleConverter;

    public ExampleServiceImpl(final ExampleRepository exampleRepository, final ExampleConverter exampleConverter) {
        this.exampleRepository = exampleRepository;
        this.exampleConverter = exampleConverter;
    }

    @Override
    public void save(@NotNull final ExampleDTO example) throws AlreadyExistsException {
        if (exampleRepository.existsByName(example.getName())) {
            throw new AlreadyExistsException(
                    String.format("Example with name '%s' already exists.", example.getName())
            );
        }

        exampleRepository.save(exampleConverter.toEntity(example));
    }
}
