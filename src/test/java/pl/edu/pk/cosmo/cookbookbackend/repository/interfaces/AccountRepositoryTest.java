package pl.edu.pk.cosmo.cookbookbackend.repository.interfaces;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.edu.pk.cosmo.cookbookbackend.repository.Entity.Account;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    AccountRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldCheckWhenAccountExistsByEmail() {
        // given
        Account account = new Account();
        account.setName("name");
        account.setEmail("email");
        account.setPassword("password");
        underTest.save(account);

        // when
        Boolean expected = underTest.existsByEmail("email");

        // then
        assertThat(expected).isTrue();
    }

    @Test
    void itShouldCheckWhenAccountNotExistsByEmail() {
        // given
        Account account = new Account();
        account.setName("name");
        account.setEmail("email");
        account.setPassword("password");

        // when
        Boolean expected = underTest.existsByEmail("email");

        // then
        assertThat(expected).isFalse();
    }
//
//    @Test
//    void findAccountById() {
//    }
//
//    @Test
//    void existsById() {
//    }
//
//    @Test
//    void deleteAccountById() {
//    }
}