package com.example.AcmeBank.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequestDTO {
    private String debitAccountNumber;

    private String creditAccountNumber;

    private Double transferAmount;
}
