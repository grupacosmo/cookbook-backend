package pl.edu.pk.cosmo.cookbookbackend.service.interfaces;

import pl.edu.pk.cosmo.cookbookbackend.repository.Entity.Account;
import pl.edu.pk.cosmo.cookbookbackend.service.dto.AccountDTO;
import pl.edu.pk.cosmo.cookbookbackend.service.exception.AlreadyExistsException;
import pl.edu.pk.cosmo.cookbookbackend.service.exception.NoAccountException;

import javax.validation.constraints.NotNull;

public interface AccountService {
    // save object
    void save(@NotNull final AccountDTO toDTO) throws AlreadyExistsException;

    // get object by id
    Account getAccountById(@NotNull final Long accountId) throws NoAccountException;

    // delete object
    void delete(@NotNull Long accountId) throws NoAccountException;

    void changePassword(@NotNull Long accountId, @NotNull String newPassword) throws NoAccountException;
}
