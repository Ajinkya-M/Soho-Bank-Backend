package com.example.soho_bank.account.model;

import com.example.soho_bank.account.model.type.AccountType;
import com.example.soho_bank.account.model.type.Currency;
import com.example.soho_bank.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Entity
@Data
@ToString(exclude = {"user"})
@EqualsAndHashCode(exclude = {"user"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // generate a account number from sequence defined in database called account_number_seq
    @Column(unique = true, nullable = false)
    private Long accountNumber;

    @Column(nullable = false, scale = 2)
    @Builder.Default
    private BigDecimal balance = BigDecimal.ZERO;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    private Currency currency;

    @CreationTimestamp
    private LocalDateTime createdAt;


    @UpdateTimestamp
    private LocalDateTime updatedAt;


    public void setBalance(BigDecimal balance) {
        if (balance == null) {
            this.balance = BigDecimal.ZERO;
        }
        else {
            this.balance = balance.setScale(2, RoundingMode.HALF_UP);
        }
    }
}
