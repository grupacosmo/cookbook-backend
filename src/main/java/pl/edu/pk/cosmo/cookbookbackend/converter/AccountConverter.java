package pl.edu.pk.cosmo.cookbookbackend.converter;

import org.springframework.stereotype.Component;
import pl.edu.pk.cosmo.cookbookbackend.controller.request.SaveAccountRequest;
import pl.edu.pk.cosmo.cookbookbackend.repository.Classes.JsonObject;
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

    public JsonObject toJSON(final Account account) {
        JsonObject map = new JsonObject();
        map.put("id", Long.toString(account.getId()));
        map.put("name", account.getName());
        map.put("email", account.getEmail());
        map.put("password", account.getPassword());
        return map;
    }
}
