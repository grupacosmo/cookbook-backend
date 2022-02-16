package pl.edu.pk.cosmo.cookbookbackend.service.dto;

import lombok.Data;

@Data
public class AccountDTO {
    private Long id;
    private String name;
    private String email;
    private String password;

}
