import org.dozer.DozerBeanMapper;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import santander.model.bank.Bank;
import santander.model.bank.BankDto;
import santander.repository.BankJpaRepository;
import santander.service.BankService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;


@RunWith(SpringRunner.class)
public class testBankService {


    @Mock
    private BankJpaRepository bankJpaRepository;

    @Mock
    private DozerBeanMapper dozerBeanMapper;

    @InjectMocks
    private BankService bankService;

    @Test
    public void saveBank() {
        Bank bank = new Bank();
        bank.setId(1L);
        bank.setCuit("222222");
        bank.setName("SANTANDER");

        BankDto bankDto = new BankDto();
        bankDto.setId(1L);
        bankDto.setCuit("222222");
        bankDto.setName("SANTANDER");

        when(dozerBeanMapper.map(any(BankDto.class), eq(Bank.class))).thenReturn(bank);
        when(bankJpaRepository.save(any(Bank.class))).thenReturn(bank);
        when(dozerBeanMapper.map(any(Bank.class), eq(BankDto.class))).thenReturn(bankDto);

        BankDto bankDto_result = bankService.createBank(bankDto);
        assertEquals("SANTANDER", bankDto_result.getName());
    }


    @Test
    public void getAllBanks() {
        Bank bank = new Bank();
        bank.setId(1L);
        bank.setCuit("222222");
        bank.setName("SANTANDER");

        BankDto bankDto = new BankDto();
        bankDto.setId(1L);
        bankDto.setCuit("222222");
        bankDto.setName("SANTANDER");

        Bank bank2 = new Bank();
        bank2.setId(2L);
        bank2.setCuit("33333333");
        bank2.setName("GALICIA");

        BankDto bankDto2 = new BankDto();
        bankDto2.setId(2L);
        bankDto2.setCuit("33333333");
        bankDto2.setName("GALICIA");

        when(bankJpaRepository.findAll()).thenReturn(Arrays.asList(bank, bank2));
        when(dozerBeanMapper.map(bank, BankDto.class)).thenReturn(bankDto, bankDto2);

        List<BankDto> result = bankService.findAllBanks();

        assertEquals(2, result.size());
    }


    @Test
    public void getBank(){
        Bank bank = new Bank();
        bank.setId(1L);
        bank.setCuit("222222");
        bank.setName("SANTANDER");

        BankDto bankDto = new BankDto();
        bankDto.setId(1L);
        bankDto.setCuit("222222");
        bankDto.setName("SANTANDER");

        when(bankJpaRepository.findById(1L)).thenReturn(Optional.of(bank));
        when(dozerBeanMapper.map(bank, BankDto.class)).thenReturn(bankDto);

        BankDto bankDto_result = bankService.getBankById(1L);
        assertEquals("SANTANDER", bankDto_result.getName());
    }



    @Test
    public void deleteBank(){
        Bank bank = new Bank();
        bank.setId(1L);
        bank.setCuit("222222");
        bank.setName("SANTANDER");

        BankDto bankDto = new BankDto();
        bankDto.setId(1L);
        bankDto.setCuit("222222");
        bankDto.setName("SANTANDER");

        when(bankJpaRepository.findById(1L)).thenReturn(Optional.of(bank));
        when(dozerBeanMapper.map(bank, BankDto.class)).thenReturn(bankDto);

        BankDto bankDto_result = bankService.deleteBankById(1L);
        assertEquals("SANTANDER", bankDto_result.getName());
    }


    @Test
    public void updateBank(){
        Bank bank = new Bank();
        bank.setId(1L);
        bank.setCuit("222222");
        bank.setName("SANTANDER");

        BankDto bankDto = new BankDto();
        bankDto.setId(1L);
        bankDto.setCuit("222222");
        bankDto.setName("SANTANDER");

        Bank bankUpdate = new Bank();
        bankUpdate.setId(1L);

        bankUpdate.setName("GALICIA");
        bankUpdate.setCuit("333333333");

        BankDto bankDtoUpdate = new BankDto();
        bankDtoUpdate.setId(1L);
        bankDtoUpdate.setName("GALICIA");
        bankDtoUpdate.setCuit("333333333");

        when(bankJpaRepository.findById(1L)).thenReturn(Optional.of(bank));
        when(bankJpaRepository.save(any(Bank.class))).thenReturn(bankUpdate);
        when(dozerBeanMapper.map(bankDtoUpdate, BankDto.class)).thenReturn(bankDtoUpdate);

        BankDto bankDto_result = bankService.updateBank(bankDto);
        assertEquals("GALICIA", bankDto_result.getName());
        assertEquals("333333333", bankDto_result.getCuit());

    }
}
