package santander.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BankNotFoundException  extends RuntimeException{
    public BankNotFoundException(){}

    public BankNotFoundException(Long id){
        super("Not found bank with ID: " + id);
    }
}
