package pl.edu.pk.cosmo.cookbookbackend.converter;

import org.springframework.stereotype.Component;
import pl.edu.pk.cosmo.cookbookbackend.controller.request.SaveExampleRequest;
import pl.edu.pk.cosmo.cookbookbackend.repository.entity.ExampleEntity;
import pl.edu.pk.cosmo.cookbookbackend.service.dto.ExampleDTO;

import java.util.Optional;

/**
 * @author Patryk Borchowiec
 */
@Component
public class ExampleConverter {
    public ExampleDTO toDTO(final SaveExampleRequest saveExampleRequest) {
        return Optional.ofNullable(saveExampleRequest)
                .map(request -> {
                    final ExampleDTO exampleDTO = new ExampleDTO();
                    exampleDTO.setId(null);
                    exampleDTO.setName(exampleDTO.getName());
                    return exampleDTO;
                })
                .orElse(null);
    }

    public ExampleEntity toEntity(final ExampleDTO example) {
        return Optional.ofNullable(example)
                .map(exampleDTO -> {
                    final ExampleEntity exampleEntity = new ExampleEntity();
                    exampleEntity.setId(exampleDTO.getId());
                    exampleEntity.setName(exampleDTO.getName());
                    return exampleEntity;
                })
                .orElse(null);
    }
}
