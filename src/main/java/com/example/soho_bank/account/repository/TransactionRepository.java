package com.example.soho_bank.account.repository;

import com.example.soho_bank.account.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}