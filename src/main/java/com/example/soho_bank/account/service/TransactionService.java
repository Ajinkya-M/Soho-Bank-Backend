package com.example.soho_bank.account.service;

import com.example.soho_bank.account.dto.TransactionDtoMapper;
import com.example.soho_bank.account.dto.TransactionRequestDto;
import com.example.soho_bank.account.dto.TransactionResponseDto;
import com.example.soho_bank.account.model.Transaction;
import com.example.soho_bank.account.model.type.TransactionType;
import com.example.soho_bank.account.repository.AccountRepository;
import com.example.soho_bank.account.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor @Slf4j
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    public List<TransactionResponseDto> getTransactions() {
        return transactionRepository.findAll().stream()
                .map(TransactionDtoMapper::transactionToTransactionResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public TransactionResponseDto createTransaction(Long user, TransactionRequestDto transactionRequestDto) {
        // check if user owns this account
        var account = accountRepository.findByAccountNumber(transactionRequestDto.getAccountId()).orElseThrow();

        if (!account.getUser().getId().equals(user)) {
            throw new RuntimeException("Account does not belongs to user");
        }

        if (transactionRequestDto.getTransactionType() == TransactionType.DEPOSIT) {
            this.accountService.depositAmount(account.getAccountNumber(), user, transactionRequestDto.getAmount());
        }
        else {
            this.accountService.withDrawAmount(account.getAccountNumber(), user, transactionRequestDto.getAmount());
        }

        var transaction = Transaction.builder()
                .transactionType(transactionRequestDto.getTransactionType())
                .amount(transactionRequestDto.getAmount())
                .account(account)
                .build();

        var savedTransaction = transactionRepository.save(transaction);
        log.info("Saved transactions : {} at {}", savedTransaction.getId(), savedTransaction.getTimestamp());

        return TransactionDtoMapper.transactionToTransactionResponseDto(savedTransaction);
    }
}
