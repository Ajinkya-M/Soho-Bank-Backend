package com.example.soho_bank.account.controller;

import com.example.soho_bank.account.dto.AccountCreateDto;
import com.example.soho_bank.account.dto.AccountResponseDto;
import com.example.soho_bank.account.dto.TransactionRequestDto;
import com.example.soho_bank.account.service.AccountService;
import com.example.soho_bank.auth.common.AuthHelpers;
import com.example.soho_bank.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.boot.convert.PeriodUnit;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<String> createAccount(@Validated @RequestBody AccountCreateDto accountCreateDto) {
        var userId = AuthHelpers.getAuthenticatedUserId();
        return ResponseEntity.ok(this.accountService.createAccount(accountCreateDto, userId));
    }

    @GetMapping
    public ResponseEntity<?> getAccounts() {
        // if admin -> fetch all accounts
        if (AuthHelpers.isRoleAdmin()) {
            List<AccountResponseDto> allAccounts = this.accountService.getAllAccount();
            return ResponseEntity.ok(allAccounts);
        }

        // for User -> fetch only their accounts
        var userId = AuthHelpers.getAuthenticatedUserId();
        return ResponseEntity.ok(this.accountService.getAccountsForUserId(userId));
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountResponseDto> getAccount(@PathVariable Long accountNumber) {
        var userId = AuthHelpers.getAuthenticatedUserId();
        return ResponseEntity.ok(this.accountService.getAccount(userId, accountNumber));
    }

    @PostMapping("/{accountNumber}/transaction")
    public ResponseEntity<?> transaction(@RequestBody TransactionRequestDto transactionRequestDto) {
        return ResponseEntity.ok("Hi");
    }


}
