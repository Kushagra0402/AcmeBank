package com.example.AcmeBank.Service;

import com.example.AcmeBank.DTO.BalanceDTO;
import com.example.AcmeBank.DTO.TransferRequestDTO;
import com.example.AcmeBank.DTO.TransferResponseDTO;
import com.example.AcmeBank.Error.CustomException;
import com.example.AcmeBank.Model.Account;
import com.example.AcmeBank.Repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;


    @Override
    public BalanceDTO getBalance(String accountNumber){
        Account account=accountRepository.findByAccountNumber(accountNumber).orElseThrow(()->new CustomException("Account Not Found"));
        BalanceDTO balanceDTO=new BalanceDTO();
        balanceDTO.setAccountNumber(accountNumber);
        balanceDTO.setBalance(account.getBalance());
        return balanceDTO;
    }



    @Override
    public TransferResponseDTO transferBalance(TransferRequestDTO transferRequestDTO){
        String debitAccountNumber=transferRequestDTO.getDebitAccountNumber();
        String creditAccountNumber=transferRequestDTO.getCreditAccountNumber();
        Double amount=transferRequestDTO.getTransferAmount();
        Account debitAccount=accountRepository.findByAccountNumber(debitAccountNumber).orElseThrow(()->new CustomException("Debit Account Not Found"));
        Account creditAccount=accountRepository.findByAccountNumber(creditAccountNumber).orElseThrow(()->new CustomException("Credit Account Not Found"));
        Double balanceOfDebitAccount= getBalance(debitAccountNumber).getBalance();

        //Different checks before executing the transaction
        if(balanceOfDebitAccount<amount){
            throw new CustomException("Balance of debit account is not enough");
        }
        if(amount<=0.00){
            throw new CustomException("Transfer amount must not be less than or equal to 0.0");
        }
        if(debitAccountNumber.equals(creditAccountNumber)){
            throw new CustomException("Transfer cannot be done between same account numbers");
        }

        //Executing the transaction
        Boolean transactionResponse=doTransaction(debitAccount,creditAccount,amount);

        //Successful Transaction will return updated balances.
        if(transactionResponse){
            BalanceDTO debitAccountResponse=new BalanceDTO(debitAccount.getAccountNumber(), debitAccount.getBalance());
            BalanceDTO creditAccountResponse=new BalanceDTO(creditAccount.getAccountNumber(),creditAccount.getBalance());
            TransferResponseDTO transferResponseDTO=new TransferResponseDTO(debitAccountResponse,creditAccountResponse,amount);
            return transferResponseDTO;
        }
        else{
            throw new CustomException("Transfer Failed");
        }
    }


    //Transactional Annotation to only execute if every statement within the function executes, i.e. provide atomicity.
    @Transactional
    public Boolean doTransaction(
       Account debitAccount, Account creditAccount, Double amount
    ){
        try {

            //Updating the balances of accounts in the transaction.
            debitAccount.setBalance(debitAccount.getBalance() - amount);
            creditAccount.setBalance(creditAccount.getBalance() + amount);
            accountRepository.save(debitAccount);
            accountRepository.save(creditAccount);
            return true;
        }catch(Exception exception){
            return false;
        }
    }
}
