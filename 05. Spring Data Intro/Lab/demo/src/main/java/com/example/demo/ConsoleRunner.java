package com.example.demo;

import com.example.demo.models.Account;
import com.example.demo.models.User;
import com.example.demo.services.AccountService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final UserService userService;
    private final AccountService accountService;

    @Autowired
    public ConsoleRunner(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User("Pesho", 25);

        Account account = new Account(BigDecimal.valueOf(25.000));
        account.setUser(user);

        user.setAccounts(new ArrayList<>() {{
            add(account);
        }});

        userService.registerUser(user);

        accountService.withdrawMoney(BigDecimal.valueOf(20.000), account.getId());
        accountService.transferMoney(BigDecimal.valueOf(30.000), account.getId());
    }
}
