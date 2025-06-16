package santander.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import santander.model.client.ClientRequest;
import santander.model.client.ClientResponse;
import santander.service.ClientService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Tag(name = "client resources")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Operation(summary = "create client record in DB")
    @PostMapping("/create/client/:bankId")
    public ResponseEntity<ClientResponse> addClient(@Valid @RequestBody ClientRequest clientRequest, @RequestParam("bankId") Long bankId) {
        return new ResponseEntity<>(clientService.createClient(clientRequest, bankId), HttpStatus.CREATED);
    }

    @Operation(summary = "get all clients")
    @GetMapping("/clients")
    public ResponseEntity<List<ClientResponse>> getAllClients() {
        List<ClientResponse> clientResponseList = clientService.findAllClients();
        return new ResponseEntity<>(clientResponseList, HttpStatus.OK);
    }


    @Operation(summary = "get client by id")
    @GetMapping("/client/{id}")
    public ResponseEntity<ClientResponse> getClient(@PathVariable("id") Long id) {
        return new ResponseEntity<>(clientService.getClientById(id), HttpStatus.OK);
    }

    @Operation(summary = "update client by request body")
    @PutMapping("/update/client/{id}")
    public ResponseEntity<ClientResponse> updateClient(@Valid @RequestBody() ClientRequest clientRequest, @PathVariable("id") Long id) {
        return new ResponseEntity<>(clientService.updateClient(clientRequest, id), HttpStatus.OK);
    }


    @Operation(summary = "delete client by id")
    @DeleteMapping("/delete/client/{id}")
    public  ResponseEntity<ClientResponse> deleteClient(@PathVariable("id") Long id) {
        return new ResponseEntity<>(clientService.deleteClient(id), HttpStatus.OK);
    }
}
