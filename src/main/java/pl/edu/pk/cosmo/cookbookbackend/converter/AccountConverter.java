package pl.edu.pk.cosmo.cookbookbackend.converter;

import org.springframework.stereotype.Component;
import pl.edu.pk.cosmo.cookbookbackend.controller.request.SaveAccountRequest;
import pl.edu.pk.cosmo.cookbookbackend.controller.response.AccountResponse;
import pl.edu.pk.cosmo.cookbookbackend.repository.Entity.Account;
import pl.edu.pk.cosmo.cookbookbackend.service.dto.AccountDTO;

import java.util.Optional;

@Component
public class AccountConverter {
    public  AccountDTO toDTO(SaveAccountRequest saveAccountRequest) {
        return Optional.ofNullable(saveAccountRequest)
                .map(request -> {
                    final AccountDTO accountDTo = new AccountDTO();
                    accountDTo.setId(null);
                    accountDTo.setName(request.getName());
                    accountDTo.setEmail(request.getEmail());
                    accountDTo.setPassword(request.getPassword());
                    return accountDTo;
                })
                .orElse(null);
    }

    public Account toEntity(final AccountDTO accountDTO) {
        return Optional.ofNullable(accountDTO)
                .map(accountMap -> {
                    final Account account = new Account();
                    account.setId(accountMap.getId());
                    account.setName(accountMap.getName());
                    account.setEmail(accountMap.getEmail());
                    account.setPassword(accountMap.getPassword());
                    return account;
                })
                .orElse(null);
    }

    public AccountResponse toAccountResponse(final Account account) {
        return Optional.ofNullable(account)
                .map(data -> {
                    final AccountResponse accountResponse = new AccountResponse();
                    accountResponse.setId(data.getId());
                    accountResponse.setName(data.getName());
                    accountResponse.setEmail(data.getEmail());
                    accountResponse.setPassword(data.getPassword());
                    return accountResponse;
                })
                .orElse(null);
    }
}
