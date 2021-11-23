package pl.edu.pk.cosmo.cookbookbackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.edu.pk.cosmo.cookbookbackend.controller.request.SaveExampleRequest;
import pl.edu.pk.cosmo.cookbookbackend.converter.ExampleConverter;
import pl.edu.pk.cosmo.cookbookbackend.service.exception.AlreadyExistsException;
import pl.edu.pk.cosmo.cookbookbackend.service.interfaces.ExampleService;

import javax.validation.Valid;

/**
 * @author Patryk Borchowiec
 */
@RestController
@RequestMapping("/example")
public class ExampleController {
    private final ExampleService exampleService;
    private final ExampleConverter exampleConverter;

    public ExampleController(final ExampleService exampleService, final ExampleConverter exampleConverter) {
        this.exampleService = exampleService;
        this.exampleConverter = exampleConverter;
    }

    @PostMapping
    public void save(@RequestBody @Valid final SaveExampleRequest request) {
        try {
            exampleService.save(exampleConverter.toDTO(request));
        } catch (AlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }
}
