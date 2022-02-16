package pl.edu.pk.cosmo.cookbookbackend.repository.interfaces;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.edu.pk.cosmo.cookbookbackend.repository.Entity.Account;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    AccountRepository underTest;


    @BeforeEach
    void setUp() {
        underTest.deleteAll();
    }

    @Nested
    class existsByEmail{
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
    }

    @Nested
    class findAccountById {
        @Test
        void itShouldReturnAccountWithGivenId() {
            // given
            Long id = 1L;
            Account account = new Account();
            account.setName("name");
            account.setEmail("email");
            account.setPassword("password");

            underTest.save(account);

            // when
            Boolean expected = underTest.findAccountById(id) != null;

            //then
            assertThat(expected).isTrue();
        }

        @Test
        void itShouldNotReturnAccountWithGivenId() {
            // given
            Long id = 1L;

            // when
            Boolean expected = underTest.findAccountById(id) != null;

            //then
            assertThat(expected).isFalse();
        }
    }

    @Nested
    class existsById {
        @Test
        void itShouldCheckIfAccountExistsWithGivenId() {
            // given
            Long id = 1L;

            Account account = new Account();
            account.setName("name");
            account.setEmail("email");
            account.setPassword("password");

            underTest.save(account);

            // when
            Boolean expected = underTest.existsById(id);

            //then
            assertThat(expected).isTrue();
        }

        @Test
        void itShouldCheckIfAccountDoesNotExistWithGivenId() {
            // given
            Long id = 1L;

            // when
            Boolean expected = underTest.existsById(id);

            //then
            assertThat(expected).isFalse();
        }
    }

    @Nested
    class deleteAccountById {
        @Test
        void itShouldCheckIfAccountIsBeingDeleted() {
            // given
            Long id = 1L;

            Account account = new Account();
            account.setName("name");
            account.setEmail("email");
            account.setPassword("password");
            underTest.save(account);



            // when
            underTest.deleteAccountById(id);
            Boolean expected = underTest.existsById(id);

            // then
            assertThat(expected).isFalse();
        }
    }
}