package pl.edu.pk.cosmo.cookbookbackend.service.interfaces;

import pl.edu.pk.cosmo.cookbookbackend.controller.request.ChangePasswordRequest;
import pl.edu.pk.cosmo.cookbookbackend.repository.Entity.Account;
import pl.edu.pk.cosmo.cookbookbackend.service.dto.AccountDTO;
import pl.edu.pk.cosmo.cookbookbackend.service.exception.AlreadyExistsException;
import pl.edu.pk.cosmo.cookbookbackend.service.exception.NoAccountException;

import javax.validation.constraints.NotNull;

public interface AccountService {

    void save(@NotNull final AccountDTO toDTO) throws AlreadyExistsException;

    Account getAccountById(@NotNull final Long accountId) throws NoAccountException;

    void delete(@NotNull Long accountId) throws NoAccountException;

    void changePassword(@NotNull ChangePasswordRequest changePasswordRequest) throws NoAccountException;
}
