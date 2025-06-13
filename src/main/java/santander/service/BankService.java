package santander.service;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santander.exceptions.BankNotFoundException;
import santander.exceptions.BankSaveErrorException;
import santander.model.bank.Bank;
import santander.model.bank.BankDto;
import santander.repository.BankJpaRepository;
import santander.utils.HttpClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankService {

    @Autowired
    private BankJpaRepository bankJpaRepository;

    @Autowired
    HttpClient httpClient;

    private final DozerBeanMapper dozerMapper = new DozerBeanMapper();

    public BankDto createBank(BankDto bankDto) {
        Bank bank = dozerMapper.map(bankDto, Bank.class);
        Bank bankSave = null;
        try {
            bankSave = bankJpaRepository.save(bank);
        } catch (Exception e) {
            throw new BankSaveErrorException(String.format("error saving bank id", bankDto.getId(), e.getMessage()));
        }

        return dozerMapper.map(bankSave, BankDto.class);
    }

    public List<BankDto> findAllBanks() {
        return bankJpaRepository.findAll().stream().map(bank -> dozerMapper.map(bank, BankDto.class)).collect(Collectors.toList());
    }


    public BankDto getBankById(Long id) {
        return bankJpaRepository.findById(id).map(bank -> dozerMapper.map(bank, BankDto.class)).orElseThrow(() -> new BankNotFoundException(id));
    }


    public BankDto updateBank(BankDto bankDto) {
        Bank oldBank = bankJpaRepository.findById(bankDto.getId()).orElseThrow(() -> new BankNotFoundException(bankDto.getId()));
        oldBank.setName(oldBank.getName());
        Bank bankSave = bankJpaRepository.save(oldBank);

        return dozerMapper.map(bankSave, BankDto.class);
    }


    public BankDto deleteBankById(Long id) {
        Bank bank = bankJpaRepository.findById(id).orElseThrow(() -> new BankNotFoundException(id));
        bankJpaRepository.delete(bank);

        return dozerMapper.map(bank, BankDto.class);
    }

    public List<BankDto> findAllBanksInternally() {
        BankDto[] bankDtos = httpClient.callInternally(BankDto[].class);
        return Arrays.asList(bankDtos);
    }
}
