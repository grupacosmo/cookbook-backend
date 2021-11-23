package pl.edu.pk.cosmo.cookbookbackend.repository.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Patryk Borchowiec
 */
@Data
@Entity
public class ExampleEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;
}
