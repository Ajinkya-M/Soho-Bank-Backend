package com.example.soho_bank.account.dto;

import com.example.soho_bank.account.model.Account;
import com.example.soho_bank.account.model.type.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseDto {

    private UUID id;

    private TransactionType transactionType;

    private BigDecimal amount;

    private LocalDateTime timestamp;

    private Long accountId;

}
