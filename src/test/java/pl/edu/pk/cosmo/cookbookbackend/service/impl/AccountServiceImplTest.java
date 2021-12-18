package pl.edu.pk.cosmo.cookbookbackend.service.impl;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.edu.pk.cosmo.cookbookbackend.converter.AccountConverter;
import pl.edu.pk.cosmo.cookbookbackend.repository.interfaces.AccountRepository;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {
    @Mock
    private AccountRepository accountRepository;

    private AccountServiceImpl underTest;
    private AccountConverter accountConverter;
    private AutoCloseable autoCloseable;



}