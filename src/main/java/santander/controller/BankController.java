package santander.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import santander.model.bank.BankRequest;
import santander.model.bank.BankResponse;
import santander.service.BankService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Tag(name = "Bank resources")
public class BankController {

    @Autowired
    private BankService bankService;
    private RestTemplate restTemplate;

    @Operation(summary = "create bank record in DB")
    @PostMapping("/create/bank")
    public ResponseEntity<BankResponse> addBank(@Valid @RequestBody BankRequest bankRequest) {
        BankResponse bankResponse = bankService.createBank(bankRequest);
        return new ResponseEntity<>(bankResponse, HttpStatus.OK);
    }

    @Operation(summary = "get all banks")
    @GetMapping("/banks")
    public List<BankResponse> getAllBanks() {
        return bankService.findAllBanks();
    }


    @Operation(summary = "get bank by id")
    @GetMapping("/bank/{id}")
    public BankResponse getBank(@PathVariable("id") Long id) {
        return bankService.getBankById(id);
    }


    @Operation(summary = "update bank by request body")
    @PutMapping("/update/bank/{id}")
    public BankResponse updateBank(@Valid @RequestBody() BankRequest bankRequest, @PathVariable("id") Long id) {
        return bankService.updateBank(bankRequest, id);
    }


    @Operation(summary = "delete bank by id")
    @DeleteMapping("/delete/bank/{id}")
    public BankResponse deleteBank(@PathVariable("id") Long id) {
        return bankService.deleteBankById(id);
    }

    @GetMapping("/call-internal")
    public List<BankResponse> getAllBanksInternally() {
        return bankService.findAllBanksInternally();
    }
}
