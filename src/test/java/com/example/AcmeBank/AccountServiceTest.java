package com.example.AcmeBank;

import com.example.AcmeBank.Controller.AccountController;
import com.example.AcmeBank.DTO.BalanceDTO;
import com.example.AcmeBank.DTO.TransferRequestDTO;
import com.example.AcmeBank.DTO.TransferResponseDTO;
import com.example.AcmeBank.Error.CustomException;
import com.example.AcmeBank.Model.Account;
import com.example.AcmeBank.Repository.AccountRepository;
import com.example.AcmeBank.Service.AccountService;
import com.example.AcmeBank.Service.AccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {


    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;



    @Test
    void testGetBalance_Success(){
        String accountNumber = "123456789";
        Double balance=1000.00;

        Account account=new Account();
        account.setBalance(balance);
        account.setAccountNumber(accountNumber);

        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(account));

        BalanceDTO response= accountService.getBalance(accountNumber);

        assertEquals(accountNumber,response.getAccountNumber());
        assertEquals(balance,response.getBalance());

    }


    @Test
    void testGetBalance_AccountNotFound(){
        String accountNumber="123456789";
        String errorMessage= "Account Not Found";

        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> accountService.getBalance(accountNumber));

        assertEquals(exception.getMessage(),errorMessage);
    }


    @Test
    public void testTransferBalance_Success() {
        // Mocking the necessary data
        String debitAccountNumber = "123456789";
        String creditAccountNumber = "987654321";
        Double transferAmount = 500.0;


        Account debitAccount = new Account();
        debitAccount.setAccountNumber(debitAccountNumber);
        debitAccount.setBalance(1000.00);

        Account creditAccount = new Account();
        creditAccount.setAccountNumber(creditAccountNumber);
        creditAccount.setBalance(2000.00);

        when(accountRepository.findByAccountNumber(debitAccountNumber)).thenReturn(Optional.of(debitAccount));
        when(accountRepository.findByAccountNumber(creditAccountNumber)).thenReturn(Optional.of(creditAccount));

        // Calling the method under test
        TransferRequestDTO request = new TransferRequestDTO(debitAccountNumber, creditAccountNumber, transferAmount);
        System.out.println("request is "+request);
        TransferResponseDTO response = accountService.transferBalance(request);
        System.out.println("response is "+response);
        // Assertions
        assertNotNull(response);
        assertEquals(debitAccountNumber, response.getDebitAccount().getAccountNumber());
        assertEquals(creditAccountNumber, response.getCreditAccount().getAccountNumber());
        assertEquals(transferAmount, response.getTransferAmount());

        // Verify the state after the transfer
        assertEquals(500.0, debitAccount.getBalance());
        assertEquals(2500.0, creditAccount.getBalance());
    }


    @Test
    public void testTransferBalance_InsufficientBalance() {
        // Mocking the necessary data
        String debitAccountNumber = "123456789";
        String creditAccountNumber = "987654321";
        Double transferAmount = 1500.0;
        String errorMessage="Balance of debit account is not enough";

        Account debitAccount = new Account();
        debitAccount.setAccountNumber(debitAccountNumber);
        debitAccount.setBalance(1000.00);


        Account creditAccount = new Account();
        creditAccount.setAccountNumber(creditAccountNumber);
        creditAccount.setBalance(2000.00);


        Mockito.when(accountRepository.findByAccountNumber(debitAccountNumber)).thenReturn(Optional.of(debitAccount));
        Mockito.when(accountRepository.findByAccountNumber(creditAccountNumber)).thenReturn(Optional.of(creditAccount));

        // Calling the method under test
        TransferRequestDTO request = new TransferRequestDTO(debitAccountNumber, creditAccountNumber, transferAmount);
        CustomException exception= assertThrows(CustomException.class, () -> accountService.transferBalance(request));

        assertEquals(errorMessage,exception.getMessage());

        // Verify that the balances remain unchanged
        assertEquals(1000.0, debitAccount.getBalance());
        assertEquals(2000.0, creditAccount.getBalance());
    }

    @Test
    public void testTransferBalance_TransferToSameAccount(){

        String debitAccountNumber = "123456789";
        String creditAccountNumber = "123456789";
        Double transferAmount = 1500.0;
        String errorMessage="Transfer cannot be done between same account numbers";

        Account debitAccount = new Account();
        debitAccount.setAccountNumber(debitAccountNumber);
        debitAccount.setBalance(3000.00);

       when(accountRepository.findByAccountNumber(debitAccountNumber)).thenReturn(Optional.of(debitAccount));

       TransferRequestDTO request = new TransferRequestDTO(debitAccountNumber, creditAccountNumber, transferAmount);

       CustomException exception=assertThrows(CustomException.class,()->accountService.transferBalance(request));

       assertEquals(errorMessage,exception.getMessage());

    }




}


