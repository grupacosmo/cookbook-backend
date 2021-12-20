package pl.edu.pk.cosmo.cookbookbackend.controller.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ChangePasswordRequest {
    @NotNull
    private Long id;
    @NotBlank
    private String password;
}
