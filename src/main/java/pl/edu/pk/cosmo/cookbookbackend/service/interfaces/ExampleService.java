package pl.edu.pk.cosmo.cookbookbackend.service.interfaces;

import pl.edu.pk.cosmo.cookbookbackend.service.dto.ExampleDTO;
import pl.edu.pk.cosmo.cookbookbackend.service.exception.AlreadyExistsException;

import javax.validation.constraints.NotNull;

/**
 * @author Patryk Borchowiec
 */
public interface ExampleService {
    void save(@NotNull final ExampleDTO example) throws AlreadyExistsException;
}
