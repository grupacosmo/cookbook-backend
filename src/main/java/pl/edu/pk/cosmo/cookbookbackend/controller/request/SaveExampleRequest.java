package pl.edu.pk.cosmo.cookbookbackend.controller.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Patryk Borchowiec
 */
@Data
public class SaveExampleRequest {
    @NotBlank
    private String name;
}
