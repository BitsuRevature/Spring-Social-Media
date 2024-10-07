package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    public Optional<Account> findAllByUsername(String username);

    public Optional<Account> findOneByUsernameAndPassword(String username, String password);

}
