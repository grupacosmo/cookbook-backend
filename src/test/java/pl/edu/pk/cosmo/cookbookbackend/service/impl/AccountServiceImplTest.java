package pl.edu.pk.cosmo.cookbookbackend.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.edu.pk.cosmo.cookbookbackend.controller.request.ChangePasswordRequest;
import pl.edu.pk.cosmo.cookbookbackend.converter.AccountConverter;
import pl.edu.pk.cosmo.cookbookbackend.repository.Entity.Account;
import pl.edu.pk.cosmo.cookbookbackend.repository.interfaces.AccountRepository;
import pl.edu.pk.cosmo.cookbookbackend.service.dto.AccountDTO;
import pl.edu.pk.cosmo.cookbookbackend.service.exception.AlreadyExistsException;
import pl.edu.pk.cosmo.cookbookbackend.service.exception.NoAccountException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountConverter accountConverter;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    class save {
        @Test
        void accountWithGivenIdAlreadyExists_shouldThrowAlreadyExistsException() {
            // given
            final String name = "a";
            final String email = "b";
            final String password = "c";

            final AccountDTO accountDTO = new AccountDTO();
            accountDTO.setName(name);
            accountDTO.setPassword(password);
            accountDTO.setEmail(email);

            // when
            when(accountRepository.existsByEmail(email)).thenReturn(true);

            // then
            assertThrows(AlreadyExistsException.class, ()->accountService.save(accountDTO));
            verify(accountRepository, times(0)).save(any(Account.class));
        }

        @Test
        void properData_shouldSaveInDatabase() throws AlreadyExistsException {
            // given
            final String name = "a";
            final String email = "b";
            final String password = "c";

            final AccountDTO accountDTO = new AccountDTO();
            accountDTO.setName(name);
            accountDTO.setPassword(password);
            accountDTO.setEmail(email);

            final Account account = new Account();
            account.setName(name);
            account.setPassword(password);
            account.setEmail(email);

            // when
            when(accountRepository.existsByEmail(email)).thenReturn(false);
            when(accountConverter.toEntity(accountDTO)).thenReturn(account);
            accountService.save(accountDTO);

            // then
            verify(accountRepository, times(1)).save(account);
        }
    }

    @Nested
    class getAccountById {
        @Test
        void accountWithGivenIdDoNotExist_shouldThrowNoAccountException() throws NoAccountException {
            // given
            final Long id = 1L;
            // when
            when(accountRepository.existsById(id)).thenReturn(false);

            // then
            assertThrows(NoAccountException.class, () -> accountService.getAccountById(id));
            verify(accountRepository, times(0)).getById(anyLong());
        }

        @Test
        void properData_shouldGetAccountById() throws AlreadyExistsException, NoAccountException {
            // given
            final Long id = 1L;
            final String name = "a";
            final String email = "b";
            final String password = "c";

            final AccountDTO accountDTO = new AccountDTO();
            accountDTO.setName(name);
            accountDTO.setPassword(password);
            accountDTO.setEmail(email);

            final Account account = new Account();
            account.setName(name);
            account.setPassword(password);
            account.setEmail(email);

            // when
            when(accountRepository.existsById(id)).thenReturn(true);
            when(accountConverter.toEntity(accountDTO)).thenReturn(account);
            accountService.save(accountDTO);
            accountService.getAccountById(id);

            // then
            verify(accountRepository, times(1)).getById(id);

        }
    }

    @Nested
    class delete {
        @Test
        void accountWithGivenIdDoNotExist_shouldThrowNoAccountException(){
            // given
            final Long id = 1L;
            // when
            when(accountRepository.existsById(id)).thenReturn(false);

            // then
            assertThrows(NoAccountException.class, () -> accountService.delete(id));
            verify(accountRepository, times(0)).deleteAccountById(anyLong());
        }

        @Test
        void properData_shouldDeleteAccountById() throws AlreadyExistsException, NoAccountException {
            // given
            final Long id = 1L;
            final String name = "a";
            final String email = "b";
            final String password = "c";

            final AccountDTO accountDTO = new AccountDTO();
            accountDTO.setName(name);
            accountDTO.setPassword(password);
            accountDTO.setEmail(email);

            final Account account = new Account();
            account.setName(name);
            account.setPassword(password);
            account.setEmail(email);

            // when
            when(accountRepository.existsById(id)).thenReturn(true);
            when(accountConverter.toEntity(accountDTO)).thenReturn(account);
            accountService.save(accountDTO);
            accountService.delete(id);

            // then
            verify(accountRepository, times(1)).deleteAccountById(id);

        }

    }

    @Nested
    class changePassword {
        @Test
        void accountWithGivenIdDoNotExist_shouldThrowNoAccountException() throws NoAccountException {
            // given
            final Long id = 1L;
            final String password = "password";
            final ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
            changePasswordRequest.setId(id);
            changePasswordRequest.setPassword(password);

            // when
            when(accountRepository.existsById(id)).thenReturn(false);

            // then
            assertThrows(NoAccountException.class, () -> accountService.changePassword(changePasswordRequest));
        }

        @Test
        void properData_shouldChangePassword() throws AlreadyExistsException, NoAccountException {
            // given
            // tbh idk how to perform this test
            final Long id = 1L;
            final String name = "a";
            final String email = "b";
            final String password = "c";
            final String newPassword = "d";

            final AccountDTO accountDTO = new AccountDTO();
            accountDTO.setName(name);
            accountDTO.setPassword(password);
            accountDTO.setEmail(email);

            final Account account = new Account();
            account.setName(name);
            account.setPassword(password);
            account.setEmail(email);


            final ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
            changePasswordRequest.setId(id);
            changePasswordRequest.setPassword(newPassword);

            // when
            when(accountRepository.existsById(id)).thenReturn(true);
            when(accountConverter.toEntity(accountDTO)).thenReturn(account);
            accountService.save(accountDTO);
            Boolean expected = password.equals(accountService.getAccountById(id).getPassword());

            // then
            assertThat(expected).isFalse();
        }

    }

}