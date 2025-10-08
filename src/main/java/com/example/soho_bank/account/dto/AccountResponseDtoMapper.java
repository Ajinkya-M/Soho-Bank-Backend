package com.example.soho_bank.account.dto;


import java.util.List;
import java.util.stream.Collectors;

import com.example.soho_bank.account.model.Account;

public class AccountResponseDtoMapper {
    public static AccountResponseDto accountToAccountResponseDto(Account account) {
        return AccountResponseDto.builder()
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .accountType(account.getAccountType())
                .currency(account.getCurrency())
                .id(account.getId())
                .build();
    }

    public static List<AccountResponseDto> accountsToAccountResponseDtos(List<Account> accounts) {
        return accounts.stream()
                .map(AccountResponseDtoMapper::accountToAccountResponseDto)
                .collect(Collectors.toList());
    }
}
