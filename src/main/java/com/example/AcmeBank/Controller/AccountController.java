package com.example.AcmeBank.Controller;

import com.example.AcmeBank.DTO.BalanceDTO;
import com.example.AcmeBank.DTO.TransferRequestDTO;
import com.example.AcmeBank.DTO.TransferResponseDTO;
import com.example.AcmeBank.Error.CustomException;
import com.example.AcmeBank.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/account-manager/{accountNumber}/balance")
    public ResponseEntity<BalanceDTO> getBalance(
            @PathVariable String accountNumber
    ){
        try {
            return ResponseEntity.ok(accountService.getBalance(accountNumber));
        }
        catch(CustomException ex){
            throw new CustomException(ex.getMessage());
        }
    }

    @PostMapping("/account-manager/transfer")
    public ResponseEntity<TransferResponseDTO> transfer(
            @RequestBody TransferRequestDTO transferRequestDTO
            ){
        try {
            return ResponseEntity.ok(accountService.transferBalance(transferRequestDTO));
        }
        catch(CustomException ex){
            throw new CustomException(ex.getMessage());
        }
    }
}
