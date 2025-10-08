package com.example.soho_bank.account.dto;


import com.example.soho_bank.account.model.type.TransactionType;
import jakarta.transaction.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDto {

    private String accountId;

    private TransactionType transactionType;

    private BigDecimal amount;


}
