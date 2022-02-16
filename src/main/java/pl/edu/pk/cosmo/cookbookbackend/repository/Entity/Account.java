package pl.edu.pk.cosmo.cookbookbackend.repository.Entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="Accounts")
@Data
public class Account {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private String password;

    public Account(){}
}
