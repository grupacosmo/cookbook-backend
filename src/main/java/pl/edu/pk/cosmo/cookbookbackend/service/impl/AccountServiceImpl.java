package pl.edu.pk.cosmo.cookbookbackend.service.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import pl.edu.pk.cosmo.cookbookbackend.converter.AccountConverter;
import pl.edu.pk.cosmo.cookbookbackend.repository.Entity.Account;
import pl.edu.pk.cosmo.cookbookbackend.repository.interfaces.AccountRepository;
import pl.edu.pk.cosmo.cookbookbackend.service.dto.AccountDTO;
import pl.edu.pk.cosmo.cookbookbackend.service.exception.AlreadyExistsException;
import pl.edu.pk.cosmo.cookbookbackend.service.exception.NoAccountException;
import pl.edu.pk.cosmo.cookbookbackend.service.interfaces.AccountService;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
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
    public void changePassword(Long accountId, String newPassword) throws NoAccountException {
        if (!accountRepository.existsById(accountId)) {
            throw new NoAccountException(
                    String.format("Account with id: '%d' does not exist!", accountId)
            );
        }
        Account account = accountRepository.findAccountById(accountId);
        account.setPassword(newPassword);
    }
}
