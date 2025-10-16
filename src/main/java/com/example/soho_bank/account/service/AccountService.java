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

import java.math.BigDecimal;
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
        var generatedAccountNumber = this.accountRepository.getNextAccountNumber();
        var account = Account.builder()
                .accountNumber(generatedAccountNumber)
                .accountType(accountCreateDto.getAccountType())
                .balance(accountCreateDto.getBalance())
                .currency(accountCreateDto.getCurrency())
                .user(user)
                .build();

        log.info("Saving account for userId={}, accountNumber={}", userId, account.getAccountNumber());
        var savedAccount = accountRepository.save(account);

        user.getAccounts().add(account);

        return savedAccount.getAccountNumber().toString();
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

    public AccountResponseDto getAccount(Long userId, Long accountNumber) {

        Account account = this.accountRepository.findByAccountNumber(accountNumber).orElseThrow();

        if (account.getUser().getId().equals(userId)) {
            return AccountResponseDtoMapper.accountToAccountResponseDto(account);
        }
        throw new RuntimeException("Account access forbidden");

    }

    @Transactional
    public AccountResponseDto depositAmount(Long accountNumber, Long userId, BigDecimal amount) {
        Account account = this.accountRepository.findByAccountNumber(accountNumber).orElseThrow();
        if (!account.getUser().getId().equals(userId)) {
            throw new RuntimeException("Account access forbidden");
        }

        account.setBalance(account.getBalance().add(amount));

        accountRepository.save(account);

        var savedAccount = this.accountRepository.findByAccountNumber(accountNumber).orElseThrow();
        return AccountResponseDtoMapper.accountToAccountResponseDto(savedAccount);
    }

    @Transactional
    public AccountResponseDto withDrawAmount(Long accountNumber, Long userId, BigDecimal amount) {
        Account account = this.accountRepository.findByAccountNumber(accountNumber).orElseThrow();
        if (!account.getUser().getId().equals(userId)) {
            throw new RuntimeException("Account access forbidden");
        }

        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds for withdraw");
        }

        account.setBalance(account.getBalance().subtract(amount));

        accountRepository.save(account);

        var savedAccount = this.accountRepository.findByAccountNumber(accountNumber).orElseThrow();
        return AccountResponseDtoMapper.accountToAccountResponseDto(savedAccount);
    }

}
