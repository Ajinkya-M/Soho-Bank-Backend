package com.example.soho_bank.account.dto;

import com.example.soho_bank.account.model.Transaction;

public class TransactionDtoMapper {

    public static TransactionResponseDto transactionToTransactionResponseDto(Transaction transaction) {
        return TransactionResponseDto.builder()
                .id(transaction.getId())
                .accountId(transaction.getAccount().getAccountNumber())
                .transactionType(transaction.getTransactionType())
                .timestamp(transaction.getTimestamp())
                .amount(transaction.getAmount())
                .build();
    }

}
