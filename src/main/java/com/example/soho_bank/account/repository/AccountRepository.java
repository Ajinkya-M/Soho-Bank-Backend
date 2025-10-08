package com.example.soho_bank.account.repository;

import com.example.soho_bank.account.model.Account;
import com.example.soho_bank.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(Long accountNumber);

    boolean existsByAccountNumber(Long accountNumber);

    List<Account> findByUser(User user);

    @Query(value = "SELECT nextval('account_number_seq')", nativeQuery = true)
    Long getNextAccountNumber();
}