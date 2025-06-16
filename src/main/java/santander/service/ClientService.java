package santander.service;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santander.exceptions.BankNotFoundException;
import santander.exceptions.ClientNotFoundException;
import santander.model.bank.Bank;
import santander.model.client.Client;
import santander.model.client.ClientRequest;
import santander.model.client.ClientResponse;
import santander.repository.BankJpaRepository;
import santander.repository.ClientJpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {
    @Autowired
    private ClientJpaRepository clientJpaRepository;

    @Autowired
    private BankJpaRepository bankJpaRepository;

    private final DozerBeanMapper dozerMapper = new DozerBeanMapper();

    public ClientResponse createClient(ClientRequest clientRequest, Long bank_id) {

        Bank bank = bankJpaRepository.findById(bank_id).orElseThrow(BankNotFoundException::new);
        Client client = dozerMapper.map(clientRequest, Client.class);
        client.setBank(bank);
        Client clientSave = null;
        try {
            clientSave = clientJpaRepository.save(client);
        } catch (Exception e) {
            return null;
        }

        ClientResponse clientResponse = dozerMapper.map(clientSave, ClientResponse.class);
        clientResponse.setBankId(bank.getId());

        return clientResponse;
    }

    public List<ClientResponse> findAllClients() {
        return clientJpaRepository.findAll().stream()
                .map(client -> {
                    ClientResponse response = dozerMapper.map(client, ClientResponse.class);
                    response.setBankId(client.getBank().getId());
                    return response;
                })
                .collect(Collectors.toList());    }


    public ClientResponse getClientById(Long id) {
        Client client = clientJpaRepository.findById(id).orElseThrow(ClientNotFoundException::new);

        ClientResponse clientResponse = dozerMapper.map(client, ClientResponse.class);
        clientResponse.setBankId(client.getBank().getId());

        return clientResponse;
    }

    public ClientResponse updateClient(ClientRequest clientRequest, Long id) {
        Client oldClient = clientJpaRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(id));

        Optional.ofNullable(clientRequest.getName()).ifPresent(oldClient::setName);
        Optional.ofNullable(clientRequest.getIdChannel()).ifPresent(oldClient::setIdChannel);
        Optional.ofNullable(clientRequest.getDni()).ifPresent(oldClient::setDni);
        Optional.ofNullable(clientRequest.getEmail()).ifPresent(oldClient::setEmail);

        Client clientSave = clientJpaRepository.save(oldClient);
        ClientResponse clientResponse = dozerMapper.map(clientSave, ClientResponse.class);
        clientResponse.setBankId(clientSave.getBank().getId());

        return clientResponse;
    }


    public ClientResponse deleteClient(Long id) {
        Client client = clientJpaRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(id));
        clientJpaRepository.delete(client);

        ClientResponse clientResponse = dozerMapper.map(client, ClientResponse.class);
        clientResponse.setBankId(client.getBank().getId());

        return clientResponse;
    }
}
