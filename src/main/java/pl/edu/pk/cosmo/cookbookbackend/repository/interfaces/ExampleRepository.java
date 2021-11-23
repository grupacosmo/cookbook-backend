package pl.edu.pk.cosmo.cookbookbackend.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pk.cosmo.cookbookbackend.repository.entity.ExampleEntity;

/**
 * @author Patryk Borchowiec
 */
public interface ExampleRepository extends JpaRepository<ExampleEntity, Long> {
    boolean existsByName(final String name);
}
