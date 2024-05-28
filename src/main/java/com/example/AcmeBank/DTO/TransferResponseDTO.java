package com.example.AcmeBank.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferResponseDTO {

    private BalanceDTO debitAccount;

    private BalanceDTO creditAccount;

    private Double transferAmount;

}
