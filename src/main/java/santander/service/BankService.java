package santander.service;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santander.exceptions.BankNotFoundException;
import santander.exceptions.BankSaveErrorException;
import santander.model.bank.Bank;
import santander.model.bank.BankRequest;
import santander.model.bank.BankResponse;
import santander.model.client.Client;
import santander.model.client.ClientResponse;
import santander.repository.BankJpaRepository;
import santander.utils.HttpClient;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BankService {

    @Autowired
    private BankJpaRepository bankJpaRepository;

    @Autowired
    HttpClient httpClient;

    private final DozerBeanMapper dozerMapper = new DozerBeanMapper();

    public BankResponse createBank(BankRequest bankRequest) {
        Bank bank = dozerMapper.map(bankRequest, Bank.class);
        Bank bankSave = null;
        try {
            bank.setCreateDate(new Date());
            bank.setUpdateDate(new Date());
            bankSave = bankJpaRepository.save(bank);
        } catch (Exception e) {
            throw new BankSaveErrorException(String.format("error saving bank", e.getMessage()));
        }

        return dozerMapper.map(bankSave, BankResponse.class);
    }

    public List<BankResponse> findAllBanks() {
        return bankJpaRepository.findAll().stream().map(bank -> dozerMapper.map(bank, BankResponse.class)).collect(Collectors.toList());
    }


    public BankResponse getBankById(Long id) {
        return bankJpaRepository.findById(id).map(bank -> dozerMapper.map(bank, BankResponse.class)).orElseThrow(() -> new BankNotFoundException(id));
    }


    public BankResponse updateBank(BankRequest bankRequest, Long id) {
        Bank oldBank = bankJpaRepository.findById(id).orElseThrow(() -> new BankNotFoundException(id));

        Optional.ofNullable(bankRequest.getCuit()).ifPresent(oldBank::setCuit);
        Optional.ofNullable(bankRequest.getName()).ifPresent(oldBank::setName);
        Optional.ofNullable(bankRequest.getEmail()).ifPresent(oldBank::setEmail);
        Optional.ofNullable(bankRequest.getPhoneNumber()).ifPresent(oldBank::setPhoneNumber);
        Optional.of(new Date()).ifPresent(oldBank::setUpdateDate);


        Bank bankSave = bankJpaRepository.save(oldBank);

        return dozerMapper.map(bankSave, BankResponse.class);
    }

    public BankResponse deleteBankById(Long id) {
        Bank bank = bankJpaRepository.findById(id).orElseThrow(() -> new BankNotFoundException(id));
        bankJpaRepository.delete(bank);

        return dozerMapper.map(bank, BankResponse.class);
    }

    public List<BankResponse> findAllBanksInternally() {
        BankResponse[] banks = httpClient.callInternally(BankResponse[].class);
        return Arrays.asList(banks);
    }
}
