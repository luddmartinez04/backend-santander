package santander.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class BankAllException extends RuntimeException{

    public BankAllException(String error, String message){

    }

    public BankAllException(String exception){
        super(exception);
    }
}
