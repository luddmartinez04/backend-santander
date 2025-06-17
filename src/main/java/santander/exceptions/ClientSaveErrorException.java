package santander.exceptions;

public class ClientSaveErrorException extends RuntimeException{

    public ClientSaveErrorException(){}

    public ClientSaveErrorException(String exception){
        super(exception);
    }
}


