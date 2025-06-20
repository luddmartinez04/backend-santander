package santander.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ClientNotFoundException  extends RuntimeException{
    public ClientNotFoundException(){}

    public ClientNotFoundException(Long id){
        super("Not found client with ID: " + id);
    }
}
