package com.example.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }



    public Optional<Account> register(Account account) {

        if(accountRepository.findAllByUsername(account.getUsername()).isPresent()){
            return Optional.empty();
        }

        return Optional.of(accountRepository.save(account));
        
    }



    public Optional<Account> login(Account account) {
        Optional<Account> existingAccount = accountRepository.findOneByUsernameAndPassword(account.getUsername(), account.getPassword());
        return existingAccount;
    }
}
