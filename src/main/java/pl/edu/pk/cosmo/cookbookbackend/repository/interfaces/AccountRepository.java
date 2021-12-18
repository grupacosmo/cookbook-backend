package pl.edu.pk.cosmo.cookbookbackend.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pk.cosmo.cookbookbackend.repository.Entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByEmail(String email);
    Account findAccountById(Long accountId);
    boolean existsById(Long accountId);
    void deleteAccountById(Long accountId);
}
