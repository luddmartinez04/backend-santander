import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dozer.DozerBeanMapper;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import santander.model.bank.Bank;
import santander.model.bank.BankRequest;
import santander.model.bank.BankResponse;
import santander.repository.BankJpaRepository;
import santander.service.BankService;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;


@RunWith(SpringRunner.class)
public class TestBankService {


    @Mock
    private BankJpaRepository bankJpaRepository;

    @Mock
    private DozerBeanMapper dozerBeanMapper;

    @InjectMocks
    private BankService bankService;


    @Test
    public void saveBank() throws IOException {
        Bank bank = new Bank();
        bank.setId(1L);
        bank.setCuit("222222");
        bank.setName("SANTANDER");
        bank.setPhoneNumber("phone");
        bank.setEmail("santanderEmail");
        bank.setCreateDate(new Date());
        bank.setUpdateDate(new Date());

        ObjectMapper mapper = new ObjectMapper();
        BankRequest bankRequest = mapper.readValue(
                new File("src/test/resources/json/bankRequest.json"),
                BankRequest.class
        );

        BankResponse bankResponse = mapper.readValue(
                new File("src/test/resources/json/bankResponse.json"),
                BankResponse.class
        );

        when(dozerBeanMapper.map(any(BankRequest.class), eq(Bank.class))).thenReturn(bank);
        when(bankJpaRepository.save(any(Bank.class))).thenReturn(bank);
        when(dozerBeanMapper.map(any(Bank.class), eq(BankResponse.class))).thenReturn(bankResponse);

        BankResponse bankResponse_result = bankService.createBank(bankRequest);
        assertEquals("SANTANDER", bankResponse_result.getName());
        assertEquals("222222", bankResponse_result.getCuit());
        assertEquals("phone", bankResponse_result.getPhoneNumber());
        assertEquals("santanderEmail", bankResponse_result.getEmail());
    }

    @Test
    public void getAllBanks() throws IOException {
        Bank bank = new Bank();
        bank.setId(1L);
        bank.setCuit("222222");
        bank.setName("SANTANDER");
        bank.setPhoneNumber("phone");
        bank.setEmail("santanderEmail");
        bank.setCreateDate(new Date());
        bank.setUpdateDate(new Date());

        Bank bank2 = new Bank();
        bank.setId(2L);
        bank.setCuit("33333");
        bank.setName("GALICIA");
        bank.setPhoneNumber("phone");
        bank.setEmail("galiciaEmail");
        bank.setCreateDate(new Date());
        bank.setUpdateDate(new Date());

        ObjectMapper mapper = new ObjectMapper();

        List<BankResponse> bankResponseList = mapper.readValue(
                new File("src/test/resources/json/banksAllResponse.json"),
                new TypeReference<>() {}
        );

        when(bankJpaRepository.findAll()).thenReturn(Arrays.asList(bank, bank2));
        when(dozerBeanMapper.map(bank, BankResponse.class)).thenReturn(bankResponseList.get(0));
        when(dozerBeanMapper.map(bank2, BankResponse.class)).thenReturn(bankResponseList.get(1));

        List<BankResponse> result = bankService.findAllBanks();

        assertEquals(2, result.size());
  }

    @Test
    public void getBank() throws IOException {
        Bank bank = new Bank();
        bank.setId(1L);
        bank.setCuit("222222");
        bank.setName("SANTANDER");
        bank.setPhoneNumber("phone");
        bank.setEmail("santanderEmail");
        bank.setCreateDate(new Date());
        bank.setUpdateDate(new Date());

        ObjectMapper mapper = new ObjectMapper();
        BankResponse bankResponse = mapper.readValue(
                new File("src/test/resources/json/bankResponse.json"),
                BankResponse.class
        );

        when(bankJpaRepository.findById(1L)).thenReturn(Optional.of(bank));
        when(dozerBeanMapper.map(bank, BankResponse.class)).thenReturn(bankResponse);

        BankResponse bankResponse_result = bankService.getBankById(1L);

        assertEquals("SANTANDER", bankResponse_result.getName());
        assertEquals("222222", bankResponse_result.getCuit());
        assertEquals("phone", bankResponse_result.getPhoneNumber());
        assertEquals("santanderEmail", bankResponse_result.getEmail());
    }

    @Test
    public void deleteBank() throws IOException {
        Bank bank = new Bank();
        bank.setId(1L);
        bank.setCuit("222222");
        bank.setName("SANTANDER");
        bank.setPhoneNumber("phone");
        bank.setEmail("santanderEmail");
        bank.setCreateDate(new Date());
        bank.setUpdateDate(new Date());

        ObjectMapper mapper = new ObjectMapper();
        BankResponse bankResponse = mapper.readValue(
                new File("src/test/resources/json/bankResponse.json"),
                BankResponse.class
        );

        when(bankJpaRepository.findById(1L)).thenReturn(Optional.of(bank));
        when(dozerBeanMapper.map(bank, BankResponse.class)).thenReturn(bankResponse);

        BankResponse bankResponse_result = bankService.deleteBankById(1L);

        assertEquals("SANTANDER", bankResponse_result.getName());
        assertEquals("222222", bankResponse_result.getCuit());
        assertEquals("phone", bankResponse_result.getPhoneNumber());
        assertEquals("santanderEmail", bankResponse_result.getEmail());
    }


        @Test
        public void updateBank() throws IOException {
            Bank bank = new Bank();
            bank.setId(1L);
            bank.setCuit("333333");
            bank.setName("GALICIA");
            bank.setPhoneNumber("phone_galicia");
            bank.setEmail("galicia_email");
            bank.setCreateDate(new Date());
            bank.setUpdateDate(new Date());

            Bank bankUpdate = new Bank();
            bankUpdate.setName("SANTANDER");
            bankUpdate.setCuit("222222");
            bankUpdate.setPhoneNumber("phone");
            bankUpdate.setEmail("santanderEmail");

            BankRequest bankRequest = new BankRequest();
            bankRequest.setName("SANTANDER");
            bankRequest.setCuit("222222");
            bankRequest.setPhoneNumber("phone");
            bankRequest.setEmail("santanderEmail");

            when(bankJpaRepository.findById(1L)).thenReturn(Optional.of(bank));
            when(bankJpaRepository.save(any(Bank.class))).thenReturn(bankUpdate);
            when(dozerBeanMapper.map(bankRequest, BankRequest.class)).thenReturn(bankRequest);

            BankResponse banResponse_result = bankService.updateBank(bankRequest, 1L);
            assertEquals("SANTANDER", banResponse_result.getName());
            assertEquals("222222", banResponse_result.getCuit());
            assertEquals("phone", banResponse_result.getPhoneNumber());
            assertEquals("santanderEmail", banResponse_result.getEmail());

        }
    }
