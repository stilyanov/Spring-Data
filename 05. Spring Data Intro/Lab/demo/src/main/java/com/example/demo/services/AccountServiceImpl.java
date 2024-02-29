package com.example.demo.services;

import com.example.demo.models.Account;
import com.example.demo.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void withdrawMoney(BigDecimal money, Long id) {
        Optional<Account> optional = this.accountRepository.findById(id);

        if (optional.isPresent()) {
            Account account = optional.get();

            if (account.getBalance().compareTo(money) >= 0) {
                account.setBalance(account.getBalance().subtract(money));
                this.accountRepository.saveAndFlush(account);
            }
        }
    }

    @Override
    public void transferMoney(BigDecimal money, Long id) {
        Optional<Account> optional = this.accountRepository.findById(id);

        if (optional.isPresent()) {
            Account account = optional.get();

            if (money.doubleValue() > 0) {
                account.setBalance(account.getBalance().add(money));
                this.accountRepository.saveAndFlush(account);
            }
        }
    }
}
