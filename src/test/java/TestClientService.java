import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.checkerframework.checker.units.qual.C;
import org.dozer.DozerBeanMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import santander.model.bank.Bank;
import santander.model.bank.BankRequest;
import santander.model.bank.BankResponse;
import santander.model.client.Client;
import santander.model.client.ClientRequest;
import santander.model.client.ClientResponse;
import santander.repository.BankJpaRepository;
import santander.repository.ClientJpaRepository;
import santander.service.BankService;
import santander.service.ClientService;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class TestClientService {

    @Mock
    private ClientJpaRepository clientJpaRepository;
    @Mock
    private BankJpaRepository bankJpaRepository;

    @Mock
    private DozerBeanMapper dozerMapper;

    @InjectMocks
    private ClientService clientService;

    @Test
    public void saveClient() throws IOException {

        Bank bank = new Bank();
        bank.setId(1L);
        bank.setCuit("222222");
        bank.setName("SANTANDER");
        bank.setPhoneNumber("phone");
        bank.setEmail("santanderEmail");
        bank.setCreateDate(new Date());
        bank.setUpdateDate(new Date());

        Client client = new Client();
        client.setId(1L);
        client.setName("PEPE");
        client.setDni("12345678");
        client.setEmail("pepe@gmail.com");
        client.setIdChannel("onlinebanking");
        client.setBank(bank);

        ObjectMapper mapper = new ObjectMapper();
        ClientRequest clientRequest = mapper.readValue(
                new File("src/test/resources/json/clientRequest.json"),
                ClientRequest.class
        );

        ClientResponse clientResponse = mapper.readValue(
                new File("src/test/resources/json/clientResponse.json"),
                ClientResponse.class
        );

        when(bankJpaRepository.findById(1L)).thenReturn(Optional.of(bank));
        when(dozerMapper.map(any(ClientRequest.class), eq(Client.class))).thenReturn(client);
        when(clientJpaRepository.save(any(Client.class))).thenReturn(client);
        when(dozerMapper.map(any(Client.class), eq(ClientResponse.class))).thenReturn(clientResponse);

        ClientResponse clientResponse_result = clientService.createClient(clientRequest, bank.getId());
        assertEquals("PEPE", clientResponse_result.getName());
        assertEquals("12345678", clientResponse_result.getDni());
        assertEquals("pepe@gmail.com", clientResponse_result.getEmail());
        assertEquals("onlinebanking", clientResponse_result.getIdChannel());
        assertEquals(1L, clientResponse_result.getBankId().longValue());

    }


    @Test
    public void getAllClients() throws IOException {
        Bank bank = new Bank();
        bank.setId(1L);
        bank.setCuit("222222");
        bank.setName("SANTANDER");
        bank.setPhoneNumber("phone");
        bank.setEmail("santanderEmail");
        bank.setCreateDate(new Date());
        bank.setUpdateDate(new Date());

        Client client = new Client();
        client.setId(1L);
        client.setName("PEPE");
        client.setDni("12345678");
        client.setEmail("pepe@gmail.com");
        client.setIdChannel("onlinebanking");
        client.setBank(bank);

        Bank bank2 = new Bank();
        bank2.setId(2L);
        bank2.setCuit("333333");
        bank2.setName("GALICIA");
        bank2.setPhoneNumber("phone2");
        bank2.setEmail("galiciaMail");
        bank2.setCreateDate(new Date());
        bank2.setUpdateDate(new Date());

        Client client2 = new Client();
        client2.setId(2L);
        client2.setName("MARIA");
        client2.setDni("87654321");
        client2.setEmail("maria@gmail.com");
        client2.setIdChannel("onlinebanking");
        client2.setBank(bank2);

        ObjectMapper mapper = new ObjectMapper();

        List<ClientResponse> clientResponseList = mapper.readValue(
                new File("src/test/resources/json/clientsAllResponse.json"),
                new TypeReference<>() {}
        );

        when(clientJpaRepository.findAll()).thenReturn(Arrays.asList(client, client2));
        when(dozerMapper.map(client, ClientResponse.class)).thenReturn(clientResponseList.get(0));
        when(dozerMapper.map(client2, ClientResponse.class)).thenReturn(clientResponseList.get(1));

        List<ClientResponse> result = clientService.findAllClients();

        assertEquals(2, result.size());
    }


    @Test
    public void getClient() throws IOException {
        Bank bank = new Bank();
        bank.setId(1L);
        bank.setCuit("222222");
        bank.setName("SANTANDER");
        bank.setPhoneNumber("phone");
        bank.setEmail("santanderEmail");
        bank.setCreateDate(new Date());
        bank.setUpdateDate(new Date());

        Client client = new Client();
        client.setId(1L);
        client.setName("PEPE");
        client.setDni("12345678");
        client.setEmail("pepe@gmail.com");
        client.setIdChannel("onlinebanking");
        client.setBank(bank);

        ObjectMapper mapper = new ObjectMapper();
        ClientResponse clientResponse = mapper.readValue(
                new File("src/test/resources/json/clientResponse.json"),
                ClientResponse.class
        );

        when(clientJpaRepository.findById(1L)).thenReturn(Optional.of(client));
        when(dozerMapper.map(client, ClientResponse.class)).thenReturn(clientResponse);

        ClientResponse clientResponse_result = clientService.getClientById(1L);

        assertEquals("PEPE", clientResponse_result.getName());
        assertEquals("12345678", clientResponse_result.getDni());
        assertEquals("pepe@gmail.com", clientResponse_result.getEmail());
        assertEquals("onlinebanking", clientResponse_result.getIdChannel());
        assertEquals(1L, clientResponse_result.getBankId().longValue());
    }


    @Test
    public void deleteClient() throws IOException {
        Bank bank = new Bank();
        bank.setId(1L);
        bank.setCuit("222222");
        bank.setName("SANTANDER");
        bank.setPhoneNumber("phone");
        bank.setEmail("santanderEmail");
        bank.setCreateDate(new Date());
        bank.setUpdateDate(new Date());

        Client client = new Client();
        client.setId(1L);
        client.setName("PEPE");
        client.setDni("12345678");
        client.setEmail("pepe@gmail.com");
        client.setIdChannel("onlinebanking");
        client.setBank(bank);


        ObjectMapper mapper = new ObjectMapper();
        ClientResponse clientResponse = mapper.readValue(
                new File("src/test/resources/json/clientResponse.json"),
                ClientResponse.class
        );

        when(clientJpaRepository.findById(1L)).thenReturn(Optional.of(client));
        when(dozerMapper.map(client, ClientResponse.class)).thenReturn(clientResponse);

        ClientResponse clientResponse_result = clientService.deleteClient(1L);

        assertEquals("PEPE", clientResponse_result.getName());
        assertEquals("12345678", clientResponse_result.getDni());
        assertEquals("pepe@gmail.com", clientResponse_result.getEmail());
        assertEquals("onlinebanking", clientResponse_result.getIdChannel());
        assertEquals(1L, clientResponse_result.getBankId().longValue());
    }



    @Test
    public void updateClient() throws IOException {
        Bank bank = new Bank();
        bank.setId(1L);
        bank.setCuit("333333");
        bank.setName("GALICIA");
        bank.setPhoneNumber("phone_galicia");
        bank.setEmail("galicia_email");
        bank.setCreateDate(new Date());
        bank.setUpdateDate(new Date());

        Client client = new Client();
        client.setId(1L);
        client.setName("MARIA");
        client.setDni("87654321");
        client.setEmail("maria@gmail.com");
        client.setIdChannel("onlinebanking");
        client.setBank(bank);

        Client clientUpdate = new Client();
        clientUpdate.setName("PEPE");
        clientUpdate.setDni("12345678");
        clientUpdate.setEmail("pepe@gmail.com");
        clientUpdate.setIdChannel("onlinebanking");
        clientUpdate.setBank(bank);

        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setName("PEPE");
        clientRequest.setDni("12345678");
        clientRequest.setEmail("pepe@gmail.com");

        // Mock del repositorio
        when(clientJpaRepository.findById(1L)).thenReturn(Optional.of(client));
        when(clientJpaRepository.save(any(Client.class))).thenReturn(clientUpdate);

        ClientResponse clientResponse_result = clientService.updateClient(clientRequest, 1L);
        assertEquals("PEPE", clientResponse_result.getName());
        assertEquals("12345678", clientResponse_result.getDni());
        assertEquals("pepe@gmail.com", clientResponse_result.getEmail());
        assertEquals("onlinebanking", clientResponse_result.getIdChannel());
        assertEquals(1L, clientResponse_result.getBankId().longValue());

    }
}
