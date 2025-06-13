package santander.exceptions;

public class BankSaveErrorException extends RuntimeException{

    public BankSaveErrorException(){}

    public BankSaveErrorException(String exception){
        super(exception);
    }
}


