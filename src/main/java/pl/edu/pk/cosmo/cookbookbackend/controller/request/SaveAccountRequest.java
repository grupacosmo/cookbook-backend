package pl.edu.pk.cosmo.cookbookbackend.controller.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SaveAccountRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String password;

}
