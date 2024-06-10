package com.tecbank.account.query.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.tecbank.account.common.dto.AccountType;
import com.tecbank.cqrs.core.domain.BaseEntity;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class BankAccount extends BaseEntity {
    @Id
    private String id;
    private String accountHolder;
    private Date creationDate;
    private AccountType accountType;
    private double balance;

}
