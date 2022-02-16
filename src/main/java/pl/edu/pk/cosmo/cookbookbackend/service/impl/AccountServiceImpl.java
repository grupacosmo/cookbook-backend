package pl.edu.pk.cosmo.cookbookbackend.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pk.cosmo.cookbookbackend.controller.request.ChangePasswordRequest;
import pl.edu.pk.cosmo.cookbookbackend.converter.AccountConverter;
import pl.edu.pk.cosmo.cookbookbackend.repository.Entity.Account;
import pl.edu.pk.cosmo.cookbookbackend.repository.interfaces.AccountRepository;
import pl.edu.pk.cosmo.cookbookbackend.service.dto.AccountDTO;
import pl.edu.pk.cosmo.cookbookbackend.service.exception.AlreadyExistsException;
import pl.edu.pk.cosmo.cookbookbackend.service.exception.NoAccountException;
import pl.edu.pk.cosmo.cookbookbackend.service.interfaces.AccountService;

import javax.validation.constraints.NotNull;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountConverter accountConverter;

    public AccountServiceImpl(AccountRepository accountRepository, AccountConverter accountConverter) {
        this.accountRepository = accountRepository;
        this.accountConverter = accountConverter;
    }

    @Override
    public void save(@NotNull AccountDTO accountDTO) throws AlreadyExistsException {
        if (accountRepository.existsByEmail(accountDTO.getEmail())) {
            throw new AlreadyExistsException(
                    String.format("Account with email: '%s' already exists", accountDTO.getEmail())
            );
        }

        accountRepository.save(accountConverter.toEntity(accountDTO));
    }

    @Override
    public Account getAccountById(Long accountId) throws NoAccountException {
        if(!accountRepository.existsById(accountId)) {
            throw new NoAccountException(
                    String.format("Account with id: '%d' does not exist!", accountId)
            );
        }

        return accountRepository.getById(accountId);
    }

    @Override
    public void delete(Long accountId) throws NoAccountException {
        if (!accountRepository.existsById(accountId)) {
            throw new NoAccountException(
                    String.format("Account with id: '%d' does not exist!", accountId)
            );
        }

        accountRepository.deleteAccountById(accountId);
    }

    @Override
    public void changePassword(@NotNull ChangePasswordRequest changePasswordRequest) throws NoAccountException{

        Long id = changePasswordRequest.getId();
        String newPassword = changePasswordRequest.getPassword();

        if (!accountRepository.existsById(id)) {
            throw new NoAccountException(
                    String.format("Account with id: '%d' does not exist!", id)
            );
        }
        Account account = accountRepository.findAccountById(id);
        account.setPassword(newPassword);
    }
}
