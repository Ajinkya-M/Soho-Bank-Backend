package com.example.soho_bank.account.service;

import com.example.soho_bank.account.dto.AccountCreateDto;
import com.example.soho_bank.account.dto.AccountResponseDto;
import com.example.soho_bank.account.dto.AccountResponseDtoMapper;
import com.example.soho_bank.account.model.Account;
import com.example.soho_bank.account.repository.AccountRepository;
import com.example.soho_bank.common.UserNotFoundException;
import com.example.soho_bank.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;


    @Transactional
    public String createAccount(AccountCreateDto accountCreateDto, Long userId) {
        var user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        var account = Account.builder()
                .accountNumber(accountCreateDto.getAccountNumber())
                .accountType(accountCreateDto.getAccountType())
                .balance(accountCreateDto.getBalance())
                .currency(accountCreateDto.getCurrency())
                .user(user)
                .build();

        log.info("Saving account for userId={}, accountNumber={}", userId, account.getAccountNumber());
        accountRepository.save(account);

        var savedAccount = accountRepository.findByAccountNumber(accountCreateDto.getAccountNumber()).orElseThrow(() -> new RuntimeException("Account not found"));

        user.getAccounts().add(account);

        return savedAccount.getAccountNumber();
    }

    public List<AccountResponseDto> getAccountsForUserId(Long userId) {
        var user = this.userRepository.findById(userId).orElseThrow();
        var accounts = accountRepository.findByUser(user);
        return AccountResponseDtoMapper.accountsToAccountResponseDtos(accounts);
    }

    public List<AccountResponseDto> getAllAccount() {
        List<Account> accounts = this.accountRepository.findAll();
        return AccountResponseDtoMapper.accountsToAccountResponseDtos(accounts);
    }

}
