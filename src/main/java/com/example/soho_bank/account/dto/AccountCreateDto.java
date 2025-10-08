package com.example.soho_bank.account.dto;

import com.example.soho_bank.account.model.type.AccountType;
import com.example.soho_bank.account.model.type.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountCreateDto {
    private String accountNumber;

    private BigDecimal balance = new BigDecimal("0.00");

    private AccountType accountType;

    private Currency currency;
}
