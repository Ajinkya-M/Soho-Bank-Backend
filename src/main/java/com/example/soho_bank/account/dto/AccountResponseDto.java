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
public class AccountResponseDto {
    private Long id;
    private Long accountNumber;
    private BigDecimal balance;
    private AccountType accountType;
    private Currency currency;
}
