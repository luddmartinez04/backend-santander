package santander.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import santander.model.bank.BankDto;
import santander.service.BankService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Tag(name = "Bank resources")
public class BankController {

    @Autowired
    private BankService bankService;
    private RestTemplate restTemplate;

    @Operation(summary = "create in DB a bank")
    @PostMapping("/create/bank")
    public ResponseEntity<BankDto> addBank(@Valid @RequestBody BankDto bankDto) {
        BankDto bankDtoLoad = bankService.createBank(bankDto);
        return new ResponseEntity<BankDto>(bankDtoLoad, HttpStatus.OK);
    }

    @Operation(summary = "get all banks")
    @GetMapping("/banks")
    public List<BankDto> getAllBanks() {
        return bankService.findAllBanks();
    }


    @Operation(summary = "get bank by id")
    @GetMapping("/bank/{id}")
    public BankDto getBank(@PathVariable("id") Long id) {
        return bankService.getBankById(id);
    }


    @Operation(summary = "update bank by request body")
    @PutMapping("/update/bank")
    public BankDto updateBank(@RequestBody() BankDto bankDto) {
        return bankService.updateBank(bankDto);
    }


    @Operation(summary = "delete bank by id")
    @DeleteMapping("/delete/bank/{id}")
    public BankDto deleteBank(@PathVariable("id") Long id) {
        return bankService.deleteBankById(id);
    }

    @GetMapping("/call-internal")
    public List<BankDto> getAllBanksInternally() {
        return bankService.findAllBanksInternally();
    }
}
