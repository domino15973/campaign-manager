package com.example.campaignmanagerbackend.service;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Getter
@Service
public class BalanceService {

    private BigDecimal balance;

    public BalanceService() {
        this.balance = new BigDecimal("1000.00");
    }

    public void deductBalance(BigDecimal amount) {
        if (balance.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance to create/update the campaign");
        }
        balance = balance.subtract(amount);
    }

    public void addBalance(BigDecimal amount) {
        balance = balance.add(amount);
    }
}
