package com.example.soho_bank.account.model;

import com.example.soho_bank.account.model.type.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor @Builder
@Entity
public class Transaction {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    @Column(nullable = false, scale = 2)
    private BigDecimal amount;

    @CreationTimestamp
    private LocalDateTime timestamp;

    @ManyToOne()
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

}
