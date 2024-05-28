package com.example.AcmeBank.Model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="account")
@Getter
@Setter
public class Account {

    @Id
    @Column(name="account_number")
    private String accountNumber;

    @Column(name="balance")
    private Double balance;


}
