package pl.edu.pk.cosmo.cookbookbackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.edu.pk.cosmo.cookbookbackend.controller.request.ChangePasswordRequest;
import pl.edu.pk.cosmo.cookbookbackend.controller.request.SaveAccountRequest;
import pl.edu.pk.cosmo.cookbookbackend.controller.response.AccountResponse;
import pl.edu.pk.cosmo.cookbookbackend.converter.AccountConverter;
import pl.edu.pk.cosmo.cookbookbackend.service.exception.AlreadyExistsException;
import pl.edu.pk.cosmo.cookbookbackend.service.exception.NoAccountException;
import pl.edu.pk.cosmo.cookbookbackend.service.interfaces.AccountService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;
    private final AccountConverter accountConverter;

    public AccountController(final AccountService accountService, AccountConverter accountConverter) {
        this.accountService = accountService;
        this.accountConverter = accountConverter;
    }


    @PostMapping()
    public void save(@RequestBody @Valid final SaveAccountRequest request) {
        try {
            accountService.save(accountConverter.toDTO(request));
        } catch (AlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public AccountResponse getAccountById(@PathVariable final Long id){
        try {
            return accountConverter.toAccountResponse(accountService.getAccountById(id));
        } catch (NoAccountException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable final Long id) {
        try {
            accountService.delete(id);
        } catch (NoAccountException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PatchMapping ("/password")
    public void changePassword(@RequestBody @Valid ChangePasswordRequest changePasswordRequest) {
        try {
            accountService.changePassword(changePasswordRequest);
        } catch (NoAccountException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
