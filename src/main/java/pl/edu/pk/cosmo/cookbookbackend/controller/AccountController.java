package pl.edu.pk.cosmo.cookbookbackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.edu.pk.cosmo.cookbookbackend.controller.request.SaveAccountRequest;
import pl.edu.pk.cosmo.cookbookbackend.converter.AccountConverter;
import pl.edu.pk.cosmo.cookbookbackend.repository.Classes.JsonObject;
import pl.edu.pk.cosmo.cookbookbackend.service.exception.AlreadyExistsException;
import pl.edu.pk.cosmo.cookbookbackend.service.exception.NoAccountException;
import pl.edu.pk.cosmo.cookbookbackend.service.interfaces.AccountService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AccountController {

    private final AccountService accountService;
    private final AccountConverter accountConverter;

    public AccountController(final AccountService accountService, AccountConverter accountConverter) {
        this.accountService = accountService;
        this.accountConverter = accountConverter;
    }

    // TESTED
    @PostMapping("/add")
    public void save(@RequestBody @Valid final SaveAccountRequest request) {
        try {
            accountService.save(accountConverter.toDTO(request));
        } catch (AlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    // TESTED
    @PostMapping("/account")
    public JsonObject getAccountById(@RequestParam(value = "id", required = true) @Valid final Long accountId){
        try {
            return accountConverter.toJSON(accountService.getAccountById(accountId));
        } catch (NoAccountException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


    @PostMapping("/delete")
    public void delete(@RequestParam("id") @Valid final Long accountId) {
        try {
            accountService.delete(accountId);
        } catch (NoAccountException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PostMapping("/changePassword")
    public void changePassword(@RequestParam("id") @Valid final Long accountId, @RequestParam("password") @Valid final String newPassword) {
        try {
            accountService.changePassword(accountId, newPassword);
        } catch (NoAccountException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }
}
