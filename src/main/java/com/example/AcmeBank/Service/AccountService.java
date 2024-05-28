package com.example.AcmeBank.Service;

import com.example.AcmeBank.DTO.BalanceDTO;
import com.example.AcmeBank.DTO.TransferRequestDTO;
import com.example.AcmeBank.DTO.TransferResponseDTO;

public interface AccountService {

    BalanceDTO getBalance(String accountNumber);

    TransferResponseDTO transferBalance(TransferRequestDTO transferRequestDTO);
}
